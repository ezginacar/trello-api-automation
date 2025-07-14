package model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Card {
    private String id;
    private String name;
    private String idList;
    private String desc;
    private boolean closed;


}