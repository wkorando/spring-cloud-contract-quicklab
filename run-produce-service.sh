docker run --rm \
-e "STUBRUNNER_IDS=com.ibm.developer:scc-quicklab:+:9876" \
-e "STUBRUNNER_STUBS_MODE=LOCAL" \
-v "$(pwd)/.m2/:/root/.m2:ro" \
-p "8083:8083" \
-p "9876:9876" \
springcloud/spring-cloud-contract-stub-runner:2.2.4.RELEASE