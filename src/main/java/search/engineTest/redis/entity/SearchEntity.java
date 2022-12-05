package search.engineTest.redis.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Data
@RedisHash("SearchEntity")
@NoArgsConstructor
public class SearchEntity {
    @Id
    private String name;

    private String rank;

    public SearchEntity(String name, String rank) {
        this.name = name;
        this.rank = rank;
    }
}
