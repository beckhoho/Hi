package com.ly.hi.lbs.http.params;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.ly.hi.lbs.common.BizInterface;

/**
 * ����Ļ�������
 */
public class BaseRequestParams {
    //�ӿڰ汾��
    protected String ak;      //��Կ

    public BaseRequestParams() {
        this.ak = BizInterface.BAIDU_LBS_AK;
    }

	public String getAk() {
		return ak;
	}

	public void setAk(String ak) {
		this.ak = ak;
	}
    
    
}
