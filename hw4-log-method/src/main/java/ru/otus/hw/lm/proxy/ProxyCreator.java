package ru.otus.hw.lm.proxy;

import ru.otus.hw.lm.Calculator;
import ru.otus.hw.lm.CalculatorImpl;
import ru.otus.hw.lm.annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

class ProxyCreator {

    static Calculator createCalculator() {
        CalculatorInvocationHandler calculatorInvocationHandler = new CalculatorInvocationHandler(new CalculatorImpl());
        return (Calculator) Proxy.newProxyInstance(
                ProxyCreator.class.getClassLoader(),
                new Class[]{Calculator.class},
                calculatorInvocationHandler
        );
    }

    public static class CalculatorInvocationHandler implements InvocationHandler {

        private final Calculator calculator;

        CalculatorInvocationHandler(Calculator calculator) {
            this.calculator = calculator;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method implMethod = calculator.getClass().getMethod(method.getName(), method.getParameterTypes());
            Object invoke = method.invoke(calculator, args);
            if (implMethod.isAnnotationPresent(Log.class)) {
                System.out.println("invoke method: " + method + ", params: " + Arrays.toString(args) + ", result: " + invoke);
            }
            return invoke;
        }
    }

}
