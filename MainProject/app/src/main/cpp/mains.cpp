#include <jni.h>
#include <string>
#include "SHA512.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_mainproject_title_TitleActivity_HASH(
        JNIEnv* env,
        jclass clazz,
        jstring j_name,
        jstring j_password,
        jstring j_salt) {
    const std::string name = env->GetStringUTFChars(j_name, 0);
    const std::string password = env->GetStringUTFChars(j_password, 0);
    const std::string salt = env->GetStringUTFChars(j_salt, 0);

    std::string result = (new SHA512())->getHash512(name, password, salt);

    return env->NewStringUTF(result.c_str());
}