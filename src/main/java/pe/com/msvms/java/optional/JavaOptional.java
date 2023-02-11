package pe.com.msvms.java.optional;

import java.util.Optional;

public class JavaOptional {

    public static void main(String[] args) {

        String value = "null";
        System.out.println(Optional.ofNullable(value).isPresent() ? "a" : "b");

    }
}
