plugins {
    alias(libs.plugins.android.application) apply false
    kotlin("android") version "1.8.10" apply false
    kotlin("kapt") version "1.8.10" apply false
    alias(libs.plugins.google.gms.google.services) apply false  // Ya lo tienes, pero asegúrate de que esta línea esté presente
}
