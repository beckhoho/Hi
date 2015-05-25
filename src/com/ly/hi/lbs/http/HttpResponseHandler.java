package com.ly.hi.lbs.http;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ly.hi.R;
import com.ly.hi.lbs.http.params.ResponseParams;

public class HttpResponseHandler<T extends ResponseParams> {

    public void onSuccess(final T response) {
        if (response == null) {
            return;
        }

//        BaseApplication instance = BaseApplication.getInstance();
//        if (instance != null) {
//            if (Constant.FAIL_99999.equals(response.getCode())) {              //�������쳣
//                ToastUtils.makeText(instance, R.string.http_connect_server_error_msg);
//            } else if (Constant.FAIL_10007.equals(response.getCode())) {      //Token����
//                instance.onTokenExpired();
//            }
//        }

        this.onSuccess(response.getStatus(), response);
    }

    /**
     * ��������ʼ֮ǰ
     */
    public void onStart() {

    }

    /**
     * �����������֮�󣨲��������Ƿ�ɹ���
     */
    public void onFinish() {
    }

    public void onSuccess(final String code, final T response) {

    }


    public void onFailure(final VolleyError error) {
//        BaseApplication instance = BaseApplication.getInstance();
//        if (instance != null) {
//            ToastUtils.makeText(instance, R.string.http_connect_connect_error_msg);
//        }
    }

    public Response.Listener<T> listener = new Response.Listener<T>() {
        @Override
        public void onResponse(T response) {
            onSuccess(response);
            onFinish();
        }
    };
    public Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            onFailure(error);
            onFinish();
        }
    };
}
