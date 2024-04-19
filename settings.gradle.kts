pluginManagement {
    repositories {
        google()
        jcenter()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven(url="https://jitpack.io")
        jcenter()
    }
}

rootProject.name = "QuanLyChiTieu"
include(":app")
 