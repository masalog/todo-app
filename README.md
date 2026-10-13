# Todoアプリ

Quarkus・HTMX・WebSocket・PostgreSQL を利用したリアルタイム対応の Todo アプリケーションです。  
Todo の追加・完了・削除がリアルタイムで全クライアントへ反映されます ⚡

---

## 🚀 アプリケーション起動方法

### リポジトリ取得

    git clone https://github.com/masalog/todo-app.git
    cd todo-app

### Docker Composeで起動

    docker compose up -d

---

## 🌐 アクセス

    http://localhost:8081

---

## 🛑 停止

    docker compose down

---

## 🔄 再ビルド

    docker compose up -d --build

---

## 🧪 テスト用DBの準備

テストはローカルの PostgreSQL（5433）に接続して実行されます。  
そのため、テスト用DBの環境変数ファイルとコンテナ起動が必要です。

### 1. `.env.test` を作成する

プロジェクト直下に `.env.test` を作成し、以下を記述します。

    POSTGRES_DB=tododb_test
    POSTGRES_USER=test_user
    POSTGRES_PASSWORD=test_pass

※ `src/test/resources/application.properties` の設定と一致している必要があります。

### 2. テスト用 PostgreSQL コンテナを起動する

    docker compose up -d postgres-test

起動後、テスト用DBは `localhost:5433` で待ち受けます。

---

## 🧪 テストの実行方法

Docker Compose が起動している状態で Maven を実行するだけでテストできます。

    docker compose up -d
    mvn test

---

## 📋 ログ確認

アプリケーションログ:

    docker compose logs -f todo-app

PostgreSQLログ:

    docker compose logs -f postgres

---

## ⚡ リアルタイム更新の確認

1. ブラウザを2つ開く  
2. Todoを追加  
3. もう片方の画面へ即時反映されることを確認  
4. 完了・削除もリアルタイム同期

---

## 📈 今後の拡張候補

- ログイン機能  
- ユーザー別Todo管理  
- タグ機能  
- Todo検索機能  
- レスポンシブ対応強化  
- ダッシュボード機能  

---

## 👨‍💻 開発者

学習・ポートフォリオ用のプロジェクトです。  
Java・Quarkus・HTMX・WebSocket・PostgreSQL を利用し、  
モダンなリアルタイムWebアプリケーションの実装を目的としています。
