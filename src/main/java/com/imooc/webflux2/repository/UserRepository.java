package com.imooc.webflux2.repository;

import com.imooc.webflux2.domain.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User,String> {

    /**
     * 根据年龄查找用户
     * @param start
     * @param end
     * @return
     */
    Flux<User> findByAgeBetween(int start, int end);

    /**
     * mongodb 语句 根据年龄查找用户 30-40
     * @return
     */
    @Query("{'age':{'$gte':30,'$lte':40}}")
    Flux<User> oldUser();

}
