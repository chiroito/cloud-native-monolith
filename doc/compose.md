# Podman に環境をデプロイする方法

Podman に環境をデプロイする方法は、アプリケーションがどこでも動くことを体験できます。
アプリケーションを動かすために必要な環境は、すでにコンテナイメージとして用意されています。
アプリケーションをビルドするだけで、すぐに体験できます。

## アプリケーションをビルド

アプリケーションをビルドしてコンテナイメージを作成します。
コンテナイメージのビルドは、`Containerfile-solution`を使用します。
`Containerfile-solution`はこのプロジェクトのルートディレクトリにあります。
この設定ファイルは、アプリケーションがどこでも動くように対応済みのソースコードを使用します。

```shell
podman build -t cloud-native-monolith -f Containerfile-solution
```

これを実行すると、アプリケーションサーバを構築するビルダが実行され、最終的にアプリケーションサーバにアプリケーションをデプロイしたコンテナイメージが作成されます。


## デプロイ

アプリケーションも含め、必要な環境を全て起動します。

```shell
docker-compose -f deploy\compose\container-compose-prod.yml up
```

ブラウザで [http://localhost:8080/](http://localhost:8080/) にアクセスすると、目次が表示されます。
