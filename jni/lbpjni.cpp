#include <lbpjni.h>
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <vector>
#include <iostream>

using namespace cv;
using namespace std;

JNIEXPORT jobjectArray  JNICALL Java_com_jni_ImgDealJni_LbpAndHis(JNIEnv* env, jclass obj , jlong addr)
{
	LOGI("one" , "one");
	Mat *src = (Mat*)addr;
	int width = src->cols ;
	int height = src->rows ;
	LOGI("two" , "one");
	int s ;
	int tmp[8] = {0};
	Mat * eigenValue = new Mat(1 , (src->rows - 2)*(src->cols  - 2) , CV_8UC1);
	jcharArray  inner = env->NewCharArray((width - 2)*(height - 2));
	jobjectArray eigen = env->NewObjectArray(1 , env->GetObjectClass(inner) , 0);

	LOGI("three" , "one");

	for (int i = 1 ; i < height -1 ; i++ )
		{
	      for(int j = 1 ; j < width - 1 ; j++)
	      {
			  if(src->at<uchar>(i - 1 , j - 1) > src->at<uchar>(i , j))
	            tmp[0]=1;
	          else
	            tmp[0]=0;

	          if(src->at<uchar>(i - 1 , j) >src->at<uchar>(i , j))
	            tmp[1]=1;
	          else
	            tmp[1]=0;
	          if(src->at<uchar>(i - 1 , j + 1) > src->at<uchar>(i , j))
	            tmp[2]=1;
	          else
	            tmp[2]=0;
	          if(src->at<uchar>(i , j + 1) > src->at<uchar>(i , j))
	            tmp[3]=1;
	      else
	            tmp[3]=0;
	          if(src->at<uchar>(i + 1 , j+1) > src->at<uchar>(i , j))
	            tmp[4]=1;
	          else
	            tmp[4]=0;
	          if(src->at<uchar>(i + 1  , j) > src->at<uchar>(i , j))
	            tmp[5]=1;
	          else
	            tmp[5]=0;
	          if(src->at<uchar>(i + 1 , j - 1) > src->at<uchar>(i , j))
	            tmp[6]=1;
	          else
	            tmp[6]=0;
	          if(src->at<uchar>(i ,j - 1) > src->at<uchar>(i , j))
	            tmp[7]=1;
	          else
	            tmp[7]=0;

	          //计算LBP编码
	           s = (tmp[0]*1+tmp[1]*2+tmp[2]*4+tmp[3]*8+tmp[4]*16+tmp[5]*32+tmp[6]*64+tmp[7]*128);
	           //s = (tmp[0]*1+tmp[1] << 1 + tmp[2] << 2 + tmp[3] << 3 + tmp[4] << 4 + tmp[5] << 5 + tmp[6] << 6 + tmp[7] << 7);
	           eigenValue->at<uchar>(0 , (i - 1)*(src->cols - 2) + (j - 1)) = s ;
	           //LOGI("result:%d" , s);
	      }
		}

        uchar * buf =  eigenValue->ptr<uchar>(0);
//        for(int i = 0; i < (width - 2)*(height - 2) ; i++)
//		{
//			//*(pArray + i) = *(buf + i); //复制buf数据元素到pArray内存空间
//        	 LOGI("result:%d" , buf[i]);
//		}
	    jchar *pArray ;
	    pArray = (jchar *)calloc((width - 2)*(height - 2) , sizeof(jchar));

	    /*复制数据*/
	    for(int i = 0; i < (width - 2)*(height - 2) ; i++)
		{
			*(pArray + i) = *(buf + i); //复制buf数据元素到pArray内存空间
		}

	    for(int i = 0 ; i <  (width - 2)*(height - 2) ; i++)
		{
			LOGI("result:%d" , pArray[i]);
		}

	    env->SetCharArrayRegion(inner , 0 , (width - 2)*(height - 2) , pArray);
	    env->SetObjectArrayElement(eigen , 0 , inner);

	    free(pArray);
	    free(buf);

        return eigen ;
}
