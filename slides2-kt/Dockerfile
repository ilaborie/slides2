FROM openjdk:11 as build

WORKDIR /project

# Download and cache Gradle
COPY gradlew .
COPY gradle ./gradle
RUN ./gradlew

# Download dependencies and cache them separately from the main source code
COPY build.gradle.kts .
RUN ./gradlew downloadDependncies

# Compile and build native image
COPY src ./src
RUN ./gradlew fatJar

# ----------------------------------------
FROM openjdk:11

COPY --from=build /project/build/slides2-full-*.jar .
CMD java -jar xxx
