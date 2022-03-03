#include <jni.h>
#include <string>
#include "SHA512.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_mainproject_title_TitleActivity_HASH(
        JNIEnv* env,
        jclass clazz,
        jstring j_password,
        jstring j_salt) {
    const std::string password = env->GetStringUTFChars(j_password, 0);
    const std::string salt = env->GetStringUTFChars(j_salt, 0);
    const int max = 10000 *  (20.0 / salt.length());
    const int op1 = salt.length();
    const int op2 = password.length();
    int op3;
    std::string result = password + salt;
    SHA512 sha512;

    if (op1 < op2) {
        op3 = 10 + op2 - op1;
    } else if (op2 < op1) {
        op3 = 10 + op1 - op2 ;
    } else {
        op3 = 10;
    }

    for (int i = 0; i < max; i++) {
        if (i % op1 == 0) {
            result += sha512.hash(salt);
        } else if (i % op2 == 0) {
            result += sha512.hash(password);
        } else if (i % op3 == 0) {
            result += sha512.hash(result);
        } else {
            result = sha512.hash(result);
        }
    }

    return env->NewStringUTF(result.c_str());
}