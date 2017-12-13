package ru.anatoli.practice_selenium.DelegationSample;

public class HumanController {
    private Legs legs = new Legs();
    private Arms arms = new Arms();

    //Getters
    public Legs getLegs() {
        return legs;
    }

    public Arms getArms() {
        return arms;
    }
}
