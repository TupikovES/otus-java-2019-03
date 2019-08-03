package ru.otus.hw.json;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
class TestObject {

    private int age;
    private Long distance;
    private String title;
    private int[] arrayInt;
    private List<Long> longList;
    private Boolean aBoolean;
    private Map<String, Double> stringDoubleMap;
    private Map<String, List<String>> stringListMap;

}
