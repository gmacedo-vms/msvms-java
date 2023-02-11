package pe.com.msvms.java.localdate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JavaLocalDate {

    public static void main(String[] args) {

        String input = "01/01/2022";
        String format = "dd/MM/yyyy";

        LocalDate tmp = LocalDate.parse(input, DateTimeFormatter.ofPattern(format));

        System.out.println(tmp.toString());

    }
}
