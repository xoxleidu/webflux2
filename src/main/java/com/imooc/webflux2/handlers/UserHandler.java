package com.imooc.webflux2.handlers;

import com.imooc.webflux2.domain.User;
import com.imooc.webflux2.repository.UserRepository;
import com.imooc.webflux2.util.CheckUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * 相当于controller
 */
@Component
public class UserHandler {

    /**
     * 注入仓库并构造函数：官方推荐
     * 相当于@Autoward但过时了
     * @param repository
     */
    private final UserRepository repository;
    public UserHandler(UserRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> getAllUser(ServerRequest request){
        return ok().contentType(APPLICATION_JSON_UTF8)
                .body(this.repository.findAll(),User.class);
    }

    public Mono<ServerResponse> createUser(ServerRequest request){
        Mono<User> userMono = request.bodyToMono(User.class);
        /**
         * return ok().contentType(APPLICATION_JSON_UTF8)
         *            .body(this.repository.saveAll(userMono),User.class);
         */
        return userMono.flatMap(user->{
            CheckUtil.checkName(user.getName());
            return ok().contentType(APPLICATION_JSON_UTF8)
                    .body(this.repository.save(user),User.class);
        });
    }

    public Mono<ServerResponse> deleteUserById(ServerRequest request){
        String id = request.pathVariable("id");
        return this.repository.findById(id)
                .flatMap(user -> this.repository.delete(user)
                .then(ok().build()))
                .switchIfEmpty(notFound().build());
    }

}
