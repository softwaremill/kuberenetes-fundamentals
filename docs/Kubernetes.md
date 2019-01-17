# Kubernetes

## Objects
### Pods

Smallest, indivisible Kuberentes object. It is composed of one or multiple containers, storage and a network IP. We should think of it as a single application instance.

Note that there is a possibility to use a single pod instance but the practice shows that it is better to use a *deployment* which adds additional capabilities to pod handling.

Pods are also ephemeral. It means that they can be terminated at any time of cluster life cycle. In consequence you should carefully decide whether your application should share state and what solution will support that. A good example is caching. Creating large in-memory caches could not be a good idea. In some conditions their replenish times and overhead could dramatically increase its use cost.  

### Deployments

Deployment is a Kubernetes controller. Its main purpose is to manage pods in terms of scaling, updating and disposal.

You should always think of your application in terms of deployments or stateful sets.

### StatefulSets

Those are enhanced deployments which offer, besides scaling and updates of pods, the guarantee of uniqueness and ordering. This is very important for application that relay of stable network resources and persistent storage. Note that deployment of this objects is sequential and blocking, therefore, in some cases it can take long to deploy all desired replicas. 

### Services
Because pods can be restarted at any time it would be very hard to control network over their instances. That is why k8s uses service objects. You should think of a service as a policy to access a strictly defined set od pods.

There are several types of services in terms of access: 
- ClusterIp (default) - reachable only internally, 
- NodePort - exposed outside a cluster on a chosen port, 
- LoadBalancer - exposed using a cloud provider's LB,
- ExternalName - targets an external service

Note that the ExternalName can be very useful to create a clean solution. Instead of applying configuration to external services directly in the application you can utilize an accessible mapping inside the cluster. It also greatly helps during tests and creating mocks because the ExternalName service target can be updated without notice of the client service.

What is important, internal services also perform load balancing between pods and also participate in service discovery.

### ConfigMaps

In simple words a config map is a cluster-wise configuration that can be accessed by other objects. ConfigMaps can be created from *.yaml files but also from text file content e.g. `kubectl create configmap config-A --from-file=kubectl_examples/data/test.properties`. To use it in the deployment 

To use it in the deployment add 
```yaml
- name: APPLICATION_LOG_LEVEL
  valueFrom:
    configMapKeyRef:
      name: log-config
      key: app-log-level
```

### Secrets

Secretes are basically encoded (base64) configmaps. You can create it from *.yaml, from a file or even from literals:

```bash
kubectl create secret generic kubernetes-fundamentals-secret \
--from-literal=AWS_ACCESS_KEY="$AWS_ACCESS_KEY" \
--from-literal=AWS_ACCESS_SECRET="$AWS_ACCESS_SECRET" \
--dry-run -o yaml | kubectl apply -f -
```

```yaml
envFrom:
- secretRef:
  name: {{ template "name" . }}-secret
```

### Jobs

Job is an object that you can fire once or create a scheduled task. Although simple it has a great feature i.e. you cannot have a job with the same name deployed. It means that jobs can even be used for a global platform update and hold history. Inside a job, like in a deployment, you can use a docker image to perform some task. Be aware that it will be tempting to use busybox images to do it because they are small and you can define a simple command to be run in the *.yaml file. This is fine until you reach a moment when the commands are getting complex and the *.yaml file grows. It would be better to think of preparing small operator images that are able to perform fine-tailored task.

### Daemon sets

You can think of it as a flavour of a deployment but which will span across the whole cluster. This object is great for introducing platform customisation mechanism like logging, additional providers.

### Ingresses

In simple words an ingress is an API object. It is used to expose and control access to all resources that you configure for your cluster. Using ingresses is the preferable way of enabling external access to your cluster, in contrary to NodePorts or forwarding. To use this object on minikube you have to install an addon: `minikube addons enable ingress`.

## Storage

- [Volumes](https://kubernetes.io/docs/concepts/storage/persistent-volumes/)
- [Nfs provisioner](https://github.com/kubernetes-incubator/nfs-provisioner)
- [Minikube volumes](https://github.com/kubernetes/minikube/blob/master/docs/persistent_volumes.md)
- [Gluster](https://github.com/gluster/gluster-kubernetes)

##  Miscellaneous
- [Init container](https://kubernetes.io/docs/concepts/workloads/pods/init-containers/)

Using wise an init container can greatly improve you platform management. But in the other hand it can easily create a complex net of dependencies that will make development really hard i.e. tracking what init containers do will need a well-defined documentation entry. 

Something worth remembering. If you want to control the sequence of spawning your applications (to emphasise deployment phases) please do not do it. Change your application to be able to be eventually operational and be able to handle missing resources for some time. 

- Sidecar containers

This is an awesome pod composition pattern. Read more about it in the official documentation regarding [managing multiple containers](https://kubernetes.io/docs/concepts/workloads/pods/init-containers/)

- [Namespaces](https://kubernetes.io/docs/concepts/overview/working-with-objects/namespaces/)

- Affinity and anti-affinity

When you start working on your platform to be highly available you will encounter topics related to [pod affinity](https://kubernetes.io/docs/concepts/overview/working-with-objects/namespaces/)

- [Logging](https://kubernetes.io/docs/concepts/cluster-administration/logging/)
- Metrics - [Prometheus](https://prometheus.io/)
- Tracing - [Jaeger](https://github.com/jaegertracing/jaeger-kubernetes)
- [High Availability](https://kubernetes.io/docs/setup/independent/high-availability/)

## Exercise
1. Prepare a helm chart defining current application with 5 instances
1. (Basic) Expose the application API using a service
1. (Intermediate) Expose the application API using forwarding.
1. (Advanced) Expose the application API using ingress.