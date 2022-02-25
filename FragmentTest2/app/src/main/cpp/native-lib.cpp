#include <jni.h>
#include <string>
#include "SHA256.h"

std::string SHA256_SHA512(std::string basicString, std::string basicString1, int salt);

extern "C" JNIEXPORT jstring
Java_com_example_fragmenttest2_title_SignInDialog_HASH(JNIEnv* env, jclass clazz, jstring name, jstring password, int salt) {
    std::string Name = reinterpret_cast<std::basic_string<char> &&>(name);
    std::string Password = reinterpret_cast<std::basic_string<char> &&>(password);

    unsigned int H[INIT_HASH_LENGTH];
    SHA256 sha256;
    unsigned char** result = sha256.padding((char*)password);

    sha256.compute(result, H);
    result = sha256.padding((char*)H);
    std::string output = sha256.hash_return(H);
    sha256.print_hash(H);

    sha256.free_block(result);

    return env->NewStringUTF(output.c_str());
}
