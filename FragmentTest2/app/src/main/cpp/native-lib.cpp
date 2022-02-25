#include <jni.h>
#include <string>
#include "SHA512.h"

std::string SHA256_SHA512(std::string basicString, std::string basicString1, int salt);

extern "C" JNIEXPORT jstring
Java_com_example_fragmenttest2_title_SignInDialog_HASH(JNIEnv* env, jclass clazz, jstring password, jstring salt) {
    std::string Password = reinterpret_cast<std::basic_string<char> &&>(password);
    std::string Salt = reinterpret_cast<std::basic_string<char> &&>(salt);
    SHA512 sha512;

    std::string output = sha512.hash(Password);

    return env->NewStringUTF(output.c_str());
}
