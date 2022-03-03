#include <jni.h>
#include <string>
#include "SHA512.h"

extern "C" JNIEXPORT jstring
Java_com_example_fragmenttest2_title_SignUpDialog_HASH(JNIEnv* env, jclass clazz, jstring password, jstring salt) {
    std::string Password = reinterpret_cast<const char *>(password);
    std::string Salt = reinterpret_cast<const char *>(salt);
    SHA512 sha512;
}
