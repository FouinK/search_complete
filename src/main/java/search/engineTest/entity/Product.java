package search.engineTest.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "product")
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<BrandName> brandNames = new ArrayList<>();

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public void addBrandName(BrandName brandName) {
        this.brandNames.add(brandName);
    }
}