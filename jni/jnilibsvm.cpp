//==========================================================================
// 2015/08/31: yctung: add this new test for libSVM in jni interface 
//==========================================================================
#include <jni.h>
#include <string.h>
#include <android/log.h>
#include <stdarg.h>
#include <stdio.h>
#include <stdlib.h>
#include <vector>
#include "./libsvm/svm-train.h"
#include "./libsvm/svm-predict.h"
#include "common.h"

// helper function to be called in Java for making svm-train
extern "C" void Java_com_libsvm_svmClass_jniSvmTrain(JNIEnv *env, jobject obj, jstring cmdIn){


	const char *cmd = env->GetStringUTFChars(cmdIn , 0);
	debug("jniSvmTrain cmd = %s", cmd);

	std::vector<char*> v;

	// add dummy head to meet argv/command format
	std::string cmdString = std::string("dummy ")+std::string(cmd);

	cmdToArgv(cmdString, v);

	// make svm train by libsvm
	svmtrain::main(v.size(),&v[0]);


	// free vector memory
	for(int i=0;i<v.size();i++){
		free(v[i]);
	}

	// free java object memory
	env->ReleaseStringUTFChars(cmdIn, cmd);
}

// helper function to be called in Java for making svm-predict
extern "C" jintArray Java_com_libsvm_svmClass_jniSvmPredict(JNIEnv *env, jobject obj, jstring cmdIn){

	jintArray  inner = env->NewIntArray(2);
	jint *pArray ;
	pArray = (jint *)calloc(2 , sizeof(jint));
	const char *cmd = env->GetStringUTFChars(cmdIn, 0);
	debug("jniSvmPredict cmd = %s", cmd);

	std::vector<char*> v;

	// add dummy head to meet argv/command format
	std::string cmdString = std::string("dummy ")+std::string(cmd);

	cmdToArgv(cmdString , v);

	// make svm train by libsvm
	svmpredict::main(v.size(),&v[0] , pArray);
	debug("pArray:%d" , pArray);


	env->SetIntArrayRegion(inner , 0 , 2 , pArray);

	// free vector memory
	for(int i=0;i<v.size();i++){

		free(v[i]);
	}

	free(pArray);

	// free java object memory
	env->ReleaseStringUTFChars(cmdIn, cmd);

	return inner ;

}



///*
//*  just some test functions -> can be removed
//*/
//extern "C" JNIEXPORT int JNICALL Java_edu_umich_eecs_androidlibsvm_MainActivity_testInt(JNIEnv * env, jobject obj){
//	return 5566;
//}

extern "C" void Java_com_libsvm_svmClass_testLog(JNIEnv *env, jobject obj, jstring logThis){
	const char * szLogThis = env->GetStringUTFChars(logThis, 0);
	debug("%s",szLogThis);

	env->ReleaseStringUTFChars(logThis, szLogThis);
} 
