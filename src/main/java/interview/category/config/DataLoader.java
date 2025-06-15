package interview.category.config;

import interview.category.entity.Product;
import interview.category.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        List<Product> products = Arrays.asList(
                Product.builder().name("iPhone 14").price(999.99).category("Electronics").build(),
                Product.builder().name("iPhone 14 Pro").price(1299.99).category("Electronics").build(),
                Product.builder().name("MacBook Pro").price(1999.99).category("Electronics").build(),
                Product.builder().name("iPad Air").price(599.99).category("Electronics").build(),
                Product.builder().name("Nike Air Max").price(129.99).category("Footwear").build(),
                Product.builder().name("Adidas Ultraboost").price(149.99).category("Footwear").build(),
                Product.builder().name("Puma RS-X").price(99.99).category("Footwear").build(),
                Product.builder().name("Levi's 501").price(69.99).category("Clothing").build(),
                Product.builder().name("Nike Dri-FIT").price(35.99).category("Clothing").build(),
                Product.builder().name("Samsung TV").price(799.99).category("Electronics").build(),
                Product.builder().name("Sony PlayStation 5").price(499.99).category("Electronics").build(),
                Product.builder().name("Xbox Series X").price(499.99).category("Electronics").build(),
                Product.builder().name("Nintendo Switch").price(299.99).category("Electronics").build(),
                Product.builder().name("Samsung Galaxy S23").price(899.99).category("Electronics").build(),
                Product.builder().name("Google Pixel 7").price(699.99).category("Electronics").build(),
                Product.builder().name("AirPods Pro").price(249.99).category("Electronics").build(),
                Product.builder().name("Apple Watch").price(399.99).category("Electronics").build(),
                Product.builder().name("Under Armour Shorts").price(29.99).category("Clothing").build(),
                Product.builder().name("New Balance 574").price(89.99).category("Footwear").build(),
                Product.builder().name("Converse Chuck Taylor").price(59.99).category("Footwear").build());

        productRepository.saveAll(products);
    }
}