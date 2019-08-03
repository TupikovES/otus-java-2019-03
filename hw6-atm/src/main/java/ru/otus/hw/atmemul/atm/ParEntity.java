package ru.otus.hw.atmemul.atm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Некий Pojo для удобства.
 * Он описывает количество банкнот определенным номиналом
 */
@AllArgsConstructor
@Getter
@ToString
public class ParEntity {

    /**
     * Номинал купюры
     */
    private Par par;

    /**
     * Количество купюр
     */
    private int count;

    public int total() {
        return par.getValue() * count;
    }

}
