package kz.iitu.lab2.repository;

import kz.iitu.lab2.entity.Buyer;
import kz.iitu.lab2.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByTranslationsTranslatedNameContainingAndTranslationsLanguageCode(
            String translatedName, String languageCode, Pageable pageable);
}
