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
                                                categories.get(0), null));
                                products.add(
                                                new Product(0, "Summer Dress", "Light and breezy summer dress", true,
                                                                categories.get(0), null));
                                products.add(new Product(0, "Evening Gown", "Luxurious evening gown", true,
                                                categories.get(0), null));
                                products.add(
                                                new Product(0, "Casual Dress", "Perfect for everyday wear", true,
                                                                categories.get(0), null));
                                products.add(new Product(0, "Bohemian Dress", "Relaxed and stylish boho dress", true,
                                                categories.get(0),
                                                null));

                                products.add(new Product(0, "Formal Shirt", "Classic formal shirt", true,
                                                categories.get(1), null));
                                products.add(new Product(0, "Casual Shirt", "Everyday casual shirt", true,
                                                categories.get(1), null));
                                products.add(new Product(0, "Linen Shirt", "Comfortable linen shirt", true,
                                                categories.get(1), null));
                                products.add(new Product(0, "Denim Shirt", "Stylish denim shirt", true,
                                                categories.get(1), null));
                                products.add(
                                                new Product(0, "Flannel Shirt", "Warm and cozy flannel shirt", true,
                                                                categories.get(1), null));

                                products.add(new Product(0, "Slim Fit Jeans", "Trendy slim fit jeans", true,
                                                categories.get(2), null));
                                products.add(new Product(0, "Straight Cut Jeans", "Classic straight cut jeans", true,
                                                categories.get(2),
                                                null));
                                products.add(new Product(0, "Ripped Jeans", "Stylish ripped jeans", true,
                                                categories.get(2), null));
                                products.add(new Product(0, "Skinny Jeans", "Modern skinny jeans", true,
                                                categories.get(2), null));
                                products.add(new Product(0, "Loose Fit Jeans", "Comfortable loose fit jeans", true,
                                                categories.get(2),
                                                null));

                                products.add(new Product(0, "Beach Swimwear", "Stylish beachwear", true,
                                                categories.get(3), null));
                                products.add(
                                                new Product(0, "Swimming Trunks", "Comfortable swim trunks", true,
                                                                categories.get(3), null));
                                products.add(new Product(0, "One-Piece Swimsuit", "Elegant one-piece", true,
                                                categories.get(3), null));
                                products.add(new Product(0, "Bikini Set", "Trendy bikini set", true, categories.get(3),
                                                null));
                                products.add(new Product(0, "Surfing Suit", "Perfect for water sports", true,
                                                categories.get(3), null));

                                products.add(new Product(0, "Silk Pajamas", "Soft and luxurious silk pajamas", true,
                                                categories.get(4),
                                                null));
                                products.add(new Product(0, "Cotton Nightgown", "Comfortable cotton nightgown", true,
                                                categories.get(4),
                                                null));
                                products.add(new Product(0, "Thermal Pajamas", "Warm and cozy thermal pajamas", true,
                                                categories.get(4),
                                                null));
                                products.add(
                                                new Product(0, "Flannel Pajamas", "Classic flannel pajamas", true,
                                                                categories.get(4), null));
                                products.add(
                                                new Product(0, "Satin Sleepwear", "Elegant satin sleepwear", true,
                                                                categories.get(4), null));

                                products.add(new Product(0, "Running Shoes",
                                                "Comfortable running shoes with great grip", true,
                                                categories.get(5), null));
                                products.add(new Product(0, "Yoga Pants", "Stretchy yoga pants for ultimate comfort",
                                                true,
                                                categories.get(5), null));
                                products.add(new Product(0, "Compression Shorts",
                                                "Supportive compression shorts for workouts", true,
                                                categories.get(5), null));
                                products.add(new Product(0, "Gym T-shirt", "Breathable gym t-shirt for comfort", true,
                                                categories.get(5), null));
                                products.add(new Product(0, "Sports Bra", "High-support sports bra for training", true,
                                                categories.get(5), null));

                                // Jackets products
                                products.add(new Product(0, "Winter Jacket", "Warm winter jacket for cold weather",
                                                true,
                                                categories.get(6), null));
                                products.add(new Product(0, "Leather Jacket", "Stylish leather jacket for casual wear",
                                                true,
                                                categories.get(6), null));
                                products.add(new Product(0, "Windbreaker", "Light and breathable windbreaker jacket",
                                                true,
                                                categories.get(6), null));
                                products.add(new Product(0, "Bomber Jacket",
                                                "Classic bomber jacket with a trendy design", true,
                                                categories.get(6), null));
                                products.add(new Product(0, "Puffer Jacket", "Cozy puffer jacket for winter", true,
                                                categories.get(6),
                                                null));

                                // Shoes products
                                products.add(new Product(0, "Running Sneakers", "Lightweight sneakers for running",
                                                true,
                                                categories.get(7), null));
                                products.add(new Product(0, "Casual Shoes",
                                                "Comfortable casual shoes for everyday wear", true,
                                                categories.get(7), null));
                                products.add(
                                                new Product(0, "Boots", "Durable boots for outdoor activities", true,
                                                                categories.get(7), null));
                                products.add(new Product(0, "Sandals", "Relaxed sandals for summer", true,
                                                categories.get(7), null));
                                products.add(new Product(0, "Loafers", "Elegant loafers for formal occasions", true,
                                                categories.get(7),
                                                null));

                                productRepository.saveAll(products);

                                for (Product product : products) {

                                        List<ProductVariant> productVariants = new ArrayList<>();

                                        for (int i = 0; i < 10; i++) {
                                                int price = ThreadLocalRandom.current().nextInt(20, 51); // Random từ 20-50
                                                int stock = ThreadLocalRandom.current().nextInt(200, 501); // Random từ 200-500
                                                String size = sizes.get(ThreadLocalRandom.current().nextInt(sizes.size())); // Random size
                                                String color = colors.get(ThreadLocalRandom.current().nextInt(colors.size())); // Random color
                                        
                                                productVariants.add(new ProductVariant(0, price, stock, size, color, product, null));
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
