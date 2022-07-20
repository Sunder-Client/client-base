package wtf.base.client.util.common;

import java.security.SecureRandom;
import java.util.Random;

public class MathUtil {
    public static final Random RNG = new SecureRandom();

    public static int random(int min, int max) {
        return RNG.nextInt((max + 1) - min) + min;
    }
}
