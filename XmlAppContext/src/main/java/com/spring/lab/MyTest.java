package com.spring.lab;

import java.util.Map;

/**
 * @author lichen
 * @date 2023-5-6 15:50
 */
public class MyTest {
    public static void main(String[] args) {
        System.out.println("---------env -------------- ");
        for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        System.out.println("--------- system properties -------------- ");
        for (Map.Entry<Object, Object> entry : System.getProperties().entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
