package com.ly.hi.im.ui;

import java.lang.reflect.Type;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import cn.bmob.im.BmobUserManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.ly.hi.R;
import com.ly.hi.im.im.bean.User;
import com.ly.hi.im.ui.listener.ShakeListener;
import com.ly.hi.im.view.HeaderLayout.onRightTextViewClickListener;
import com.ly.hi.lbs.biz.SendModel;
import com.ly.hi.lbs.biz.base.BaseModel;
import com.ly.hi.lbs.common.BizInterface;
import com.ly.hi.lbs.response.BaseResponseParams;
import com.ly.hi.lbs.response.CreatePoiRes;
import com.ly.hi.lbs.response.DeletePoiRes;
import com.ly.hi.lbs.response.DetailTablesRes;

/**
 * ҡһҡactivity
 * 
 * @author liuy
 * 
 */
public class ShakeActivity extends BaseActivity {

	ShakeListener mShakeListener = null;
	Vibrator mVibrator;
	private RelativeLayout mImgUp;
	private RelativeLayout mImgDn;
	private RelativeLayout mTitle;

	// private SlidingDrawer mDrawer;
	private Button mDrawerBtn;

	private SendModel mModel = null;// ��������
	private BmobUserManager mUserManager;
	private User mUser;

	private ProgressDialog progress;

//	private Handler mDetailTableHandler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			switch (msg.what) {
//			case BaseModel.MSG_SUC:
//				BaseResponseParams<DetailTablesRes> response = (BaseResponseParams<DetailTablesRes>) msg.obj;
//				if (BaseModel.REQ_SUC.equals(response.getStatus())) {
//					if (!"0".equals(response.getObj().getTotal())) {
//						String geoId = response.getObj().getPois().get(0).getId();
//						deleteGeoByTitle(geoId);
//					}
//				}
//				break;
//			}
//
//		}
//	};
//
//	private Handler mDeleteGeoHandler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			switch (msg.what) {
//			case BaseModel.MSG_SUC:
//				progress.dismiss();
//				BaseResponseParams<DeletePoiRes> response = (BaseResponseParams<DeletePoiRes>) msg.obj;
//				if (BaseModel.REQ_SUC.equals(response.getStatus())) {
//					ShowToast("ɾ���ɹ���");
//				} else if ("21".equals(response.getStatus())) {
//					ShowToast("ɾ���ɹ���");
//				}
//				break;
//			default:
//				progress.dismiss();
//				break;
//			}
//
//		}
//	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shake);
		ShowToast("ҡһҡ���Զ�����λ����Ϣ!");
		mUserManager = BmobUserManager.getInstance(this);
		// drawerSet ();//���� drawer���� �л� ��ť�ķ���
//		 initTopBarForLeft("ҡһҡ");
//		 mHeaderLayout.getRightImageButton().setEnabled(false);

		initTopBarForBoth("ҡһҡ", "���λ��", new onRightTextViewClickListener() {

			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				// Intent intent = new Intent(ShakeActivity.this, NearPeopleActivity.class);
				// startAnimActivity(intent);
				// getDetailTable();
				mUser = mUserManager.getCurrentUser(User.class);
				if (!TextUtils.isEmpty(mUser.getUsername())) {
					deleteGeoByTitle(mUser.getUsername());
				}

			}
		});
		mHeaderLayout.getRightTextView().setEnabled(true);

		mVibrator = (Vibrator) getApplication().getSystemService(VIBRATOR_SERVICE);

		mImgUp = (RelativeLayout) findViewById(R.id.shakeImgUp);
		mImgDn = (RelativeLayout) findViewById(R.id.shakeImgDown);
		// mTitle = (RelativeLayout) findViewById(R.id.shake_title_bar);

		// mDrawer = (SlidingDrawer) findViewById(R.id.slidingDrawer1);
		// mDrawerBtn = (Button) findViewById(R.id.handle);
		/*
		 * mDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() { public void onDrawerOpened() {
		 * mDrawerBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.shake_report_dragger_down)); TranslateAnimation titleup = new
		 * TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-1.0f); titleup.setDuration(200);
		 * titleup.setFillAfter(true); mTitle.startAnimation(titleup); } });
		 */
		/* �趨SlidingDrawer���رյ��¼����� */
		/*
		 * mDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() { public void onDrawerClosed() {
		 * mDrawerBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.shake_report_dragger_up)); TranslateAnimation titledn = new
		 * TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-1.0f,Animation.RELATIVE_TO_SELF,0f); titledn.setDuration(200);
		 * titledn.setFillAfter(false); mTitle.startAnimation(titledn); } });
		 */
		mShakeListener = new ShakeListener(this);
		mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
			public void onShake() {
				// Toast.makeText(getApplicationContext(), "��Ǹ����ʱû���ҵ���ͬһʱ��ҡһҡ���ˡ�\n����һ�ΰɣ�", Toast.LENGTH_SHORT).show();
				startAnim(); // ��ʼ ҡһҡ���ƶ���
				mShakeListener.stop();
				startVibrato(); // ��ʼ ��
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						// Toast.makeText(getApplicationContext(), "��Ǹ����ʱû���ҵ�\n��ͬһʱ��ҡһҡ���ˡ�\n����һ�ΰɣ�", 500).setGravity(Gravity.CENTER,0,0).show();
						// Toast mtoast;
						// mtoast = Toast.makeText(getApplicationContext(),
						// "��Ǹ����ʱû���ҵ�\n��ͬһʱ��ҡһҡ���ˡ�\n����һ�ΰɣ�", 10);
						// //mtoast.setGravity(Gravity.CENTER, 0, 0);
						// mtoast.show();
						Intent intent = new Intent(ShakeActivity.this, NearLocationActivity.class);
						startActivity(intent);
						mVibrator.cancel();
						mShakeListener.start();
						// finish();
					}
				}, 2000);
			}
		});
	}

	/**
	 * ɾ������
	 */
	protected void deleteGeoByTitle(String title) {
//		mModel = new SendModel(mDeleteGeoHandler, getApplicationContext(), getTag(), getRequestQueue());
//		mModel.deletePoiByTitle(title);
		
		
		String url = BizInterface.DELETE_POI;
		RequestParams params = new RequestParams();
        params.addBodyParameter("title", title);
        params.addBodyParameter("geotable_id", BizInterface.BAIDU_LBS_GEOTABLE_ID);
        params.addBodyParameter("ak", BizInterface.BAIDU_LBS_AK);

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST,
                url,
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                    	progress = new ProgressDialog(ShakeActivity.this);
    					progress.setMessage("���������");
    					progress.setCanceledOnTouchOutside(false);
    					progress.show();
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                    	progress.dismiss();
                    	BaseResponseParams<DeletePoiRes> responseParams = new BaseResponseParams<DeletePoiRes>();
                        DeletePoiRes data = responseParams.parseResponseData(responseInfo.result, DeletePoiRes.class);
                        if("0".equals(responseParams.getStatus())){
                        	ShowToast("����ɹ���");
                        }if ("21".equals(responseParams.getStatus())){
                        	ShowToast("����ɹ���");
                        }else{
                        	ShowToast("���ʧ�ܣ�");
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                    	ShowToast("���ʧ�ܣ�");
                    	progress.dismiss();
                    }
                });
		
	}

	/**
	 * ��ȡ�б���ϸ
	 */
//	protected void getDetailTable() {
////		mModel = new SendModel(mDetailTableHandler, getApplicationContext(), getTag(), getRequestQueue());
//
//		mUser = mUserManager.getCurrentUser(User.class);
////		mModel.detailGeotable(mUser.getUsername(), mUser.getObjectId());
//		
//		
//		String url = BizInterface.DETAIL_GEOTABLE + mUser.getUsername() + "&tags=" + mUser.getObjectId();
//		HttpUtils http = new HttpUtils();
//		http.send(HttpRequest.HttpMethod.GET,
//		    url,
//		    new RequestCallBack<String>(){
//		        @Override
//		        public void onLoading(long total, long current, boolean isUploading) {
//		        }
//
//		        @Override
//		        public void onSuccess(ResponseInfo<String> responseInfo) {
//		        }
//
//		        @Override
//		        public void onStart() {
//		        }
//
//		        @Override
//		        public void onFailure(HttpException error, String msg) {
//		        }
//		});
//	}

	public void startAnim() { // ����ҡһҡ��������
		AnimationSet animup = new AnimationSet(true);
		TranslateAnimation mytranslateanimup0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, -0.5f);
		mytranslateanimup0.setDuration(1000);
		TranslateAnimation mytranslateanimup1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, +0.5f);
		mytranslateanimup1.setDuration(1000);
		mytranslateanimup1.setStartOffset(1000);
		animup.addAnimation(mytranslateanimup0);
		animup.addAnimation(mytranslateanimup1);
		mImgUp.startAnimation(animup);

		AnimationSet animdn = new AnimationSet(true);
		TranslateAnimation mytranslateanimdn0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, +0.5f);
		mytranslateanimdn0.setDuration(1000);
		TranslateAnimation mytranslateanimdn1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, -0.5f);
		mytranslateanimdn1.setDuration(1000);
		mytranslateanimdn1.setStartOffset(1000);
		animdn.addAnimation(mytranslateanimdn0);
		animdn.addAnimation(mytranslateanimdn1);
		mImgDn.startAnimation(animdn);
	}

	public void startVibrato() { // ������
		mVibrator.vibrate(new long[] { 500, 200, 500, 200 }, -1); // ��һ�����������ǽ������飬 �ڶ����������ظ�������-1Ϊ���ظ�����-1���մ�pattern��ָ���±꿪ʼ�ظ�
	}

	public void shake_activity_back(View v) { // ������ ���ذ�ť
		this.finish();
	}

	// public void linshi(View v) { //������
	// startAnim();
	// }
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mShakeListener != null) {
			mShakeListener.stop();
		}
	}
}