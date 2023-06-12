package com.example.rabbitboot.config;

import org.springframework.context.annotation.Configuration;

/**
 * @author: beiyuan
 * @className: TtlQueueConfig
 * @date: 2022/10/2  11:04
 */
@Configuration
public class TtlQueueConfig {
    public static final String X_EXCHANGE="X";
    public static final String Y_DEAD_LETTER_EXCHANGE="Y";
    public static final String QUEUE_A="QA";
    public static final String QUEUE_B="QB";
    public static final String DEAD_LETTER_QUEUE="QD";



}
