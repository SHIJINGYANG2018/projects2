package com.sjyspring.com;

import java.util.*;
import java.util.stream.Collectors;

public class MainClass {
    static Vector<GdMessage> submit = new Vector<>();

    public static void main(String[] args)
    {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000 ; i++) {
            GdSubmitMessage gdSubmitMessage = new GdSubmitMessage();
            gdSubmitMessage.setRedis_list_name("aaa"+(i/100));
            gdSubmitMessage.setUser_id(i);
            submit.add(gdSubmitMessage);
        }
        GdMessage[] msgs = getCacheMsgmore(10000000);
        long end = System.currentTimeMillis();
        System.out.println(end-start);
         start = System.currentTimeMillis();
        List<GdSubmitMessage> submitMessages = (List) Arrays.asList(msgs);
        GdSubmitMessage[] result = new GdSubmitMessage[submitMessages.size()];
        submitMessages.toArray(result);
         end = System.currentTimeMillis();
        /*Map<String, List<GdSubmitMessage>> listNameMap = submitMessages.stream().collect(Collectors.groupingBy(GdSubmitMessage::getRedis_list_name));
        for (Map.Entry<String, List<GdSubmitMessage>> listEntry : listNameMap.entrySet()) {
            List<GdSubmitMessage> messages = listEntry.getValue();
            GdSubmitMessage[] result = new GdSubmitMessage[messages.size()];
            messages.toArray(result);
            System.out.println(Arrays.toString(result));
        }*/
        System.out.println(end-start);


        start = System.currentTimeMillis();
        List<GdMessage> list  = new ArrayList<GdMessage>();
        list = Arrays.asList(msgs);
        GdSubmitMessage[] temArray = new GdSubmitMessage[list.size()];
        list.toArray(temArray);
        end = System.currentTimeMillis();
        System.out.println(end-start);
    }


    public static GdMessage[] getCacheMsgmore(int count) {
        int batchNum = 0;
        int size = getSize();
        if (count > size){
            batchNum = size;
        }
        else{
            batchNum = count;
        }

        List<GdMessage> list = new ArrayList<GdMessage>();
        for (int i = 0; i < batchNum; i++) {
            if (getSize() == 0) {
                break;
            }
            GdMessage sr = get();
            if (null != sr){
                list.add(sr);
            }
        }

        GdMessage[] result = new GdMessage[list.size()];
        return list.toArray(result);
    }

    public synchronized static GdMessage get() {
        GdMessage message;
        if (submit.isEmpty()) {
            return null;
        } else {
            message = submit.remove(getSize() - 1);
        }
        return message;

    }

    /**
     * @return submit在内存中的大小
     */
    public synchronized static int getSize() {
        return submit.size();
    }

    public static void add(GdMessage csm) {
        submit.add(csm);
    }
}
