package de.mobilecomputing.ekrememre.medify.eanapi;

import java.util.Scanner;

public class EanResponseParser {
    private static final String TAG = "EanResponseParser";

    private static final String KEY_ERROR = "error=";
    private static final String KEY_NAME = "name=";

    public static class Product {
        public int error;
        public String name;

        public Product(int error, String name) {
            this.error = error;
            this.name = name;
        }
    }

    public static Product parse(String body) {
        if (!body.contains(KEY_ERROR)) {
            return new Product(0, "");
        }

        Product product = new Product(0, "");

        Scanner scanner = new Scanner(body);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.startsWith(KEY_ERROR)) {
                try {
                    product.error = Integer.parseInt(line.substring(KEY_ERROR.length()));
                } catch (NumberFormatException e) {
                    return new Product(-1, "");
                }
            }

            if (line.startsWith(KEY_NAME)) {
                product.name = line.substring(KEY_NAME.length());
            }
        }

        return product;
    }
}
