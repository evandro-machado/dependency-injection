package com.di.dependency_injection.classes;

import com.di.dependency_injection.annotations.Comp;
import com.di.dependency_injection.annotations.Inj;
import com.di.dependency_injection.interfaces.A;

@Comp
public class B {

    @Inj
    private A a;

    public A getA() {
        return a;
    }
}
