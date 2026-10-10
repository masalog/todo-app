# ✅ Todo アプリ

Quarkus・HTMX・H2 を使って作ったシンプルな Todo アプリです。

Todo の追加・完了・削除ができます。

データは H2 Database に保存されるため、アプリを再起動しても内容は保持されます。

---

# 🎯 できること

- ➕ Todo追加
- ✅ 完了チェック
- 🗑️ Todo削除
- 💾 データ保存
- ⚡ ページリロード不要

---

# 🛠️ 使用技術

- ☕ Java 21
- 🚀 Quarkus
- ⚡ HTMX
- 🗄️ Hibernate ORM Panache
- 💾 H2 Database
- 🧪 Mockito / RestAssured

---

# 🖥️ デスクトップから起動

毎回 URL を入力する代わりに、Windows のデスクトップにショートカットを作成しておくと便利です。
 
## 作成手順
 
① デスクトップで右クリック
 
```text
新規作成
↓
ショートカット
```
 
② 項目の場所に次を入力
 
```text
http://localhost:8081/
```
 
③ ショートカット名を入力
 
```text
Todo
```
 
④ 完了を押す
 
するとデスクトップに
 
```text
Todo
```
 
のアイコンが作成されます。

※事前にアプリを起動しておく必要があります。

---

# 🚀 起動方法

開発モード

```powershell
mvn quarkus:dev
```

ブラウザで開く

```text
http://localhost:8081/
```

---

# 📦 JAR作成

```powershell
mvn clean package
```

生成

```text
target/
└─ todo-app-1.0.0-SNAPSHOT-runner.jar
```

実行

```powershell
java -jar target\todo-app-1.0.0-SNAPSHOT-runner.jar
```

---

# 💾 データ保存先

```text
data/
 ├─ todo.mv.db
 └─ todo.trace.db
```

データをリセットしたい場合は削除してください。

---
