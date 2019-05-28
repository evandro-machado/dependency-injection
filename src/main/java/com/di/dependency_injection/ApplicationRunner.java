package com.di.dependency_injection;

import com.di.dependency_injection.container.ComponentContainer;

public class ApplicationRunner {

    public static void main(String[] args) {
        ComponentContainer.loadComponents();
    }
}
