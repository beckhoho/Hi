package com.ly.hi.im.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ly.hi.R;
import com.ly.hi.im.util.PixelUtil;

/**
 * �Զ���ͷ������
 * 
 * @ClassName: HeaderLayout
 * @Description: TODO
 * @author smile
 * @date 2014-5-19 ����2:30:30
 */
public class HeaderLayout extends LinearLayout {
	private LayoutInflater mInflater;
	private View mHeader;
	private LinearLayout mLayoutLeftContainer;
	private LinearLayout mLayoutRightContainer;
	private TextView mHtvSubTitle, mRightTextView;
	private LinearLayout mLayoutRightImageButtonLayout, mLayoutRightTextViewLayout;
	private Button mRightImageButton;
	private onRightImageButtonClickListener mRightImageButtonClickListener;
	private onRightTextViewClickListener mRightTextViewClickListener;

	private LinearLayout mLayoutLeftImageButtonLayout;
	private ImageButton mLeftImageButton;
	private onLeftImageButtonClickListener mLeftImageButtonClickListener;

	public enum HeaderStyle {// ͷ��������ʽ
		DEFAULT_TITLE, TITLE_LIFT_IMAGEBUTTON, TITLE_RIGHT_IMAGEBUTTON, TITLE_DOUBLE_IMAGEBUTTON, TITLE_DOUBLE_TEXTVIEW;
	}

	public HeaderLayout(Context context) {
		super(context);
		init(context);
	}

	public HeaderLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public void init(Context context) {
		mInflater = LayoutInflater.from(context);
		mHeader = mInflater.inflate(R.layout.common_header, null);
		addView(mHeader);
		initViews();
	}

	public void initViews() {
		mLayoutLeftContainer = (LinearLayout) findViewByHeaderId(R.id.header_layout_leftview_container);
		// mLayoutMiddleContainer = (LinearLayout)
		// findViewByHeaderId(R.id.header_layout_middleview_container);�м䲿�������������������ťʱ�ɴ�
		mLayoutRightContainer = (LinearLayout) findViewByHeaderId(R.id.header_layout_rightview_container);
		mHtvSubTitle = (TextView) findViewByHeaderId(R.id.header_htv_subtitle);

	}

	public View findViewByHeaderId(int id) {
		return mHeader.findViewById(id);
	}

	public void init(HeaderStyle hStyle) {
		switch (hStyle) {
		case DEFAULT_TITLE:
			defaultTitle();
			break;

		case TITLE_LIFT_IMAGEBUTTON:
			defaultTitle();
			titleLeftImageButton();
			break;

		case TITLE_RIGHT_IMAGEBUTTON:
			defaultTitle();
			titleRightImageButton();
			break;

		case TITLE_DOUBLE_IMAGEBUTTON:
			defaultTitle();
			titleLeftImageButton();
			titleRightImageButton();
			break;
			
		case TITLE_DOUBLE_TEXTVIEW:
			defaultTitle();
			titleLeftImageButton();
			titleRightTextView();
			break;
		}
	}

	// Ĭ�����ֱ���
	private void defaultTitle() {
		mLayoutLeftContainer.removeAllViews();
		mLayoutRightContainer.removeAllViews();
	}

	// ����Զ��尴ť
	private void titleLeftImageButton() {
		View mleftImageButtonView = mInflater.inflate(R.layout.common_header_button, null);
		mLayoutLeftContainer.addView(mleftImageButtonView);
		mLayoutLeftImageButtonLayout = (LinearLayout) mleftImageButtonView.findViewById(R.id.header_layout_imagebuttonlayout);
		mLeftImageButton = (ImageButton) mleftImageButtonView.findViewById(R.id.header_ib_imagebutton);
		mLayoutLeftImageButtonLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (mLeftImageButtonClickListener != null) {
					mLeftImageButtonClickListener.onClick();
				}
			}
		});
	}

	// �Ҳ��Զ��尴ť
	private void titleRightImageButton() {
		View mRightImageButtonView = mInflater.inflate(R.layout.common_header_rightbutton, null);
		mLayoutRightContainer.addView(mRightImageButtonView);
		mLayoutRightImageButtonLayout = (LinearLayout) mRightImageButtonView.findViewById(R.id.header_layout_imagebuttonlayout);
		mRightImageButton = (Button) mRightImageButtonView.findViewById(R.id.header_ib_imagebutton);
		mLayoutRightImageButtonLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (mRightImageButtonClickListener != null) {
					mRightImageButtonClickListener.onClick();
				}
			}
		});
	}

	// �Ҳ��Զ�������
	private void titleRightTextView() {
		View rightTextView = mInflater.inflate(R.layout.common_header_righttext, null);
		mLayoutRightContainer.addView(rightTextView);
		mLayoutRightTextViewLayout = (LinearLayout) rightTextView.findViewById(R.id.header_layout_textlayout);
		mRightTextView = (TextView) rightTextView.findViewById(R.id.header_ib_text);
		mLayoutRightTextViewLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (mRightTextViewClickListener != null) {
					mRightTextViewClickListener.onClick();
				}
			}
		});
	}

	/**
	 * ��ȡ�ұ߰�ť
	 * 
	 * @Title: getRightImageButton
	 * @Description: TODO
	 * @param @return
	 * @return Button
	 * @throws
	 */
	public Button getRightImageButton() {
		if (mRightImageButton != null) {
			return mRightImageButton;
		}
		return null;
	}
	/**
	 * ��ȡ�ұ�����
	 * 
	 * @Title: getRightImageButton
	 * @Description: TODO
	 * @param @return
	 * @return TextView
	 * @throws
	 */
	public TextView getRightTextView() {
		if (mRightTextView != null) {
			return mRightTextView;
		}
		return null;
	}

	public void setDefaultTitle(CharSequence title) {
		if (title != null) {
			mHtvSubTitle.setText(title);
		} else {
			mHtvSubTitle.setVisibility(View.GONE);
		}
	}

	public void setTitleAndRightButton(CharSequence title, int backid, String text, onRightImageButtonClickListener onRightImageButtonClickListener) {
		setDefaultTitle(title);
		mLayoutRightContainer.setVisibility(View.VISIBLE);
		if (mRightImageButton != null && backid > 0) {
			mRightImageButton.setWidth(PixelUtil.dp2px(45));
			mRightImageButton.setHeight(PixelUtil.dp2px(40));
			mRightImageButton.setBackgroundResource(backid);
			mRightImageButton.setText(text);
			setOnRightImageButtonClickListener(onRightImageButtonClickListener);
		}
	}

	public void setTitleAndRightText(CharSequence title, String text, onRightTextViewClickListener onRightTextViewClickListener) {
		setDefaultTitle(title);
		mLayoutRightContainer.setVisibility(View.VISIBLE);
		if (mRightTextView != null) {
			mRightTextView.setWidth(PixelUtil.dp2px(45));
			mRightTextView.setHeight(PixelUtil.dp2px(40));
			mRightTextView.setText(text);
			setOnRightTextViewClickListener(onRightTextViewClickListener);
		}
	}
	
	public void setTitleAndRightImageButton(CharSequence title, int backid, onRightImageButtonClickListener onRightImageButtonClickListener) {
		setDefaultTitle(title);
		mLayoutRightContainer.setVisibility(View.VISIBLE);
		if (mRightImageButton != null && backid > 0) {
			mRightImageButton.setWidth(PixelUtil.dp2px(30));
			mRightImageButton.setHeight(PixelUtil.dp2px(30));
			mRightImageButton.setTextColor(getResources().getColor(R.color.transparent));
			mRightImageButton.setBackgroundResource(backid);
			setOnRightImageButtonClickListener(onRightImageButtonClickListener);
		}
	}

	public void setTitleAndLeftImageButton(CharSequence title, int id, onLeftImageButtonClickListener listener) {
		setDefaultTitle(title);
		if (mLeftImageButton != null && id > 0) {
			mLeftImageButton.setImageResource(id);
			setOnLeftImageButtonClickListener(listener);
		}
		mLayoutRightContainer.setVisibility(View.INVISIBLE);
	}

	public void setOnRightImageButtonClickListener(onRightImageButtonClickListener listener) {
		mRightImageButtonClickListener = listener;
	}
	
	public void setOnRightTextViewClickListener(onRightTextViewClickListener listener) {
		mRightTextViewClickListener = listener;
	}

	public interface onRightImageButtonClickListener {
		void onClick();
	}
	public interface onRightTextViewClickListener{
		void onClick();
	}

	public void setOnLeftImageButtonClickListener(onLeftImageButtonClickListener listener) {
		mLeftImageButtonClickListener = listener;
	}

	public interface onLeftImageButtonClickListener {
		void onClick();
	}

}
