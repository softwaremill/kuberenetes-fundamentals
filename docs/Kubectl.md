# Kubectl operations

## Configuration

To see current configuration use `kubectl config view`. Please note that the result depends on the `KUBECONFIG` environmental variable value or will point to default `~/.kube/config`.

Do not forget about completion. Add `source <(kubectl completion bash)` to relevant shell configuration file.

## Asking about state

1. List pods: `kubectl get pods` or `kubectl get po` 
1. List deployments: `kubectl get deployments` or `kubectl get deploy` 
1. List services: `kubectl get servies` or `kubectl get svc` etc. 

Add `-o wide` to acquire more data or `-o json` to output data in JSON format.

Add `--all-namespaces` to list the requested object(s) across all namespaces. It is useful for system level objects.

Use `-l key=value` or `-l key!=value` to filter the result using a label.

Add `--field-selector=status.phase=Running` to select only running objects.

Use `-w` to wait for changes in the fetched resources list e.g. `kubectl get po -w` will wait in the terminal for the pods list change.

## Handling objects

1. To show the definition of a k8s primitive utilize `kubectl explain` eg. `kubectl explain services`
1. To create a pod using a managed docker images use `kubectl run nginx --image=nginx`.  Behind the scenes this command creates a doployment.
1. To create an object using a file use `kubectl create -f` or `kubectl apply -f` e.g. `kubectl apply -f ./kubectl_examples/simple_pod.yaml`.
1. To delete an object utilize `kubectl delete` e.g. `kubectl delete pod nginx-65899c769f-8xw76` or `kubectl delete -f ./kubectl_examples/simple_pod.yaml`
1. To update a resource utilize `kubectl edit`, patch the image or explicitly change chosen attributes `kubectl set image` e.g ` kubectl set image deployment/nginx busybox=busybox nginx=nginx:1.9.1`. Note that only the shorthand commands are pleasant to use.
1. To see the update status use `kubectl rollout` e.g. `kubectl rollout history deployment/nginx`. To undo utilize `kubectl rollout undo`

## Scaling

To scale the resource `kubectl scale --replicas=3 <resource>`

## Proxy

To expose Kubernetes API locally use `kubectl proxy`. This can be handy if you want to use your machine as an cluster operator to perform the installation procedure.

## Handling nodes

1. To unschedule a node and delete all pods `kubectl drain <node>`
1. To reschedule a node `kubectl uncordon <node>`

## Exercises

1. Display all services and pods currently running on your kubernetes cluster.
1. Display all images utilized to run the pods on your kubernetes cluster. 
1. Deploy a busy box that prints all its environmental variables, scale it to 5 instances and delete.