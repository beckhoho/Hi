package com.ly.hi.lbs.request;

/**
 * <Pre>
 * TODO �������ļ���ʲô
 * </Pre>
 *
 * @author ����
 * @version 1.0
 *          <p/>
 *          Create by 2015/4/3 0003 ���� 8:18
 */
public class CreateTableReq{
    private String name;	//Geotable����������	String(45)	��ѡ
    private String geotype;	//Geotable�������ݵ�����	Int32	��ѡ 1����poi 2����poi 3����poi Ĭ��Ϊ1

    private String is_published;	//�Ƿ񷢲�������	Int32

    public CreateTableReq(String name, String geotype, String is_published) {
        this.name = name;
        this.geotype = geotype;
        this.is_published = is_published;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGeotype() {
        return geotype;
    }

    public void setGeotype(String geotype) {
        this.geotype = geotype;
    }

    public String getIs_published() {
        return is_published;
    }

    public void setIs_published(String is_published) {
        this.is_published = is_published;
    }
}
