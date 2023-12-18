package kz.iitu.lab2.repository;

import kz.iitu.lab2.entity.ProductTranslation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTranslationRepository extends JpaRepository<ProductTranslation,Long> {
}
