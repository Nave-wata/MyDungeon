#include <jni.h>
#include <string>
#include "SHA512.cpp"

extern "C" JNIEXPORT jstring  extern "C" jstring
Java_com_example_fragmenttest2_title_SignInDialog_HASH(JNIEnv* env, jclass clazz, jstring name, jstring password, const int salt) {
    std::string result =  SHA256_SHA512(name, password, salt);
    return env->NewStringUTF(result.c_str());
}