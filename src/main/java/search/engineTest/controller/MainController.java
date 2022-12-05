package search.engineTest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import search.engineTest.entity.Category;
import search.engineTest.redis.service.SearchService;
import search.engineTest.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final CategoryRepository categoryRepository;
    private final SearchService searchService;

    @GetMapping("/setData")
    public ResponseEntity<?> setData() {
        searchService.setData();
        return ResponseEntity.ok("");
    }

    @GetMapping("/redis")
    public ResponseEntity<?> resultRedis() {
        List<String> resultList = searchService.getRedisStringValue("전");
        return ResponseEntity.ok(resultList);
    }

    @GetMapping("/jpa")
    public ResponseEntity<?> resultJpa() {
        List<Category> categories = categoryRepository.findByNameContains("전");
        List<String> resultList = new ArrayList<>();
        for (Category category : categories) {
            resultList.add(category.getName());
        }

        return ResponseEntity.ok(resultList);
    }
}
