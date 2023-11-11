package org.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.core.ResolvableType;

import java.util.HashMap;
import java.util.List;

/**
 * @author lichen
 * @date 2023-9-22 17:49
 */
public class ResolvableTypeTest {
    private HashMap<Integer, List<String>> myMap;

    @Test
    public void name() throws NoSuchFieldException {
        ResolvableType t = ResolvableType.forField(getClass().getDeclaredField("myMap"));

        //t.getSuperType(); // AbstractMap<Integer, List<String>>
        //t.asMap(); // Map<Integer, List<String>>
        view(t.getGeneric(0).resolve());  // Integer
        view(t.getGeneric(1).resolve()); // List
        t.getGeneric(1); // List<String>
        t.resolveGeneric(1, 0); // String
    }

    private void view(Class<?> clazz) {
        System.out.println(clazz);
    }
}
