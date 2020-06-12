package com.srx.factory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 创建bean对象的工厂；
 *
 * Bean在计算机英语中有可重用组件的意思；
 * 一个完整的项目是由各个不同的组将组成的
 *
 * JavaBean不等于实体类，JavaBean的范围远远要超过实体类，
 * JavaBean就是使用Java语言编写的可重用组件
 *
 * 我们使用BeanFactory来创建可重用组件
 *
 * 第一个，需要配置文件来配置我们的service和dao
 *      配置的内容：唯一标识（全限定类名）{key,value}
 * 第二个，通过读取配置文件中配置的内容，反射创建对象
 *
 * 配置文件可以选择xml和properties
 */
public class BeanFactory {
    //读取配置文件
    //1.0定义一个properties对象
    private static Properties props;
    //2.0使用静态代码块为properties对象赋值
    static {
        try {
            //实力化Properties对象
            props = new Properties();//有些new是不可能省略的
            //获取Properties文件的流对象
            //InputStream in = new FileInputStream("");//不要使用这种方式应为maven工程的相对路径不一样
            InputStream in = BeanFactory.class.getClassLoader().getResourceAsStream("bean.properties");
            props.load(in);
        } catch (Exception e) {
            throw new ExceptionInInitializerError("初始化properties失败");
        }
    }

    //3.0根究bean的名称获取bean对象
    public static Object getBean(String beanName){
        Object bean = null;
        try {
            String beanPath = props.getProperty(beanName);
            System.out.println(beanPath);
            bean = Class.forName(beanPath).newInstance();//获取到当前类路径的类加载器，并且创建实例化对象
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return bean;

    }
}
