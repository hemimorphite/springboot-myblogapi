Untuk jalankan aplikasi api menggunakan bash script
./mvnw spring-boot:run

Untuk jalankan aplikasi api menggunakan maven
mvn spring-boot:run

Untuk jalankan test menggunakan bash script
./mvnw test

Untuk jalankan test menggunakan maven
mvn test

Untuk menjalankan container docker 
docker build -f Dockerfile . -t myblogapi

docker run -P -d -p 80:8080 myblogapi /bin/bash


