import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
	id("org.springframework.boot") version "2.1.7.RELEASE"
	id("io.spring.dependency-management") version "1.0.8.RELEASE"
	kotlin("jvm") version "1.3.41"
	kotlin("plugin.spring") version "1.3.41"
}

group = "co.idwall"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jsoup:jsoup:1.12.1")
	implementation("org.telegram:telegrambots:4.4.0.1")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.kotlintest:kotlintest-runner-junit5:3.4.0")
	testImplementation("io.kotlintest:kotlintest-extensions-spring:3.4.0")
	testImplementation("io.mockk:mockk:1.9")
	testImplementation("com.github.tomakehurst:wiremock-jre8:2.24.1")
	testImplementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.+")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

val test by tasks.getting(Test::class) {
	useJUnitPlatform { }
}