package com.di.dependency_injection.classes;

import com.di.dependency_injection.annotations.Comp;
import com.di.dependency_injection.annotations.Inj;

@Comp
public class C {

    @Inj
    private AImpl aImpl;

    public AImpl getAImpl() {
        return aImpl;
    }
}
