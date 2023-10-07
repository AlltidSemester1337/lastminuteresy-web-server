<h1>Lastminuteresy-web-server</h1>

<p>
Web server for lastminute-resy website content.

Build docker image: ./gradlew buildImage
Run locally:
./gradlew buildImage
./gradlew publishImageToLocalRegistry
docker run -p 8080:8080 <image_name>
</p>

<p>
Also serves demo restaurant booking page at /demorestaurant/bookTable.html and GET /demorestaurant/admin/unbook
</p>