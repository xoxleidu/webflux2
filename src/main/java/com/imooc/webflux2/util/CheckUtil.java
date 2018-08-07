package com.imooc.webflux2.util;

import com.imooc.webflux2.exceptions.CheckException;

import java.util.stream.Stream;

public class CheckUtil {

    private static final String[] INVALID_NAMES = {"admin","administrator"};

    /**
     * 效验名字，不成功抛出效验异常
     * @param value
     */
    public static void checkName(String value){
        Stream.of(INVALID_NAMES).filter(name -> name.equalsIgnoreCase(value))
                .findAny().ifPresent(name -> {
                    throw new CheckException("name",value);
        });
    }
}
