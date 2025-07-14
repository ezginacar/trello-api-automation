package client;

import io.restassured.response.Response;
import model.CardList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.APIBase;

import java.util.Map;

import static constants.Endpoints.POST_CREATE_LIST;

public class CardListClient extends APIBase {
    private static final Logger logger = LogManager.getLogger(CardListClient.class);

    public CardList createList(String name, String boardId) {
        Map<String, Object> params = Map.of(
                "name", name,
                "idBoard", boardId
        );
        Response response = sendRequest(POST_CREATE_LIST, "post", getCustomRequest(null, params, null));
        CardList lists = response.as(CardList.class);
        if (lists != null) {
            logger.info("List created: {}", lists);
        }
        return lists;
    }



}
