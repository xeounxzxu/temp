tasks.getByName("jar") {
    enabled = true
}

dependencies {
//    implementation("org.springframework.boot:spring-boot-starter-web")
    // https://mvnrepository.com/artifact/org.springframework/spring-web
    implementation("org.springframework:spring-web:4.3.11.RELEASE")
}
