# ローカルで開発する方法

ローカルで開発する方法は、ワークショップで使うことを想定しています。
このワークショップは、オンプレミスにデプロイすることだけを想定したモノリスアプリケーションを、どこでも動くモノリスアプリケーションに変更することを体験できます。

## 開発環境

本アプリケーションの開発環境は、以下の3つが必要です。

* JBoss EAP
* PostgreSQL
* マイクロサービスアプリ

JBoss EAP は、アプリケーションのビルド時に一緒に自動で構築されます。
PostgreSQLとマイクロサービスアプリは、`docker-compose`で構築できます。
開発環境は、想定している開発PCのリソースの都合上、セッション情報をセッションストアに格納しません。
セッション情報は JBoss EAP の内部に保存されます。

## ソースコード

ソースコードは 2 種類あります。

* クラウドに対応していないソースコード
* クラウドに対応したソースコード

クラウドに対応していないソースコードは、`src/main/java`にあります。
クラウドに対応したソースコードは、`src/solution/java`にあります。

## アプリケーションをビルド

アプリケーションのビルドは Maven を使います。
アプリケーションをビルドすると、JBoss EAP 8.0も自動的に構成されます。
JBoss EAPをダウンロードし、手動で構築する必要はありません。
自動で構築されたJBoss EAPは、PostgreSQLに接続するドライバを含みます。
これは、Mavenが使用する`pom.xml`にPostgreSQLのドライバを含むように設定しているためです。
これは、JBoss EAPの機能であるGalleonの`postgresql-datasource`レイヤによって実現しています。

このレイヤは、アプリケーションのビルドをする前に、PostgreSQLのドライバのバージョンを環境変数を必要とします。
```shell
export POSTGRESQL_DRIVER_VERSION=42.7.3
./mvnw package
```

デフォルトでは、クラウドに対応していないソースコードがビルドされます。
クラウドに対応したソースコードをビルドするには`solution`プロファイルを使用します。
これは、Mavenの実行に`-P solution`を追加して実行します。


## アプリケーションを確認

アプリケーションを動かすには、PostgreSQLと外部のマイクロサービスを起動してからアプリケーションサーバを起動します。
このリポジトリでは、これらをコンテナイメージを使って簡単に起動できるようにしています。
PostgreSQLとマイクロサービスは、`docker-compose`を使用して次のように起動します。

```
docker-compose -f deploy\compose\container-compose-dev.yml up
```

この`docker-compose` では、PostgreSQL は、ローカルホストの `tcp:5432` で待ち受け、マイクロサービスは、ローカルホストの `tcp:8080` で待ち受けます。

アプリケーションサーバの起動は、環境変数を設定した上で、お好きな方法で起動してください。
IDEと連携しても、`standalone.bat`や`standalone.sh`を直接実行しても大丈夫です。
自動で構築されたJBoss EAPは、`target/server`にあります。
アプリケーションは、`target/server/standalone/deployments`に`ROOT.war`としてデプロイされています。

必要な環境変数は以下のとおりです。

* POSTGRESQL_DATABASE - PostgreSQL内のデータベースの名前
* POSTGRESQL_USER - データベースに接続するユーザ名
* POSTGRESQL_PASSWORD - データベースに接続するユーザのパスワード
* POSTGRESQL_SERVICE_HOST - データベースが動くホスト名
* POSTGRESQL_SERVICE_PORT - データベースが待ち受けるポート番号
* MICROSERVICE_HOSTNAME - マイクロサービスのホスト名とポート番号

`docker-compose` を使って環境を構築した場合は、以下のとおり環境変数を設定して、JBoss EAP を起動します。

Windows
```shell
set POSTGRESQL_DATABASE=user
set POSTGRESQL_USER=user
set POSTGRESQL_PASSWORD=pw
set POSTGRESQL_SERVICE_HOST=localhost
set POSTGRESQL_SERVICE_PORT=5432
set MICROSERVICE_HOSTNAME=localhost:8082
target\server\bin\standalone.bat
```

Linux
```shell
export POSTGRESQL_DATABASE=user
export POSTGRESQL_USER=user
export POSTGRESQL_PASSWORD=pw
export POSTGRESQL_SERVICE_HOST=localhost
export POSTGRESQL_SERVICE_PORT=5432
export MICROSERVICE_HOSTNAME=localhost:8082
target/server/bin/standalone.sh
```

ブラウザで [http://localhost:8080/](http://localhost:8080/) にアクセスすると、目次が表示されます。


## コンテナイメージをビルド

コンテナイメージのビルドは、`Containerfile-dev`を使用します。
`Containerfile-dev`はこのプロジェクトのルートディレクトリにあります。

```shell
podman build -t cloud-native-monolith -f Containerfile-dev
```
これを実行すると、アプリケーションサーバを構築するビルダが実行され、最終的にアプリケーションサーバにアプリケーションをデプロイしたコンテナイメージが作成されます。



## 参考
https://github.com/jboss-developer/jboss-eap-quickstarts/tree/8.0.x/todo-backend

