document.addEventListener("DOMContentLoaded", () => {

  const ws = new WebSocket("ws://localhost:8081/ws/todos");

  ws.onopen = () => {
    console.log("WebSocket OPEN");
  };

  ws.onmessage = (event) => {
    console.log("RAW MESSAGE:", event.data);

    // JSON をパース
    let msg;
    try {
      msg = JSON.parse(event.data);
    } catch (e) {
      console.error("JSON parse error:", e);
      return;
    }

    console.log("PARSED MESSAGE:", msg);

    // イベントの種類で処理を分ける
    switch (msg.event) {

      case "todo.created":
        console.log("TODO が作成されました:", msg.id);
        htmx.trigger("#todo-list", "refresh");
        break;

      case "todo.toggled":
        console.log("TODO の状態が変更されました:", msg.id);
        htmx.trigger("#todo-list", "refresh");
        break;

      case "todo.deleted":
        console.log("TODO が削除されました:", msg.id);
        htmx.trigger("#todo-list", "refresh");
        break;

      default:
        console.warn("未知のイベント:", msg.event);
    }
  };

  ws.onerror = (event) => {
    console.error("WebSocket ERROR", event);
  };

  ws.onclose = () => {
    console.log("WebSocket CLOSE");
  };
});
