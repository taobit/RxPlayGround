plugins {
    kotlin("jvm") version "1.3.72"
}

group = "com.pluralsight.rxjava2"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("ch.qos.logback:logback-core:1.2.3")
    implementation("org.slf4j:slf4j-api:1.7.26")
    implementation("io.reactivex.rxjava3:rxkotlin:3.0.0")
    // RxJava
//    implementation("io.reactivex.rxjava2:rxjava:2.2.8")

    // Nitrite Embedded Document Database
    implementation("org.dizitart:nitrite:3.2.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}