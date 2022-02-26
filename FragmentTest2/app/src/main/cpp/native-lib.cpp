#include <jni.h>
#include <string>
#include "SHA512.h"

extern "C" JNIEXPORT jstring
Java_com_example_fragmenttest2_title_SignUpDialog_HASH(JNIEnv* env, jclass clazz, jstring password1, jstring salt1) {
    std::string Password1 = reinterpret_cast<std::basic_string<char> &&>(password1);
    std::string Salt1 = reinterpret_cast<std::basic_string<char> &&>(salt1);
    std::string str1 = Password1;
    SHA512 sha512;

    for (int i = 0; i < 1; i++) {
        str1 = sha512.hash(str1);
    }

    return env->NewStringUTF(str1.c_str());
}

extern "C" JNIEXPORT jstring
Java_com_example_fragmenttest2_title_SignInDialog_HASH(JNIEnv* env, jclass clazz, jstring password2, jstring salt2) {
    std::string Password2 = reinterpret_cast<std::basic_string<char> &&>(password2);
    std::string Salt2 = reinterpret_cast<std::basic_string<char> &&>(salt2);
    std::string str2 = Password2;
    SHA512 sha512;

    for (int i = 0; i < 1; i++) {
        str2 = sha512.hash(str2);
    }

    return env->NewStringUTF(str2.c_str());
}