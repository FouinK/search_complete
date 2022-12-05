package search.engineTest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import search.engineTest.entity.BrandName;
import search.engineTest.entity.Category;
import search.engineTest.entity.Product;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.insertPlaces();
        initService.insertUsers();
        initService.insertCategory();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void insertPlaces() {
            BrandName brandName = createBrandName();
            em.persist(brandName);
        }

        public void insertUsers() {
            Product product = createProduct();
            em.persist(product);
        }

        public void insertCategory() {
            for (int i = 0; i < 10000; i++) {
                Category category = createCategory("전자기기"+i);
                em.persist(category);
            }
        }
    }


    private static Product createProduct() {
        Product product = new Product();
        product.setTitle("최고사양 닌텐도");
        return product;
    }

    private static BrandName createBrandName() {
        BrandName brandName = new BrandName();
        brandName.setName("닌텐도");
        return brandName;
    }

    private static Category createCategory(String name) {
        Category category = new Category();
        category.setName(name);
        return category;
    }

}