package com.example.todo;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import jakarta.inject.Inject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.todo.repository.TodoRepository;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

@QuarkusTest
@DisplayName("Todo Web エンドポイントの結合テスト")
class TodoWebResourceTest {

    @Inject
    TodoRepository repo;

    /** フォーム本文を UTF-8 でエンコードする RestAssured 設定。 */
    private static final RestAssuredConfig UTF8 = RestAssuredConfig.config()
            .encoderConfig(EncoderConfig.encoderConfig()
                    .defaultContentCharset("UTF-8")
                    .appendDefaultContentCharsetToContentTypeIfUndefined(true));

    @Test
    @DisplayName("作成 → 一覧 → 完了トグル → 削除の一連の流れを HTML 断片で検証する")
    void createToggleAndDelete() {
        String title = "牛乳を買う";

        String html = given()
                .config(UTF8)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("title", title)
                .when().post("/web/todos")
                .then()
                .statusCode(200)
                .contentType(containsString("text/html"))
                .body(containsString(title))
                .body(containsString("<li"))
                .body(containsString("hx-put"))
                .body(containsString("hx-delete"))
                .extract().asString();

        Long todoId = extractTodoId(html);

        given()
                .when().get("/web/todos")
                .then()
                .statusCode(200)
                .body(containsString(title))
                .body(containsString("todo-" + todoId));

        given()
                .when().put("/web/todos/" + todoId + "/toggle")
                .then()
                .statusCode(200)
                .body(containsString("todo-item done"))
                .body(containsString("checked"));

        given()
                .when().delete("/web/todos/" + todoId)
                .then()
                .statusCode(200);

        given()
                .when().get("/web/todos")
                .then()
                .statusCode(200)
                .body(not(containsString("todo-" + todoId)));
    }

    @Test
    @DisplayName("日本語タイトルが文字化けせず往復できることを検証する")
    void japaneseTitleRoundTrip() {
        String title = "資料をレビューする";

        String html = given()
                .config(UTF8)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("title", title)
                .when().post("/web/todos")
                .then()
                .statusCode(200)
                .body(containsString(title))
                .extract().asString();

        extractTodoId(html);

        given()
                .when().get("/web/todos")
                .then()
                .statusCode(200)
                .body(containsString(title));
    }

    @Test
    @DisplayName("存在しない ID のトグルは 404 を返す")
    void toggleUnknownReturns404() {
        given()
                .when().put("/web/todos/999999/toggle")
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("タイトルに HTML を含めても XSS 対策としてエスケープされる")
    void titleIsHtmlEscaped() {
        String html = given()
                .config(UTF8)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("title", "<script>alert(1)</script>")
                .when().post("/web/todos")
                .then()
                .statusCode(200)
                .body(not(containsString("<script>alert(1)</script>")))
                .body(containsString("&lt;script&gt;"))
                .extract().asString();

        extractTodoId(html);
    }

    /** HTML の id="todo-123" から Long の ID を取り出す */
    private Long extractTodoId(String html) {
        int idIdx = html.indexOf("id=\"todo-");
        int start = html.indexOf('"', idIdx) + 1;
        int end = html.indexOf('"', start);
        String liId = html.substring(start, end); // todo-123
        String idStr = liId.substring("todo-".length());
        return Long.valueOf(idStr);
    }
}
