# Todoアプリ

Quarkus・HTMX・WebSocket・PostgreSQL を利用したリアルタイム対応の Todo アプリケーションです。

Todo の追加・完了・削除がリアルタイムで全クライアントへ反映されます ⚡

---

# 🚀 アプリケーション起動方法

## リポジトリ取得

```bash
git clone https://github.com/masalog/todo-app.git
cd todo-app
```

## 本番環境起動

```bash
docker compose -f compose.prod.yml up -d --build
```

起動確認:

```bash
docker compose -f compose.prod.yml ps
```

---

# 🌐 アクセス

```text
http://localhost:8081
```

---

# 🛑 停止

本番環境停止:

```bash
docker compose -f compose.prod.yml down
```

テスト環境停止:

```bash
docker compose -f compose.test.yml down
```

本番・テスト環境をすべて停止:

```bash
docker compose -f compose.prod.yml down
docker compose -f compose.test.yml down
```

---

# 🔄 再ビルド

```bash
docker compose -f compose.prod.yml up -d --build
```

---

# 🧪 テスト環境

本プロジェクトでは本番環境とテスト環境を完全に分離しています。

| 環境 | Project | DB | User | Network |
|--------|--------|--------|--------|--------|
| 本番 | todo-prod | tododb | todo | todo_prod_network |
| テスト | todo-test | tododb_test | todo_test | todo_test_network |

---

# 🧪 テスト用DB起動

テスト実行前に PostgreSQL を起動します。

```bash
docker compose -f compose.test.yml up -d postgres-test
```

状態確認:

```bash
docker compose -f compose.test.yml ps
```

---

# 🧪 テスト実行

テスト実行専用コンテナ (`test-runner`) を利用します。

```bash
docker compose -f compose.test.yml run --rm test-runner
```

成功例:

```text
Tests run: 22, Failures: 0, Errors: 0, Skipped: 0

BUILD SUCCESS
```

### test-runnerについて

`test-runner` は常駐しません。

```text
コンテナ作成
↓
mvn test 実行
↓
テスト結果出力
↓
コンテナ削除 (--rm)
```

毎回クリーンな環境でテストが実行されます。

---

# ❤️ ヘルスチェック

## PostgreSQL

```bash
docker compose -f compose.prod.yml ps
```

例:

```text
todo-prod-postgres-1   Up (healthy)
```

---

## Quarkus

健全性確認:

```bash
curl http://localhost:8081/q/health
```

レスポンス例:

```json
{
  "status": "UP",
  "checks": [
    {
      "name": "Database connections health check",
      "status": "UP"
    }
  ]
}
```

---

# 📋 ログ確認

アプリケーションログ:

```bash
docker compose -f compose.prod.yml logs -f todo-app
```

PostgreSQLログ:

```bash
docker compose -f compose.prod.yml logs -f postgres
```

テストDBログ:

```bash
docker compose -f compose.test.yml logs -f postgres-test
```

---

# ⚡ リアルタイム更新確認

1. ブラウザを2つ開く
2. Todoを追加する
3. もう片方の画面へ即時反映されることを確認する
4. 完了・削除もリアルタイム同期されることを確認する

---

# 🏗️ Docker構成

## 本番

```text
compose.prod.yml
│
├─ todo-prod-todo-app-1
└─ todo-prod-postgres-1
```

## テスト

```text
compose.test.yml
│
├─ todo-test-postgres-test-1
└─ test-runner（実行時のみ）
```

---

# 🔒 セキュリティ/運用

本プロジェクトでは以下を実施しています。

- PostgreSQLポート非公開
- 本番・テスト完全分離
- Docker Network分離
- Docker Volume分離
- Database/User分離
- 非rootユーザー実行
- Health Check
- restart: unless-stopped
- マルチステージビルド
- テストランナー分離

---

# 📈 今後の拡張候補

- ログイン機能
- ユーザー別Todo管理
- タグ機能
- Todo検索機能
- レスポンシブ対応強化
- ダッシュボード機能
- GitHub ActionsによるCI/CD
- PostgreSQLバックアップ自動化

---

# 👨‍💻 開発者

学習・ポートフォリオ用のプロジェクトです。

- Java 21
- Quarkus
- HTMX
- WebSocket
- PostgreSQL
- Docker Compose

を利用し、モダンなリアルタイムWebアプリケーションの実装を目的としています。