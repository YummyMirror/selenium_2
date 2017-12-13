package ru.anatoli.practice_selenium.DelegationSample;

public class Human {
    private static HumanController humanController = new HumanController();

    public static void main(String[] args) {
        String s = humanController.getArms().raiseHands(2);
        humanController.getLegs().doSomething();
        String s1 = humanController.getLegs().numberOfLegs(5);
    }
}
