import java.time.LocalDate;
import java.util.Locale.Builder;
import java.util.Locale;
import java.time.format.DateTimeFormatter;

public final class Help{
    public static void main(String[] args) {
        final Locale mexico = new Builder().setLanguage("es").setRegion("MX").build();
        final LocalDate date = LocalDate.now();
        final DateTimeFormatter formatter = 
            DateTimeFormatter.ofPattern("EEEE, dd LLLL yyyy", mexico);
        final String bottomLine = date.format(formatter);
        // final LocalDate parsedDate = LocalDate.parse(bottomLine, formatter);
        System.out.printf(bottomLine);
    }
}