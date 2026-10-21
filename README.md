# Todoアプリ

Quarkus・HTMX・PostgreSQL を利用した Todo アプリケーションです。

Todo の追加・完了・削除が行えます ✅

---

# 🏗️ Docker構成

本プロジェクトでは、本番環境とテスト環境を完全に分離しています。

## 本番環境

```text
compose.prod.yml
│
├─ todo-prod-todo-app-1
└─ todo-prod-postgres-1
```

## テスト環境

```text
compose.test.yml
│
├─ todo-test-postgres-test-1
└─ test-runner（実行時のみ）
```

## 環境分離

| 環境 | Project | DB | User | Network |
|--------|--------|--------|--------|--------|
| 本番 | todo-prod | tododb | todo | todo_prod_network |
| テスト | todo-test | tododb_test | todo_test | todo_test_network |

---

# ⚙️ 環境変数ファイルの準備

本プロジェクトでは `.env` および `.env.test` は Git 管理対象外です。

アプリケーション起動前に、プロジェクトルートへ以下のファイルを作成してください。

```text
.env
.env.test
```

## .env

以下の環境変数を定義してください。

```text
POSTGRES_USER
POSTGRES_PASSWORD
POSTGRES_DB

QUARKUS_DATASOURCE_USERNAME
QUARKUS_DATASOURCE_PASSWORD
QUARKUS_DATASOURCE_JDBC_URL
```

### JDBC URL

```text
jdbc:postgresql://postgres:5432/<database_name>
```

| 項目 | 説明 |
|--------|--------|
| postgres | compose.prod.yml の PostgreSQL サービス名 |
| 5432 | PostgreSQL コンテナのポート |
| database_name | 接続先データベース名 |

---

## .env.test

以下の環境変数を定義してください。

```text
POSTGRES_USER
POSTGRES_PASSWORD
POSTGRES_DB

QUARKUS_DATASOURCE_USERNAME
QUARKUS_DATASOURCE_PASSWORD
QUARKUS_DATASOURCE_JDBC_URL
```

### JDBC URL

```text
jdbc:postgresql://postgres-test:5432/<database_name>
```

| 項目 | 説明 |
|--------|--------|
| postgres-test | compose.test.yml の PostgreSQL サービス名 |
| 5432 | PostgreSQL コンテナのポート |
| database_name | 接続先データベース名 |

> **注意**
>
> Docker コンテナ内では `localhost` はコンテナ自身を指します。
>
> PostgreSQL へ接続する場合は、以下のサービス名を利用してください。
>
> 本番:
>
> ```text
> postgres
> ```
>
> テスト:
>
> ```text
> postgres-test
> ```

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

正常時:

```text
todo-prod-postgres-1   Up (healthy)
todo-prod-todo-app-1   Up (healthy)
```

---

# 🌐 アクセス

```text
http://localhost:8081
```

---

# ❤️ ヘルスチェック

## PostgreSQL

確認:

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

# 🧪 テスト環境

## テスト用DB起動

テスト実行前に PostgreSQL を起動します。

```bash
docker compose -f compose.test.yml up -d postgres-test
```

状態確認:

```bash
docker compose -f compose.test.yml ps
```

正常時:

```text
todo-test-postgres-test-1   Up (healthy)
```

---

## テスト実行

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

# 🔄 再ビルド

```bash
docker compose -f compose.prod.yml up -d --build
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

# 🔒 セキュリティ / 運用

本プロジェクトでは以下を実施しています。

- PostgreSQLポート非公開
- 本番・テスト完全分離
- Docker Network分離
- Docker Volume分離
- Database/User分離
- 非rootユーザー実行
- PostgreSQL Health Check
- Quarkus Health Check
- restart: unless-stopped
- マルチステージビルド
- テストランナー分離
- Docker Health Check による起動順制御
- 最小権限の原則に基づくコンテナ実行

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

技術スタック:

- Java 21
- Quarkus
- HTMX
- PostgreSQL
- Docker Compose

を利用し、モダンなWebアプリケーションの実装を目的としています。