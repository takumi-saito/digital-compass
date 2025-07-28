# CLAUDE.md

このファイルは、このリポジトリでコードを扱う際の Claude Code (claude.ai/code) へのガイダンスを提供します。

## プロジェクト概要

これは Kotlin と Jetpack Compose を使用した Android アプリケーションです。 Material Design 3 と Compose UI に重点を置いた最新の Android アプリとしてセットアップされています。

## ビルドと開発コマンド

### ビルドコマンド
```bash
# デバッグ APK をビルド
./gradlew assembleDebug

# リリース APK をビルド
./gradlew assembleRelease

# クリーンビルド
./gradlew clean

# 接続されたデバイスにビルド&インストール
./gradlew installDebug
```

### テスト
```bash
# ユニットテストを実行
./gradlew test

# インストルメンテーションテストを実行（デバイス/エミュレータが必要）
./gradlew connectedAndroidTest

# 特定のテストクラスを実行
./gradlew test --tests "t.saito.digitalcompass.ExampleUnitTest"
```

### コード品質
```bash
# Lint チェックを実行
./gradlew lint

# Lint 結果を確認
# 結果は次の場所にあります: app/build/reports/lint-results-debug.html
```

## アーキテクチャ

### 技術スタック
- **言語**: Kotlin 2.0.21
- **UI フレームワーク**: Jetpack Compose と Material 3
- **ビルドシステム**: Gradle （ Kotlin DSL 使用）
- **最小 SDK**: 31 （ Android 12 ）
- **ターゲット SDK**: 35 （ Android 15 ）

### プロジェクト構造
- **app/src/main/java/t/saito/digitalcompass/** - メインアプリケーションコード
  - `MainActivity.kt` - Compose を使用したエントリポイント
  - `ui/theme/` - Compose テーマ設定（ Color 、 Type 、 Theme ）
- **app/src/main/res/** - Android リソース
  - アプリ設定用の XML リソース
  - アイコン用の drawable と mipmap リソース
- **gradle/libs.versions.toml** - 依存関係のバージョンカタログ

### 主要な依存関係
- AndroidX Core KTX
- Lifecycle Runtime KTX
- Activity Compose
- Compose BOM (Bill of Materials)
- Material 3 Components

## 開発ノート

### Compose UI パターン
このアプリは以下の標準的な Compose パターンを使用しています：
- MainActivity 内の `setContent` で UI を初期化
- `DigitalCompassTheme` ラッパーで一貫したテーマを適用
- `Scaffold` で Material Design 構造を実現
- 開発用の Preview アノテーション

### Gradle 設定
- 依存関係管理にバージョンカタログ（ libs.versions.toml ）を使用
- ビルドスクリプトに Kotlin DSL を使用
- Java 11 互換性