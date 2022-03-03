#include <jni.h>
#include <string>
#include "SHA512.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_mainproject_title_TitleActivity_HASH(
        JNIEnv* env,
        jclass clazz,
        jstring j_password,
        jstring j_salt){

    std::string password = env->GetStringUTFChars(j_password, 0);
    std::string salt = env->GetStringUTFChars(j_salt, 0);
    std::string result = password;
    SHA512 sha512;

    for (int i = 0; i < 10000; i++) {
        if (i % password.length() == 0) {
            result = sha512.hash(result) + salt;
        } else {
            result = sha512.hash(result);
        }
    }

    return env->NewStringUTF(result.c_str());
}