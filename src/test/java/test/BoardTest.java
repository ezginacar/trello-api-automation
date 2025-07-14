package test;

import client.BoardClient;
import client.CardClient;
import client.CardListClient;
import model.Board;
import model.Card;
import model.CardList;
import org.junit.jupiter.api.*;
import utils.APIBase;
import utils.TestDataGenerator;


import java.util.Map;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Trello Board E2E Test")
public class BoardTest extends APIBase {

    private Board board;
    private CardList cardList;

    private TestDataGenerator testData;
    private BoardClient boardClient;
    private CardListClient listClient;
    private CardClient cardClient;

    @BeforeAll
    void setupClass(){
        testData = new TestDataGenerator();
        boardClient = new BoardClient();
        listClient = new CardListClient();
        cardClient = new CardClient();
    }


    @Test
    @Order(1)
    @DisplayName("Create a new board")
    void createBoard() {
        board = boardClient.createBoard(testData.generateName("Ezgi Test Board-"));
    }

    @Test
    @Order(2)
    @DisplayName("Create a new list on the board")
    void createANewListOnTheBoard() {
       cardList = listClient.createList(testData.generateName("Ezgi Test List-"), board.getId());
    }


    @Test
    @Order(3)
    @DisplayName("Create 2 new cards under the list")
    void createTwoNewCardsUnderTheList() {
        cardList.addCard(cardClient.createCard("Ezgi Test Card1", cardList.getId()));
        cardList.addCard(cardClient.createCard("Ezgi Test Card2", cardList.getId()));

    }

    @Test
    @Order(4)
    @DisplayName("Update the name of a random card")
    void updateCardNameUnderTheList() {
        int i = testData.getRandomInt(0, cardList.getCardList().size() - 1);
        String id = cardList.getCardList().get(i).getId();

        Map<String, Object> params = Map.of(
                "name", testData.generateName("Ezgi Updated Card-")
        );

        cardClient.updateCard(id, params);
    }

    @Test
    @Order(5)
    @DisplayName("Delete all cards in the board")
    void deleteAllCardsInTheBoard() {
        cardClient.deleteAllCardsInList(cardList);
    }

    @Test
    @Order(6)
    @DisplayName("Delete the board")
    void deleteTheBoard() {
        boardClient.deleteBoard(board);
    }




}
