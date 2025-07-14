package client;

import io.restassured.response.Response;
import model.Card;
import model.CardList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.APIBase;

import java.util.Map;

import static constants.Endpoints.*;

public class CardClient extends APIBase {
    private static final Logger logger = LogManager.getLogger(CardClient.class);

    public Card createCard(String name, String idList) {
        Map<String, Object> params = Map.of(
                "name", name,
                "idList", idList
        );
        Response response = sendRequest(POST_CREATE_CARD, "post", getCustomRequest(null, params, null));
        Card card = response.as(Card.class);
        if (card != null) {
            logger.info("Card created: {}", card);
        }
        return card;
    }


    public Card updateCard(String cardId, Map<String, Object> updatedParams) {
        String endpoint = String.format(UPDATE_CARD, cardId);
        Response response = sendRequest(endpoint, "put", getCustomRequest(null, updatedParams, null));
        Card card = response.as(Card.class);
        logger.info("🆕 The card updated with new params: {}", card);
        return card;
    }

    public void deleteCard(String cardId) {
        String endpoint = String.format(UPDATE_CARD, cardId);
        Response response = sendRequest(endpoint, "delete", getRequest());
        if (response.getStatusCode() == 200) {
            logger.info("Card with ID {} deleted successfully.", cardId);
        } else {
            logger.error("Failed to delete card with ID {}. Status code: {}", cardId, response.getStatusCode());
        }
    }

    public void deleteAllCardsInList(CardList lists) {
        if (lists.getCardList() == null || lists.getCardList().isEmpty()) {
            logger.warn("List '{}' has no cards to delete.", lists.getName());
            return;
        }

        for (Card card : lists.getCardList()) {
            deleteCard(card.getId());
        }

        lists.getCardList().clear();
        logger.info("✅ All cards under list '{}' deleted successfully.", lists.getName());
    }
}
