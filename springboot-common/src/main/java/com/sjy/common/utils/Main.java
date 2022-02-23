package com.sjy.common.utils;

import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @author: shijingyang
 * @date: 2022/1/5
 */
public class Main {
    public static void main(String[] args) {
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("#root.purchaseName");
        Order order = new Order();
        order.setPurchaseName("张三");
        System.out.println(expression.getValue(order));
    }
}
