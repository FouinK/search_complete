package search.engineTest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import search.engineTest.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
