package com.sjy.com.rabbitmq;

public class A {

        class Inner {
            public String  v1 = "Fake News";
            public String v2 = "Go ahead";
        }

        private static String GetVal() {
            try {
                return Inner.class.newInstance().v1;
            } catch (Exception e) {
                try {
                    return Inner.class.getDeclaredConstructor(A.class).newInstance((A)null).v2;
                } catch (Exception ee) {
                    ee.printStackTrace();
                    return "Fake News, Go ahead";
                }
            }
        }
        public static void main(String[] args) {

            String s = new String(new char[] {'没','人','比','我','更','懂','j','a','v','a'});
            String si = "没人比我更懂java";
            System.out.println(s == si);
            System.out.println(s.intern() == "没人比我更懂java");
            System.out.println(s == si.intern());
        }
    public static  int calc(int n) {
        try {
            n += 1;
            if (n / 0 > 0) {
                n += 1;
            } else {
                n -= 10;
            }
            return n;
        } catch (Exception e) {
            n++;
        }
        n++;
        return n++;
    }
    }
