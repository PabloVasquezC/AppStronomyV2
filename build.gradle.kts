plugins {
    alias(libs.plugins.android.application) apply false
    kotlin("android") version "1.8.10" apply false  // Asegúrate de que la versión de Kotlin sea la correcta
    kotlin("kapt") version "1.8.10" apply false
    alias(libs.plugins.google.gms.google.services) apply false  // Agregar kapt para la compilación de Room
}


