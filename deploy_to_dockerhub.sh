mvn clean -Dspring.profiles.active=test
mvn install -Dspring.profiles.active=test -Dmaven.test.skip=true

docker build -f test.Dockerfile -t dats_ddos_middleware .
docker image tag dats_ddos_middleware burakvural93/dats_ddos_middleware:latest
docker image push burakvural93/dats_ddos_middleware:latest