package com.app.model;
import java.util.concurrent.ThreadLocalRandom;

public class DiceRoller {

    public int d2() {
        return ThreadLocalRandom.current().nextInt(1,3);
    }

    public int d3() {
        return ThreadLocalRandom.current().nextInt(1,4);
    }

    public int d4() {
        return ThreadLocalRandom.current().nextInt(1,5);
    }

    public int d6() {
        return ThreadLocalRandom.current().nextInt(1,7);
    }

    public int d8() {
        return ThreadLocalRandom.current().nextInt(1,9);
    }

    public int d10() {
        return ThreadLocalRandom.current().nextInt(1,11);
    }

    public int d12() {
        return ThreadLocalRandom.current().nextInt(1,13);
    }

    public int d20() {
        return ThreadLocalRandom.current().nextInt(1,21);
    }
}
