plugins {
    java
}

group = "me.zimzaza4.redstoneconnector"
// 版本以 gradle.properties 的 version= 为准，release.yml 从那里读取
version = properties["version"] as String? ?: "1.0.0"

// 不声明 toolchain：本机与 CI 的 JDK 大版本可能不同，
// options.release = 17 已锁定字节码版本，两端用各自 JDK 编译结果一致
java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    // Core
    compileOnly(libs.paper.api)
    compileOnly(libs.slimefun4)
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
        options.release = 17
    }

    processResources {
        filesMatching("plugin.yml") {
            expand("version" to project.version.toString())
        }
    }

    jar {
        archiveBaseName.set("RedstoneConnector")
        archiveVersion.set(project.version.toString())
    }
}

defaultTasks("clean", "build")
