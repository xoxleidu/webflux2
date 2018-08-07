package com.imooc.webflux2.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Document(collection = "user")

@Data//自动set-get-toString
public class User {

    @Id
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String password;
    @Range(min = 1,max = 120)
    private int age;
    private Date date;

}
