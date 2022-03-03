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
        jstring j_password,
        jstring j_salt) {
    std::string password = env->GetStringUTFChars(j_password, 0);
    std::string salt = env->GetStringUTFChars(j_salt, 0);
    std::string result = password;
    SHA512 sha512;

    result = sha512.hash(result);

    return env->NewStringUTF(password.c_str());
}