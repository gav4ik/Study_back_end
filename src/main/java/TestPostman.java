import io.restassured.response.Response;
//import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TestPostman {
    public static void main(String[] args) {
testGetRequest();
    }

        public static void testGetRequest() {
            // Выполняем GET запрос
            Response response = given()
                    .header("Content-Type", "application/json")
                    .when()
                    .get("http://localhost:8080/api/food")
                    .then()
                    .statusCode(200)
                    .body("$", notNullValue())
                    .extract()
                    .response();

            System.out.println("GET Response: " + response.prettyPrint());
        }
    }

