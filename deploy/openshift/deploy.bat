oc process postgresql-persistent -n openshift --param-file=postgres.env | oc create -f -
oc create secret generic --from-file=identities.yaml sessionstore-secret
oc apply -f openshift.yaml