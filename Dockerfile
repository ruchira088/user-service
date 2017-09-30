FROM hseeberger/scala-sbt:8u141-jdk_2.12.3_0.13.16

WORKDIR /opt/user-service

COPY ./build.sbt .
COPY ./project ./project

RUN sbt compile

COPY . .

RUN sbt compile

EXPOSE 9000 5005

ENTRYPOINT ["sbt", "-jvm-debug", "5005"]

CMD ["~run"]