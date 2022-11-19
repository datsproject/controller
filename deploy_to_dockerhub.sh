mvn clean -Dspring.profiles.active=test
mvn install -Dspring.profiles.active=test -Dmaven.test.skip=true

docker build -f test.Dockerfile -t dats_ddos_controller .
docker image tag dats_ddos_controller burakvural93/dats_ddos_controller:latest
docker image push burakvural93/dats_ddos_controller:latest