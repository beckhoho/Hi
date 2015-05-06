package com.ly.hi.lbs.response;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * <Pre>
 * TODO �������ļ���ʲô
 * </Pre>
 *
 * @author ����
 * @version 1.0
 *          <p/>
 *          Create by 2015/4/1 0001 ���� 3:05
 */
public class BaseResponseParams<T> {

    protected String status;
    protected String message;
//    protected String method;

   /* public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }*/

    /**
     * �������ص�json����,ת����װ����
     *
     * @param response
     * @param x
     * @param <T>
     * @return
     */
    public <T> T parseResponseData(String response, Type x) {
        Gson gson = new Gson();
        TypeToken<BaseResponseParams> typeToken = new TypeToken<BaseResponseParams>() {
        };
        BaseResponseParams responseParams = gson.fromJson(response, typeToken.getType());
        if (responseParams != null) {
            setStatus(responseParams.getStatus());
            setMessage(responseParams.getMessage());
            T data;
            try {
                data = gson.fromJson(response, x);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                return null;
            } catch (IllegalStateException e) {
                e.printStackTrace();
                return null;
            }
            return data;

        }
        return null;

    }

    /**
     * �������ص�json����,ת����װ����
     *
     * @param response
     * @param classOfT
     * @param <T>
     * @return
     */
    public <T> T parseResponseData(String response, Class<T> classOfT) {
        return parseResponseData(response, (Type) classOfT);
    }


    public BaseResponseParams() {
    }

    //ʵ���������
    protected T obj;

    public BaseResponseParams(T obj) {
        this.obj = obj;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}