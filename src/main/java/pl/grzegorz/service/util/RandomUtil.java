package pl.grzegorz.service.util;

import org.apache.commons.lang3.RandomStringUtils;
import pl.grzegorz.domain.enumeration.TypNieruchomosci;
import pl.grzegorz.domain.enumeration.TypSklepu;

import java.security.SecureRandom;

/**
 * Utility class for generating random Strings.
 */
public final class RandomUtil {

    private static final int DEF_COUNT = 20;

    private static final SecureRandom SECURE_RANDOM;

    static {
        SECURE_RANDOM = new SecureRandom();
        SECURE_RANDOM.nextBytes(new byte[64]);
    }

    private RandomUtil() {
    }

    public static String generateRandomAlphanumericString() {
        return RandomStringUtils.random(DEF_COUNT, 0, 0, true, true, null, SECURE_RANDOM);
    }

    public static String generateRandomString() {
        return RandomStringUtils.random(DEF_COUNT, 0, 0, true, false, null, SECURE_RANDOM);
    }

    public static Integer generateRandomNumber() {
        return Integer.valueOf(RandomStringUtils.random(5, 0, 0, false, true, null, SECURE_RANDOM));
    }

    public static Float generateRandomFloat() {
        return Float.valueOf(RandomStringUtils.random(5, 0, 0, false, true, null, SECURE_RANDOM));
    }

    public static Double generateRandomDouble() {
        return Double.valueOf(RandomStringUtils.random(5, 0, 0, false, true, null, SECURE_RANDOM));
    }

    public static TypSklepu generateRandomTypSklepu() {
        Integer integer = RandomUtil.generateRandomNumber() % 5;
        if(integer == 0) return TypSklepu.PRZEMYSLOWY;
        if(integer == 1) return TypSklepu.SPOZYWCZY;
        if(integer == 2) return TypSklepu.CHEMIA;
        if(integer == 3) return TypSklepu.DELIKATESY;
        if(integer == 4) return TypSklepu.USLUGI;
        return  TypSklepu.PRZEMYSLOWY;
    }
    public static Boolean generateRandomBoolean() {
        return RandomUtil.generateRandomNumber() % 2 == 0;
    }
    public static TypNieruchomosci generateRandomTypNieruchomosci() {
        Integer integer = RandomUtil.generateRandomNumber() % 5;
        if(integer == 0) return TypNieruchomosci.BLOK;
        if(integer == 1) return TypNieruchomosci.DOM;
        if(integer == 2) return TypNieruchomosci.HOTEL;
        if(integer == 3) return TypNieruchomosci.WIEZOWIEC;
        return  TypNieruchomosci.BLOK;
    }


    /**
     * Generate a password.
     *
     * @return the generated password.
     */
    public static String generatePassword() {
        return generateRandomAlphanumericString();
    }

    /**
     * Generate an activation key.
     *
     * @return the generated activation key.
     */
    public static String generateActivationKey() {
        return generateRandomAlphanumericString();
    }

    /**
     * Generate a reset key.
     *
     * @return the generated reset key.
     */
    public static String generateResetKey() {
        return generateRandomAlphanumericString();
    }
}
