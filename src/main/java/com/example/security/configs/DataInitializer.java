package com.example.security.configs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
                                categoryRepository.save(new Category(0, "Men's Pants", generateSlug("Men's Pants"), true, "menpant.jpg", null));
                                categoryRepository.save(new Category(0, "Men's Shirts", generateSlug("Men's Shirts"), true, "menshirt.jpg", null));
                                categoryRepository.save(new Category(0, "Men's Tracksuit Shirts", generateSlug("Men's Tracksuit Shirts"), true,
                                                "mentracksuitshirt.jpg", null));
                                categoryRepository.save(new Category(0, "Women's Pants", generateSlug("Women's Pants"), true, "womenpant.jpg", null));
                                categoryRepository
                                                .save(new Category(0, "Women's Shirts", generateSlug("Women's Shirts"), true, "womenshirt.jpg", null));
                                categoryRepository.save(new Category(0, "Women's Tracksuit Shirts", generateSlug("Women's Tracksuit Shirts"), true,
                                                "womenstracksuitshirt.jpg", null));

                        }
                };
        }

        public String generateSlug(String name) {
                return name.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("^-|-$", "");
            }
            
        @Order(3)
        @Bean
        public CommandLineRunner initializeProducts(ProductRepository productRepository,
                        CategoryRepository categoryRepository, ProductImageRepository productImageRepository,
                        ProductVariantRepositry productVariantRepository) {

                return args -> {

                        // List<String> colors = Arrays.asList("White", "Red", "Black", "Blue",
                        // "Green");
                        String[] sizes = { "XS", "S", "M", "L", "XL" };

                        List<Product> products = new ArrayList<>();
                        List<Category> categories = categoryRepository.findAll();

                        if (productRepository.count() == 0) {

                                products.add(new Product(0, "Men's Jogger Khaki Pants with Zipper Pockets",
                                                "These men's khaki pants give a feeling of ventilation and good absorption due to the cotton content. The product has good elasticity due to the spandex content, so it is comfortable to move, and durable due to the woven structure of the khaki fabric. Experiencing khaki pants helps you feel the surface of the fabric is firm, keeping the product in good shape but still soft and not stiff.",
                                                true,
                                                21, categories.get(0), null));
                                products.add(
                                                new Product(0, "Men's Cafe Trousers with Mobile Waistband",
                                                                "Convenient mobile waistband design flexibly adjusts to the waist. Elegant, modern style suitable for all activities from office to street. Effective odor control technology and up to 98% UV protection.",
                                                                true,
                                                                22, categories.get(0), null));
                                products.add(new Product(0, "Men's Ankle Length Pants",
                                                "Add this meticulously tailored pair of dress pants to your men's elegant wardrobe. Designed for Asian body shape, straight leg, ankle length helps men look neat, polite and more neat every time they dress up.",
                                                true,
                                                19, categories.get(0), null));

                                ////////////////
                                products.add(new Product(0, "Moving Forward Men's Sports Fleece Jacket",
                                                "Stay active and warm with the YODY fleece jacket. The jacket uses the first Pique Scuba knit fabric from the US, which is the perfect combination of comfort, durability and style on the fabric. The fabric is woven in three layers with two extremely strong yarn systems. The fabric has outstanding softness, elasticity, and light drape, suitable for many types of clothing from casual to sporty.",
                                                true,
                                                20, categories.get(1), null));
                                products.add(new Product(0, "Airycool Men's Polo Shirt Breathable Collar",
                                                "Using Airycool fabric, this shirt helps to wick away sweat, is breathable and keeps you cool on hot days. The basic straight shape with a sophisticated collar design is suitable for going out or going to work. It is both polite and cool, comfortable all day long.",
                                                true,
                                                18, categories.get(1), null));
                                products.add(new Product(0, "Keep Moving Men's Zip-Up Sports Jacket",
                                                "The Keep Moving men's sports jacket is made from high-quality polyamide and elastane, which is highly elastic, colorfast and wrinkle-resistant. Smart fabric technology helps the shirt breathe, absorb sweat quickly, and bring a dry, comfortable feeling when moving.",
                                                true,
                                                19, categories.get(1), null));

                                ///////////
                                products.add(new Product(0, "Men's T-shirt Train Hard Win Easy",
                                                "Basic men's T-shirt brings comfort and convenience for all activities. This shirt feels soft to wear, has good elasticity, absorbs quickly, dries quickly and is airy thanks to the interwoven weave with many small holes on the surface to help circulate moisture and air well.",
                                                true,
                                                21, categories.get(2), null));
                                products.add(new Product(0, "T-shirt Nam In Training",
                                                "YODY men's sports T-shirts emphasize comfort, elasticity and good absorbency for the wearer. This is a basic, convenient sports product, providing a comfortable wearing experience, can be worn anytime, anywhere without causing stuffiness. Simple design, strong and sturdy print.",
                                                true,
                                                22, categories.get(2),
                                                null));
                                products.add(new Product(0, "Men's Long Sleeve Sports Shirt with Back Patch",
                                                "Men's long-sleeved sports T-shirts help men wear light and comfortable in autumn and winter. The product has a cool, smooth, soft handfeel, creating a comfortable feeling when in contact with the skin. Ventilation, good absorption thanks to modern finishing technology. Excellent elasticity thanks to the fiber composition, at the same time has high durability, does not stretch, does not pill.",
                                                true,
                                                17, categories.get(2), null));

                                ///////////
                                products.add(new Product(0, "Women's Tapered Jeans with Pockets",
                                                "Another jeans design that helps you to effectively enhance your legs. Denim material with ribbed effect creates a natural look for the pants. The product is soft, airy, and effectively elastic, helping women to move and exercise comfortably every day. Note: machine wash in gentle mode with products of the same color, iron at a temperature below 150◦C. The product may fade in the first 1-3 washes.",
                                                true,
                                                18, categories.get(3), null));
                                products.add(
                                                new Product(0, "Rayon Straight Leg Jeans with Belt",
                                                                "Women's straight-leg jeans give you the confidence to dress up in a stylish way. Dynamic design with convenient straps for women to freely create their own outfit combinations. The straight-leg form is extremely flattering, suitable for work, going out, dating...",
                                                                true,
                                                                16, categories.get(3), null));
                                products.add(new Product(0, "Women's Tencel Paper Straight Pleated Jeans",
                                                "Super comfortable with Women's Tencel Paper Straight Pleated Jeans. Pants are made from tencel fiber with a matte shine, classic, simple look, soft hand feeling. Super comfortable straight design with soft, light and extremely airy material for confident summer wear.",
                                                true,
                                                22, categories.get(3), null));

                                ////////////
                                products.add(new Product(0, "Women's Vest with Buttons and Pockets",
                                                "Women's vest with unique double face diamond material, no wrinkles, no pilling. Slightly stretchy fabric creates a comfortable feeling. Convenient pocket design enhances modern, elegant beauty. The perfect choice for stylish ladies.",
                                                true,
                                                21, categories.get(4),
                                                null));
                                products.add(new Product(0, "Women's Dynamic Cargo Jacket",
                                                "Women's jacket with youthful, dynamic design. The product uses fabric with a natural wavy effect created by weaving with the tension of two different fiber systems, making the jacket especially impressive, light, comfortable, and durable. The box pocket design creates a unique, new personality highlight.",
                                                true,
                                                22, categories.get(4),
                                                null));
                                products.add(new Product(0,
                                                "Women's Set of Antique German Leaves with Chrysanthemum Foot Wrap A",
                                                "Luxurious and elegant women's clothing set for ladies. The product has a 2-axis Twill weave that helps the fabric have porosity to create a firm form. The leaf collar design creates a luxurious and impressive highlight. The material is thick, durable, absorbent and effectively limits wrinkles.",
                                                true,
                                                23, categories.get(4),
                                                null));

                                ///////////
                                products.add(new Product(0, "Airycool Women's Sports Polo Color Combination",
                                                "This summer, let YODY \"cool down\" you with the new generation of Airycool sports Polo shirt design. Comfortable from material to design, modern & strong to upgrade your sports wardrobe today.",
                                                true,
                                                24, categories.get(5), null));
                                products.add(new Product(0, "Yody Sport Run For Health Women's Polo",
                                                "The new YODY SPORT women's polo shirt with Ponte De Roma material helps to elevate the sports experience of women. Ponte De Roma material is inspired by classic Roman bridges. The fabric feels soft and sturdy with a double knit structure. One of the great things about Ponte De Roma is that it is easy to care for and holds its shape well. The comfortable, stretchy design makes sports activities more flexible and confident.",
                                                true,
                                                21, categories.get(5), null));
                                products.add(new Product(0, "Women's Sports Polo with Outstanding Eye-Catching",
                                                "Experience the feeling of cool touch - Wear and love with the new super product of Airycool air-conditioned sports Polo shirt at YODY. Using new technology, new design, this is definitely an indispensable item for women this summer.",
                                                true,
                                                18, categories.get(5), null));

                                productRepository.saveAll(products);
                                List<ProductVariant> productVariants = new ArrayList<>();
                                List<ProductImage> productImages = new ArrayList<>();

                                Map<Integer, String[]> productColors = new HashMap<>();
                                productColors.put(1, new String[] { "black", "brown", "silver" });
                                productColors.put(2, new String[] { "black", "blue", "silver" });
                                productColors.put(3, new String[] { "black", "blue", "brown" });
                                productColors.put(4, new String[] { "black", "blue", "brown" });
                                productColors.put(5, new String[] { "black", "brown", "white" });
                                productColors.put(6, new String[] { "black", "blue", "white" });
                                productColors.put(7, new String[] { "blue", "brown", "white" });
                                productColors.put(8, new String[] { "brown", "white" });
                                productColors.put(9, new String[] { "black", "blue", "brown" });
                                productColors.put(10, new String[] { "black", "brown", "white" });
                                productColors.put(11, new String[] { "black", "brown" });
                                productColors.put(12, new String[] { "black", "brown" });
                                productColors.put(13, new String[] { "black", "brown", "red" });
                                productColors.put(14, new String[] { "black", "brown", "white" });
                                productColors.put(15, new String[] { "black", "brown", "white" });
                                productColors.put(16, new String[] { "black", "green", "white" });
                                productColors.put(17, new String[] { "black", "pink", "purple" });
                                productColors.put(18, new String[] { "black", "red", "white" });

                                Map<Integer, String> productCategories = new HashMap<>();
                                productCategories.put(1, "menpants/pro1");
                                productCategories.put(2, "menpants/pro2");
                                productCategories.put(3, "menpants/pro3");
                                productCategories.put(4, "menshirts/pro1");
                                productCategories.put(5, "menshirts/pro2");
                                productCategories.put(6, "menshirts/pro3");
                                productCategories.put(7, "mentracksuitshirt/pro1");
                                productCategories.put(8, "mentracksuitshirt/pro2");
                                productCategories.put(9, "mentracksuitshirt/pro3");
                                productCategories.put(10, "womenpants/pro1");
                                productCategories.put(11, "womenpants/pro2");
                                productCategories.put(12, "womenpants/pro3");
                                productCategories.put(13, "womenshirts/pro1");
                                productCategories.put(14, "womenshirts/pro2");
                                productCategories.put(15, "womenshirts/pro3");
                                productCategories.put(16, "womenstracksuitshirt/pro1");
                                productCategories.put(17, "womenstracksuitshirt/pro2");
                                productCategories.put(18, "womenstracksuitshirt/pro3");

                                for (Product product : products) {
                                        int stock = ThreadLocalRandom.current().nextInt(200, 501);
                                        String[] colors = productColors.getOrDefault(product.getId(), new String[] {});
                                        String category = productCategories.getOrDefault(product.getId(), "");

                                        for (String color : colors) {
                                                for (String size : sizes) {
                                                        ProductVariant productVariant = new ProductVariant(0, stock,
                                                                        size, color, product, null);
                                                        for (int imgIndex = 1; imgIndex <= 3; imgIndex++) {
                                                                productImages.add(new ProductImage(0,
                                                                                category + "_" + color + imgIndex
                                                                                                + ".jpg",
                                                                                productVariant));
                                                        }
                                                        productVariants.add(productVariant);
                                                }
                                        }
                                }

                                productVariantRepository.saveAll(productVariants);
                                productImageRepository.saveAll(productImages);

                        }
                };
        }
}
