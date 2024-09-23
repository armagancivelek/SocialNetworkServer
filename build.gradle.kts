
val kotlin_version: String by project
val logback_version: String by project
val koin_version: String by project
val koin_ktor: String by project
val kmongo_version: String by project
val ktor_version: String by project


plugins {
    kotlin("jvm") version "2.0.0"
    id("io.ktor.plugin") version "2.3.12"
}

group = "example.com"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-websockets-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-gson-jvm")
    implementation("io.ktor:ktor-server-call-logging-jvm")
    implementation("io.ktor:ktor-server-cors-jvm")
    implementation("io.ktor:ktor-server-default-headers-jvm")
    implementation("io.ktor:ktor-server-host-common-jvm")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-auth-jwt-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")


    //KMongo
    implementation("org.litote.kmongo:kmongo:$kmongo_version")
    // Kmongo Coroutine
    implementation("org.litote.kmongo:kmongo-coroutine:$kmongo_version")

    // TEST DEPENDENCIES

    // Ktor Test
  //  testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
    //Koin Test
    testImplementation("io.insert-koin:koin-test:$koin_version")
    // Kotlin Test
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")

    // Truth
    testImplementation("com.google.truth:truth:1.1.3")

    // Koin core features
    implementation(platform("io.insert-koin:koin-bom:$koin_version"))
    implementation("io.insert-koin:koin-core")
    // Koin for Ktor
    implementation("io.insert-koin:koin-ktor:$koin_ktor")
    // SLF4J Logger
    implementation("io.insert-koin:koin-logger-slf4j:$koin_ktor")
    //Validation
    implementation("io.ktor:ktor-server-request-validation:$ktor_version")
    //Status pages
    implementation("io.ktor:ktor-server-status-pages:$ktor_version")





}
