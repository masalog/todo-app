package com.example.todo.web;

/**
 * Login 関連の HTML 描画の契約。
 *
 * Resource はこのインターフェースに依存する。
 * 実装は LoginHtmlRendererImpl。
 */
public interface LoginHtmlRenderer {

    /** ログイン成功メッセージを生成する。 */
    String renderLoginSuccess();

    /** ログイン失敗メッセージを生成する。 */
    String renderLoginFailed();
}
