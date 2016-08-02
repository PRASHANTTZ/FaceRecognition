#include <jni.h>
/* Header for class com_example_jniandroidtest1_imageProc */

#ifndef _Included_jni_ULBPJNI
#define _Included_jni_ULBPJNI
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_example_jniandroidtest1_imageProc
 * Method:    grayproc
 * Signature: ([III)[I
 */
JNIEXPORT jintArray JNICALL Java_com_jni_ImgDealJni_UlbpAndhis
  (JNIEnv *, jclass , jlong , jint);

#include <android/log.h>
#define LOG_TAG "Ulbpjni"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)

#ifdef __cplusplus
}
#endif
#endif
