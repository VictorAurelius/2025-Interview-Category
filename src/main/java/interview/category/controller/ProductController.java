package interview.category.controller;

import interview.category.dto.ApiResponse;
import interview.category.dto.ProductDTO;
import interview.category.service.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

        private final IProductService productService;

        @GetMapping
        public ResponseEntity<ApiResponse<Map<String, Object>>> getAllProducts(
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(defaultValue = "id") String sort) {
                Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
                Page<ProductDTO> productPage = productService.getAllProducts(pageable);

                Map<String, Object> response = new HashMap<>();
                response.put("content", productPage.getContent());
                response.put("totalElements", productPage.getTotalElements());
                response.put("totalPages", productPage.getTotalPages());
                response.put("currentPage", productPage.getNumber());
                response.put("size", productPage.getSize());

                return ResponseEntity.ok(ApiResponse.success("Products retrieved successfully", response));
        }

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<ProductDTO>> getProductById(@PathVariable Long id) {
                ProductDTO product = productService.getProductById(id);
                return ResponseEntity.ok(ApiResponse.success("Product retrieved successfully", product));
        }

        @GetMapping("/search")
        public ResponseEntity<ApiResponse<List<ProductDTO>>> searchProducts(
                        @RequestParam @Valid String name) {
                List<ProductDTO> products = productService.searchProductsByName(name);
                return ResponseEntity.ok(
                                ApiResponse.success(
                                                String.format("Found %d products matching: %s", products.size(), name),
                                                products));
        }

        @GetMapping("/category/{category}")
        public ResponseEntity<ApiResponse<List<ProductDTO>>> getProductsByCategory(
                        @PathVariable @Valid String category) {
                List<ProductDTO> products = productService.getProductsByCategory(category);
                return ResponseEntity.ok(
                                ApiResponse.success(String.format("Found %d products in category: %s", products.size(),
                                                category), products));
        }

        @PostMapping
        public ResponseEntity<ApiResponse<ProductDTO>> createProduct(@RequestBody @Valid ProductDTO productDTO) {
                ProductDTO createdProduct = productService.createProduct(productDTO);
                return new ResponseEntity<>(
                                ApiResponse.success("Product created successfully", createdProduct),
                                HttpStatus.CREATED);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<ProductDTO>> updateProduct(
                        @PathVariable Long id,
                        @RequestBody @Valid ProductDTO productDTO) {
                ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
                return ResponseEntity.ok(ApiResponse.success("Product updated successfully", updatedProduct));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse<Object>> deleteProduct(@PathVariable Long id) {
                productService.deleteProduct(id);
                return ResponseEntity.ok(ApiResponse.success("Product deleted successfully"));
        }
}