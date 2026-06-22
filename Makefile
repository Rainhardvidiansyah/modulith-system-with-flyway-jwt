run:
	mvn spring-boot:run

run-dev:
	mvn spring-boot:run -Dspring-boot.run.profiles=dev

build:
	mvn clean package -DskipTests

clean:
	mvn clean

migrate:
	mvn flyway:migrate

migrate-dev:
	mvn flyway:migrate -Dflyway.configFiles=flyway-dev.conf

migrate-prod:
	mvn flyway:migrate -Dflyway.configFiles=flyway.conf

