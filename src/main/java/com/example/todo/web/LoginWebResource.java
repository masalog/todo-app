package com.example.todo.web;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.nio.charset.StandardCharsets;

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

    /**
     * ユーザー名の存在有無による応答時間の差を減らすための
     * ダミー BCrypt ハッシュ。
     *
     * ユーザーが存在しない場合でも BCrypt 検証を実行し、
     * ユーザー列挙攻撃（User Enumeration）の難易度を上げる。
     */
    private static final String DUMMY_HASH =
            "$2a$12$WBAUkSTbaPdF2oZTcaEWR.nM0GHs3gz1/AYsdS5MyP4S8i4p0fkhK";

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

        /*
         * クライアント側の入力検証だけに依存せず、
         * サーバー側でも入力値を検証する。
         *
         * null の場合でも BCrypt を実行できるよう、
         * 空文字へ正規化する。
         */
        String safeUsername =
                username != null ? username : "";

        String safePassword =
                password != null ? password : "";

        boolean tooLong =
                safePassword.getBytes(StandardCharsets.UTF_8).length > 72;

        char[] candidate =
                tooLong ? new char[0] : safePassword.toCharArray();
        
        User user = service
                .findByUsername(safeUsername)
                .orElse(null);

        /*
         * ユーザーが存在しない場合でもダミーハッシュに対して
         * BCrypt 検証を実行する。
         *
         * これにより、
         * 「存在するユーザー」
         * 「存在しないユーザー」
         * の応答時間の差を減らし、
         * ユーザー名の推測を困難にする。
         */
        String hash =
                user != null
                        ? user.getPasswordHash()
                        : DUMMY_HASH;

        /*
         * 常に BCrypt 検証を実行する。
         */
        boolean verified =
                BCrypt.verifyer(
                        BCrypt.Version.VERSION_2A,
                        at.favre.lib.crypto.bcrypt.LongPasswordStrategies.none())
                        .verify(
                                candidate,
                                hash)
                        .verified;

        /*
         * ユーザー名未入力、
         * パスワード未入力、
         * ユーザー不存在、
         * パスワード不一致
         * のすべてを同一の認証失敗として扱う。
         */
        if (safeUsername.isBlank()
                || safePassword.isBlank()
                || tooLong
                || user == null
                || !verified) {
            return renderer.renderLoginFailed();
        }

        return renderer.renderLoginSuccess();
    }
}