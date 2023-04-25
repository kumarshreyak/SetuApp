package dev.shreyak.spinTheWheel.model;

import dev.shreyak.spinTheWheel.util.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Detail {
    private String consentStart;
    private String consentExpiry;
    private Customer Customer;
    private FIDataRange FIDataRange;
    private String consentMode;
    private List<String> consentTypes;
    private String fetchType;
    private Frequency Frequency;
    private List<DataFilter> DataFilter;
    private DataLife DataLife;
    private DataConsumer DataConsumer;
    private Purpose Purpose;
    private List<String> fiTypes;
    private String redirectUrl;


    public boolean isConsentExpiryValid() {
        ZonedDateTime expiryDateTime = ZonedDateTime.parse(consentExpiry, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        ZonedDateTime currentDateTime = ZonedDateTime.now();

        return expiryDateTime.isAfter(currentDateTime);
    }

    public static Detail create(String mobileNumber, String redirectUrl) {
        LocalDateTime dateNow = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime expiry = dateNow.plusMinutes(10);

        Detail detail = new Detail();
        detail.setConsentStart(DateUtils.formatToISOString(dateNow));
        detail.setConsentExpiry(DateUtils.formatToISOString(expiry));
        detail.setCustomer(new Customer(mobileNumber + "@onemoney"));
        detail.setFIDataRange(new FIDataRange("2021-04-01T00:00:00Z", "2021-10-01T00:00:00Z"));
        detail.setConsentMode("STORE");
        detail.setConsentTypes(List.of("TRANSACTIONS", "PROFILE", "SUMMARY"));
        detail.setFetchType("PERIODIC");
        detail.setFrequency(new Frequency(30, "MONTH"));
        detail.setDataFilter(List.of(new DataFilter("TRANSACTIONAMOUNT", "5000", ">=")));
        detail.setDataLife(new DataLife(1, "MONTH"));
        detail.setDataConsumer(new DataConsumer("setu-fiu-id"));
        detail.setPurpose(new Purpose(new Category("101"), "Loan underwriting", "https://api.rebit.org.in/aa/purpose/101.xml", null));
        detail.setFiTypes(List.of("DEPOSIT"));
        detail.setRedirectUrl(redirectUrl);

        return detail;
    }

}
