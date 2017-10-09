package util;

import java.util.Random;

public class RandomDataGenerator {
    public static int generateRandomNumber() {
        Random rand = new Random();
        return Math.abs(rand.nextInt());
    }
}
