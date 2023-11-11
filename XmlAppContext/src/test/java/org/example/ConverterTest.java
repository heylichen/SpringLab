package org.example;

import org.junit.Test;
import org.springframework.core.ResolvableType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lichen
 * @date 2023-9-26 11:03
 */
public class ConverterTest {
    @Test
    public void name() {
        ResolvableType rt = ResolvableType.forClass(DeserializingConverter.class).as(Converter.class);
        System.out.println();
    }

    @Test
    public void name2() {
        
    }

    private List<Class<?>> getClassHierarchy(Class<?> type) {
        List<Class<?>> hierarchy = new ArrayList<>(20);
        Set<Class<?>> visited = new HashSet<>(20);
        addToClassHierarchy(0, ClassUtils.resolvePrimitiveIfNecessary(type), false, hierarchy, visited);
        boolean array = type.isArray();

        int i = 0;
        while (i < hierarchy.size()) {
            Class<?> candidate = hierarchy.get(i);
            candidate = (array ? candidate.getComponentType() : ClassUtils.resolvePrimitiveIfNecessary(candidate));
            Class<?> superclass = candidate.getSuperclass();
            if (superclass != null && superclass != Object.class && superclass != Enum.class) {
                addToClassHierarchy(i + 1, candidate.getSuperclass(), array, hierarchy, visited);
            }
            addInterfacesToClassHierarchy(candidate, array, hierarchy, visited);
            i++;
        }

        if (Enum.class.isAssignableFrom(type)) {
            addToClassHierarchy(hierarchy.size(), Enum.class, array, hierarchy, visited);
            addToClassHierarchy(hierarchy.size(), Enum.class, false, hierarchy, visited);
            addInterfacesToClassHierarchy(Enum.class, array, hierarchy, visited);
        }

        addToClassHierarchy(hierarchy.size(), Object.class, array, hierarchy, visited);
        addToClassHierarchy(hierarchy.size(), Object.class, false, hierarchy, visited);
        return hierarchy;
    }


    private void addInterfacesToClassHierarchy(Class<?> type, boolean asArray,
                                               List<Class<?>> hierarchy, Set<Class<?>> visited) {

        for (Class<?> implementedInterface : type.getInterfaces()) {
            addToClassHierarchy(hierarchy.size(), implementedInterface, asArray, hierarchy, visited);
        }
    }

    private void addToClassHierarchy(int index, Class<?> type, boolean asArray,
                                     List<Class<?>> hierarchy, Set<Class<?>> visited) {

        if (asArray) {
            type = Array.newInstance(type, 0).getClass();
        }
        if (visited.add(type)) {
            hierarchy.add(index, type);
        }
    }

}
