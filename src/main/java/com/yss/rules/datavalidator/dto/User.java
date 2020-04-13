package com.yss.rules.datavalidator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author daomingzhu
 * @date 2020/4/9 9:53
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;
    private Integer age;
    private LocalDateTime birthday;
}
