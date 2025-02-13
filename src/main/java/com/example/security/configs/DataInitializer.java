package com.example.security.configs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.example.security.entities.Category;
import com.example.security.entities.Role;
import com.example.security.entities.Product;
import com.example.security.entities.ProductImage;
import com.example.security.entities.ProductVariant;
import com.example.security.repositories.CategoryRepository;
import com.example.security.repositories.ProductImageRepository;
import com.example.security.repositories.RoleRepository;
import com.example.security.repositories.ProductRepository;
import com.example.security.repositories.ProductVariantRepositry;

@Configuration
public class DataInitializer {

        @Order(1)
        @Bean
        public CommandLineRunner initializeRoles(RoleRepository roleRepository) {
                return args -> {
                        if (roleRepository.count() == 0) {
                                roleRepository.save(new Role(0, "ROLE_ADMIN", "ADMIN", null));
                                roleRepository.save(new Role(0, "ROLE_USER  ", "USER", null));
                        }
                };
        }

        @Order(2)
        @Bean
        public CommandLineRunner initializeCategories(CategoryRepository categoryRepository) {
                return args -> {
                        if (categoryRepository.count() == 0) {
                                categoryRepository.save(new Category(0, "Dress", true, "product-1.jpg", null));
                                categoryRepository.save(new Category(0, "Shirts", true, "product-1.jpg", null));
                                categoryRepository.save(new Category(0, "Jeans", true, "product-1.jpg", null));
                                categoryRepository.save(new Category(0, "Swimwear", true, "product-1.jpg", null));
                                categoryRepository.save(new Category(0, "Sleepwear", true, "product-1.jpg", null));
                                categoryRepository.save(new Category(0, "Sportswear", true, "product-1.jpg", null));
                                categoryRepository.save(new Category(0, "Jackets", true, "product-1.jpg", null));
                                categoryRepository.save(new Category(0, "Shoes", true, "product-1.jpg", null));
                        }
                };
        }

        @Order(3)
        @Bean
        public CommandLineRunner initializeProducts(ProductRepository productRepository,
                        CategoryRepository categoryRepository, ProductImageRepository productImageRepository,
                        ProductVariantRepositry productVariantRepository) {

                return args -> {

                        List<String> colors = Arrays.asList("White", "Red", "Black", "Blue", "Green");
                        List<String> sizes = Arrays.asList("XS", "S", "M", "L", "XL");

                        List<Product> products = new ArrayList<>();
                        List<Category> categories = categoryRepository.findAll();

                        if (productRepository.count() == 0) {

                                products.add(new Product(0, "Floral Dress", "Elegant floral dress", true,
                                                 200, categories.get(0), null));
                                products.add(
                                                new Product(0, "Summer Dress", "Light and breezy summer dress", true,
                                                                 200, categories.get(0), null));
                                products.add(new Product(0, "Evening Gown", "Luxurious evening gown", true,
                                                 200, categories.get(0), null));
                                products.add(
                                                new Product(0, "Casual Dress", "Perfect for everyday wear", true,
                                                                 200, categories.get(0), null));
                                products.add(new Product(0, "Bohemian Dress", "Relaxed and stylish boho dress", true,
                                                 200, categories.get(0),
                                                null));

                                products.add(new Product(0, "Formal Shirt", "Classic formal shirt", true,
                                                 200, categories.get(1), null));
                                products.add(new Product(0, "Casual Shirt", "Everyday casual shirt", true,
                                                 200, categories.get(1), null));
                                products.add(new Product(0, "Linen Shirt", "Comfortable linen shirt", true,
                                                 200, categories.get(1), null));
                                products.add(new Product(0, "Denim Shirt", "Stylish denim shirt", true,
                                                 200, categories.get(1), null));
                                products.add(
                                                new Product(0, "Flannel Shirt", "Warm and cozy flannel shirt", true,
                                                                 200, categories.get(1), null));

                                products.add(new Product(0, "Slim Fit Jeans", "Trendy slim fit jeans", true,
                                                 200, categories.get(2), null));
                                products.add(new Product(0, "Straight Cut Jeans", "Classic straight cut jeans", true,
                                                 200, categories.get(2),
                                                null));
                                products.add(new Product(0, "Ripped Jeans", "Stylish ripped jeans", true,
                                                 200, categories.get(2), null));
                                products.add(new Product(0, "Skinny Jeans", "Modern skinny jeans", true,
                                                 200, categories.get(2), null));
                                products.add(new Product(0, "Loose Fit Jeans", "Comfortable loose fit jeans", true,
                                                 200, categories.get(2),
                                                null));

                                products.add(new Product(0, "Beach Swimwear", "Stylish beachwear", true,
                                                 200, categories.get(3), null));
                                products.add(
                                                new Product(0, "Swimming Trunks", "Comfortable swim trunks", true,
                                                                 200, categories.get(3), null));
                                products.add(new Product(0, "One-Piece Swimsuit", "Elegant one-piece", true,
                                                 200, categories.get(3), null));
                                products.add(new Product(0, "Bikini Set", "Trendy bikini set", true,  200, categories.get(3),
                                                null));
                                products.add(new Product(0, "Surfing Suit", "Perfect for water sports", true,
                                                 200, categories.get(3), null));

                                products.add(new Product(0, "Silk Pajamas", "Soft and luxurious silk pajamas", true,
                                                 200, categories.get(4),
                                                null));
                                products.add(new Product(0, "Cotton Nightgown", "Comfortable cotton nightgown", true,
                                                 200, categories.get(4),
                                                null));
                                products.add(new Product(0, "Thermal Pajamas", "Warm and cozy thermal pajamas", true,
                                                 200, categories.get(4),
                                                null));
                                products.add(
                                                new Product(0, "Flannel Pajamas", "Classic flannel pajamas", true,
                                                                 200, categories.get(4), null));
                                products.add(
                                                new Product(0, "Satin Sleepwear", "Elegant satin sleepwear", true,
                                                                 200, categories.get(4), null));

                                products.add(new Product(0, "Running Shoes",
                                                "Comfortable running shoes with great grip", true,
                                                 200, categories.get(5), null));
                                products.add(new Product(0, "Yoga Pants", "Stretchy yoga pants for ultimate comfort",
                                                true,
                                                 200, categories.get(5), null));
                                products.add(new Product(0, "Compression Shorts",
                                                "Supportive compression shorts for workouts", true,
                                                 200, categories.get(5), null));
                                products.add(new Product(0, "Gym T-shirt", "Breathable gym t-shirt for comfort", true,
                                                 200, categories.get(5), null));
                                products.add(new Product(0, "Sports Bra", "High-support sports bra for training", true,
                                                 200, categories.get(5), null));

                                // Jackets products
                                products.add(new Product(0, "Winter Jacket", "Warm winter jacket for cold weather",
                                                true,
                                                 200, categories.get(6), null));
                                products.add(new Product(0, "Leather Jacket", "Stylish leather jacket for casual wear",
                                                true,
                                                 200, categories.get(6), null));
                                products.add(new Product(0, "Windbreaker", "Light and breathable windbreaker jacket",
                                                true,
                                                 200, categories.get(6), null));
                                products.add(new Product(0, "Bomber Jacket",
                                                "Classic bomber jacket with a trendy design", true,
                                                 200, categories.get(6), null));
                                products.add(new Product(0, "Puffer Jacket", "Cozy puffer jacket for winter", true,
                                                 200, categories.get(6),
                                                null));

                                // Shoes products
                                products.add(new Product(0, "Running Sneakers", "Lightweight sneakers for running",
                                                true,
                                                 200, categories.get(7), null));
                                products.add(new Product(0, "Casual Shoes",
                                                "Comfortable casual shoes for everyday wear", true,
                                                 200, categories.get(7), null));
                                products.add(
                                                new Product(0, "Boots", "Durable boots for outdoor activities", true,
                                                                 200, categories.get(7), null));
                                products.add(new Product(0, "Sandals", "Relaxed sandals for summer", true,
                                                 200, categories.get(7), null));
                                products.add(new Product(0, "Loafers", "Elegant loafers for formal occasions", true,
                                                 200, categories.get(7),
                                                null));

                                productRepository.saveAll(products);

                                for (Product product : products) {

                                        List<ProductVariant> productVariants = new ArrayList<>();

                                        for (int i = 0; i < 10; i++) {
                                                // int price = ThreadLocalRandom.current().nextInt(20, 51); // Random từ 20-50
                                                int stock = ThreadLocalRandom.current().nextInt(200, 501); // Random từ 200-500
                                                String size = sizes.get(ThreadLocalRandom.current().nextInt(sizes.size())); // Random size
                                                String color = colors.get(ThreadLocalRandom.current().nextInt(colors.size())); // Random color
                                        
                                                productVariants.add(new ProductVariant(0, stock, size, color, product, null));
                                            }
                                        
                                            product.setVariants(productVariants);
                                            productVariantRepository.saveAll(productVariants);

                                        for (ProductVariant variant : productVariants) {
                                                List<ProductImage> productImages = new ArrayList<>();
                                                productImages.add(new ProductImage(0, "product-1.jpg", variant));
                                                productImages.add(new ProductImage(0, "product-1.jpg", variant));
                                                productImages.add(new ProductImage(0, "product-1.jpg", variant));

                                                variant.setImages(productImages);
                                                productImageRepository.saveAll(productImages);
                                        }

                                }
                        }
                };
        }
}
