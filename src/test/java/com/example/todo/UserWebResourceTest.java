package com.example.todo;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
@DisplayName("User Web エンドポイントの結合テスト")
class UserWebResourceTest {

    private static final RestAssuredConfig UTF8 = RestAssuredConfig.config()
            .encoderConfig(EncoderConfig.encoderConfig()
                    .defaultContentCharset("UTF-8")
                    .appendDefaultContentCharsetToContentTypeIfUndefined(true));

    @Test
    @DisplayName("ユーザー登録の成功と重複失敗を HTML で返す")
    void registrationReturnsSuccessAndDuplicateFeedback() {
        String username = "test-" + UUID.randomUUID();

        given()
                .config(UTF8)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("username", username)
                .formParam("password", "password123")
                .when().post("/web/users/register")
                .then()
                .statusCode(200)
                .contentType(containsString("text/html"))
                .body(containsString("ユーザー登録が完了しました。"));

        given()
                .config(UTF8)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("username", username)
                .formParam("password", "password123")
                .when().post("/web/users/register")
                .then()
                .statusCode(200)
                .contentType(containsString("text/html"))
                .body(containsString("登録できませんでした。"));
    }

    @Test
    @DisplayName("無効な登録入力も画面に失敗メッセージを返す")
    void invalidRegistrationReturnsFeedback() {
        given()
                .config(UTF8)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("username", "valid-user")
                .formParam("password", "short")
                .when().post("/web/users/register")
                .then()
                .statusCode(200)
                .contentType(containsString("text/html"))
                .body(containsString("登録できませんでした。"));
    }
}
