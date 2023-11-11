package com.spring.lab;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lichen
 * @date 2023-5-6 11:44
 */
public class Driver {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Hello h = context.getBean("hello", Hello.class);
        System.out.println(h.say("LiSi"));
    }
}
