# Helm Operations

The best place for helm commands reference is the official [documentation](https://docs.helm.sh/helm/). Note that in the early phases Helm changed very frequent. Online tutorials or some blog entries can be obsolete.

Do not forget about completion. Add `source <(helm completion <shell>)` to relevant shell configuration file.

## Helm installation

Minikube by default uses RBAC so the helm installation needs a seriviceaccount with required access rights.

To install helm:
Create service account and cluster role binding (BEWARE - to simplify things this example uses ClusterAdmin role).
```bash
kubectl apply -f helm-installation/helm-rbac.yaml
```
Install helm on k8s with created serviceaccount:
```bash
helm init --service-account tiller
```

## Helm chart structure

```
.
└── charts
    └── example-application-backend
        ├── Chart.yaml (defines main chart attributes, obligatory)
        ├── templates (holds all Kubernetes resource definitions as tempaltes)
        │   ├── deployment.yaml (describes a k8s deployment)
        │   ├── _helpers.tpl (additional templating helpers)
        │   ├── NOTES.txt (notes displayed after successful installation)
        │   └── service.yaml (descrbies a k8s service)
        └── values.yaml (configuration values)

```

More chart examples can be found on the [helm/charts](https://github.com/helm/charts) repository. A good example of a stable release could be [postgresql](https://github.com/helm/charts/tree/master/stable/postgresql) or [jaeger](https://github.com/helm/charts/tree/master/incubator/jaeger) which is in incubation phase.

## Frequently used commands

1. Helm has to be initialized  using the `helm init`. This will deploy a *Tiller* on your Kubernetes cluster which handles the deployment. To initialize only a Helm client run `helm init --client-only`. There is also a possibility to `--wait` for tiller deployment. Note that this is not possible in all Helm versions.
1. To list all present releases uses `helm list`. Regular expressions can be used to filter.
1. To list all configured repositories use `helm repo list`.
1. To show the release history use `helm history` e.g. `helm history example-application-backend`.
1. To delete the release and preserve the its name use `helm del`. To clear also the release name to be able to reuse it invoke `helm del --purge`
1. Each helm chart can be packaged before deployment using `helm package` command.
1. You can use local repo by starting http server: `helm serve` or install local charts just using the chart path.
### Installing and upgrading

Installing from a local, packaged chart
```bash
helm install charts/example-application-backend
```

Installing (when does not exist) or upgrading from a local, packaged chart. Note that release name is explicitly set (this is a good practice).
```bash
helm upgrade \
--install app \
--wait \
charts/kubernetes-fundamentals
```

Installing (when does not exist) or upgrading from `helm/charts` repository
```bash
helm upgrade \
--install metrics \
--values charts/prometheus/values-serverFiles.yaml \
--set alertmanager.enabled=false \
--set nodeExporter.enabled=false \
--set pushgateway.enabled=false \
--set server.service.type="NodePort" \
--set server.service.nodePort=31500 \
--wait \
stable/prometheus
```

There is also a possibility to rollback the deployment to a specified revision: `helm rollback`.

Those flags are useful:
- `--wait` to wait for the deployment (readiness probe)
- `--dry-run` to check the deployment

## Creating a local Helm repository

By default there is a local repository created which stores packaged charts in `~/.helm/repository`. It is utilized when explicitly exposing it using the `helm server` command. There is also a possibility to create a local chart repository. It can be really helpful during development and cooperation between programmers. 
 
To create a custom repository create a directory and move chosen chart packages there. A repository needs an index which is created using `helm repo index` command. To serve it use `helm serve --repo-path`. Now you can add the repository using `helm repo add` using a specified name.  

## Interesting features
- [Hooks](https://docs.helm.sh/developing_charts/#hooks)
- [Subcharts](https://docs.helm.sh/chart_template_guide/#subcharts-and-global-values)
- [Dependencies](https://docs.helm.sh/helm/#helm-dependency)

# Notes
1. There are several solution to enable serving charts. One of them is [Chart Museum](https://github.com/helm/chartmuseum) or [jFrog artifactory](https://jfrog.com/integration/helm-repository/).
1. Helm chart versioning and storage location for production and development is absolutely crucial. You have to decide where you will store the charts and check whether your customer will be access this resource. It is also important to think how developers will share their charts during development process.

## Exercises
1. Describe how to install [elasticsearch](https://www.elastic.co/products/elasticsearch) using a helm chart.
1. Package an example helm chart.
1. Update the chart version and package it once again.
1. Create a local helm repository that holds a single chart with multiple versions.
