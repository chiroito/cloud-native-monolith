# Cloud Native Monolith
これは、Jakarta EEアプリケーションをクラウドに対応するためのサンプルアプリケーションです。
クラウドに対応していないアプリケーションを、クラウドでも動かせるようにします。

クラウドに対応していないソースコードは、`src/main/java`にあります。
クラウドに対応したソースコードは、`src/solution/java`にあります。

このアプリケーションは、RDBMSとしてPostgreSQLを必要とし、一部の処理で外部のマイクロサービスを必要とします。
これらのPostgreSQLと外部のマイクロサービスは、コンテナイメージを使用して構築できます。


このREADMEは、以下の流れを紹介します。

* アプリケーションをビルド
* アプリケーションをデプロイ

## X. アプリケーションをビルド

ここではアプリケーションのビルドは以下の３つの作業です。

* アプリケーションのソースコードから Web Archive (WAR) ファイル を作成
* アプリケーションサーバを構築
* コンテナイメージを作成

### X.1 WARの作成とアプリケーションサーバを構築

WARの作成と、アプリケーションサーバの構築は、Mavenを使います。
デフォルトでは、クラウドに対応していないソースコードがビルドされます。
クラウドに対応したソースコードをビルドするには`solution`プロファイルを使用します。
これは、Mavenの実行に`-P solution`を追加して実行します。

アプリケーションをビルドすると、JBoss EAP 8.0も自動的に構成されます。
JBoss EAPをダウンロードし、手動で構築する必要はありません。
自動で構築されたJBoss EAPは、PostgreSQLに接続するドライバを含みます。
これは、Mavenが使用する`pom.xml`にPostgreSQLのドライバを含むように設定しているためです。
これは、JBoss EAPの昨日であるGalleonの`postgresql-datasource`レイヤによって実現しています。

このレイヤは、アプリケーションのビルドをする前に、PostgreSQLのドライバのバージョンを環境変数を必要とします。
```shell
export POSTGRESQL_DRIVER_VERSION=42.7.3
./mvnw package
```

### X.2 コンテナイメージをビルド

コンテナイメージの作成は、ビルドのあとに`Containerfile`を使用します。
`Containerfile`はこのプロジェクトのルートディレクトリにあります。

```shell
podman build -t cloud-native-monolith .
```
これを実行すると、アプリケーションサーバを構築するビルダが実行され、最終的にアプリケーションサーバにアプリケーションをデプロイしたコンテナイメージが作成されます。


## Y. アプリケーションをデプロイ

アプリケーションを動かすには、PostgreSQLと外部のマイクロサービスを起動してからアプリケーションサーバを起動します。


### Y.1 ローカルの開発環境でローカルプロセスとしてアプリケーションを動かす

これは、開発をする環境での構築方法です。
事前に、[WARの作成とアプリケーションサーバを構築](#x1-warの作成とアプリケーションサーバを構築)の手順にあるようにアプリケーションとアプリケーションサーバを構築します。

* PostgreSQLと外部のマイクロサービスを起動
* アプリケーションサーバを起動

PostgreSQLと外部のマイクロサービスは、`docker-compose`を使用して次のように起動します。
```
docker-compose -f container-compose-dev.yml up
```

アプリケーションサーバの起動は、IDEが提供する方法で設定します。
もしIDEを使用していない場合は、ビルドして作成されたWARをアプリケーションサーバにデプロイして、アプリケーションサーバを起動します。

```shell
cp target/ROOT.war target/server/standalone/deployments
target/server/bin/standalone.sh
```



### Y.2 Podmanにデプロイする










### Y.3 OpenShiftにデプロイする



















### JBoss EAP の起動時に必要な環境変数
* POSTGRESQL_DATABASE - PostgreSQL内のデータベースの名前
* POSTGRESQL_SERVICE_HOST - データベースが動くホスト名
* POSTGRESQL_SERVICE_PORT - データベースが待ち受けるポート番号
* POSTGRESQL_USER - データベースに接続するユーザ名
* POSTGRESQL_PASSWORD - データベースに接続するユーザのパスワード
* POSTGRESQL_DATASOURCE - JBoss EAP 上のデータソースの名前 (myDatasource)

```
oc new-app postgresql-ephemeral \
-p DATABASE_SERVICE_NAME=todo-backend-db \
-p POSTGRESQL_DATABASE=todos
```

## 参考
https://github.com/jboss-developer/jboss-eap-quickstarts/tree/8.0.x/todo-backend


##