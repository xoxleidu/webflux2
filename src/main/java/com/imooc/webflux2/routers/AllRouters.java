package com.imooc.webflux2.routers;

import com.imooc.webflux2.handlers.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * 相当于MVC的总入口
 * 把handler组织起来
 */
@Configuration
public class AllRouters {

    @Bean
    RouterFunction<ServerResponse> userRouter(UserHandler handler){
        /**
         *
         * nest(RequestPredicate predicate, RouterFunction<T> routerFunction)
         * nest(predicate:断言:url里面满足条件的都能进来，routerFunction自己编写的路由函数)
         *
         * return
         * RouterFunctions.nest(RequestPredicates.path("/user"),
         *      RouterFunctions.route(RequestPredicates.GET("/"),handler::getAllUser));
         *
         * return nest
         * 1.@RequestMapping("/user")
         * 2.@GetMapping("/all"),getAll()
         * 3.@PostMapping("/add"),编码,createUser()
         * 4.@DeleteMapping("/delete/{id}"),deleteUserById()
         *
         */
        return nest(path("/user"),
                route(GET("/all"),handler::getAllUser)
                .andRoute(POST("/add").and(accept(APPLICATION_JSON_UTF8)),handler::createUser)
                .andRoute(DELETE("/delete/{id}"),handler::deleteUserById));
    }

}
