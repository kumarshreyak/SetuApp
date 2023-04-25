package dev.shreyak.spinTheWheel.util;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static String formatToISOString(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return date.format(formatter);
    }

    public static boolean isConsentExpiryValid(String consentExpiry) {
        ZonedDateTime expiryDateTime = ZonedDateTime.parse(consentExpiry, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        ZonedDateTime currentDateTime = ZonedDateTime.now();

        return expiryDateTime.isAfter(currentDateTime);
    }
}
