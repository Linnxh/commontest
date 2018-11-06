package com.task;

import java.text.SimpleDateFormat;
import java.util.Date;
// 原文：https://blog.csdn.net/u010397369/article/details/17465649

public class ExampleJob2 {
    public void execute(){
        System.out.println("ExampleJob2~~~"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "执行ExampleJob2");
    }
}
