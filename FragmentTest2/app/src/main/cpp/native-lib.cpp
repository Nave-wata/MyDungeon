#include <jni.h>
#include <string>
#include "SHA512.h"

extern "C" JNIEXPORT jstring
Java_com_example_fragmenttest2_title_SignUpDialog_HASH(JNIEnv* env, jclass clazz, jstring password, jstring salt) {
    std::string Password = reinterpret_cast<std::basic_string<char> &&>(password);
    std::string Salt = reinterpret_cast<std::basic_string<char> &&>(salt);
    std::string str = Password;
    SHA512 sha512;

    for (int i = 0; i < 1; i++) {
        str = sha512.hash("A");
    }

    return env->NewStringUTF(str.c_str());
}

extern "C" JNIEXPORT jstring
Java_com_example_fragmenttest2_title_SignInDialog_HASH(JNIEnv* env, jclass clazz, jstring password, jstring salt) {
    std::string Password = reinterpret_cast<std::basic_string<char> &&>(password);
    std::string Salt = reinterpret_cast<std::basic_string<char> &&>(salt);
    std::string str = Password;
    SHA512 sha512;

    for (int i = 0; i < 1; i++) {
        str = sha512.hash("A");
    }

    return env->NewStringUTF(str.c_str());
}