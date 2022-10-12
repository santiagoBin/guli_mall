package com.atguigu.gulimall.common;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class GulimallCommonApplicationTests {

    @Test
    void contextLoads() {
        Integer[] ints = {60,1, 2, 3, 4, 5, 6, 7, 7, 7, 3, 29, 1, 90};
        List<Integer> collect = Arrays.stream(ints).distinct().sorted().collect(Collectors.toList());
        System.out.println(collect);
    }
}
