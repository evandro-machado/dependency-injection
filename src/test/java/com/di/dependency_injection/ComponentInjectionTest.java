package com.di.dependency_injection;

import com.di.dependency_injection.classes.B;
import com.di.dependency_injection.classes.C;
import com.di.dependency_injection.container.ComponentContainer;
import com.di.dependency_injection.interfaces.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ComponentInjectionTest {

    @Test
    public void nothing() {
        ComponentContainer.loadComponents();

        A a = ComponentContainer.getBean(A.class);
        B b = ComponentContainer.getBean(B.class);
        C c = ComponentContainer.getBean(C.class);


        Assertions.assertNotNull(a);
        Assertions.assertNotNull(b);
        Assertions.assertNotNull(b.getA());
        Assertions.assertNotNull(c);
        Assertions.assertNotNull(c.getAImpl());

        Assertions.assertEquals(a, b.getA());
        Assertions.assertEquals(b.getA(), c.getAImpl());
    }
}
