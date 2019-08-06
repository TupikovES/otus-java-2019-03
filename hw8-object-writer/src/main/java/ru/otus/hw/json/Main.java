package ru.otus.hw.json;

import com.google.gson.Gson;

import java.util.Collections;
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
        Gson gson = new Gson();
        String s = mapper.writeValueAsString(testObject);
        System.out.println(s);
        System.out.println(gson.toJson(testObject));
        TestObject testObject1 = gson.fromJson(s, TestObject.class);
        if (testObject1.equals(testObject)) {
            System.out.println("TRUE");
        }

        System.out.println(mapper.writeValueAsString(null));
        System.out.println(gson.toJson(null));

        System.out.println(mapper.writeValueAsString((byte)1));
        System.out.println(gson.toJson((byte)1));

        System.out.println(mapper.writeValueAsString((short)1f));
        System.out.println(gson.toJson((short)1f));

        System.out.println(mapper.writeValueAsString(1));
        System.out.println(gson.toJson(1));

        System.out.println(mapper.writeValueAsString(1L));
        System.out.println(gson.toJson(1L));

        System.out.println(mapper.writeValueAsString(1f));
        System.out.println(gson.toJson(1f));

        System.out.println(mapper.writeValueAsString(1d));
        System.out.println(gson.toJson(1d));

        System.out.println(mapper.writeValueAsString("aaa"));
        System.out.println(gson.toJson("aaa"));


        System.out.println(mapper.writeValueAsString('a'));
        System.out.println(gson.toJson('a'));

        System.out.println(mapper.writeValueAsString(new int[] {1, 2, 3}));
        System.out.println(gson.toJson(new int[] {1, 2, 3}));

        System.out.println(mapper.writeValueAsString(List.of(1, 2 ,3)));
        System.out.println(gson.toJson(List.of(1, 2 ,3)));

        System.out.println(mapper.writeValueAsString(Collections.singletonList(1)));
        System.out.println(gson.toJson(Collections.singletonList(1)));
    }

}
