LOCAL_PATH := $(call my-dir)  
include $(CLEAR_VARS)    
OpenCV_INSTALL_MODULES:=on
OPENCV_CAMERA_MODULES:=off
OPENCV_LIB_TYPE:=SHARE
include D:\OpenCV-2.4.9-android-sdk\sdk\native\jni\OpenCV.mk
LOCAL_MODULE     := image_deal 
LOCAL_SRC_FILES  := ImgDealJni.cpp  , resizejni.cpp  , lbpjni.cpp , ulbpjni.cpp  , binarySearch.cpp
                   
LOCAL_LDLIBS += -lm -llog 
 include $(BUILD_SHARED_LIBRARY) 
 
 include $(CLEAR_VARS)    
OpenCV_INSTALL_MODULES:=on
OPENCV_CAMERA_MODULES:=off
OPENCV_LIB_TYPE:=SHARE
include D:\OpenCV-2.4.9-android-sdk\sdk\native\jni\OpenCV.mk
LOCAL_MODULE     := libsvm 
LOCAL_SRC_FILES  := \
	common.cpp jnilibsvm.cpp \
	libsvm/svm-train.cpp \
	libsvm/svm-predict.cpp \
	libsvm/svm.cpp
                   
LOCAL_LDLIBS += -lm -llog 
 include $(BUILD_SHARED_LIBRARY) 