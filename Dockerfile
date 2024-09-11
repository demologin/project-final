ARG APP_IMAGE
FROM ${APP_IMAGE}

ARG PROJECT_DIR
RUN apt update -y \
    && apt install openjdk-17-jdk -y \
    && apt install maven -y \
    && mkdir ${PROJECT_DIR} ${PROJECT_DIR}/resources ${PROJECT_DIR}/src

ENV PROJECT_DIR=${PROJECT_DIR}
WORKDIR ${PROJECT_DIR}

COPY resources/ ./resources/
COPY src/ ./src/
COPY ./config/app-start.sh ./pom.xml ./
RUN mvn clean install -Pprod && rm -rf ./src ~/.m2

ARG APP_OUT_PORT
EXPOSE ${APP_OUT_PORT}

ENTRYPOINT ["./app-start.sh"]