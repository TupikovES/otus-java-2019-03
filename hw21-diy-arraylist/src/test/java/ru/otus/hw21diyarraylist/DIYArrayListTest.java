package ru.otus.hw21diyarraylist;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class DIYArrayListTest {

    private DIYArrayList<String> testCollection;
    private static ArrayList<String> stringArrays = new ArrayList<>();

    @BeforeAll
    public static void setUp() {
        for (int i = 0; i < 30; i++) {
            stringArrays.add("str_" + i);
        }
    }

    @BeforeEach
    public void setUpTest() {
        testCollection = new DIYArrayList<>();
    }

    /**
     * Проверяем метод {@link List#addAll(Collection)}
     */
    @Test
    public void addAll() {
        boolean b = testCollection.addAll(stringArrays);

        assertTrue(b);
        assertEquals(testCollection.size(), stringArrays.size());
        for (int i = 0; i < stringArrays.size(); i++) {
            assertEquals(stringArrays.get(i), testCollection.get(i));
        }
    }

    /**
     * Проверяем копирование в нашу коллекцию с помощью метода {@link Collections#copy(List, List)}
     */
    @Test
    public void copyTo() throws IllegalAccessException {
        List<String> str = new ArrayList<>();
        str.add("copy");
        str.add("from");
        str.add("DIYArrayList");
        testCollection.addAll(str);
        str.set(2, "TEST");
        Collections.copy(testCollection, str);
        assertEquals(testCollection.get(2), str.get(2));
        assertEquals(testCollection.get(2), "TEST");
    }

    /**
     * Проверяем копирование из нашей коллекции с помощью метода {@link Collections#copy(List, List)}
     */
    @Test
    public void copyFrom() throws IllegalAccessException {
        List<String> str = new ArrayList<>();
        str.add("copy");
        str.add("from");
        str.add("DIYArrayList");
        testCollection.addAll(str);
        str.set(2, "TEST");
        Collections.copy(str, testCollection);
        assertEquals(str.get(2), testCollection.get(2));
        assertEquals(str.get(2), "DIYArrayList");
    }

    /**
     * Проверяем сортировку с помощью метода {@link Collections#sort(List, Comparator)}
     */
    @Test
    public void sort() {
        testCollection.addAll(stringArrays);
        assertEquals(testCollection.get(testCollection.size() - 1), stringArrays.get(stringArrays.size() - 1));

        Collections.sort(testCollection, String::compareTo);
        //Наша коллекция как-то отсортирована
        assertNotEquals(testCollection.get(testCollection.size() - 1), stringArrays.get(stringArrays.size() - 1));

        Collections.sort(stringArrays, String::compareTo);
        //Отсортированы одинакого, значит наша коллекция сортрируется верно
        assertEquals(testCollection.get(testCollection.size() - 1), stringArrays.get(stringArrays.size() - 1));

        List<Integer> integers = new ArrayList<>();
        integers.add(3);
        integers.add(2);
        integers.add(1);

        //Проверка на числах
        DIYArrayList<Integer> testIntegers = new DIYArrayList<>();
        testIntegers.addAll(integers);

        assertEquals(testIntegers.get(0), 3);
        assertEquals(testIntegers.get(1), 2);
        assertEquals(testIntegers.get(2), 1);
        Collections.sort(testIntegers, Integer::compareTo);
        assertEquals(testIntegers.get(0), 1);
        assertEquals(testIntegers.get(1), 2);
        assertEquals(testIntegers.get(2), 3);

    }
}