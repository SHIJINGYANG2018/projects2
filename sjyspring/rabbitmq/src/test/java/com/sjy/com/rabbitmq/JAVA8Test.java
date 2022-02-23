package com.sjy.com.rabbitmq;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JAVA8Test  {

    public static void main(String[] args) {


        List<Integer> collect = Stream.iterate(1, x ->
                ++x).limit(100).collect(Collectors.toList());

    }

}
