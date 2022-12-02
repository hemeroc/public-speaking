To collect the required native image metadata run

```
 # Runs on JVM with native-image-agent
./gradlew -Pagent run
 # Copies the metadata collected by the agent into the project sources
./gradlew metadataCopy --task run --dir src/main/resources/META-INF/native-image
 # Builds image using metadata acquired by the agent.
 ./gradlew nativeCompile
```