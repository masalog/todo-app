package com.example.todo.web;

import com.example.todo.domain.Todo;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

/**
 * {@link TodoHtmlRenderer} の実装（HTML 文字列を組み立てる）。
 *
 * HTML の組み立てと XSS エスケープをここに集約し、Resource / Service を
 * HTML の詳細から切り離す（Hono 版の renderTask / templates.ts 相当）。
 */
@ApplicationScoped
public class TodoHtmlRendererImpl implements TodoHtmlRenderer {

    /** 一覧（<li> の連なり）。空なら空メッセージを返す。 */
    @Override
    public String renderList(List<Todo> todos) {
        if (todos.isEmpty()) {
            return "<li class=\"empty\">まだ Todo がありません。上のフォームから追加してください。</li>";
        }
        StringBuilder sb = new StringBuilder();
        for (Todo todo : todos) {
            sb.append(renderTodo(todo));
        }
        return sb.toString();
    }

    /**
     * Todo 1 件を <li> 断片として描画する。
     * - チェックボックス（トグル）は PUT /web/todos/{id}/toggle を叩き、
     *   自身（outerHTML）を置換する（件数は変わらないため個別置換でよい）。
     * - 削除ボタンは DELETE /web/todos/{id} を叩き、リスト全体（#todo-list の
     *   innerHTML）を差し替える（最後の 1 件を消したとき空メッセージを出すため）。
     * - XSS 対策としてタイトルを HTML エスケープする。
     */
    @Override
    public String renderTodo(Todo todo) {
        String liId = "todo-" + todo.getId();
        String doneClass = todo.isCompleted() ? " done" : "";
        String checked = todo.isCompleted() ? " checked" : "";
        String safeTitle = escapeHtml(todo.getTitle());

        return """
            <li id="%s" class="todo-item%s">
              <input type="checkbox" class="toggle"%s
                     hx-put="/web/todos/%s/toggle"
                     hx-target="#%s"
                     hx-swap="outerHTML">
              <span class="todo-title">%s</span>
              <button class="btn-delete"
                      hx-delete="/web/todos/%s"
                      hx-target="#todo-list"
                      hx-swap="innerHTML"
                      hx-confirm="削除しますか？">削除</button>
            </li>
            """.formatted(liId, doneClass, checked,
                          todo.getId(), liId,
                          safeTitle,
                          todo.getId());
    }

    /** 最低限の HTML エスケープ（XSS 対策）。 */
    private String escapeHtml(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
