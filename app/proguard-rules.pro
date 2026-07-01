# Room rules
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**

# Retrofit rules
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepattributes AnnotationDefault
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

# Kotlin Serialization rules
-keepattributes *Annotation*, EnclosingMethod, InnerClasses
-keepclassmembers class com.example.csc475_ct8_final.** {
    *** Companion;
    *** $serializer;
}

# Preserve Domain Model for serialization/deserialization
-keep class com.example.csc475_ct8_final.Recipe { *; }
-keep class com.example.csc475_ct8_final.RecipeDto { *; }
-keep class com.example.csc475_ct8_final.RecipeResponseDto { *; }
-keep class com.example.csc475_ct8_final.IngredientDto { *; }
