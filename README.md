# Counting number of routes in myproject

This is a dummy project which counts number of routes in "myproject" namespace 
using Fabric8 Kubernetes Client v4.11.1 .

You need to have an OpenShift cluster running. Apply Strimzi installation file:
```
oc apply -f 'https://strimzi.io/install/latest?namespace=myproject' -n myproject
```

Project is configured to use `strimzi-cluster-operator` ServiceAccount in order to 
interact with OpenShift API. You can check the deployment fragment:
```
spec:
  template:
    spec:
      serviceAccount: strimzi-cluster-operator
```

## Deploy to OpenShift:

Run OpenShift Maven Plugin goals to deploy:

```
mvn oc:build oc:resource oc:apply
```

Once application is deployed, you can check pod status and then try to hit application:
```
eclipse-jkube-quarkus-sample : $ oc get routes
NAME                           HOST/PORT                                                 PATH      SERVICES                       PORT      TERMINATION   WILDCARD
eclipse-jkube-quarkus-sample   eclipse-jkube-quarkus-sample-myproject.127.0.0.1.nip.io             eclipse-jkube-quarkus-sample   8080                    None
eclipse-jkube-quarkus-sample : $ oc get pods
NAME                                          READY     STATUS      RESTARTS   AGE
eclipse-jkube-quarkus-sample-1-ttl2k          1/1       Running     0          8m
eclipse-jkube-quarkus-sample-s2i-1-build      0/1       Completed   0          8m
my-cluster-entity-operator-5cbc4cff88-xnxpq   3/3       Running     0          26m
my-cluster-kafka-0                            2/2       Running     0          27m
my-cluster-zookeeper-0                        1/1       Running     0          28m
strimzi-cluster-operator-597c48675d-mtqtf     1/1       Running     0          28m
```
Once Pods come in running state, you should be able to curl route:
```
eclipse-jkube-quarkus-sample : $ curl eclipse-jkube-quarkus-sample-myproject.127.0.0.1.nip.io/getroutes
1 Routes found in myproject namespace
```
