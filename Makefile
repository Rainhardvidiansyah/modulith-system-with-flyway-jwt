run:
	mvn spring-boot:run

build:
	mvn clean package -DskipTests

clean:
	mvn clean

migrate:
	mvn flyway:migrate
