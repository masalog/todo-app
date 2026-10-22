package com.example.todo.web;

/**
 * User 関連の HTML 描画の契約。
 *
 * Resource はこのインターフェースに依存する。
 * 実装は UserHtmlRendererImpl。
 */
public interface UserHtmlRenderer {

    /** ユーザー登録成功メッセージを生成する。 */
    String renderRegisterSuccess();

    /** ユーザー登録失敗メッセージを生成する。 */
    String renderRegisterFailed();
}
