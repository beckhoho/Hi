package com.ly.hi.lbs.bean;

import com.android.volley.VolleyError;

/**
 * <Pre>
 * TODO �������ļ���ʲô
 * </Pre>
 *
 * @author ����
 * @version 1.0
 *          <p/>
 *          Create by 2015/4/1 0001 ���� 2:44
 */
public class LBSVolleyError {

    private VolleyError error;
    private String method;

    public LBSVolleyError(VolleyError error, String method) {
        this.error = error;
        this.method = method;
    }
    public LBSVolleyError(VolleyError error) {
        this.error = error;
    }

    public VolleyError getError() {
        return error;
    }

    public void setError(VolleyError error) {
        this.error = error;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
