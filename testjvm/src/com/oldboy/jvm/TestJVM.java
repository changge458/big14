package com.oldboy.jvm;

import java.util.ArrayList;
import java.util.List;

public class TestJVM {

    public static void main(String[] args) throws Exception {
        List<Demo> list = new ArrayList<>();

        Thread.sleep(5000);

        while (true){

            Thread.sleep(100);

            list.add(new Demo());
        }

    }


}
