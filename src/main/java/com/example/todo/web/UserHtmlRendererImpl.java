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
            <p role="status">ユーザー登録が完了しました。</p>
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
            <p role="alert">登録できませんでした。入力内容を確認するか、別のユーザー名をお試しください。</p>
            """;
    }
}