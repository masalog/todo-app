package com.example.todo.web;

import com.example.todo.service.UserService;

import jakarta.inject.Inject;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * htmx 用のユーザー関連 Web エンドポイント。
 *
 * このクラスは HTTP の入出力
 * （パラメータ受け取り・レスポンス返却）
 * のみを担当する。
 *
 * ビジネスロジックは UserService に、
 * HTML 描画は UserHtmlRenderer に委譲する。
 *
 * フォームは application/x-www-form-urlencoded で送信されるため、
 * @FormParam で受け取る。
 */
@Path("/web/users")
@Produces(MediaType.TEXT_HTML)
public class UserWebResource {

    @Inject
    UserService service;

    @Inject
    UserHtmlRenderer renderer;

    /**
     * ユーザー登録を行う。
     *
     * 登録完了後は結果メッセージを返す。
     */
    @POST
    @Path("/register")
    public String register(
            @FormParam("username") String username,
            @FormParam("password") String password) {

        /*
         * htmx が 4xx 応答を通常は差し替えないため、
         * 入力エラーも HTML の成功応答として返す。
         */
        if (username == null || username.isBlank()
                || username.length() > 255
                || password == null
                || password.isBlank()
                || password.length() < 8
                || password.length() > 72) {
            return renderer.renderRegisterFailed();
        }

        /*
         * 登録処理を実行する。
         *
         * ユーザー名重複や競合により登録できなかった場合は
         * 登録失敗として扱う。
         */
        if (!service.create(username, password)) {
            return renderer.renderRegisterFailed();
        }

        return renderer.renderRegisterSuccess();
    }
}