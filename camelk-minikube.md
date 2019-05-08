# Camel-K Exploration

I was tasked with trying to figure out ways to make Camel-K more accessible to users in VS Code, so I started by exploring Camel-K with Minikube. 

* To install [Camel-K](https://github.com/apache/camel-k), grab the latest release (such as [0.3.3](https://github.com/apache/camel-k/releases/tag/0.3.3) and make accessible on the path.
* To install Minikube, I followed the directions [here](https://github.com/apache/camel-k/blob/master/docs/cluster-setup.adoc). [This link](https://kubernetes.io/docs/tasks/tools/install-minikube/) was also very helpful.
* I then created a local folder with two files - start.sh and helloworld.groovy. The source for start.sh is [here](https://github.com/apache/camel-k/blob/master/docs/cluster-setup.adoc) and the source for helloworld.groovy is [here](https://camel.apache.org/staging/camel-k/latest/running/running.html). I recommend adding " -v5" to the end of the last line of start.sh to enable a bit more logging.

Once "kamel" and Minikube were installed, I worked through these steps with the help of Zoran Regvart and Andrea Tarocchi.

## Steps (With minikube and kamel installed on system)

1. Execute `./start.sh`
2. Run `kamel install --cluster-setup`
3. Run `kamel install`
4. Run `minikube addons enable registry` (Note registry issue we hit below.)
5. Run `kubectl get pods --all-namespaces` (to get the id for the camel-k-operator)
6. Run `kubectl log -f camel-k-operator-676999f5bc-tsgwf` (after get pod name so you can watch the camel log)
7. Run your route with `kamel run --dev helloworld.groovy`
8. Monitor the console and the log to ensure that everything looks good.
9. To stop your camelk processes, run `minikube stop -p camelk`
10. You can restart later without running the start.sh script by just using `minikube start -p camelk`

### Issue enabling registry addon

Unfortunately, we hit a problem with the script called to enable the registry. You can find that script [here](https://github.com/kubernetes/minikube/blob/master/deploy/addons/registry/registry-rc.yaml.tmpl#L28). On line 28, true needs to be “true”.

You can fix this locally to get past it. Download the file, make the fix, and then install by running `kubectl -n kube-system create -f my-modified-registry.yaml`

## Other useful links

* <https://github.com/apache/camel-k>
* <https://github.com/apache/camel-k/blob/master/docs/cluster-setup.adoc>
* <https://camel.apache.org/staging/camel-k/latest/index.html>
* <https://kubernetes.io/docs/tasks/debug-application-cluster/debug-application/>
* <https://kubernetes.io/docs/reference/kubectl/cheatsheet/>
* <https://kubernetes.io/docs/tasks/access-application-cluster/list-all-running-container-images/>
* <https://kubernetes.io/docs/tasks/tools/install-minikube/>
