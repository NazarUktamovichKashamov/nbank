package api.generators;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomData {
    private RandomData(){}
    public static String CreateSingleWordName(){
        return RandomStringUtils.randomAlphabetic(7);
    }
    public static String CreateValidName(){
        return RandomStringUtils.randomAlphabetic(7) + " " + RandomStringUtils.randomAlphabetic(7);
    }
    public static String CreateThreeWordsInvalidName(){
        return RandomStringUtils.randomAlphabetic(7) + " " + RandomStringUtils.randomAlphabetic(7)  + " " + RandomStringUtils.randomAlphabetic(7);
    }
    public static String CreateNumericName(){
        return RandomStringUtils.randomAlphanumeric(7);
    }

    public static String CreateNumber(){
        return RandomStringUtils.randomNumeric(2);
    }

}
