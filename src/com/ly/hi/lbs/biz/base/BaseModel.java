package com.ly.hi.lbs.biz.base;

import java.io.UnsupportedEncodingException;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.ly.hi.CustomApplication;
import com.ly.hi.R;
import com.ly.hi.lbs.bean.LBSVolleyError;

/**
 * <Pre>
 * ҵ�������
 * </Pre>
 */
public class BaseModel  {

    //http�������
    protected RequestQueue requestQueue;
    protected Handler handler;

    protected static Context context;

    private String tag = "tag";

    //http���سɹ�
    public static final int MSG_SUC = 0;
    public static final String REQ_SUC = "0";
    //http����ʧ��
    public static final int MSG_FAILED = 0x0051;

    //��Ȩʧ��
    public static final int HTTP_CONNECT_AUTHFAILURE_ERROR = 0x0060;
    //�������
    public static final int HTTP_CONNECT_NETWORK_ERROR = 0x0061;
    //������
    public static final int HTTP_CONNECT_NOCONNECTION_ERROR = 0x0062;
    //����ת��ʧ��
    public static final int HTTP_CONNECT_PARSE_ERROR = 0x0063;
    //����������
    public static final int HTTP_CONNECT_SERVER_ERROR = 0x0064;
    //���ӳ�ʱ
    public static final int HTTP_CONNECT_TIMEOUT_ERROR = 0x0065;

    //�ӿڷ�����-key
    public static final String KEY_METHOD = "method";

    //������ʾ
    public static final String KEY_ERROR_TIP = "key_error_tip";

    @Deprecated
    public BaseModel(Context context) {
        this(null,context);
    }

    public BaseModel(Context ctx, String tag){
        this(null,ctx,tag, CustomApplication.getInstance().getRequestQueue());
    }

    public BaseModel(Context ctx, String tag, RequestQueue requestQueue){
        this(null,ctx,tag,requestQueue);
    }

    @Deprecated
    public BaseModel(Handler handler, Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
        if(handler != null)
            this.handler = handler;
    }

    public BaseModel(Handler handler, Context context, String tag, RequestQueue requestQueue) {
        this.context = context;
        this.tag = tag;
        this.requestQueue = requestQueue;
        if(handler != null){
            this.handler = handler;
        }
    }

    /**
     * Http���سɹ��ص�����
     * @param h         handler
     * @param obj       http�������ݶ���
     * @param method    ���ʵĽӿڷ���
     */
    public static void onSuccess(Handler h, Object obj, String method) {

        if(h == null){
            throw new IllegalArgumentException("Handler cannot be empty.");
        }

        Message msg = h.obtainMessage(BaseModel.MSG_SUC,obj);

        Bundle data = new Bundle();
        data.putString("method",method);
        msg.setData(data);

        h.sendMessage(msg);

    }

    public static void onSuccess(Handler h, Object obj) {

        if(h == null){
            throw new IllegalArgumentException("Handler cannot be empty.");
        }

        Message msg = h.obtainMessage(BaseModel.MSG_SUC, obj);

        Bundle data = new Bundle();
        msg.setData(data);

        h.sendMessage(msg);

    }

    /**
     * Http����ʧ�ܻص�����
     * @param h         handler
     * @param err
     */
    public static void onFailed(Handler h, LBSVolleyError err) {

        if(h == null){
            throw new IllegalArgumentException("Handler cannot be empty.");
        }

        VolleyError volleyError = err.getError();
        Message msg;
        Bundle data = new Bundle();

        if (volleyError instanceof AuthFailureError) {
            //Error indicating that there was an authentication failure when performing a Request.
            msg = h.obtainMessage(HTTP_CONNECT_AUTHFAILURE_ERROR, err);
            data.putString(KEY_ERROR_TIP, context.getString(R.string.http_connect_auth_failure_error_msg));

        } else if (volleyError instanceof NetworkError) {
            //Indicates that there was a network error when performing a Volley request.
            msg = h.obtainMessage(HTTP_CONNECT_NETWORK_ERROR, err);
            data.putString(KEY_ERROR_TIP, context.getString(R.string.http_connect_network_error_msg));

        } else if (volleyError instanceof NoConnectionError) {
            //Error indicating that no connection could be established when performing a Volley request.
            msg = h.obtainMessage(HTTP_CONNECT_NOCONNECTION_ERROR, err);
            data.putString(KEY_ERROR_TIP, context.getString(R.string.http_connect_no_connection_error_msg));

        } else if (volleyError instanceof ParseError) {
            //Indicates that the server's response could not be parsed.
            msg = h.obtainMessage(HTTP_CONNECT_PARSE_ERROR, err);
            data.putString(KEY_ERROR_TIP, context.getString(R.string.http_connect_parse_error_msg));

        } else if (volleyError instanceof ServerError) {
            //Indicates that the server responded with an error response.
            msg = h.obtainMessage(HTTP_CONNECT_SERVER_ERROR, err);
            data.putString(KEY_ERROR_TIP, context.getString(R.string.http_connect_server_error_msg));

        } else if (volleyError instanceof TimeoutError) {
            //Indicates that the connection or the socket timed out.
            msg = h.obtainMessage(HTTP_CONNECT_TIMEOUT_ERROR, err);
            data.putString(KEY_ERROR_TIP, context.getString(R.string.http_connect_timeout_error_msg));

        } else {
            //δ֪�Ĵ���
            msg = h.obtainMessage(MSG_FAILED, err);
            data.putString(KEY_ERROR_TIP, context.getString(R.string.http_connect_unknow_error_msg));
        }


        data.putString(KEY_METHOD,err.getMethod());
        msg.setData(data);

        h.sendMessage(msg);

    }

    /**
     * ��URL����ת����utf-8�ַ���
     * @param url
     * @return
     */
    public static String urltoUTF8(String url) {
        String url_utf8 = null;
        try {
            url_utf8 = new String(url.getBytes("UTF-8"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return url_utf8;
    }

    /**
     * ��ȡ�ӿڷ�������
     * @param url
     */
    public static String getMethodName(String url) {
        String name = new String();
        name = url.substring(url.indexOf("=")+1,url.length());

        return name;
    }

    /**
     * ��ȡ����URL��ַ
     * @param url
     * @return
     */
    public static String getUrl(String url){

        String name = url.split("\\?")[0];

        return name;
    }

    /**
     * ����request��tag���
     * @param request
     */
    public void setTag(Request<?> request){
        request.setTag(tag);
    }

    /**
     * ���request�����������
     * @param request
     */
    public void addRequest(Request<?> request){
        setTag(request);
        requestQueue.add(request);
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
