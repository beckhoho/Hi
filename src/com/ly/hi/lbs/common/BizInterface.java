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
    
}
