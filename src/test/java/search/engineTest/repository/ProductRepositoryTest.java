package search.engineTest.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import search.engineTest.entity.BrandName;
import search.engineTest.entity.Category;
import search.engineTest.entity.Product;
import search.engineTest.redis.repository.SearchRepository;
import search.engineTest.redis.service.SearchService;

import javax.persistence.EntityManager;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.*;

@SpringBootTest
@Transactional
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    BrandNameRepository brandNameRepository;
    @Autowired
    SearchRepository searchRepository;
    @Autowired
    SearchService searchService;
    @Autowired
    EntityManager em;

    @BeforeEach
    public void initDb() throws UnsupportedEncodingException {
        Product product = new Product();
        product.setTitle("최고사양 닌텐도");
        em.persist(product);
        em.flush();
        em.clear();

        // given
        BrandName brandName = new BrandName();
        brandName.setName("닌텐도");
        brandName.setProduct(product);
        em.persist(brandName);

        Category category = new Category();
        String helloString = "전자기기";
        byte[] euckrStringBuffer  = helloString.getBytes(Charset.forName("euc-kr"));
        // euc-kr 로 변환된 byte 문자열을 다시 유니코드 String 으로 변환.
        // String 생성자의
        // 첫 번째 인자로 문자열 byte 배열을  넣어주고,
        // 두 번째 인자로 byte 배열의 인코딩 값을 넣어준다.
        String decodedHelloString = new String(euckrStringBuffer, "euc-kr");
        category.setName(decodedHelloString);
        category.setProduct(product);

        em.persist(category);
        em.flush();
        em.clear();


    }
//
//    @Test
//    @DisplayName("연관관계 포함 상품 저장")
//    @Rollback(value = false)
//    public void saveProduct() {
//        Product product = new Product();
//        product.setTitle("최고사양 닌텐도");
//        em.persist(product);
//        em.flush();
//        em.clear();
//
//        // given
//        BrandName brandName = new BrandName();
//        brandName.setName("닌텐도");
//        brandName.setProduct(product);
//        em.persist(brandName);
//
//        Category category = new Category();
//        category.setName("전자기기");
//        category.setProduct(product);
//
//        em.persist(category);
//        em.flush();
//        em.clear();
//
//
//        // when
//
//
//        // then
//    }

//    @Test
//    @DisplayName("전체 검색 후 레디스에 삽입 조회")
//    public void findAll() {
//        // given
//        List<Product> products = productRepository.findAll();
//        List<Category> categories = categoryRepository.findAll();
//        List<BrandName> brandNames = brandNameRepository.findAll();
//
//        SearchEntity searchEntity = new SearchEntity();
//
//        // when
//        for (BrandName brandName : brandNames) {
//            searchEntity.setName(brandName.getName());
//            searchEntity.setRank("0");
//
//        }
//
//        for (Category category : categories) {
//            searchEntity.setName(category.getName());
//            searchEntity.setRank("0");
//        }
//
//        for (Product product : products) {
//
//            String[] word = product.getTitle().split(" ");
//
//            for (String w : word) {
//                searchEntity.setName(w);
//                searchEntity.setRank("0");
//            }
//
//        }
//
//        searchRepository.save(searchEntity);
//        // then
//
//        Iterable<SearchEntity> list = searchRepository.findAll();
//        Iterable<SearchEntity> containsList = searchRepository.findByNameContains("닌");
//
//        for (SearchEntity entity : list) {
//            System.out.println("entity.getName() = " + entity.getName());
//            System.out.println("entity.getRank() = " + entity.getRank());
//        }
//
//        for (SearchEntity entity : containsList) {
//            System.out.println("entity.getName() = " + entity.getName());
//            System.out.println("entity.getRank() = " + entity.getRank());
//        }
//    }

    @Test
    @DisplayName("Redis 시간 측정")
    public void findRedis() {
        long startTime = System.currentTimeMillis();
        searchService.setData();

        for (int i = 0; i < 10000; i++) {
            List<String> list = searchService.getRedisStringValue("전");
            for (String s : list) {
//                System.out.println("s = " + s);
            }
        }

        long stopTime = System.currentTimeMillis();
        System.out.println(stopTime - startTime);
    }

    @Test
    @DisplayName("Jpa 시간 측정")
    public void ProductRepositoryTest() {
        // given
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 10000; i++) {
            List<Category> list = categoryRepository.findByNameContains("전");
            for (Category s : list) {
//                System.out.println("s = " + s.getName());
            }
        }

        long stopTime = System.currentTimeMillis();
        System.out.println(stopTime - startTime);
    }

}