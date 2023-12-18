package kz.iitu.lab2.service;

import kz.iitu.lab2.dtos.ProductDTO;
import kz.iitu.lab2.dtos.ProductResponse;
import kz.iitu.lab2.entity.Product;
import kz.iitu.lab2.entity.ProductTranslation;
import kz.iitu.lab2.repository.ProductRepository;
import kz.iitu.lab2.repository.ProductTranslationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductTranslationRepository productTranslationRepository;

    public ProductResponse createProductWithTranslations(Product product, List<ProductTranslation> translations) {
        Product savedProduct = productRepository.save(product);

        for (ProductTranslation translation : translations) {
            translation.setProduct(savedProduct);
            productTranslationRepository.save(translation);
        }
        ProductResponse productResponse = new ProductResponse();
        productResponse.setSold(product.isSold());
        productResponse.setPrice(product.getPrice());
        return productResponse;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<ProductDTO> getProductById(Long productId, String languageCode) {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        return optionalProduct.map(product -> {
            if (product.getTranslations() != null && !product.getTranslations().isEmpty()) {
                ProductTranslation selectedTranslation = product.getTranslations().stream()
                        .filter(translation -> languageCode.equals(translation.getLanguageCode()))
                        .findFirst()
                        .orElse(null);

                if (selectedTranslation != null) {
                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setId(product.getId());
                    productDTO.setTranslatedName(selectedTranslation.getTranslatedName());
                    productDTO.setTranslatedDescription(selectedTranslation.getTranslatedDescription());
                    return productDTO;
                }
            }
            return null;
        });
    }

    public List<ProductDTO> getAllProducts(int page, int size, String sortBy, String order, String languageCode) {
        Sort sort = order.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> products = productRepository.findAll(pageable);

        return products.stream()
                .map(product -> convertToDTO(product, languageCode))
                .collect(Collectors.toList());
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        if (productRepository.existsById(id)) {
            updatedProduct.setId(id);
            return productRepository.save(updatedProduct);
        } else {
            return null;
        }
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<ProductDTO> findProductsByNameContaining(
            String translatedName, String languageCode, int page, int size, String sortBy, String order) {
        Sort sort = order.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> allByTranslationsTranslatedNameContainingAndTranslationsLanguageCode = productRepository.findAllByTranslationsTranslatedNameContainingAndTranslationsLanguageCode(
                translatedName, languageCode, pageable);

        return  allByTranslationsTranslatedNameContainingAndTranslationsLanguageCode.stream()
                .map(allByTranslations
                        -> convertToDTO(allByTranslations,languageCode))
                .collect(Collectors.toList());
    }

    private ProductDTO convertToDTO(Product product, String languageCode) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());

        // Найти соответствующий перевод продукта по languageCode
        if (product.getTranslations() != null && !product.getTranslations().isEmpty()) {
            ProductTranslation selectedTranslation = product.getTranslations().stream()
                    .filter(translation -> languageCode.equals(translation.getLanguageCode()))
                    .findFirst()
                    .orElse(null);

            if (selectedTranslation != null) {
                productDTO.setTranslatedName(selectedTranslation.getTranslatedName());
                productDTO.setTranslatedDescription(selectedTranslation.getTranslatedDescription());
            }
        }

        return productDTO;
    }

//    public List<Product> findAllProductsByPrice(double price, int page, int size, String sortBy, String order) {
//        Sort sort = order.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
//                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
//        Pageable pageable = PageRequest.of(page, size, sort);
//        return productRepository.findAllByPrice(price, pageable).getContent();
//    }
}

