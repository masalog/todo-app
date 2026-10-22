package com.example.todo.web;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * LoginHtmlRenderer の実装。
 *
 * HTML 文字列の生成のみを担当する。
 * 業務ロジックは保持しない。
 */
@ApplicationScoped
public class LoginHtmlRendererImpl
        implements LoginHtmlRenderer {

    /**
     * ログイン成功メッセージを生成する。
     *
     * @return 成功メッセージ HTML
     */
    @Override
    public String renderLoginSuccess() {
        return """
            <p class="success">
                ログインしました。
            </p>
            """;
    }

    /**
     * ログイン失敗メッセージを生成する。
     *
     * @return 失敗メッセージ HTML
     */
    @Override
    public String renderLoginFailed() {
        return """
            <p class="error">
                ユーザー名またはパスワードが正しくありません。
            </p>
            """;
    }
}