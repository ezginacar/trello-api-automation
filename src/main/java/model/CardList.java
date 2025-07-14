package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardList {
    private String id;
    private String name;
    private boolean closed;
    private String idBoard;
    private long pos;

    @Getter
    @Setter
    private List<Card> cardList;


    public void addCard(Card card) {
        if (cardList == null) {
            cardList = new java.util.ArrayList<>();
        }
        cardList.add(card);
    }


}