package com.app.model;
import java.util.concurrent.ThreadLocalRandom;

public class DiceRoller {

    //SINGLETON
    private DiceRoller() {
        System.out.println("Dice Roller created.");
    }

    private static DiceRoller instance = null;
    public static synchronized DiceRoller getInstance() {
        if(instance == null)
            instance = new DiceRoller();
        return instance;
    }
    public int d2(int k) {
        if(k > 0)
            return d2(k - 1) + (ThreadLocalRandom.current().nextInt(1,3));
        else
            return 0;
    }

    public int d3(int k) {
        if(k > 0)
            return d3(k - 1) + (ThreadLocalRandom.current().nextInt(1,4));
        else
            return 0;
    }

    public int d4(int k) {
        if(k > 0)
            return d4(k - 1) + (ThreadLocalRandom.current().nextInt(1, 5));
        else
            return 0;
    }

    public int d6(int k) {
        if(k > 0)
            return d6(k - 1) + (ThreadLocalRandom.current().nextInt(1,7));
        else
            return 0;
    }

    public int d8(int k) {
        if(k > 0)
            return d8(k - 1) + (ThreadLocalRandom.current().nextInt(1,9));
        else
            return 0;
    }

    public int d10(int k) {
        if(k > 0)
            return d10(k - 1) + (ThreadLocalRandom.current().nextInt(1,11));
        else
            return 0;
    }

    public int d12(int k) {
        if(k > 0)
            return d12(k - 1) + (ThreadLocalRandom.current().nextInt(1,13));
        else
            return 0;
    }

    public int d20(int k) {
        if(k > 0)
            return d20(k - 1) + (ThreadLocalRandom.current().nextInt(1,21));
        else
            return 0;
    }

}
