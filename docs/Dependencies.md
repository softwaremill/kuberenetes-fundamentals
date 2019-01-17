# Software and dependencies

## Kubectl

To be able to interact with a Kubernetes cluster you need to acquire its command line tool.

### Installation

The easiest way to start with minikube is to follow the official [kubernets quide](https://kubernetes.io/docs/tasks/tools/install-kubectl/). For Linux machines the most efficient way is to install from the official repository, for macOS you can use Homebrew.

To check whether the installation was successful type `kubectl version`.

### Notes

- Although accessible and great as a CLI tool `kubectl` has, like to whole k8s ecosystem one visible drawback, the versions do change ofter and sometimes carry different, affecting work changes. All team members should be sure that they are using the same version as used to perform operations on a production cluster.

## Minikube

### Installation

The easiest way to start with Minikube is to follow the official [kubernets quide](https://kubernetes.io/docs/tasks/tools/install-minikube/).

To check whether the installation was successful type `minikube version`.

### Notes

- A while ago there was some issues with integration between VirtualBox and Minikube (spotted on Ubuntu 16.04). Use latest possible VirtualBox version. 
- It does not matter whether you install Minikube on Windows, macOS or Linux. 

## Helm

Helm is a Kubernetes package manager. 

### Installation

Follow the official [Helm documentation](https://docs.helm.sh/using_helm/) to install it.

### Notes

- Like k8s Helm version does change often. Be sure that you are using same version as the utility that performs the operation against a production cluster.
- Working with kubernetes without Helm is really difficult. At some point all application platforms reach the level when handling composition of services is crucial. Without Helm it can easily end in implementing the same idea but using different tools i.e utilizing k8s API to perform same work as Helm.

## JQ
Kubernetes objects are eventually described as JSON. The information stored in all kinds of primitives is rich and sometimes overwhelming. To better handle results use JQ to perform all sorts of queries against `kubectl` command results.
