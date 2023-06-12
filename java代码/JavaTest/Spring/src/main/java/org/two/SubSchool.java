package org.two;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: beiyuan
 * @className: SubSchool
 * @date: 2022/6/28  17:44
 */
@Component
public class SubSchool extends School{
    @Value("岳麓书院")
    private String name;
    @Value("岳麓山")
    private String address;
}
