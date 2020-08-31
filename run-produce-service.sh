docker run --rm \
-e "STUBRUNNER_IDS=produce-service:9876" \
-e "STUBRUNNER_REPOSITORY_ROOT=file://contracts" \
-e "STUBRUNNER_STUBS_MODE=LOCAL" \
-e "STUBRUNNER_GENERATE_STUBS=TRUE" \
-p "8083:8083" \
-p "9876:9876" \
springcloud/spring-cloud-contract-stub-runner:2.2.4.RELEASE