package com.security.utils;

import org.springframework.util.StringUtils;

/**
 * @Author: tongq
 * @Date: 2020/5/6 13:22
 * @since：
 */
public class cs {
    static class A {
        public String value = "A";
        public void Print(){
            System.out.println(this.value);
        }
    }
    static class B extends A{
        public String value = "B";
        @Override
        public void Print(){
            super.Print();
        }
    }
    public static void main(String[] args) {
        System.out.println(1+2+","+3+2);

        int i = 5;
        i += ++i + i-- +  i + 3;
        System.out.println(i);

        byte b = 1;
        float f = 1;
        int g = 1;
        System.out.println(b+f+g);

        int j = 0 , s =0;
        do{
            if (j%2==0){
                j++;
                continue;
            }
            j++;
            s = s+j;
        }while (j<7);
        System.out.println(s);

        MyClass myObj = new MyClass();
        myObj.Print();

        String c =new String("hello");
        String d = new String("hello");

        A a = new B();
        a.value = "C";
        a.Print();

        String user = "11";
        if (! "spadmin".equals(user) && ! "admin".equals(user)){
            System.out.println("无权");
        }else {
            System.out.println(user);
        }

        String test = "aaabbbccc";
        System.out.println("下标："+test.indexOf("a"));


        String needsub = "/A";
        System.out.println("substring： " + needsub.substring(0,needsub.lastIndexOf("/")));
        System.out.println(StringUtils.isEmpty( needsub.substring(0,needsub.lastIndexOf("/"))));

    }





}

class MyClass {
    private static int number;
    public void Print() {
        number = number + 1;
        System.out.println(number);
    }
}

