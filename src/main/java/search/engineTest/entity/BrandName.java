package search.engineTest.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class BrandName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
