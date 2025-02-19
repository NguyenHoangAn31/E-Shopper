// package com.example.security.advice;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.web.bind.annotation.ControllerAdvice;
// import org.springframework.web.bind.annotation.ModelAttribute;

// import com.example.security.services.cart.CartService;

// import org.springframework.ui.Model;

// @ControllerAdvice
// public class GlobalControllerAdvice {

//     @Autowired
//     private CartService cartService;

//     @ModelAttribute
//     public void addCartToLayout(Model model) {
//         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//         if (authentication != null && authentication.isAuthenticated()
//                 && !"anonymousUser".equals(authentication.getName())) {
//             int count = cartService.getCountCartByEmail(authentication.getName());
//             model.addAttribute("cart", count);
//         }
//     }

// }