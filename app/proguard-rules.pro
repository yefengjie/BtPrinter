# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/night/Documents/develop/android/android-sdk-macosx/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
# -- Android Annotations --
-dontwarn org.springframework.**

# -- keep eventbus lib --
-keepclassmembers class ** {
    public void onEvent*(**);
    public void onEventMainThread*(**);
    public void onEventBackgroundThread*(**);
    public void onEventBusAsync*(**);
}
-keepclassmembers class * extends de.greenrobot.event.util.ThrowableFailureEvent {
    public <init>(java.lang.Throwable);
}
-dontwarn de.greenrobot.event.util.*$Support
-dontwarn de.greenrobot.event.util.*$SupportManagerFragment