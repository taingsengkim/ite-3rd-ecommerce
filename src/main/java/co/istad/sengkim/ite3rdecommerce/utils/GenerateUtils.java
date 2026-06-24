package co.istad.sengkim.ite3rdecommerce.utils;

import java.text.Normalizer;
import java.util.Random;

public class GenerateUtils {

    //generate product code

    public static String generateProductCode(){
        Random random = new Random();
        int randomDigits = random.nextInt(10000);
        return String.format("ITE-3RD-%04d",randomDigits);
    }

    //generate product slug
    public static String generateSlug(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase()
                .trim()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-");
    }
}
