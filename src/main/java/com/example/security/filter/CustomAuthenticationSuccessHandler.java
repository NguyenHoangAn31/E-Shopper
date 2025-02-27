package com.example.security.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.example.security.services.cart.CartService;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final CartService cartService;

    public CustomAuthenticationSuccessHandler(@Lazy CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        HttpSession session = request.getSession();
        String email = authentication.getName();

        // Lấy số lượng giỏ hàng và lưu vào session
        int cartCount = cartService.getCountCartByEmail(email);
        session.setAttribute("cart", cartCount);

        // Chuyển hướng sau khi lưu xong
        response.sendRedirect("/?loginSuccess=true");
    }
}
