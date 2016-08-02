package com.ui.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.facerecognation.MainActivity;
import com.example.facerecognation.R;
import com.example.facerecognation.loginActivity;
import com.util.sharePUtil;


/**
 *     class desc: ����ҳ��������
 */
public class ViewPagerAdapter extends PagerAdapter
{

	// �����б�
	private List<View> mViews;
	private Activity mActivity;

	public ViewPagerAdapter(List<View> mViews, Activity mActivity)
	{
		this.mViews = mViews;
		this.mActivity = mActivity;
	}

	// ����arg1λ�õĽ���
	@Override
	public void destroyItem(View arg0, int arg1, Object arg2)
	{
		((ViewPager) arg0).removeView(mViews.get(arg1));
	}

	@Override
	public void finishUpdate(View arg0)
	{
	}

	// ��õ�ǰ������
	@Override
	public int getCount()
	{
		if (mViews != null)
		{
			return mViews.size();
		}
		return 0;
	}

	// ��ʼ��arg1λ�õĽ���
	@Override
	public Object instantiateItem(View arg0, int arg1)
	{
		((ViewPager) arg0).addView(mViews.get(arg1), 0);
		if (arg1 == mViews.size() - 1)
		{
			Button mStartWeiboImageButton = (Button) arg0
					.findViewById(R.id.iv_start_weibo);
			mStartWeiboImageButton.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					// �����Ѿ�����
					setGuided();
				}
			});
		}
		return mViews.get(arg1);
	}

	/**
	 * 
	 * method desc�������Ѿ��������ˣ��´����������ٴ�����
	 */
	
	private void setGuided()
	{
		sharePUtil.setShareFirstPre(mActivity);
		Intent i = new Intent(mActivity , MainActivity.class);
		mActivity.startActivity(i);
		mActivity.finish();
	}
	
	// �ж��Ƿ��ɶ������ɽ���
	@Override
	public boolean isViewFromObject(View arg0, Object arg1)
	{
		return (arg0 == arg1);
	}
	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1)
	{
	}

	@Override
	public Parcelable saveState()
	{
		return null;
	}

	@Override
	public void startUpdate(View arg0)
	{
		
	}

}