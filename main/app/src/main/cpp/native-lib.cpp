#include <jni.h>
#include <string>
#include "SHA512.h"

extern "C" JNIEXPORT jstring
Java_com_example_main_title_SignUpDialog_HASH(JNIEnv * env, jclass clazz, jstring password, jstring salt) {
    std::string Password = reinterpret_cast<const char *>(password);
    std::string Salt = reinterpret_cast<const char *>(salt);
    std::string result = "a";
    SHA512 sha512;

    //result = sha512.hash(result);
}
