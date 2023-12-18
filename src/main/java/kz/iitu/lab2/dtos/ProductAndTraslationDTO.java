package kz.iitu.lab2.dtos;

import kz.iitu.lab2.entity.Product;
import kz.iitu.lab2.entity.ProductTranslation;
import lombok.Data;

import java.util.List;

@Data
public class ProductAndTraslationDTO {
    private Product product;
    private List<ProductTranslation> translations;
}
