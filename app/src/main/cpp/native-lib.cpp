#include <jni.h>
#include <string>
#include <iostream>
#include "FileSeparation.cpp"

extern "C" JNIEXPORT jstring JNICALL Java_erofeevsky_sfedu_chankifire_MainActivity_stringFromJNI(JNIEnv* env, jobject mainActivityInst) {
    std::string hello = "Hello from C++";


    const jclass mainActivityClass = env->GetObjectClass(mainActivityInst);
    const jmethodID mid = env->GetMethodID(mainActivityClass, "GetFile", "()Ljava/lang/String;");
    const jobject res = env->CallObjectMethod(mainActivityInst, mid);
    const std::string act_msg = env->GetStringUTFChars((jstring) res, JNI_FALSE);


    FileSeparation fs;
    std::string fs_hello = fs.toString();

    return env->NewStringUTF(act_msg.c_str());
}

