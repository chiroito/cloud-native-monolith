OpenShift に環境をデプロイします。
コンテナイメージはすでに公開されているものを使用します。

```shell
cd deploy/openshift
# OpenShift のプロジェクトを作成してください。
# 作成したプロジェクトに oc project してください。
# OpenShift のコンソールで JBoss EAP と Data Grid の Operator をインストールしてください

# Windowsの場合
deploy.bat

# Mac Linuxの場合
bash deploy.bat
```