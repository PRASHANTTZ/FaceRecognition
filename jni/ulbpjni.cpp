#include <ulbpjni.h>
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <iostream>

using namespace cv;
using namespace std;

#define PAI 3.1415

Mat * CLBP(Mat *);
int getX(int  ,int  ,int );
int getY(int  ,int  ,int );

JNIEXPORT jintArray  JNICALL Java_com_jni_ImgDealJni_UlbpAndhis(JNIEnv* env, jclass obj , jlong addr , jint div)
{
	Mat *src = (Mat*)addr ;
	int width = src->cols /div;
    int height = src->rows / div ;

    LOGI("chan:%d" , src->channels());

    jintArray  inner = env->NewIntArray(256*div*div);
    jint *pArray ;
    pArray = (jint *)calloc(256*div*div , sizeof(jint));

    for(int i = 0 ; i < div ; i++)
    {
    	for(int j = 0 ; j < div ; j++)
    	{
    		Rect r(i*width , j*height , width , height);
    		Mat rol_mat = Mat(*src , r);
    		Mat *lbp_mat = CLBP(&rol_mat);

    		for(int k = 0 ; k < (width - 4)*(height - 4) ; k++)
    		{
    			pArray[lbp_mat->at<uchar>(0 , k) + 256*((i*div) + j)] ++ ;
    		}
    	}
    }

    env->SetIntArrayRegion(inner , 0 , 256*div*div , pArray);

    free(pArray);
    //LOGI("result2");

    return inner ;
}
Mat * CLBP(Mat *src)
{
    int tmp[8]={0};
	int r = 2 ;
	Mat * eigenValue = new Mat(1 , (src->rows - 2*r)*(src->cols  - 2*r) , CV_8UC1);
    int s ;

	/*°ë¾¶*/
    /*±ßÔµ²»´¦Àí*/
	for (int i = r ; i < src->rows - r; i++ )
	{
      for(int j = r ;j< src->cols - r ; j++)
      {
		  if(src->at<uchar>(getX(0 , i , r) , getY(0 , j , r)) >= src->at<uchar>(i , j))
		  {
				tmp[0]=1;
		  }
          else
		  {
               tmp[0] = 0 ;
		  }
		 // cout << (int)src->at<uchar>(i , j - 1) << endl;
          if(src->at<uchar>(getX(1 , i , r) , getY(1 , j , r)) >= src->at<uchar>(i , j))
		  {
				tmp[1] = 1 ;
		  }
          else
		  {
				tmp[1]=0;
		  }
          if(src->at<uchar>(getX(2 , i , r), getY(2 , j , r)) >= src->at<uchar>(i , j))
		  {
				tmp[2]=1;
		  }
          else
		  {
				tmp[2]=0;
		  }
          if(src->at<uchar>(getX(3 , i , r) , getY(3 , j , r)) >= src->at<uchar>(i , j))
		  {
				tmp[3]=1;
		  }
		  else
		  {
				tmp[3]=0;
		  }
          if(src->at<uchar>(getX(4 , i , r) , getY(4 , j , r)) >= src->at<uchar>(i , j))
		  {
				tmp[4]=1;
		  }
          else
		  {
				tmp[4]=0;
		  }
          if(src->at<uchar>(getX(5 , i , r)  , getY(5 , j , r)) >= src->at<uchar>(i , j))
		  {
				tmp[5]=1;
		  }
          else
		  {
				tmp[5]=0;
		  }
          if(src->at<uchar>(getX(6 , i , r) , getY(6 , j , r)) >= src->at<uchar>(i , j))
		  {
				tmp[6]=1;
		  }
          else
		  {
				tmp[6]=0 ;
		  }
          if(src->at<uchar>(getX(7 , i  , r) , getY(7 , j , r)) >= src->at<uchar>(i , j))
		  {
				tmp[7]=1;
		  }
          else
		  {
				tmp[7]=0 ;
		  }

		  s = (tmp[0]*1+tmp[1]*2+tmp[2]*4+tmp[3]*8+tmp[4]*16+tmp[5]*32+tmp[6]*64+tmp[7]*128);
          //¼ÆËãLBP±àÂë
		  eigenValue->at<uchar>(0 , (i - r)*(src->cols - 2*r) + (j - r)) = s ;
		   //cout << (int)eigenValue->at<uchar>(0 , (i - 1)*(src->cols - 2) + (j - 1) );

		  //LOGI("value:%d" , s);
      }

	 // cout << endl ;

	}
	  return eigenValue ;
}

int getX(int p ,int x ,int r)
{
	int P = 8 ;
	int tran =  (r*cos(2*PAI*p/P) > 0)? (r*cos(2*PAI*p/P) + 0.5) : r*cos(2*PAI*p/P) - 0.5;
	return x + tran;
}

int getY(int p ,int y , int r)
{
	int P = 8 ;
	int tran =  (r*sin(2*PAI*p/P) > 0)? (r*sin(2*PAI*p/P) + 0.5) : r*sin(2*PAI*p/P) - 0.5;
	return y - tran;
}
