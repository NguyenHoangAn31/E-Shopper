package com.example.security.controllers.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.security.dto.ProductVariant.ProductVariantResponse;
import com.example.security.dto.product.ProductResponse;
import com.example.security.dto.user.UserRequestCreate;
import com.example.security.dto.user.UserResponse;
import com.example.security.services.category.CategoryService;
import com.example.security.services.product.ProductService;
import com.example.security.services.productvariant.ProductVariantService;
import com.example.security.services.user.UserService;

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
            @RequestParam(required = true) String category,
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size) {

        System.out.println("Filter: " + category);

        Page<ProductResponse> productPage = productService.getAllProducts(page, size, category);

        model.addAttribute("pageTitle", "Shop");
        model.addAttribute("category", category);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());

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
    public String cart(Model model) {
        model.addAttribute("pageTitle", "Cart");
        return "client/cart";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            // Đăng nhập bằng username/password
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            System.out.println("Đăng nhập bằng tài khoản: " + username);
            model.addAttribute("user", userService.getUser(username));

        } else if (authentication instanceof OAuth2AuthenticationToken) {
            // Đăng nhập bằng OAuth2
            OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
            String email = oauthUser.getAttribute("email"); // Lấy email

            System.out.println("Đăng nhập bằng OAuth2: " + email + " - " + email);
            model.addAttribute("user", userService.getUser(email));

        }

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

        if (bindingResult.hasErrors()) {
            // model.addAttribute("errorMessage", "Please correct the errors in the form.");
            return "client/register";
        }

        if (!userService.checkExistAccount(dto.getEmail())) {
            userService.register(dto);
            return "redirect:/login";
        }

        model.addAttribute("errorMessage", "Email is already registered!");
        return "client/register";
    }
}
