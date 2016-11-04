package com.xpjun;

import java.util.Scanner;

/**
 * Created by lenovo on 2016/11/4.
 */
public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculation calculation = new Calculation();
        while (scanner.hasNext()){
            String math = scanner.next();
            double result = calculation.getResult(math);
            if (result==3.14159) System.out.println("非法输入");
            else System.out.println(""+result);
        }
    }
}
