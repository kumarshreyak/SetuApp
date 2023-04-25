package dev.shreyak.spinTheWheel.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Detail {
    private String consentStart;
    @JsonProperty("consentExpiry")
    private String consentExpiry;
    @JsonProperty("Customer")
    private Customer Customer;
    @JsonProperty("FIDataRange")
    private FIDataRange FIDataRange;
    private String consentMode;
    private List<String> consentTypes;
    private String fetchType;
    @JsonProperty("Frequency")
    private Frequency Frequency;
    @JsonProperty("DataFilter")
    private List<DataFilter> DataFilter;
    @JsonProperty("DataLife")
    private DataLife DataLife;
    @JsonProperty("DataConsumer")
    private DataConsumer DataConsumer;
    @JsonProperty("Purpose")
    private Purpose Purpose;
    private List<String> fiTypes;
    private String redirectUrl;




    public static Detail create(String mobileNumber, String redirectUrl) {
        LocalDateTime dateNow = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime expiry = dateNow.plusMinutes(100);

        Detail detail = new Detail();
        detail.setConsentStart(DateUtils.formatToISOString(dateNow));
        detail.setConsentExpiry(DateUtils.formatToISOString(expiry));
        detail.setCustomer(new Customer(mobileNumber + "@onemoney"));
        detail.setFIDataRange(new FIDataRange("2023-04-26T00:00:00Z", "2023-04-30T00:00:00Z"));
        detail.setConsentMode("STORE");
        detail.setConsentTypes(List.of("PROFILE"));
        detail.setFetchType("ONETIME");
        detail.setFrequency(new Frequency(30, "MONTH"));
        detail.setDataFilter(List.of(new DataFilter("TRANSACTIONAMOUNT", "5000", ">=")));
        detail.setDataLife(new DataLife(1, "MONTH"));
        detail.setDataConsumer(new DataConsumer("setu-fiu-id"));
        detail.setPurpose(new Purpose(new Category("Loan underwriting"), "101", "https://api.rebit.org.in/aa/purpose/101.xml", "null"));
        detail.setFiTypes(List.of("DEPOSIT"));
//        detail.setRedirectUrl(redirectUrl);

        return detail;
    }

}
