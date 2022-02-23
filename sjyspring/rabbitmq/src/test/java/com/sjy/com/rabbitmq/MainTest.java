package com.sjy.com.rabbitmq;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class MainTest {
    /*public static void main(String[] args) {
        1+2+4+8+16+3
        Map<String, Object> map = new HashMap<>();
        String str = "没人比我更懂java";
        StrObject obj = new StrObject("没人比我更懂java");
        map.put("str", str);
        map.put("obj", obj);

        str = "真的没人比我更懂java";
        System.out.printf(map.get("str").toString()+"; ");

        StrObject new_obj = (StrObject) map.get("obj");
        new_obj.setStr("真的没人比我更懂java");
        System.out.printf(map.get("obj").toString()+"; ");
    }
    static class StrObject{
        String str;
        public StrObject(String str){
            this.str = str;
        }
        public void setStr(String str){
            this.str = str;
        }
        @Override
        public String toString() {
            return str;
        }
    }*/
    /*public static void main(String[] args) {

        *//**
         *  A. q.front - q.rear == MaxSize;
         *
         *     B. q.front == q.rear;
         *
         *     C. q.front == (q.rear+1) % MaxSize;
         *
         *     D. q.front + q.rear == MaxSize;
         *//*
        int MaxSize=10;
        Queue q = new Queue(MaxSize);
        System.out.println( q.front - q.rear == MaxSize);
        System.out.println( q.front == q.rear);
        System.out.println(q.front == (q.rear+1) % MaxSize);
        System.out.println(q.front + q.rear == MaxSize);
    }
    private static Integer method(Integer i){
        try{
            if(i++ > 0)
                throw new IOException();
            return i++;
        }catch (IOException e){
            i++;
            return i++;
        }catch (Exception e){
            i++;
            return i++;
        }finally {
            return i++;
        }
    }*/
    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        new String();
        char[] chars = "没人比我更懂java".toCharArray();
        Charset cs = Charset.forName("UTF-8");
        CharBuffer cb = CharBuffer.allocate(chars.length);
        cb.put(chars);
        cb.flip();
        ByteBuffer bb = cs.encode(cb);
        System.out.println(bb.array().length);
        System.out.println(chars.length);
        Thread t = new Thread() {
            public void run() {
                cnn();
            }
        };
        t.run();
        System.out.print("FakeNews ");
        System.out.print("; ");
        t.start();
        System.out.print("FakeNews ");
    }
    static void cnn() {
        System.out.print("CNN ");
    }


}

