package ru.otus.hw.atmemul.atm;

import lombok.Getter;
import ru.otus.hw.atmemul.atm.exception.IllegalParException;
import ru.otus.hw.atmemul.atm.exception.ImpossibleWithdrawAmountRequestedException;
import ru.otus.hw.atmemul.department.operation.Operation;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SimpleATM implements ATM {

    @Getter
    private String atmUuid;
    private EnumMap<Par, Integer> parCount = new EnumMap<>(Par.class);
    private Memento memento;

    public SimpleATM(EnumMap<Par, Integer> parCount) {
        atmUuid = UUID.randomUUID().toString();
        System.out.println("Init ATM #" + atmUuid);
        parCount.forEach((par, count) -> this.parCount.put(par, count));
        saveState(parCount);
    }

    private void saveState(EnumMap<Par, Integer> parCount) {
        memento = new Memento();
        memento.save(parCount);
    }

    @Override
    public void deposit(ParEntity... parEntities) {
        try {
            validatePar(parEntities);
            depositAtm(parEntities);
        } catch (IllegalParException e) {
            System.out.println(e.getMessage());
        }
    }

    private void depositAtm(ParEntity[] parEntities) {
        int totalDeposit = 0;
        for (ParEntity parEntity : parEntities) {
            Integer oldCount = parCount.get(parEntity.getPar());
            int newCount = oldCount + parEntity.getCount();
            totalDeposit += parEntity.total();
            parCount.put(parEntity.getPar(), newCount);
        }
        System.out.println("Внесено: " + totalDeposit);
    }

    private void validatePar(ParEntity[] parEntities) throws IllegalParException {
        for (ParEntity parEntity : parEntities) {
            if (!parCount.containsKey(parEntity.getPar())) {
                throw new IllegalParException(parEntity.getPar());
            }
        }
    }

    @Override
    public List<ParEntity> withdraw(int value) {
        try {
            checkIssuance(value);
            return withdrawMinimal(value);
        } catch (ImpossibleWithdrawAmountRequestedException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    private void checkIssuance(int value) throws ImpossibleWithdrawAmountRequestedException {
        AtomicBoolean check = new AtomicBoolean(false);
        parCount.forEach((par, integer) -> {
            if (value % par.getValue() == 0) {
                check.set(true);
            }
        });
        if (!check.get() || total() < value) {
            throw new ImpossibleWithdrawAmountRequestedException();
        }
    }

    private List<ParEntity> withdrawMinimal(int value) {
        List<ParEntity> withdrawList = new ArrayList<>();
        AtomicInteger thisValue = new AtomicInteger(value);
        parCount.entrySet()
                .stream()
                .sorted((o1, o2) -> Integer.compare(o2.getKey().getPriority(), o1.getKey().getPriority()))
                .forEach(par -> {
                    ParEntity parEntity = withdrawFromAtm(par, thisValue.get());
                    if (parEntity.total() > 0) {
                        withdrawList.add(parEntity);
                    }
                    thisValue.addAndGet(-parEntity.total());
                });

        return withdrawList;
    }

    private ParEntity withdrawFromAtm(Map.Entry<Par, Integer> entry, int value) {
        int needCount = value / entry.getKey().getValue();
        if (needCount <= entry.getValue()) {
            parCount.put(entry.getKey(), parCount.get(entry.getKey()) - needCount);
            return new ParEntity(entry.getKey(), needCount);
        } else {
            int count = parCount.get(entry.getKey());
            parCount.put(entry.getKey(), 0);
            return new ParEntity(entry.getKey(), count);
        }
    }

    @Override
    public void printReport() {
        System.out.println("ATM #" + atmUuid);
        System.out.println("Par: " + parCount);
        System.out.println("Total: " + total());
    }

    public int total() {
        AtomicReference<Integer> total = new AtomicReference<>(0);
        parCount.forEach((par, count) -> {
            int parCount = par.getValue() * count;
            total.updateAndGet(v -> v + parCount);
        });
        return total.get();
    }

    @Override
    public void recovery() {
        EnumMap<Par, Integer> state = memento.getState();
        parCount.clear();
        state.forEach((par, count) -> parCount.put(par, count));
    }

    @Override
    public void perform(Operation operation) {
        operation.perform(this);
    }
}
