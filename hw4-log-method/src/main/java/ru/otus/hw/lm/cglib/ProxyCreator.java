package ru.otus.hw.lm.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import ru.otus.hw.lm.CalculatorImpl;
import ru.otus.hw.lm.annotation.Log;

import java.lang.reflect.Method;
import java.util.Arrays;

class ProxyCreator {

    static CalculatorImpl createCalculator() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CalculatorImpl.class);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            Object invoke = proxy.invokeSuper(obj, args);
            if (method.isAnnotationPresent(Log.class)) {
                System.out.println("invoke method: " + method + ", params: " + Arrays.toString(args) + ", result: " + invoke);
            }
            return invoke;
        });

        return  (CalculatorImpl) enhancer.create();
    }

}
