package com.example.security.controllers.client;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.security.dto.ContactDto;
import com.example.security.dto.ProductVariant.ProductVariantResponse;
import com.example.security.dto.cart.CartDetailResponse;
import com.example.security.dto.cart.CartRequest;
import com.example.security.dto.order.OrderRequest;
import com.example.security.dto.product.ProductResponse;
import com.example.security.dto.user.UserRequestCreate;
import com.example.security.dto.user.UserResponse;
import com.example.security.services.cart.CartService;
import com.example.security.services.cartdetail.CartDetailService;
import com.example.security.services.category.CategoryService;
import com.example.security.services.contact.ContactService;
import com.example.security.services.passwordresettoken.PasswordResetTokenService;
import com.example.security.services.product.ProductService;
import com.example.security.services.productvariant.ProductVariantService;
import com.example.security.services.user.UserService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductVariantService productVariantService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartDetailService cartDetailService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("pageTitle", "Home");
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("top8Trandy", productService.getTop8Trandy());
        model.addAttribute("top8JustArrived", productService.getTop8JustArrived());

        return "client/index";
    }

    @GetMapping("/shop")
    public String shop(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search, 
            @RequestParam(required = false) String sort, 
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {

        Page<ProductResponse> productPage = productService.getAllProducts(page, size, category, search, sort);

        model.addAttribute("pageTitle", "Shop");
        model.addAttribute("category", category);
        model.addAttribute("search", search);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("search", search); 
        model.addAttribute("sort", sort); 

        return "client/shop";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("pageTitle", "About");
        return "client/about";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("pageTitle", "Contact");
        model.addAttribute("contact", new ContactDto());
        return "client/contact";
    }

    @PostMapping("/contact")
    public String handleContact(@Valid @ModelAttribute("contact") ContactDto dto, BindingResult bindingResult,
            Model model) throws MessagingException {

        if (bindingResult.hasErrors()) {
            return "client/contact";
        }

        contactService.sendEmailContact(dto);
        model.addAttribute("pageTitle", "Contact");
        model.addAttribute("message", "Your message has been sent successfully!");
        return "client/contact";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam(required = true) int id, Model model) {

        // chuyền product để lấy mô tả và review

        List<ProductVariantResponse> productsVariant = productVariantService.getAllProductVariantsBy(id);
        List<ProductResponse> related = productService.getAllProductsRelatedById(id);

        List<String> sizes = productsVariant.stream().map(ProductVariantResponse::getSize).distinct().toList();
        List<String> colors = productsVariant.stream().map(ProductVariantResponse::getColor).distinct().toList();

        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("productsVariant", productsVariant);
        model.addAttribute("sizes", sizes);
        model.addAttribute("colors", colors);
        model.addAttribute("related", related);
        model.addAttribute("pageTitle", "Product Detail");

        return "client/detail";
    }

    @GetMapping("/cart")
    public String cart(Model model, Authentication authentication) {
        model.addAttribute("pageTitle", "Cart");
        if (authentication != null && authentication.isAuthenticated()) {
            List<CartDetailResponse> cartItems = cartService.getCartByEmail(authentication.getName());
            model.addAttribute("carts", cartItems);
        }
        return "client/cart";
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<Integer> addToCart(@RequestBody CartRequest c, HttpServletRequest request) {
        HttpSession session = request.getSession();
        cartService.saveCart(userService.getIdUserByEmail(c.getEmail()), c.getProducts());
        int cartCount = cartService.getCountCartByEmail(c.getEmail());
        session.setAttribute("cart", cartCount);
        return ResponseEntity.ok(cartCount);
    }

    @GetMapping("/cart-action/{action}/{id}")
    public ResponseEntity<Map<String, Object>> cartAction(@PathVariable String action, @PathVariable int id,
            HttpServletRequest request) {
        Map<String, Object> result = cartDetailService.cartAction(action, id);
        HttpSession session = request.getSession();
        int cartCount = cartService.getCountCartByEmail(request.getUserPrincipal().getName());
        session.setAttribute("cart", cartCount);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/checkout")
    public String checkout(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            List<CartDetailResponse> cartItems = cartService.getCartByEmail(authentication.getName());
            model.addAttribute("carts", cartItems);
            UserResponse user = userService.getUser(authentication.getName());
            model.addAttribute("user", user);
        }
        model.addAttribute("pageTitle", "Checkout");
        model.addAttribute("order", new OrderRequest());
        return "client/checkout";
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {

        model.addAttribute("user", userService.getUser(authentication.getName()));
        model.addAttribute("pageTitle", "Profile");
        return "client/profile";
    }

    @PostMapping("/profile")
    public String handleUpdateProfile(@Valid @ModelAttribute("user") UserResponse dto, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("Errors: " + bindingResult.getAllErrors());
            return "client/profile";
        }
        userService.updateUser(dto);
        return "redirect:/profile";

    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> processForgotPassword(@RequestParam String email) {
        Map<String, String> response = new HashMap<>();
        try {
            passwordResetTokenService.sendResetPasswordEmail(email);
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "client/resetpassword";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam String token, @RequestParam String newPassword, Model model) {
        if (!passwordResetTokenService.resetPassword(token, newPassword)) {
            model.addAttribute("error", "Token is invalid or expired!");
            return "client/resetpassword";
        }

        model.addAttribute("message", "Password has been reset!");
        return "client/resetpassword";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("pageTitle", "Login");
        return "client/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("pageTitle", "Register");
        model.addAttribute("user", new UserRequestCreate());
        return "client/register";
    }

    @PostMapping("/register")
    public String handleRegister(@Valid @ModelAttribute("user") UserRequestCreate dto, BindingResult bindingResult,
            Model model) {
        System.out.println("User Details: " + dto);

        // Nếu có lỗi bắt buộc nhập, chỉ hiển thị lỗi này
        if (bindingResult.hasFieldErrors("email") || bindingResult.hasFieldErrors("name")
                || bindingResult.hasFieldErrors("password")) {
            return "client/register";
        }

        // Nếu không có lỗi required, tiếp tục kiểm tra các lỗi còn lại
        if (!userService.checkExistAccount(dto.getEmail())) {
            userService.register(dto);
            return "redirect:/login";
        }

        model.addAttribute("errorMessage", "Email is already registered!");
        return "client/register";
    }

}
