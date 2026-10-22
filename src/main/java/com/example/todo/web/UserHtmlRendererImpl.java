package com.example.todo.web;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * User 関連 HTML 描画の実装。
 *
 * HTML 文字列の生成のみを担当する。
 * 業務ロジックは保持しない。
 */
@ApplicationScoped
public class UserHtmlRendererImpl
        implements UserHtmlRenderer {

    /**
     * ユーザー登録成功メッセージを生成する。
     *
     * @return 成功メッセージ HTML
     */
    @Override
    public String renderRegisterSuccess() {
        return """
            <p>ユーザー登録が完了しました。</p>
            """;
    }

    /**
     * ユーザー登録失敗メッセージを生成する。
     *
     * @return 失敗メッセージ HTML
     */
    @Override
    public String renderRegisterFailed() {
        return """
            <p>ユーザー名が既に使用されています。</p>
            """;
    }
}