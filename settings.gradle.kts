pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        // ✅ Pour les librairies hébergées sur GitHub (ex: MPAndroidChart, EmailJS)
        maven { url = uri("https://jitpack.io") }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        // ✅ Pour les dépendances GitHub via JitPack
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "EduCtrack"
include(":app")
