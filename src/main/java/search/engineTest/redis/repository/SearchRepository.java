package search.engineTest.redis.repository;

import org.springframework.data.repository.CrudRepository;
import search.engineTest.redis.entity.SearchEntity;

public interface SearchRepository extends CrudRepository<SearchEntity, String> {
    Iterable<SearchEntity> findByNameContains(String word);
}

