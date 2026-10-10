# ✅ Todo アプリ

Quarkus・HTMX・H2 を使って作ったシンプルな Todo アプリです。

Todo の追加・完了・削除ができ、データは H2 Database に保存されるため、アプリを再起動しても内容は保持されます。

WebSocketを実装しており、複数端末からリアルタイムで H2 Database にアクセスできます。

## 🎯 できること

- ➕ Todo追加
- ✅ 完了チェック
- 🗑️ Todo削除
- 💾 データ保存
- ⚡ ページリロード不要
- 🔄 WebSocketによるリアルタイム更新

## 🛠️ 使用技術

- ☕ Java 21
- 🚀 Quarkus 3.28
- ⚡ HTMX
- 🔌 WebSocket
- 🗄️ Hibernate ORM Panache
- 💾 H2 Database
- 🧪 JUnit5
- 🎭 Mockito
- ✅ RestAssured

## 📂 フォルダ構成

```text
todo-app
├─ .gitignore
├─ README.md
├─ pom.xml
├─ src
│  ├─ main
│  │  ├─ java
│  │  │  └─ com/example/todo
│  │  │     ├─ domain
│  │  │     │  └─ Todo.java
│  │  │     ├─ repository
│  │  │     │  └─ TodoRepository.java
│  │  │     ├─ service
│  │  │     │  ├─ NotificationService.java
│  │  │     │  ├─ NotificationServiceImpl.java
│  │  │     │  ├─ TodoService.java
│  │  │     │  └─ TodoServiceImpl.java
│  │  │     ├─ web
│  │  │     │  ├─ TodoHtmlRenderer.java
│  │  │     │  ├─ TodoHtmlRendererImpl.java
│  │  │     │  └─ TodoWebResource.java
│  │  │     └─ websocket
│  │  │        ├─ SessionManager.java
│  │  │        ├─ TodoEndpoint.java
│  │  │        └─ TodoWebSocket.java
│  │  └─ resources
│  │     ├─ application.properties
│  │     └─ META-INF
│  │        └─ resources
│  │           ├─ htmx.min.js
│  │           ├─ index.html
│  │           ├─ styles.css
│  │           └─ websocket.js
│  └─ test
│     ├─ java
│     │  └─ com/example/todo
│     │     ├─ TodoWebResourceTest.java
│     │     ├─ service
│     │     │  ├─ NotificationServiceImplTest.java
│     │     │  └─ TodoServiceImplTest.java
│     │     └─ websocket
│     │        ├─ SessionManagerTest.java
│     │        ├─ TodoEndpointTest.java
│     │        └─ TodoWebSocket.java
│     └─ resources
│        └─ application.properties
└─ data
   ├─ todo.mv.db
   └─ todo.trace.db
```

## 🚀 起動方法

### 開発モード

```bash
mvn quarkus:dev
```

ブラウザで開く

```text
http://localhost:8081/
```

## 💾 データ保存先

```text
data/
├─ todo.mv.db
└─ todo.trace.db
```

データをリセットしたい場合は削除してください。

## 🧪 テスト実行

```bash
mvn test
```

## 📄 ライセンス

MIT License