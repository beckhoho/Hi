package com.ly.hi.lbs.http;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by yancey on 2015/4/22 0022.
 */
public class RequestQueueManager {
    private static RequestQueue mRequestQueue;

    public static void init(Context context) {
        // ���SslHttpStack true����֧��ssl
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public static RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }

    /**
     * ����������
     *
     * @param request
     * @param tag
     */
    public static void addRequest(Request<?> request, Object tag) {
        if (tag != null) {
            request.setTag(tag);
        }
        try {
            getRequestQueue().add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ȡ����������
     *
     * @param tag
     */
    public static void cancelAll(Object tag) {
        if (mRequestQueue != null)
            try {
                getRequestQueue().cancelAll(tag);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
