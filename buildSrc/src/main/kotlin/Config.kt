import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.publish.maven.MavenPublication

val Project.isSnapshotBuild
    get() = (version as String).contains("snapshot", true)

fun MavenPublication.configurePom() = pom {
    name.set("Google Play Publisher")
    description.set("Gradle Play Publisher is a plugin that allows you to upload your " +
                            "App Bundle or APK and other app details to the " +
                            "Google Play Store.")
    url.set("https://github.com/Triple-T/gradle-play-publisher")

    licenses {
        license {
            name.set("The MIT License (MIT)")
            url.set("http://opensource.org/licenses/MIT")
            distribution.set("repo")
        }
    }

    developers {
        developer {
            id.set("SUPERCILEX")
            name.set("Alex Saveau")
            email.set("saveau.alexandre@gmail.com")
            roles.set(listOf("Owner"))
            timezone.set("-8")
        }
    }

    scm {
        connection.set("scm:git@github.com:Triple-T/gradle-play-publisher.git")
        developerConnection.set("scm:git@github.com:Triple-T/gradle-play-publisher.git")
        url.set("https://github.com/Triple-T/gradle-play-publisher")
    }
}

fun Project.configureMaven(handler: RepositoryHandler) = handler.maven {
    name = if (isSnapshotBuild) "Snapshots" else "Release"
    url = if (isSnapshotBuild) {
        uri("https://oss.sonatype.org/content/repositories/snapshots/")
    } else {
        uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
    }

    credentials {
        username = System.getenv("SONATYPE_NEXUS_USERNAME")
        password = System.getenv("SONATYPE_NEXUS_PASSWORD")
    }
}

object Config {
    object Libs {
        object All {
            const val agp = "com.android.tools.build:gradle:7.0.0"
            const val agpTest = "com.android.tools.build:builder-test-api:7.0.0"
            const val agpCommon = "com.android.tools:common:30.0.0"
            const val agpDdms = "com.android.tools.ddms:ddmlib:30.0.0"

            const val ap =
                    "com.google.apis:google-api-services-androidpublisher:v3-rev20210527-1.31.0"
            const val googleClient = "com.google.api-client:google-api-client:1.31.5"
            const val apacheClientV2 = "com.google.http-client:google-http-client-apache-v2:1.39.2"
            const val auth = "com.google.auth:google-auth-library-oauth2-http:0.26.0"
            const val guava = "com.google.guava:guava:30.1.1-jre"
            const val gson = "com.google.http-client:google-http-client-gson:1.39.2"

            const val junit = "org.junit.jupiter:junit-jupiter-api:5.7.0"
            const val junitEngine = "org.junit.jupiter:junit-jupiter-engine:5.7.0"
            const val junitParams = "org.junit.jupiter:junit-jupiter-params:5.7.0"
            const val truth = "com.google.truth:truth:1.1.3"
            const val mockito = "org.mockito:mockito-core:3.10.0"
        }
    }
}
