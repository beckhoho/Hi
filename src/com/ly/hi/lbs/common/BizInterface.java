package com.ly.hi.lbs.common;

/**
 * <Pre>
 * TODO �������ļ���ʲô
 * </Pre>
 *
 * @author ����
 * @version 1.0
 *          <p/>
 *          Create by 2015/4/1 0001 ���� 2:52
 */
public interface BizInterface {
	String BAIDU_LBS_GEOTABLE_ID = "98950";
	String BAIDU_LBS_GEOTABLE_TYPE = "1";
    String BAIDU_LBS_AK = "5SudMsMqw27P38x7G8WGTyyc";

    
    String CREATE_GEOTABLE = "http://api.map.baidu.com/geodata/v3/geotable/create";

    /**
     * ���������
     */
    String CREATE_POI = "http://api.map.baidu.com/geodata/v3/poi/create";
    
    /**
     * ���������
     */
    String UPDATE_POI = "http://api.map.baidu.com/geodata/v3/poi/update";
    /**
     * ɾ�������
     */
    String DELETE_POI = "http://api.map.baidu.com/geodata/v3/poi/delete";
    /**
     * ��ѯ����ϸ����
     */
    String DETAIL_GEOTABLE = new StringBuffer("http://api.map.baidu.com/geodata/v3/poi/list?geotable_id="
    ).append(BAIDU_LBS_GEOTABLE_ID).append("&ak=").append(BAIDU_LBS_AK).append("&title=").toString();
    
}
