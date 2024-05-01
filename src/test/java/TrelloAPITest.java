
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;
public class TrelloAPITest {

    private static final String API_KEY = "05124c370e597951a79af1fd725fe8e9";
    private static final String API_TOKEN = "ATTA2924e3c7d4d72813c91e1888dd8da9882ff010df5c0982464ed14d225a245077DD26300F";
    private String boardId;
    private String cardId1;
    private String cardId2;
    private String listId;

    private TrelloAPI trelloAPI;

    @BeforeClass
    public void setUp() {
        trelloAPI = new TrelloAPI(API_KEY, API_TOKEN);
    }

    @Test(priority = 1)
    public void testCreateBoard() {
        Response createBoardResponse = trelloAPI.createBoard("Test Board2");
        Assert.assertEquals(createBoardResponse.getStatusCode(), 200, "Board creation failed");
        boardId = createBoardResponse.path("id");
    }

    @Test(priority = 2)
    public void testCreateCards() {
        listId = trelloAPI.createList(boardId,"new list");


        Response createCardResponse1 = trelloAPI.createCard(listId, "Card 3");
        Assert.assertEquals(createCardResponse1.getStatusCode(), 200, "Card 1 creation failed");
        cardId1 = createCardResponse1.path("id");

        Response createCardResponse2 = trelloAPI.createCard(listId, "Card 4");
        Assert.assertEquals(createCardResponse2.getStatusCode(), 200, "Card 2 creation failed");
        cardId2 = createCardResponse2.path("id");
    }

    @Test(priority = 3)
    public void testUpdateRandomCard() {
        Random random = new Random();
        boolean updateFirstCard = random.nextBoolean();

        String cardIdToUpdate = updateFirstCard ? cardId1 : cardId2;

        Response updateCardResponse = trelloAPI.updateCard(cardIdToUpdate, "Updated Card");
        Assert.assertEquals(updateCardResponse.getStatusCode(), 200, "Card update failed");
    }

    @Test(priority = 4)
    public void testDeleteCards() {
        Response deleteCardResponse1 = trelloAPI.deleteCard(cardId1);
        Assert.assertEquals(deleteCardResponse1.getStatusCode(), 200, "Card 1 deletion failed");

        Response deleteCardResponse2 = trelloAPI.deleteCard(cardId2);
        Assert.assertEquals(deleteCardResponse2.getStatusCode(), 200, "Card 2 deletion failed");
    }

    @Test(priority = 5)
    public void testDeleteBoard() {
        Response deleteBoardResponse = trelloAPI.deleteBoard(boardId);
        Assert.assertEquals(deleteBoardResponse.getStatusCode(), 200, "Board deletion failed");
    }
}
