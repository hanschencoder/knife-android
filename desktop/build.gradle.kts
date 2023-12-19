import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

group = "site.hanschen.knife.desktop"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

kotlin {
    jvmToolchain(8)
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    implementation("com.android.tools.ddms:ddmlib:31.2.0")
}

compose.desktop {
    application {
        mainClass = "site.hanschen.knife.desktop.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "site.hanschen.knife.desktop"
            packageVersion = "1.0.0"
            description = "Knife for Android"
            copyright = "© 2023 chenhang. All rights reserved."
            licenseFile.set(project.file("LICENSE"))
        }
    }
}
