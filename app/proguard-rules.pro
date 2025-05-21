# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# Gson
-keep class com.google.gson.** { *; }
-keepattributes *Annotation*
-keepattributes EnclosingMethod

# OkHttp
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }

# ZXing
-dontwarn com.google.zxing.**
-keep class com.google.zxing.** { *; }

# Compose
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**