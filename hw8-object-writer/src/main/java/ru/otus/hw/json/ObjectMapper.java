package ru.otus.hw.json;

public interface ObjectMapper {

    String writeValueAsString(Object obj) throws IllegalAccessException;

}
