#include <jni.h>
/* Header for class com_example_jniandroidtest1_imageProc */

#ifndef _Included_jni_LBPJNI
#define _Included_com_LBPJNI
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_example_jniandroidtest1_imageProc
 * Method:    grayproc
 * Signature: ([III)[I
 */
JNIEXPORT jobjectArray JNICALL Java_com_jni_ImgDealJni_LbpAndHis
  (JNIEnv *, jclass , jlong);

#include <android/log.h>
#define LOG_TAG "lbpjni"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)

#ifdef __cplusplus
}
#endif
#endif
