package ru.otus.hw.lm.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import ru.otus.hw.lm.CalculatorImpl;
import ru.otus.hw.lm.annotation.Log;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class ProxyCreator {

    private static Set<String> methods = new HashSet<>();

    static CalculatorImpl createCalculator() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CalculatorImpl.class);
        saveAnnotationMethods(CalculatorImpl.class.getDeclaredMethods());
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            Object invoke = proxy.invokeSuper(obj, args);
            if (methods.contains(getMethodId(method))) {
                System.out.println("invoke method: " + method + ", params: " + Arrays.toString(args) + ", result: " + invoke);
            }
            return invoke;
        });

        return  (CalculatorImpl) enhancer.create();
    }

    private static void saveAnnotationMethods(Method ...declaredMethods) {
        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(Log.class)) {
                methods.add(getMethodId(method));
            }
        }
    }

    private static String getMethodId(Method method) {
        StringBuilder postfix = new StringBuilder();
        for (Class<?> parameterType : method.getParameterTypes()) {
            postfix.append("_").append(parameterType.getName());
        }
        return method.getName() + postfix.toString();
    }

}
