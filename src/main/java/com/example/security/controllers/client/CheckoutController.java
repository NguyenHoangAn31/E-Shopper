package com.example.security.controllers.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.example.security.dto.order.OrderRequest;
import com.example.security.services.order.OrderService;
import com.example.security.services.payment.PaypalService;
import com.example.security.services.payment.VNPAYService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class CheckoutController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaypalService paypalService;

    @Autowired
    private VNPAYService vnpayService;

    double exchangeRate = 24000; // Ví dụ: 1 USD = 24,000 VND

    String cancelUrl = "http://localhost:8080/checkout?status=canceled";
    String successUrl = "http://localhost:8080?status=success";

    @PostMapping("/checkout")
    public String checkoutProcess(@RequestBody OrderRequest orderRequest, HttpServletRequest request) {

        try {
            if (orderRequest.getPaymentMethod().equals("VN Pay")) {
                String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
                String vnpayUrl = vnpayService.createOrder(request, (int) (orderRequest.getTotalPrice() * exchangeRate),
                        orderRequest.getDescription(), baseUrl);
                return vnpayUrl;

            } else if (orderRequest.getPaymentMethod().equals("Paypal")) {
                try {
                    System.out.println("create payment: " + orderRequest);
                    Payment payment = paypalService.createPayment(
                            orderRequest.getTotalPrice(),
                            "USD",
                            "paypal",
                            "sale",
                            orderRequest.getDescription(),
                            cancelUrl,
                            successUrl);
                    for (Links link : payment.getLinks()) {
                        if (link.getRel().equals("approval_url")) {
                            return link.getHref();
                        }
                    }
                } catch (PayPalRESTException e) {
                    e.printStackTrace();
                }
            }
            orderService.checkoutProcess(orderRequest);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }
    }

    @GetMapping("/vnpay/vnpay-payment-return")
    public RedirectView paymentCompleted(HttpServletRequest request) {
        int paymentStatus = vnpayService.orderReturn(request);

        request.getParameterMap()
                .forEach((key, value) -> System.out.println("Param: " + key + " = " + String.join(", ", value)));

        // Tạo URL để redirect về frontend
        String redirectUrl = "http://localhost:8080" + (paymentStatus == 1 ? "" : "/checkout") + "?status="
                + (paymentStatus == 1 ? "success" : "canceled");

        // Trả về RedirectView
        RedirectView redirectView = new RedirectView(redirectUrl);
        return redirectView;
    }

}
