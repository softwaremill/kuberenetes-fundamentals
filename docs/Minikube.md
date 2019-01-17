# Minikube operations

Do not forget about completion. Add `source <(minikube completion <shell>)` to relevant shell configuration file.

## Frequently used commands
1. To start a local k8s cluster type `minikube start`. There is a possibility to configure starting parameters e.g. `minikube start --memory 8192`. This can be crucial with larger deployments.
1. `minikube delete` to delete and destroy the cluster.
1. `minikube stop` to stop but not destroy the cluster. 
1. `minikube ssh` to open an SSH connection to the minikube instance. 
1. `minikube mount` to mount a directory into minikube.
1. `minikube dashboard` to open the dashboard in the default browser

## Docker handling

There is a possibility to treat local docker repository as a minikube repository i.e. set docker environmental variables by running `eval $(minikube docker-env)`

## Exercise

1. Run minikube using 4GB of memory
1. Start adn delete a minikube cluster
1. SSH minikube
1. Expose a service
1. Familiarize yourself with the dashboard

## Notes
1. Using minikube can also be tricky. Remember that you are always utilizing your own machine and therefore have limited resources. It is really easy to drain all the memory, especially when starting using Helm and deploying everything that is possible. Without allocated resources the scheduler cannot work and minikube can become unresponsive e.g. the dashboard can freeze.
1. When starting using minikube you should move all your deployment there. Many people are moving from Docker Compose and it could be tempting to utilize your seasoned compose files and try to integrate both solutions. There is a tool called [kompose](https://github.com/kubernetes/kompose) which will help you in conversion.