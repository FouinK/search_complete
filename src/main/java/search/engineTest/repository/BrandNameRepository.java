package search.engineTest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import search.engineTest.entity.BrandName;

public interface BrandNameRepository extends JpaRepository<BrandName, Long> {

}
