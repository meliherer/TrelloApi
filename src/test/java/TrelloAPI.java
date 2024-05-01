

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class TrelloAPI {
    private String apiKey;
    private String apiToken;
    private String baseUrl;

    public TrelloAPI(String apiKey, String apiToken) {
        this.apiKey = apiKey;
        this.apiToken = apiToken;
        this.baseUrl = "https://api.trello.com/1";
        RestAssured.baseURI = baseUrl;
    }

    public Response login(String username, String password) {
        return RestAssured.given()
                .queryParam("key", apiKey)
                .queryParam("token", apiToken)
                .queryParam("username", username)
                .queryParam("password", password)
                .contentType(ContentType.JSON)
                .when()
                .post("/login");
    }

    public Response createBoard(String boardName) {
        return RestAssured.given()
                .queryParam("key", apiKey)
                .queryParam("token", apiToken)
                .queryParam("name", boardName)
                .contentType(ContentType.JSON)
                .when()
                .post("/boards");
    }

    public String createList(String boardId, String listName) {
        Response response = RestAssured.given()
                .queryParam("key", apiKey)
                .queryParam("token", apiToken)
                .queryParam("idBoard", boardId)
                .queryParam("name", listName)
                .contentType(ContentType.JSON)
                .when()
                .post("/lists");

        return response.path("id");
    }

    public Response createCard(String boardId, String cardName) {
        return RestAssured.given()
                .queryParam("key", apiKey)
                .queryParam("token", apiToken)
                .queryParam("idList", boardId)
                .queryParam("name", cardName)
                .contentType(ContentType.JSON)
                .when()
                .post("/cards");
    }


    public Response updateCard(String cardId, String updatedCardName) {
        return RestAssured.given()
                .queryParam("key", apiKey)
                .queryParam("token", apiToken)
                .queryParam("name", updatedCardName)
                .contentType(ContentType.JSON)
                .when()
                .put("/cards/{id}", cardId);
    }

    public Response deleteCard(String cardId) {
        return RestAssured.given()
                .queryParam("key", apiKey)
                .queryParam("token", apiToken)
                .contentType(ContentType.JSON)
                .when()
                .delete("/cards/{id}", cardId);
    }

    public Response deleteBoard(String boardId) {
        return RestAssured.given()
                .queryParam("key", apiKey)
                .queryParam("token", apiToken)
                .contentType(ContentType.JSON)
                .when()
                .delete("/boards/{id}", boardId);
    }
}
