package org.example;

import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyTest {
  @Test
  public void name() {
    Method[] ms = Component.class.getDeclaredMethods();
    System.out.println();

    System.out.println(getClassHierarchy(Integer.class));
    System.out.println(getClassHierarchy(Integer[].class));
  }


  /**
   * Returns an ordered class hierarchy for the given type.
   * @param type the type
   * @return an ordered list of all classes that the given type extends or implements
   */
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
