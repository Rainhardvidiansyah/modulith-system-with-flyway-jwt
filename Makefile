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

# Variable for migration
MIGRATION_BASE_PATH=src/main/resources/db/migration

.PHONY: migration

# Main command to generate Flyway File
migration:
	@if [ -z "$(module)" ]; then \
		echo "❌ Error: please mention the module! (Example: module=product or module=global)"; \
		exit 1; \
	fi
	@if [ -z "$(name)" ]; then \
		echo "❌ Error: Please mention the file name (example: name=alter_tabel_product)"; \
		exit 1; \
	fi
	@TIMESTAMP=$$(date +%Y%m%d%H%M%S); \
	FILE_PATH="$(MIGRATION_BASE_PATH)/$(module)/V$${TIMESTAMP}__$(name).sql"; \
	touch $${FILE_PATH}; \
	echo "✅ successfully created new flyway file:"; \
	echo "   👉 $${FILE_PATH}"

# how to use it?
# make migration module=product name=alter_tabel_product