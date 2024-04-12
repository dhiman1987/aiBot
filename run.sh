mvn clean install -Dmaven.test.skip=true
VERSION=0.0.3
docker build --build-arg VERSION=$VERSION --platform=linux/amd64 -t dhiman1987/aibot:$VERSION-amd64 .
docker build --build-arg VERSION=$VERSION -t dhiman1987/aibot:$VERSION .
docker run -d -p 8080:8080 --env-file=env.list dhiman1987/aibot:$VERSION