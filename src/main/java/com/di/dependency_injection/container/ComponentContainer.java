package com.di.dependency_injection.container;

import com.di.dependency_injection.annotations.Comp;
import com.di.dependency_injection.annotations.Inj;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ComponentContainer {

    public static final Map<String, Object> registry = new HashMap<>();

    public static void loadComponents() {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        List<String> components = scanComponents(loader);
        components.stream().forEach(ComponentContainer::createInstance);
        components.forEach(ComponentContainer::injectFields);
    }

    public static <T> T getBean(Class clazz) {
        return (T) registry.get(clazz.getName());
    }

    private static void injectFields(String className) {
        try {
            List<Field> fields = Arrays.asList(Class.forName(className).getDeclaredFields());
            fields
                    .stream()
                    .filter(field -> Stream.of(field.getAnnotations())
                            .anyMatch(annotation -> annotation.annotationType() == Inj.class))
                    .forEach(field -> setField(className, field));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void setField(String className, Field field) {
        try {
            Object target = registry.get(className);
            Object fieldValue = registry.get(field.getType().getName());
            field.setAccessible(true);
            field.set(target, fieldValue);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void createInstance(String className) {
        try {
            Class clazz = Class.forName(className);
            Object instance = clazz.newInstance();

            registry.put(clazz.getName(), instance);
            Stream.of(clazz.getInterfaces()).forEach(aClass -> registry.put(aClass.getName(), instance));
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static List<String> scanComponents(ClassLoader loader) {
        List<String> componentClasses = new ArrayList<>();
        try {
            componentClasses = ClassPath.from(loader).getTopLevelClassesRecursive("com.di.dependency_injection")
                    .stream()
                    .filter(classInfo -> Stream.of(classInfo.load().getAnnotations())
                            .anyMatch(annotation -> annotation.annotationType() == Comp.class))
                    .map(classInfo -> classInfo.getName())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Error trying to load classes from class path.");
            e.printStackTrace();
        }
        return componentClasses;
    }
}
