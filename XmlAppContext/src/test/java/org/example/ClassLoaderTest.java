package org.example;

import org.junit.Test;
import org.springframework.util.ClassUtils;

/**
 * @author lichen
 * @date 2023-9-27 9:56
 */
public class ClassLoaderTest {
    @Test
    public void name() {
        ClassLoader cl = ClassUtils.getDefaultClassLoader();
        System.out.println();
    }
}
