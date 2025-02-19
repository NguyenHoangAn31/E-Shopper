package com.example.security.controllers.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.example.security.dto.order.OrderRequest;
import com.example.security.entities.Order;
import com.example.security.services.order.OrderService;
import com.example.security.services.payment.PaypalService;
import com.example.security.services.payment.VNPAYService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

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

    String cancelUrl = "http://localhost:8080/api/paypal/cancel";
    String successUrl = "http://localhost:8080/api/paypal/success";

    Map<String, OrderRequest> orderMap = new HashMap<>();

    @PostMapping("/checkout")
    public String checkoutProcess(@Valid @RequestBody OrderRequest orderRequest, BindingResult bindingResult,
            HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessages.append(error.getDefaultMessage()).append("\n"));
            return "notblank";
        }
        try {
            if (orderRequest.getPaymentMethod().equals("VN Pay")) {

                String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
                Map<String, String> vnpayUrl = vnpayService.createOrder(request,
                        (int) (orderRequest.getTotalPrice() * exchangeRate),
                        orderRequest.getDescription(), baseUrl);
                String key = vnpayUrl.get("key");
                String paymentUrl = vnpayUrl.get("paymentUrl");
                orderMap.put(key, orderRequest);
                return paymentUrl;

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
                            orderMap.put(payment.getId(), orderRequest);
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

    @GetMapping("/paypal/success")
    public void successPay(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId,
            HttpServletResponse response) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                orderService.checkoutProcess(orderMap.get(paymentId));
                orderMap.remove(paymentId);
                response.sendRedirect("http://localhost:8080?orderstatus=success");
                return;
            }
        } catch (PayPalRESTException | IOException e) {
            e.printStackTrace();
        }
        try {
            response.sendRedirect("http://localhost:8080/checkout?orderstatus=failed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/paypal/cancel")
    public void paypalCancel(
            HttpServletResponse response) throws IOException {

        response.sendRedirect("http://localhost:8080/checkout?orderstatus=canceled");
    }

    @GetMapping("/vnpay/vnpay-payment-return")
    public RedirectView paymentCompleted(HttpServletRequest request) {
        int paymentStatus = vnpayService.orderReturn(request);

        String txnRef = request.getParameter("vnp_TxnRef");

        if (paymentStatus == 1) {
            OrderRequest o = orderMap.get(txnRef);
            orderService.checkoutProcess(o);
        }

        orderMap.remove(txnRef);

        // Tạo URL để redirect về frontend
        String redirectUrl = "http://localhost:8080" + (paymentStatus == 1 ? "" : "/checkout") + "?orderstatus="
                + (paymentStatus == 1 ? "success" : "canceled");

        // Trả về RedirectView
        RedirectView redirectView = new RedirectView(redirectUrl);
        return redirectView;
    }

}
