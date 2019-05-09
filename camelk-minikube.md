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
4. Run `minikube addons enable registry` (If "registry" pod doesn't show up when you do the next step, note registry issue we hit below with the resolution.)
5. Run `kubectl get pods --all-namespaces` (to get the id for the camel-k-operator and make sure all pods are running)
6. Run `kubectl log -f camel-k-operator-[id]` (after get full pod name from "get pods" - it will be something like camel-k-operator-676999f5bc-cfbwc - and this command makes it so you can watch the camel log)
7. Run your route in another console with `kamel run --dev helloworld.groovy`
8. Monitor the console and the log to ensure that everything looks good.
9. To stop your camelk processes, run `minikube stop -p camelk`
10. You can restart later without running the start.sh script by just using `minikube start -p camelk`

### Issue enabling registry addon

Update: Looks like this has been fixed upstream for a later version of minikube.

Unfortunately, we hit a problem with the script called to enable the registry. You can find that script [here](https://github.com/kubernetes/minikube/blob/master/deploy/addons/registry/registry-rc.yaml.tmpl#L28). On line 28, true needs to be “true”.

You can fix this locally to get past it. Download the file, make the fix, and then install by running `kubectl -n kube-system create -f my-modified-registry.yaml`

## Minikube + Camel-K in VS Code

The [Kubernetes Tools extension from Microsoft](https://marketplace.visualstudio.com/items?itemName=ms-kubernetes-tools.vscode-kubernetes-tools) offers a number of cool tools we can use with Minikube and Camel-K.

Once the Camel-K pods have started in Minikube, you can see them show up in the Kubernetes Activity view. This removes the need to explicitly do step 5 above:

![Kubernetes Activity with Camel-K](images/kubernetes-view-camelk.jpg)

A cool feature of the Kubernetes view is when you right-click on a pod under the minikube node, you get a pop-up menu that allows you to "Follow Logs"...

![Kubernetes View pop-up menu](images/kubernetes-view-camelk-popup.jpg)

...which opens the log for that pod in a new Terminal window.

![Kubernetes View operator log](images/kubernetes-view-camelk-operator-log.jpg)

With [Language Support for Apache Camel](https://marketplace.visualstudio.com/items?itemName=camel-tooling.vscode-apache-camel) installed, you also get LSP support for Camel XML and Java routes:

![Hello XML](images/kubernetes-view-camelk-hello-xml.jpg)

At this time, you still have to run step 7 from above in a separate Terminal, but I think we can help with that by providing a specialized right-click menu option for running a Kamel service.

## Other useful links

* <https://github.com/apache/camel-k>
* <https://github.com/apache/camel-k/blob/master/docs/cluster-setup.adoc>
* <https://camel.apache.org/staging/camel-k/latest/index.html>
* <https://kubernetes.io/docs/tasks/debug-application-cluster/debug-application/>
* <https://kubernetes.io/docs/reference/kubectl/cheatsheet/>
* <https://kubernetes.io/docs/tasks/access-application-cluster/list-all-running-container-images/>
* <https://kubernetes.io/docs/tasks/tools/install-minikube/>

## Notes

* Getting a development environment setup locally seems to be the trickiest part, but once it's up and running it's pretty easy to run a Camel configuration and watch the log.
* Sanity note: Minikube is very slow on Windows, but much easier to work with on a linux (Fedora 30 in my case) box.

## Changes

* Initial version of this file added [8-MAY-2019]

## Next steps

* Work with the Kubernetes view in VS Code to see if we can start/stop the camelk cluster and access the Camel log.
* See if there's an easy way to add a right-click "Run on Camel-K" command in the Explorer on supported file types (java, Groovy, xml, etc.).
* Work through installing Camel-k on minishift and see if we can use the VS Code OpenShift tools and work through a similar workflow.
