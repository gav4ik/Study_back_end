import io.restassured.filter.session.SessionFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


public class TestPostman {

        @Test
        @DisplayName("Проверка списка товаров")
        public void testGetRequest() {
            // Выполняем GET запрос
            SessionFilter sessionFilter = new SessionFilter();
            List<Food> list = getFoodList(sessionFilter);

            assertAll(
                    () -> assertEquals(4, list.size()),
                    () -> assertTrue(list.contains(new Food("Помидор","VEGETABLE",false))),
                    () -> assertTrue(list.contains(new Food("Капуста","VEGETABLE",false))),
                    () -> assertTrue(list.contains(new Food("Яблоко","FRUIT",false))),
                    () -> assertTrue(list.contains(new Food("Апельсин","FRUIT",true)))
            );

        }

    @Test
    @DisplayName("Добавление/удаление товаров")
    void testAddFood() {
        SessionFilter sessionFilter = new SessionFilter();

        Food mara = new Food("Маракуя", "FRUIT", true);
        Food carrot = new Food("Морковь", "VEGETABLE", false);

        postAddFood(sessionFilter, mara, "food");
        List<Food> list = getFoodList(sessionFilter);

        assertAll( "Первое добавление",
                () -> assertEquals(5, list.size()),
                () -> assertTrue(list.contains(new Food("Маракуя","FRUIT",true))),
                () -> assertTrue(list.contains(new Food("Помидор","VEGETABLE",false))),
                () -> assertTrue(list.contains(new Food("Капуста","VEGETABLE",false))),
                () -> assertTrue(list.contains(new Food("Яблоко","FRUIT",false))),
                () -> assertTrue(list.contains(new Food("Апельсин","FRUIT",true)))
        );

        postAddFood(sessionFilter, carrot,"food");
        List<Food> list1 = getFoodList(sessionFilter);

        assertAll("Второе добавление",
                () -> assertEquals(6, list1.size()),
                () -> assertTrue(list1.contains(new Food("Маракуя","FRUIT",true))),
                () -> assertTrue(list1.contains(new Food("Морковь", "VEGETABLE", false)))
        );
        //Удаление продуктов
        postAddFood(sessionFilter, mara, "data/reset");
        List<Food> list2 = getFoodList(sessionFilter);

        assertAll("Удаление продуктов",
                () -> assertEquals(4, list2.size()),
                () -> assertFalse(list2.contains(new Food("Маракуя","FRUIT",true))),
                () -> assertTrue(list2.contains(new Food("Помидор","VEGETABLE",false))),
                () -> assertTrue(list2.contains(new Food("Капуста","VEGETABLE",false))),
                () -> assertTrue(list2.contains(new Food("Яблоко","FRUIT",false))),
                () -> assertTrue(list2.contains(new Food("Апельсин","FRUIT",true))),
                () -> assertFalse(list2.contains(new Food("Морковь", "VEGETABLE", false)))
        );


    }

    private List<Food> getFoodList(SessionFilter sessionFilter) {
        return given()
                .filter(sessionFilter)
                .header("Content-Type", "application/json")
                .header("Cache-Control", "no-cache")
                .when()
                .get("http://localhost:8080/api/food")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getList("", Food.class);
    }

    private void postAddFood(SessionFilter sessionFilter, Food food, String endPoint) {
        given()
                .filter(sessionFilter)
                .header("Content-Type", "application/json")
                .header("Cache-Control", "no-cache")
                .body(food)
                .when()
                .post("http://localhost:8080/api/" + endPoint)
                .then()
                .statusCode(200)
                .log().all();
    }
}

