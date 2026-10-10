package com.example.todo.web;

import com.example.todo.service.TodoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * htmx 用の Web エンドポイント。
 *
 * このクラスは HTTP の入出力（パラメータ受け取り・ステータスコード決定）だけに
 * 専念する薄い層。ビジネスロジックは {@link TodoService} に、HTML 描画は
 * {@link TodoHtmlRenderer} に委譲する。
 *
 * htmx は全リクエストで HTML レスポンスを期待する。追加・削除は一覧全体
 * （renderList）を返し、フォーム/ボタン側は hx-swap=innerHTML でリストを
 * 丸ごと差し替える。これにより空メッセージの表示/非表示が常に正しくなる。
 * フォームは application/x-www-form-urlencoded で送られるため
 * {@code @FormParam} で受ける。
 */
@Path("/web")
@Produces(MediaType.TEXT_HTML)
public class TodoWebResource {

    @Inject
    TodoService service;

    @Inject
    TodoHtmlRenderer renderer;

    /** 一覧（<li> の連なり）を返す。 */
    @GET
    @Path("/todos")
    public String list() {
        return renderer.renderList(service.findAll());
    }

    /**
     * 作成 → リスト全体（<li> の連なり）を返す。
     * フォーム側は hx-swap=innerHTML でリストを丸ごと差し替えるため、
     * 空メッセージの表示/非表示が常に正しくなる。
     */
    @POST
    @Path("/todos")
    public String create(@FormParam("title") String title) {
        service.create(title);
        return renderer.renderList(service.findAll());
    }

    /** 完了状態をトグル → 更新後の <li> 断片を返す（hx-swap=outerHTML で自身を置換）。 */
    @PUT
    @Path("/todos/{id}/toggle")
    public Response toggle(@PathParam("id") Long id) {
        return service.toggle(id)
                .map(todo -> Response.ok(renderer.renderTodo(todo)).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * 削除 → リスト全体（<li> の連なり）を返す。
     * ボタン側は hx-swap=innerHTML でリストを丸ごと差し替えるため、
     * 最後の 1 件を削除したときに空メッセージが正しく表示される。
     */
    @DELETE
    @Path("/todos/{id}")
    public String delete(@PathParam("id") Long id) {
        service.delete(id);
        return renderer.renderList(service.findAll());
    }
}
