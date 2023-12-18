package kz.iitu.lab2.controller;

import kz.iitu.lab2.dtos.ProductAndTraslationDTO;
import kz.iitu.lab2.dtos.ProductResponse;
import kz.iitu.lab2.entity.Product;
import kz.iitu.lab2.entity.ProductTranslation;
import kz.iitu.lab2.dtos.ProductDTO;
import kz.iitu.lab2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductAndTraslationDTO productDTO) {
        Product product = productDTO.getProduct();
        List<ProductTranslation> translations = productDTO.getTranslations();
        ProductResponse createdProduct = productService.createProductWithTranslations(product, translations);
        return ResponseEntity.ok(createdProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id, @RequestHeader("languageCode") String languageCode) {
        return productService.getProductById(id,languageCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String order,
            @RequestHeader("languageCode") String languageCode
    ) {
        List<ProductDTO> products = productService.getAllProducts(page, size, sortBy, order,languageCode);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Product updated = productService.updateProduct(id, updatedProduct);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/findByName")
    public ResponseEntity<List<ProductDTO>> findProductsByNameContaining(
            @RequestHeader("languageCode") String languageCode,
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String order) {
        List<ProductDTO> products = productService.findProductsByNameContaining(
                name, languageCode, page, size, sortBy, order);
        return ResponseEntity.ok(products);
    }

}