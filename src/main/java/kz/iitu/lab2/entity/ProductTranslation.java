package kz.iitu.lab2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "product_translation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "language_code")
    private String languageCode;

    @Column(name = "translated_name")
    private String translatedName;

    @Column(name = "translated_description")
    private String translatedDescription;

}
