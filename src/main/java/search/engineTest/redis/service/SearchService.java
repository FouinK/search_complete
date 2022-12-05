package search.engineTest.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import search.engineTest.entity.Category;
import search.engineTest.repository.CategoryRepository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SearchService {
    private final RedisTemplate<String, String> redisTemplate;
    private final CategoryRepository categoryRepository;

    private static final String SEARCH_KEY = "Have Word";
    private static final String ASTERISK  = "*";
    private static final int TIME_LIMIT = 100000;

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, String> hashOperations;
    public List<String> getRedisStringValue(String key) {

        //검색으로 들어온 값
        ScanOptions scanOptions = ScanOptions.scanOptions()
                .match(ASTERISK + key
                        + ASTERISK).build();

        Cursor<Map.Entry<String, String>> cursor =
                hashOperations.scan(SEARCH_KEY, scanOptions);
        List<String> searchList = new ArrayList<>();

        while (cursor.hasNext()) {
            Map.Entry<String, String> entry = cursor.next();
            searchList.add(entry.getKey());
        }

        return searchList;
    }


    public void setData() {

        if(redisTemplate.getExpire(SEARCH_KEY) < 0){
            List<Category> nameList = categoryRepository.findAll();
            Map<String,String> nameDataMap = nameList.stream()
                    .collect(Collectors.toMap(Category::getName,Category::getName));
            hashOperations.putAll(SEARCH_KEY, nameDataMap);
            redisTemplate.expire(SEARCH_KEY,TIME_LIMIT,TimeUnit.SECONDS);
        }

    }
}
