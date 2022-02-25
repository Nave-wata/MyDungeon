#include <jni.h>
#include <string>
#include "SHA512.h"

std::string SHA256_SHA512(std::string basicString, std::string basicString1, int salt);

extern "C" JNIEXPORT jstring
Java_com_example_fragmenttest2_title_SignInDialog_HASH(JNIEnv* env, jclass clazz, jstring name, jstring password, int salt) {
    std::string Name = reinterpret_cast<std::basic_string<char> &&>(name);
    std::string Password = reinterpret_cast<std::basic_string<char> &&>(password);
    SHA512 sha512;

    std::string output = sha512.hash("Hello World");

    return env->NewStringUTF(output.c_str());
}
