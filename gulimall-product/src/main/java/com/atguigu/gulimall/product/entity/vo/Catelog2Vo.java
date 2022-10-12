package com.atguigu.gulimall.product.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Catelog2Vo {
    private String catelog1Id;
    private List<Catelog3Vo> catelog3List;
    private String id;
    private String name;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Catelog3Vo{
        private String catelog2Id;
        private String id;
        private String name;
    }
}
