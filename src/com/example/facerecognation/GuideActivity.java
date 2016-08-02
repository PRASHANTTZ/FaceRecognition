package com.example.facerecognation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.ui.adapter.ViewPagerAdapter;

/**
 * ��������
 */
public class GuideActivity extends Activity implements OnPageChangeListener
{
    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;
    // ��¼��ǰѡ��λ��
    private int currentIndex ;
    private  ProgressBar  dataLoadPro ;
    private loadDataNati loadDataHandler ;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide);
        // ��ʼ��ҳ��
        initViews();
        
        loadDataHandler = new loadDataNati();
        new Thread(new loadDataThread()).start();
        /*�����µ��߳����������ݼ���*/
        dataLoadPro.setVisibility(View.VISIBLE);
        
    }

    private void initViews() {
    	
        LayoutInflater inflater = LayoutInflater.from(this);
        views = new ArrayList<View>();
        // ��ʼ������ͼƬ�б�
        views.add(inflater.inflate(R.layout.what_new_one, null));
        views.add(inflater.inflate(R.layout.what_new_two, null));
        views.add(inflater.inflate(R.layout.what_new_three, null));
        //��ʼ��Adapter
        vpAdapter = new ViewPagerAdapter(views, this);
        vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setAdapter(vpAdapter);
        // �󶨻ص�
        vp.setOnPageChangeListener(this);
        
        dataLoadPro = (ProgressBar)findViewById(R.id.dataLoadPro);
    }
    // ������״̬�ı�ʱ����
    @Override
    public void onPageScrollStateChanged(int arg0) {
    	
    }
    // ����ǰҳ�汻����ʱ����
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    	
    }

    // ���µ�ҳ�汻ѡ��ʱ����
    @Override
    public void onPageSelected(int arg0) {
    	
    }
     class loadDataThread implements Runnable{

		@Override
		public void run() {
			
			// TODO Auto-generated method stub
			/*���ݼ���*/
			Message  msg = new Message();
			msg.what = 0 ;
			/*ת������*/
			deepFile(getApplicationContext() , "myfacedata");
			/*д��xml*/
			
			loadDataHandler.sendMessage(msg);
		}
     }
     
     public void deepFile(Context con , String path) 
     { 
         try 
         { 
            String str[] = con.getAssets().list(path);
            
            if (str.length > 0) { 
            	
         	   //�����Ŀ¼ 
               File file = new File(Environment.getExternalStorageDirectory() + "/"+path); 
               file.mkdirs(); 
               
               for (String string : str) { 
             	  
                  path = path + "/" + string ; 
                  System.out.println("zhoulc:\t" + path); 
                   // textView.setText(textView.getText()+"\t"+path+"\t"); 
                  deepFile(con , path); 
                  path = path.substring(0 , path.lastIndexOf('/')); 
               } 
               
            } else
            {
         	   //������ļ� 
                InputStream is = con.getAssets().open(path); 
                File f = new  File(Environment.getExternalStorageDirectory() 
                        + "/" + path);

                FileOutputStream fos = new FileOutputStream(f); 
                
               byte[] buffer = new byte[1024]; 
               int count = 0 ; 
                 while (true) { 
                   count++; 
                   int len = is.read(buffer); 
                    if (len == -1) { 
                       break; 
                  } 
                  fos.write(buffer, 0, len); 
              } 
              is.close(); 
              fos.close(); 
          } 
       } catch (IOException e) { 
    	   
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
        } 
     }
     class loadDataNati extends Handler{

		@Override
		public void handleMessage(Message msg) {
			
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			switch(msg.what)
			{
			case 0:
				
				dataLoadPro.setVisibility(View.GONE);
				
				break;
			}
			
		}
    	 
     } 
}