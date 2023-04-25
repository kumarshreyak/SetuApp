package dev.shreyak.spinTheWheel.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import kotlin.ParameterName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsentRequest {
    @JsonProperty("Detail")
    private Detail Detail;

    private List<SetuContext> context;
    private String redirectUrl;
}

