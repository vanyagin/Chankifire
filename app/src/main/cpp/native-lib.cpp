#include <jni.h>
#include <string>
#include "FileSeparation.cpp"

extern "C" JNIEXPORT jstring JNICALL
Java_erofeevsky_sfedu_chankifire_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    FileSeparation fs;
    std::string fs_hello = fs.toString();
    return env->NewStringUTF(fs_hello.c_str());
}