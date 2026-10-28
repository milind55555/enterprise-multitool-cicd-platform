.PHONY: test package docker-build run clean
test:
	cd app && mvn -B -ntp clean verify
package:
	cd app && mvn -B -ntp clean package
docker-build:
	docker build -t enterprise-demo:local .
run:
	cd app && mvn spring-boot:run
clean:
	cd app && mvn clean
