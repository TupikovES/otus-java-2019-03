package ru.otus.hw.json;

import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IllegalAccessException {
        TestObject testObject = TestObject.builder()
                .aBoolean(true)
                .age(28)
                .arrayInt(new int[]{22, 33, 44})
                .distance(100000L)
                .longList(List.of(2L, 55L, 77L))
                .stringDoubleMap(Map.of("Key1", 22.2, "Key2", 33.3))
                .stringListMap(Map.of("Key3", List.of("value1", "value2"), "Key4", List.of("value4", "value5")))
                .title("Test title")
                .build();

        ObjectMapper mapper = new JsonObjectMapper();
        String s = mapper.writeValueAsString(testObject);
        System.out.println(s);
        Gson gson = new Gson();
        TestObject testObject1 = gson.fromJson(s, TestObject.class);
        if (testObject1.equals(testObject)) {
            System.out.println("TRUE");
        }
    }

}
