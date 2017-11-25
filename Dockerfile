FROM maven

WORKDIR /pedido

ADD pom.xml /pedido/pom.xml

ADD src /pedido/src

RUN ["mvn","package","-DskipTests"]

EXPOSE 8082
CMD ["mvn","spring-boot:run"]
