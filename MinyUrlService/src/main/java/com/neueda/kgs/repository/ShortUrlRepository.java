package com.neueda.kgs.repository;

import com.neueda.kgs.model.ShortUrl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortUrlRepository extends MongoRepository<ShortUrl,String> {
    ShortUrl findByKey(Long key);
    ShortUrl findByLongUrl(String url);
}
