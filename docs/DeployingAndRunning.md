# Deploying and running example application on k8s - exercise

## Using minikube as docker engine

Minikube uses docker as containter engine so you can use it as a local docker machine.
To use docker daemon running on minikube (for example to build image, run container itp.):
```bash
eval $(minikube docker-env)
```
or on fish:
```bash
eval (minikube docker-env)
```
It will automatically recognize your shell and use commands specific to it.

## Building docker image

The example application uses [sbt-native-packager](https://github.com/sbt/sbt-native-packager) sbt plugin to build and publish docker image. 
To build example application docker image run:
```bash
sbt docker:publishLocal
```

It will complile the app and create the docker image on docker machine (minikube in this case).

## Deployment using helm

Run helm command:
```bash
helm upgrade \
--install app \
helm-examples/charts/kubernetes-fundamentals
```

## Exercise: check all created k8s objects

1. Pod
1. Deployment
1. Service

## Exercise: scale application to 5 replicas

Using helm scale example application to run 5 pods.
