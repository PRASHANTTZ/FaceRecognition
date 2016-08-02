#include <binarysearch.h>
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <iostream>

using namespace std ;
using namespace cv ;

static int modeArray[] = {0 ,
1,2,3,4,6,7,8,12,14,15,16,
24,28,30,31,32,48,56,60,62,
63,64,96,120,122,124,126,127,
128,129,131,135,139,155,187,192,193,
195,199,207,223,224,225,227,231,239,240,
241,243,247,248,249,251,252,253,254,255
} ;


JNIEXPORT jboolean  JNICALL Java_com_jni_ImgDealJni_binarySearch(JNIEnv* env, jclass obj , jint pixels)
{
	    int length = 58 ;
		int begin = 0 ;
		int middle = 0 ;
		int end = length - 1 ;

		while(begin <= end)
		{
			middle = (end + begin) >> 1 ;

			if(modeArray[middle] ==  pixels)
			{
				return true ;
			}

			if(modeArray[middle] > pixels)
			{
				end = middle - 1;
			}
			else if(modeArray[middle] < pixels)
			{
				begin  = middle + 1 ;
			}
		}

		return false ;

}
