import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Board {



    public static void main(String[] args) {

        String uri = "https://api.trello.com";
        String key = "21d6a6488c26d645e51875c5b50bdbe3";
        String screat = "e80dfb005102f1e3adb9b337dfa98264596c34f5c385e24527fb170763a1d765";
        String token = "32cc835ca66c598610e2b813815479902d2c4c7146b335dbc80844e03c10468a";
        String body = "{\n" + " \"name\": \"guest5\"\n" + "}";


        RestAssured.baseURI = uri;
        Response res = given().
                header("Content-Type", "application/json").
                auth().oauth(key,screat,token,screat).
                when().
                body(body).
                post("/1/boards/").
                then().assertThat().statusCode(200).and().contentType(ContentType.JSON).
                extract().response();
        System.out.println(res.asString());
        JsonPath js = new JsonPath(res.asString());
        System.out.println("New User ID: " + js.get("id"));

        Response res1 = given().
                header("Content-Type", "application/json").
                auth().oauth(key,screat,token,screat).
                when().
                get("/1/boards/"+ js.get("id")).
                then().assertThat().statusCode(200).and().contentType(ContentType.JSON).
                body("name",equalTo("guest5")).
                extract().response();
        System.out.println(res1.asString());

        Response res2 = given().
                header("Content-Type", "application/json").
                auth().oauth(key,screat,token,screat).
                when().
                delete("/1/boards/"+ js.get("id")).
                then().assertThat().statusCode(200).and().contentType(ContentType.JSON).
                extract().response();
        System.out.println(res2.asString());
    }
}
