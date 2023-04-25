package dev.shreyak.spinTheWheel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Purpose {
    @JsonProperty("Category")
    private Category Category;
    private String code;
    private String text;
    private String refUri;
}
