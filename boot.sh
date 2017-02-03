clear

echo "Building app"

./gradlew clean build

echo "Deploying app"

java -jar build/libs/*.jar
