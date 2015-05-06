package com.ly.hi.im.ui;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import cn.bmob.im.BmobChatManager;
import cn.bmob.im.BmobNotifyManager;
import cn.bmob.im.bean.BmobInvitation;
import cn.bmob.im.bean.BmobMsg;
import cn.bmob.im.config.BmobConfig;
import cn.bmob.im.db.BmobDB;
import cn.bmob.im.inteface.EventListener;
import com.ly.hi.CustomApplication;
import com.ly.hi.R;
import com.ly.hi.im.common.MyMessageReceiver;
import com.ly.hi.im.ui.fragment.ContactFragment;
import com.ly.hi.im.ui.fragment.RecentFragment;
import com.ly.hi.im.ui.fragment.SettingsFragment;
import com.ly.hi.im.view.TabShadeView;

/**
 * ��½
 * 
 * @ClassName: MainActivity
 * @Description: TODO
 * @author smile
 * @date 2014-5-29 ����2:45:35
 */
public class MainActivity extends ActivityBase implements EventListener, ViewPager.OnPageChangeListener, OnClickListener {

	private ViewPager mViewPager;
	private List<Fragment> mTabs = new ArrayList<Fragment>();
	private FragmentPagerAdapter mAdapter;
	private List<TabShadeView> mTabIndicator = new ArrayList<TabShadeView>();

	// private Button[] mTabs;
	private ContactFragment contactFragment;
	private RecentFragment recentFragment;
	private SettingsFragment settingFragment;
	// private Fragment[] fragments;
	// private int index;
	private int currentTabIndex = 0;

	private ImageView iv_recent_tips, iv_contact_tips;// ��Ϣ��ʾ

	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setOverflowShowingAlways();
//		getActionBar().setDisplayShowHomeEnabled(false);;
//		getSupportActionBar().setDisplayShowHomeEnabled(false);
		// ������ʱ�����񣨵�λΪ�룩-���������̨�Ƿ���δ������Ϣ���еĻ���ȡ����
		// �������ü�����ȽϺ������͵�������Ҳ����ȥ����仰-ͬʱ����onDestory���������stopPollService����
		// BmobChat.getInstance(this).startPollService(30);
		// �����㲥������
		initNewMessageBroadCast();
		initTagMessageBroadCast();
		initView();
		// initTab();
	}

	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
	private void initView() {
		// mTabs = new Button[3];
		// mTabs[0] = (Button) findViewById(R.id.btn_message);
		// mTabs[1] = (Button) findViewById(R.id.btn_contract);
		// mTabs[2] = (Button) findViewById(R.id.btn_set);
		iv_recent_tips = (ImageView) findViewById(R.id.iv_recent_tips);
		iv_contact_tips = (ImageView) findViewById(R.id.iv_contact_tips);
		// �ѵ�һ��tab��Ϊѡ��״̬
		// mTabs[0].setSelected(true);

		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
		initDatas();
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(this);

	}

	private void initDatas() {
		recentFragment = new RecentFragment();
		contactFragment = new ContactFragment();
		settingFragment = new SettingsFragment();
		mTabs.add(recentFragment);
		mTabs.add(contactFragment);
		mTabs.add(settingFragment);

		// for (String title : mTitles) {
		// TabFragment tabFragment = new TabFragment();
		// Bundle args = new Bundle();
		// args.putString("title", title);
		// tabFragment.setArguments(args);
		//
		// }
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return mTabs.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return mTabs.get(arg0);
			}
		};
		initTabIndicator();

	}

	private void initTabIndicator() {
		TabShadeView one = (TabShadeView) findViewById(R.id.id_indicator_one);
		TabShadeView two = (TabShadeView) findViewById(R.id.id_indicator_two);
		TabShadeView three = (TabShadeView) findViewById(R.id.id_indicator_three);

		mTabIndicator.add(one);
		mTabIndicator.add(two);
		mTabIndicator.add(three);

		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);

		one.setImageView(1.0f);
		one.setTextView(1.0f);
	}

	// private void initTab(){
	// contactFragment = new ContactFragment();
	// recentFragment = new RecentFragment();
	// settingFragment = new SettingsFragment();
	// fragments = new Fragment[] {recentFragment, contactFragment, settingFragment };
	// // �����ʾ��һ��fragment
	// getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, recentFragment).
	// add(R.id.fragment_container, contactFragment).hide(contactFragment).show(recentFragment).commit();
	// }

	/**
	 * button����¼�
	 * 
	 * @param view
	 */
	// public void onTabSelect(View view) {
	// switch (view.getId()) {
	// case R.id.btn_message:
	// index = 0;
	// break;
	// case R.id.btn_contract:
	// index = 1;
	// break;
	// case R.id.btn_set:
	// index = 2;
	// break;
	// }
	// if (currentTabIndex != index) {
	// FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
	// trx.hide(fragments[currentTabIndex]);
	// if (!fragments[index].isAdded()) {
	// trx.add(R.id.fragment_container, fragments[index]);
	// }
	// trx.show(fragments[index]).commit();
	// }
	// mTabs[currentTabIndex].setSelected(false);
	// //�ѵ�ǰtab��Ϊѡ��״̬
	// mTabs[index].setSelected(true);
	// currentTabIndex = index;
	// }

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// СԲ����ʾ
		if (BmobDB.create(this).hasUnReadMsg()) {
			iv_recent_tips.setVisibility(View.VISIBLE);
		} else {
			iv_recent_tips.setVisibility(View.GONE);
		}
		if (BmobDB.create(this).hasNewInvite()) {
			iv_contact_tips.setVisibility(View.VISIBLE);
		} else {
			iv_contact_tips.setVisibility(View.GONE);
		}
		MyMessageReceiver.ehList.add(this);// �������͵���Ϣ
		// ���
		MyMessageReceiver.mNewNum = 0;

	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MyMessageReceiver.ehList.remove(this);// ȡ���������͵���Ϣ
	}

	@Override
	public void onMessage(BmobMsg message) {
		// TODO Auto-generated method stub
		refreshNewMsg(message);
	}

	/**
	 * ˢ�½���
	 * 
	 * @Title: refreshNewMsg
	 * @Description: TODO
	 * @param @param message
	 * @return void
	 * @throws
	 */
	private void refreshNewMsg(BmobMsg message) {
		// ������ʾ
		boolean isAllow = CustomApplication.getInstance().getSpUtil().isAllowVoice();
		if (isAllow) {
			CustomApplication.getInstance().getMediaPlayer().start();
		}
		iv_recent_tips.setVisibility(View.VISIBLE);
		// ҲҪ�洢����
		if (message != null) {
			BmobChatManager.getInstance(MainActivity.this).saveReceiveMessage(true, message);
		}
		if (currentTabIndex == 0) {
			// ��ǰҳ�����Ϊ�Ựҳ�棬ˢ�´�ҳ��
			if (recentFragment != null) {
				recentFragment.refresh();
			}
		}
	}

	NewBroadcastReceiver newReceiver;

	private void initNewMessageBroadCast() {
		// ע�������Ϣ�㲥
		newReceiver = new NewBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(BmobConfig.BROADCAST_NEW_MESSAGE);
		// ���ȼ�Ҫ����ChatActivity
		intentFilter.setPriority(3);
		registerReceiver(newReceiver, intentFilter);
	}

	/**
	 * ����Ϣ�㲥������
	 * 
	 */
	private class NewBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// ˢ�½���
			refreshNewMsg(null);
			// �ǵðѹ㲥���ս��
			abortBroadcast();
		}
	}

	TagBroadcastReceiver userReceiver;

	private void initTagMessageBroadCast() {
		// ע�������Ϣ�㲥
		userReceiver = new TagBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(BmobConfig.BROADCAST_ADD_USER_MESSAGE);
		// ���ȼ�Ҫ����ChatActivity
		intentFilter.setPriority(3);
		registerReceiver(userReceiver, intentFilter);
	}

	/**
	 * ��ǩ��Ϣ�㲥������
	 */
	private class TagBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			BmobInvitation message = (BmobInvitation) intent.getSerializableExtra("invite");
			refreshInvite(message);
			// �ǵðѹ㲥���ս��
			abortBroadcast();
		}
	}

	@Override
	public void onNetChange(boolean isNetConnected) {
		// TODO Auto-generated method stub
		if (isNetConnected) {
			ShowToast(R.string.network_tips);
		}
	}

	@Override
	public void onAddUser(BmobInvitation message) {
		// TODO Auto-generated method stub
		refreshInvite(message);
	}

	/**
	 * ˢ�º�������
	 * 
	 * @Title: notifyAddUser
	 * @Description: TODO
	 * @param @param message
	 * @return void
	 * @throws
	 */
	private void refreshInvite(BmobInvitation message) {
		boolean isAllow = CustomApplication.getInstance().getSpUtil().isAllowVoice();
		if (isAllow) {
			CustomApplication.getInstance().getMediaPlayer().start();
		}
		iv_contact_tips.setVisibility(View.VISIBLE);
		if (currentTabIndex == 1) {
			if (contactFragment != null) {
				contactFragment.refresh();
			}
		} else {
			// ͬʱ����֪ͨ
			String tickerText = message.getFromname() + "������Ӻ���";
			boolean isAllowVibrate = CustomApplication.getInstance().getSpUtil().isAllowVibrate();
			BmobNotifyManager.getInstance(this).showNotify(isAllow, isAllowVibrate, R.drawable.ic_launcher, tickerText, message.getFromname(), tickerText.toString(),
					NewFriendActivity.class);
		}
	}

	@Override
	public void onOffline() {
		// TODO Auto-generated method stub
		showOfflineDialog(this);
	}

	@Override
	public void onReaded(String conversionId, String msgTime) {
		// TODO Auto-generated method stub
	}

	private static long firstTime;

	/**
	 * ���������η��ؼ����˳�
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (firstTime + 2000 > System.currentTimeMillis()) {
			super.onBackPressed();
		} else {
			ShowToast("�ٰ�һ���˳�����");
		}
		firstTime = System.currentTimeMillis();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try {
			unregisterReceiver(newReceiver);
		} catch (Exception e) {
		}
		try {
			unregisterReceiver(userReceiver);
		} catch (Exception e) {
		}
		// ȡ����ʱ������
		// BmobChat.getInstance(this).stopPollService();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		if (positionOffset > 0) {
			TabShadeView left = mTabIndicator.get(position);
			TabShadeView right = mTabIndicator.get(position + 1);
			left.setImageView(1 - positionOffset);
			left.setTextView(1 - positionOffset);
			right.setImageView(positionOffset);
			right.setTextView(positionOffset);
		}
		currentTabIndex = position;
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		resetOtherTabs();
		switch (v.getId()) {
		case R.id.id_indicator_one:
			mTabIndicator.get(0).setImageView(1.0f);
			mTabIndicator.get(0).setTextView(1.0f);
			mViewPager.setCurrentItem(0, false);
			currentTabIndex = 0;
			break;
		case R.id.id_indicator_two:
			mTabIndicator.get(1).setImageView(1.0f);
			mTabIndicator.get(1).setTextView(1.0f);
			mViewPager.setCurrentItem(1, false);
			currentTabIndex = 1;
			break;
		case R.id.id_indicator_three:
			mTabIndicator.get(2).setImageView(1.0f);
			mTabIndicator.get(2).setTextView(1.0f);
			mViewPager.setCurrentItem(2, false);
			currentTabIndex = 2;
			break;
		}

	}

	/**
	 * ����������Tab
	 */
	private void resetOtherTabs() {
		for (int i = 0; i < mTabIndicator.size(); i++) {
			mTabIndicator.get(i).setImageView(0);
			mTabIndicator.get(i).setTextView(0);
		}
	}

	private void setOverflowShowingAlways() {
		try {
			// true if a permanent menu key is present, false otherwise.
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
