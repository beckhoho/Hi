package com.ly.hi.lbs.response;

/**
 * <Pre>
 * TODO �������ļ���ʲô
 * </Pre>
 *
 * @author ����
 * @version 1.0
 *          <p/>
 *          Create by 2015/4/1 0001 ���� 5:50
 */
public class CreatePoiRes {

    private String id;    //���������ݵ�id	String	����


    public CreatePoiRes(int status, String message, String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
