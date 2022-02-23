package com.sjy.com.rabbitmq;


import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiThreadCompete extends Thread {

    static  final  int LOOP = 100000;
    static int counter = 0;
    //static AtomicInteger counter= new AtomicInteger(0);

    @Override
    public void run() {
        for (int i = 0; i < LOOP; i++) {
           synchronized (MultiThreadCompete.class){

               counter++;
           }
            //counter.getAndIncrement();
    //        counter.getAndAdd(1);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ArrayList<MultiThreadCompete> tlist = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            tlist.add(new MultiThreadCompete());
            tlist.get(i).start();
        }
        while (true){
            if (tlist.stream().noneMatch(MultiThreadCompete::isAlive)) {
                System.out.println(counter);
                break;
            }
            //Thread.sleep(100);
        }
    }
}
