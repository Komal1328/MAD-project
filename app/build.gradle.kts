plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}


android {
    namespace = "com.example.studentbudgetigapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.studentbudgetigapp"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation("androidx.test:rules:1.5.0")
    testImplementation("junit:junit:4.13.2")

    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-core:21.1.1")
    implementation("com.firebase:firebase-client-android:2.5.2")
//    implementation("com.firebase:firebase-ui-database:1.6.0")

    //noinspection GradleCompatible,GradleCompatible
    implementation("com.android.support:cardview-v7:28.0.0")

    //noinspection GradleCompatible
    implementation("com.android.support:support-v4:28.0.0")
    androidTestCompileOnly("com.android.support:support-annotations:28.0.0")
    androidTestCompileOnly("com.android.support.test:runner:1.0.2")
    androidTestCompileOnly("com.android.support.test:rules:1.0.2")
    androidTestCompileOnly("com.android.support.test.espresso:espresso-core:3.0.2")
    testImplementation("androidx.test.ext:junit:1.1.5")


    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//    implementation ("com.github.TutorialsAndroid:GButton:v1.0.19")
    implementation ("com.google.android.gms:play-services-auth:20.4.0")
    implementation(platform("com.google.firebase:firebase-bom:32.7.1"))
    implementation("com.google.firebase:firebase-analytics")

    implementation ("com.github.bumptech.glide:glide:4.14.2")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.14.2")
    implementation ("com.github.clans:fab:1.6.4")
    implementation ("com.google.firebase:firebase-database:19.7.0")
    implementation ("com.google.firebase:firebase-database:20.0.0")

}

