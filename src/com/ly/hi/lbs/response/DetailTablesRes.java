package com.ly.hi.lbs.response;

import java.util.List;

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
public class DetailTablesRes {
	private String size;//	������������
	private String total;//	ȫ������������
	private List<DetailTable> pois;//	Poi����б�
	
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public List<DetailTable> getPois() {
		return pois;
	}
	public void setPois(List<DetailTable> pois) {
		this.pois = pois;
	}

}
