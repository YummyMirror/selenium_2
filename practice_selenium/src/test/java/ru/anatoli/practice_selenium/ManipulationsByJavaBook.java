package ru.anatoli.practice_selenium;

public class ManipulationsByJavaBook {
    public static void main(String[] args) {
        int maxValue = Integer.MAX_VALUE;
        int minValue = Integer.MIN_VALUE;

        if (maxValue == 2147483647 && minValue == -2147483648) {
            System.out.println("TRUE");
        } else {
            System.out.println("FALSE");
        }


        Integer integer = 70;
        int i = 70;
        Integer integer2 = new Integer("5");

        String s = String.valueOf(integer);
        String s2 = String.valueOf(i);

        if (integer.intValue() == i) {
            System.out.println("true1");
        } else {
            System.out.println("false1");
        }

        if (s.equals(s2)) {
            System.out.println("true2");
        } else {
            System.out.println("false2");
        }
    }
}