package ru.otus.hw011;

import java.util.List;

import com.google.common.collect.Lists;

public class Main {

    public static void main(String[] args) {
        List<String> nameList = List.of("John", "Mary", "Dom");
        String names = String.join(", ", nameList);

        Lists.reverse(nameList);
    }

}
