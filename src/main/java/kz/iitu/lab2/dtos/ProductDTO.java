package kz.iitu.lab2.dtos;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String translatedName;
    private String translatedDescription;
}
