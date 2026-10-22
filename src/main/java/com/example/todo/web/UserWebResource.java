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
 * このクラスは HTTP の入出力（パラメータ受け取り・レスポンス返却）
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

        if (service.findByUsername(username).isPresent()) {
            return renderer.renderRegisterFailed();
        }

        service.create(username, password);

        return renderer.renderRegisterSuccess();
    }
}