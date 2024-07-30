# OpenShift に環境をデプロイする方法


## コンテナレジストリを公開

みなさんの端末とOpenShiftの両方からアクセス可能なコンテナレジストリを準備します。
ここでは、OpenShift の内部にあるレジストリを、OpenShift の外からもアクセスできるように公開します。

Linux
```shell
oc patch configs.imageregistry.operator.openshift.io/cluster --patch '{"spec":{"defaultRoute":true}}' --type=merge
export HOST=$(oc get route default-route -n openshift-image-registry --template={{.spec.host}})
oc get secret -n openshift-ingress  router-certs-default -o go-template='{{index .data "tls.crt"}}' | base64 -d | sudo tee /etc/pki/ca-trust/source/anchors/${HOST}.crt  > /dev/null
sudo update-ca-trust enable
sudo podman login -u kubeadmin -p $(oc whoami -t) $HOST
```

## プロジェクトを作成

このアプリケーションをデプロイするための、OpenShiftのプロジェクトを作成します。
プロジェクト名は `cloud-native-monolith` とします。

```shell
export PROJECT=cloud-native-monolith
oc new-project ${PROJECT}
```

## Operatorをインストール

OpenShiftのコンソールから、JBoss EAPとData GridのOperatorをインストールします。


## コンテナイメージを作成

コンテナファイルは、作成したいアプリケーションによって異なります。
すでにどこでも動くように対応済みなアプリケーションの場合は`Containerfile-solution`を使用します。
これは、ソースコードとして `src/solution` を使用します。
ワークショップなどでどこでも動くように対応中の場合は、`Containerfile-dev`を使用します。
これはソースコードとして `src/main` を使用します。

Linux
```shell
export CONTAINERFILE=Containerfile-solution
podman build -t ${HOST}/${PROJECT}/cloud-native-monolith -f ${CONTAINERFILE}
podman push ${HOST}/${PROJECT}/cloud-native-monolith
```

## デプロイ

最後に、アプリケーションとそれが依存する環境を全てデプロイします。

Linux
```shell
cd deploy/openshift
bash deploy.bat
```

URLを取得します。
```shell
oc get route route -n cloud-native-monolith --template={{.spec.host}}
```

ブラウザでこの URL にアクセスすると、目次が表示されます。

