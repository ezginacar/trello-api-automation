package client;

import io.restassured.response.Response;
import model.Board;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.APIBase;

import java.util.Map;

import static constants.Endpoints.POST_CREATE_BOARD;
import static constants.Endpoints.UPDATE_BOARD;

public class BoardClient extends APIBase {
    private static final Logger logger = LogManager.getLogger(BoardClient.class);

    public Board createBoard(String name) {
        Map<String, Object> params = Map.of("name", name);
        Response response = sendRequest(POST_CREATE_BOARD, "post", getCustomRequest(null, params, null));
        Board board = response.as(Board.class);
        if (board != null) {
            logger.info("Board created: {}", board.getName());
        }
        return board;
    }

    public void deleteBoard(Board board) {
        String endpoint = String.format(UPDATE_BOARD, board.getId());
        Response response = sendRequest(endpoint, "delete", getRequest());
        if (response.getStatusCode() == 200) {
            logger.info("🗑️ Board '{}' deleted successfully.", board);

            board.setId(null);
            board.setName(null);
            board.setUrl(null);
            board.setClosed(true);

        } else {
            logger.error("Failed to delete board '{}'. Status code: {}", board.getName(), response.getStatusCode());
        }
    }



}
