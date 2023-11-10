<h1>Lastminuteresy-web-server</h1>

<p>
Web server for lastminute-resy website content.
Currently deployed to <a>TODO</a> 

Build docker image: ./gradlew buildImage
Run locally:
./gradlew buildImage
./gradlew publishImageToLocalRegistry
docker run -p 8080:8080 <image_name>
</p>

<p>
Also serves demo restaurant booking page at 
<a>TODO</a> and 
GET <a>TODO</a>
</p>

<h2>Deploy process</h2>
<p>prerequisties: docker image for linux/arm64 (see above or use Dockerfile) and gcloud / credentials configured</p>
<ul>
<li>push artifact (image) <br>
docker push europe-west1-docker.pkg.dev/${PROJECT_ID}/lastminuteresy/web:tag</li>
<li>(optional) credentials for cluster<br>
gcloud container clusters get-credentials hello-cluster --region europe-west1</li>
<li><strong>create deployment for new image</strong><br>
kubectl create deployment lasttableresy-web --image=europe-west1-docker.pkg.dev/${PROJECT_ID}/lastminuteresy/web:tag</li>
<br>alternative rolling update only <br>
kubectl set image deployment/lasttableresy-web web=europe-west1-docker.pkg.dev/${PROJECT_ID}/lastminuteresy/web:new-tag
<li>set replicas (if needed)<br>
kubectl scale deployment lastminuteresy-web --replicas=3
<br>autoscaling (optional)<br>
kubectl autoscale deployment lastminuteresy-web --cpu-percent=80 --min=1 --max=5
<li>???</li>
<li>Profit</li>
</ul>
