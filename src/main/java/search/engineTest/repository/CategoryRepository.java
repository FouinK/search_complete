package search.engineTest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import search.engineTest.entity.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByNameContains(String w);
}
