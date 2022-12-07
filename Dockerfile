# (1)
FROM maven:3-openjdk-11-slim as build
# (2)
RUN useradd -m myuser
# (3)
WORKDIR /usr/src/app/
# (4)
RUN chown myuser:myuser /usr/src/app/
# (5)
USER myuser
# (6)
COPY --chown=myuser pom.xml ./
# (7)
RUN mvn dependency:go-offline -Pproduction
# (8)
COPY --chown=myuser:myuser src src
COPY --chown=myuser:myuser frontend frontend
COPY --chown=myuser:myuser package.json ./
COPY --chown=myuser:myuser package-lock.json* pnpm-lock.yaml* webpack.config.js* ./
# (9)
RUN mvn clean package -DskipTests -Pproduction
# (10)
FROM openjdk:11-jdk-slim
# (11)
COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar
# (12)
RUN useradd -m myuser
# (13)
USER myuser
# (14)
EXPOSE 8080
# (15)
CMD java -jar /usr/app/app.jar
