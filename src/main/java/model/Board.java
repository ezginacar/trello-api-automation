package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Board {
    private String id;
    private String name;
    private String url;
    private String shortUrl;
    private boolean closed;
}
