package com.sjy.com.rabbitmq;

public class Queue {
         int size;
         int[] data;
         int front, rear;

        public Queue(int size) {
            this.size = size;
            data = new int[size];
            front = 0; rear = 0;
        }
    }