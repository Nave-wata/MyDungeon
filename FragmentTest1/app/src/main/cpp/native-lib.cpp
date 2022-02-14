#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_fragmenttest1_Fragment_1Main_stringFromJNI(JNIEnv *env, jobject thiz) {
    std::string text = "Hello from C++";
    return env->NewStringUTF(text.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_fragmenttest1_MainActivity_stringFromJNI(JNIEnv *env, jobject thiz) {
    std::string text = "Hello from C++";
    return env->NewStringUTF(text.c_str());
}