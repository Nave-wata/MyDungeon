#include <jni.h>
#include <string>
#include "SHA512.cpp"

extern "C" JNIEXPORT jstring
Java_com_example_fragmenttest2_title_SignInDialog_HASH(JNIEnv* env, jclass clazz, jstring name, jstring password, int salt) {
    std::string Name = reinterpret_cast<basic_string<char> &&>(name);
    std::string Password = reinterpret_cast<basic_string<char> &&>(password);

    std::string result =  SHA256_SHA512(Name, Password, salt);
    return env->NewStringUTF(result.c_str());
}