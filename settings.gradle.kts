pluginManagement {
    repositories {
        google()
//        mavenCentral()
        maven { url = uri("https://maven.firbase.google.com") }
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "StudentBudgetigApp"
include(":app")
 