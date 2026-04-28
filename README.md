# SrvStopper

あらかじめ指定したプラグインが読み込まれていない場合にサーバを停止する Paper プラグインです。

## 概要

サーバ起動時に、設定で指定したプラグインがすべて有効になっているかを確認します。  
不足しているプラグインがある場合はログに警告を出力し、`force-stop: true` の場合はサーバを自動停止します。

## 動作環境

- Paper 1.18.2 以上
- Java 17 以上

## インストール

[Releases](https://github.com/GiganticMinecraft/SrvStopper/releases) から最新の Jar をダウンロードし、サーバの`plugins/` ディレクトリに配置してください。

## 設定

初回起動時に `plugins/SrvStopper/config.yml` が生成されます。

```yaml
force-stop: true
required-plugins:
  - PluginA
  - PluginB
```

| キー                 | 値                | デフォルト  | 説明                                 |
|--------------------|------------------|--------|------------------------------------|
| `force-stop`       | `true` / `false` | `true` | 指定したプラグインがすべて有効ではない場合にサーバを停止するかどうか |
| `required-plugins` | プラグイン名のリスト       | `[]`   | 起動時に有効であることを確認するプラグイン名の一覧          |

## ビルド

```bash
./gradlew build
```

ビルド成果物は `build/libs/SrvStopper-<version>.jar` に生成されます。

## リリース

1. `gradle.properties` の `version` を新しいバージョン番号に更新してコミット・プッシュします。

   ```properties
   version=x.y.z
   ```

2. GitHub Actions の [Release ワークフロー](https://github.com/GiganticMinecraft/SrvStopper/actions/workflows/release.yml) を手動実行します (`workflow_dispatch`)。

ワークフローが成功すると、`v<version>` タグが作成され、Jar が添付された GitHub Release が自動生成されます。
すでに `gradle.properties` に記載のバージョンと同じ名前のタグが存在している場合、ワークフローは失敗します。

## ライセンス

このプロジェクトのライセンスについては [LICENSE](LICENSE) を参照してください。
