# ✅ Todoアプリ

Quarkus・HTMX・WebSocket・PostgreSQL を利用して開発したリアルタイム対応の Todo アプリケーションです。

Todo の追加・完了・削除を行うと、WebSocket を通じて接続中のすべてのクライアントへ即時反映されます。ページ全体のリロードは不要で、複数ブラウザや複数端末間でリアルタイムにTodoを共有できます。

---

## 🎯 主な機能

- ➕ Todo追加
- ✅ Todo完了／未完了切り替え
- 🗑️ Todo削除
- ⚡ HTMXによる部分更新
- 🔄 WebSocketによるリアルタイム同期
- 🐘 PostgreSQLへの永続化
- 🧪 単体テスト・結合テスト対応

---

## 🏗️ システム構成

```text
Browser
   │
   ▼
HTMX
   │
   ▼
Quarkus REST
   │
   ├── Service Layer
   │
   ├── Panache Repository
   │
   ▼
PostgreSQL

WebSocket
   ▲
   │
リアルタイム通知
```

---

## 🛠️ 使用技術

| 分類 | 技術 |
|--------|--------|
| Language | Java 21 |
| Framework | Quarkus 3.28 |
| Frontend | HTMX |
| Realtime | WebSocket |
| ORM | Hibernate ORM Panache |
| Database | PostgreSQL |
| Build Tool | Maven |
| Test | JUnit5 |
| Mock | Mockito |
| API Test | RestAssured |
| Utility | Lombok |

---

## 📂 フォルダ構成

```text
todo-app
├─ .gitignore
├─ README.md
├─ pom.xml
├─ docker-compose.yml
├─ Dockerfile
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
│  │     ├─ META-INF
│  │     │  └─ resources
│  │     │     ├─ index.html
│  │     │     ├─ htmx.min.js
│  │     │     ├─ app.js
│  │     │     └─ styles.css
│  │     └─ application.properties
│  └─ test
│     └─ java
│        └─ com/example/todo
└─ target
```

---

## 🚀 起動方法

### 1. リポジトリを取得

```bash
git clone https://github.com/masalog/todo-app.git
cd todo-app
```

### 2. Docker Composeで起動

```bash
docker compose up -d
```

初回起動時は以下が自動で実行されます。

- PostgreSQLコンテナ作成
- Todoアプリコンテナ作成
- Dockerイメージビルド
- データベース接続

### 3. 起動確認

```bash
docker compose ps
```

---

## 🌐 アクセス

```text
http://localhost:8081
```

---

## 🛑 停止

```bash
docker compose down
```

---

## 🔄 再ビルド

ソースコード変更後に再ビルドする場合

```bash
docker compose up -d --build
```

---

## 📋 ログ確認

アプリケーションログ

```bash
docker compose logs -f todo-app
```

PostgreSQLログ

```bash
docker compose logs -f postgres
```

---

## 🧪 テスト実行

```bash
mvn test
```

---

## 🔄 リアルタイム更新の確認

1. ブラウザを2つ開く
2. Todoを追加する
3. もう片方の画面へ即時反映されることを確認
4. 完了・削除操作もリアルタイムで同期されることを確認

---

## 📈 今後の拡張候補

- 🔐 ログイン機能
- 👤 ユーザー別Todo管理
- 🏷️ タグ機能
- 🔍 Todo検索機能
- 📱 レスポンシブ対応強化
- ☸ Kubernetes対応
- ☁ AWSデプロイ
- 📊 ダッシュボード機能

---

## 👨‍💻 開発者

GitHub公開用の学習・ポートフォリオプロジェクトです。

Java・Quarkus・HTMX・WebSocket・PostgreSQLを利用し、モダンなリアルタイムWebアプリケーションの実装を目的として開発しています。
