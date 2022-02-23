package com.sjy.com.rabbitmq;

import java.util.*;

public class Tests {
    public static void main(String[] args) {
        String s = new String(new char[]{'没', '人', '比', '我', '更', '懂', 'j', 'a', 'v', 'a'});
        String si = "没人比我更懂java";
        System.out.println(s == si);
        System.out.println(s.intern() == "没人比我更懂java");
        System.out.println(s == si.intern());
        int intA[][] = {
                {1, 2, 3}
                , {2, 3, 4}
                , {3, 4, 5}
                , {4, 5, 6}};
        int height = intA.length;
        int width = intA[0].length;
        System.out.println(width + "   " + height);
        System.out.println(intA[height - 1][width - 1]);
        System.out.println(searchMatrix(intA, 3));
        HashMap<String, Object> hashMap = new HashMap<>();
        HashMap<String, Object> hashMap1 = new HashMap<>();
        HashMap<String, Object> hashMap2 = new HashMap<>();
        Object[] objects={1,null,3};
        hashMap1.put("c",0);
        Object[] objects1={"v",2,hashMap1};
        hashMap2.put("b",objects1);
        hashMap.put("a",hashMap2);
        hashMap.put("d",objects);
        System.out.println(showMap(hashMap));
    }

    /**
     * * 对任意一个Map<String, Object>, 其 key 为 String,
     *    * 其 value 为 Map<String, Object> Object[] Number String 中的任意一种,
     *    * 显然叶子节点是 value 类型为 Number 或 String的节点,
     *    * 将 Map 转为多条字符串, 每条字符串表达其中一个叶子节点,
     *    * 比如:
     *    * {"a":{"b":["v",2,{"c":0}]},"d":[1,null,3]}
     *    * 将转化为以下这些字符串
     *    * a.b[0] = v
     *    * a.b[1] = 2
     *    * a.b[2].c = 0
     *    * d[0] = 1
     *    * d[1] = null
     *    * d[2] = 3
     * @param map
     * @return
     */

    public static Set<String> showMap(Map<String, Object> map) {
        Set<String> strings = new HashSet<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            StringBuilder sb = new StringBuilder();
            sb.append(key);
            if (value instanceof Object[]){
                showObjArray(strings,sb,value);
            }else if (value ==null|| value instanceof Number || value instanceof String){
                sb.append("=").append(value);
                strings.add(sb.toString());
            }else{
                showMap(strings,sb,value);
            }
        }
        return strings;
    }
    public static  void showObjArray(Set<String> strings,StringBuilder sb,Object o){
        Object[]objs = (Object[])o;
        String str;
        for (int i = 0; i < objs.length; i++) {
            str=sb.toString();
            sb.append("[").append(i).append("]");
            Object object = objs[i];
            if (object==null || object instanceof Number || object instanceof String){
                sb.append("=").append(object);
                strings.add(sb.toString());
            }else if (object instanceof Object[]){
                showObjArray(strings,sb,object);
            }else {
                showMap(strings, sb,object);

            }
            sb=new StringBuilder(str);
        }

    }
    public static void showMap(Set<String> strings, StringBuilder sb, Object hashMap) {
        Map<String, Object> hashMap1 = (Map<String, Object>) hashMap;
        for (String ss : hashMap1.keySet()) {
            sb.append(".").append(ss);
            Object object = hashMap1.get(ss);
            if (object == null || object instanceof Number || object instanceof String){
                sb.append("=").append(object);
                strings.add(sb.toString());
            }else if (object instanceof Object[]){
                showObjArray(strings,sb, object);
            }else{
                showMap(strings,sb, object);
            }
        }
    }
    public static boolean searchMatrix(int[][] nums, int x) {


        try {
            int i = nums.length - 1;
            int j = nums[0].length - 1;
            if (nums[i][j] < x) {
                return false;
            }
            if (nums[0][0] > x) {
                return false;
            }
            i = 0;
            while (i < nums.length && j >= 0) {
                if (nums[i][j] == x) return true;
                else if (nums[i][j] > x) --j;
                else ++i;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
