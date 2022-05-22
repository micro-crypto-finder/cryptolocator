# Micro-Crypto-Finder

## CryptoLocator

REST Service responsible to find the correct current BitCoin price per currency.

### Usage

Access `GET <service_address>:<service_port>/api/crypto-locator/price/bitcoin/<currency>` to receive a `JSON` corresponding price.

### Source code

Main source can be found under `src/main`, while 2 testing folders are provided as well: `src/integrationTest` for integration tests and `src/test` for unit tests (with mocked third-party behave).

#### Build and test

On every build all tests are executed, this behave can be changed on `build.gradle` by changing the `check` task ref.

- To build the source code, execute `./gradlew build`
- To all test on source code, execute `./gradlew check`
- To unit test on source code, execute `./gradlew test`
- To integration test on source code, execute `./gradlew integrationTest`

#### Docker and deployment

A public image of this service can be found on [DockerHub](https://hub.docker.com/r/jjbeto/micro-crypto-finder-cryptolocator). You can easily setup a local docker using the provided `docker-compose.yml` by running the command `docker-compose up`.

The image allows a couple of parameters to change it's behave (caching, fallback currency and others), please refer Spring documentation for general parameters and `src/main/resources/application.properties` for custom variables.

Docker build is done by **JIB Gradle Plugin** (more [here](https://github.com/GoogleContainerTools/jib)). The following main commands is available:

- You can execute a new docker build by running `./gradlew jibDockerBuild`.
- You can Build&Publish a docker image by running `./gradlew jib`

Note: for every new docker push, the `latest` tag is updated and a new version is atached according to gradle's `${project.version}` property.

#### Actuator

Spring's Actuator was activated for health-checks and generic information required for maintenance and/or support. GIT and JVM information for instance is exposed for easily track delivered code, as well as readiness and liveness probes.

#### Caching

I've decided to use **EHCACHE** for simplicity. Cache is done on service level, as future reuse would be facilitated. The main purpose of using cache here is to keep the price for a short time (initially 30s TTL, configurable), so I'm considering 30sec still current, otherwise 1s could be set for short, or cache could be disabled entirely (via Spring conf parameter).

#### OpenFeign

Chose for it's high simplicity and readability. As CoinDesk returns `application/javascript` as response type, OpenFegn was not able to deal with deserialization, so I had to call Jackson for the rescue.

#### Metrics

No metrics was created (only the ones provided automatically by Actuator), an interesting improvement to make though (number of requests per endpoint and cache usage).
