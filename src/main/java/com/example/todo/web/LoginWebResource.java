package com.example.todo.web;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.todo.domain.User;
import com.example.todo.service.UserService;

import jakarta.inject.Inject;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * htmx 用のログイン Web エンドポイント。
 *
 * このクラスは HTTP の入出力
 * （パラメータ受け取り・レスポンス返却）
 * のみを担当する。
 *
 * ビジネスロジックは UserService に、
 * HTML 描画は LoginHtmlRenderer に委譲する。
 */
@Path("/web/users")
@Produces(MediaType.TEXT_HTML)
public class LoginWebResource {

    @Inject
    UserService service;

    @Inject
    LoginHtmlRenderer renderer;

    /**
     * ログインを行う。
     *
     * ユーザー名でユーザーを検索し、
     * BCrypt を利用してパスワードを検証する。
     *
     * @param username ユーザー名
     * @param password 入力されたパスワード
     * @return ログイン結果の HTML
     */
    @POST
    @Path("/login")
    public String login(
            @FormParam("username") String username,
            @FormParam("password") String password) {

        User user = service
                .findByUsername(username)
                .orElse(null);

        if (user == null) {
            return renderer.renderLoginFailed();
        }

        boolean verified =
                BCrypt.verifyer()
                        .verify(
                                password.toCharArray(),
                                user.getPasswordHash())
                        .verified;

        if (!verified) {
            return renderer.renderLoginFailed();
        }

        return renderer.renderLoginSuccess();
    }
}