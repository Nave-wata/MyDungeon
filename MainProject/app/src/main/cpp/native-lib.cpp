#include <jni.h>
#include <string>
#include "SHA512.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_mainproject_title_SignUpDialog_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_mainproject_title_SignUpDialog_HASH(
        JNIEnv* env,
        jobject /* this */,
        jstring password,
        jstring salt) {
    std::string Password = reinterpret_cast<const char *>(password);
    std::string Salt = reinterpret_cast<const char *>(salt);
    std::string result = Password;
    SHA512 sha512;

    result = sha512.hash("A");

    return env->NewStringUTF(result.c_str());
}