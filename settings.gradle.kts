enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Demax"
include("androidApp")
include(":core")
include(":feature-authorization")
include(":feature-destruction-details")
include(":feature-destruction-edit")
include(":feature-destructions")
include(":feature-help-history")
include(":feature-profile")
include(":feature-resource-details")
include(":feature-resource-edit")
include(":feature-resource-help")
include(":feature-resources")
include(":feature-responses")
