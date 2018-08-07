package com.imooc.webflux2.handlers;

import com.imooc.webflux2.exceptions.CheckException;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * 效验 handler切面
 * 继承的 WebExceptionHandler 有很多 handler
 * 保证我们的 handler 最高 设置 @Order
 * @Order 类的优先级 越小越高
 */
@Component
@Order(-2)
public class ExceptionHandler implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        ServerHttpResponse response = exchange.getResponse();
        //设置响应头400
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        //设置返回类型
        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
        
        //异常信息
        String errorMsg = toStr(ex);

        DataBuffer db = response.bufferFactory().wrap(errorMsg.getBytes());
        
        return response.writeWith(Mono.just(db));
        
    }

    private String toStr(Throwable ex) {

        //if:已知异常 else:未知异常，打印堆栈，方便定位
        if (ex instanceof CheckException){
            CheckException e = (CheckException) ex;
            return e.getFieldName() + "已知异常-值:" + e.getFieldValue();
        }else {
            ex.printStackTrace();
            return ex.toString();
        }

    }

}
