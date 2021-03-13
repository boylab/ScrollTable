package com.boylab.protocol;

/**
 * 1、宽高自定义类 实现 ItemParams 接口，
 * 2、实体类实现 ItemParams 接口，作为数据
 */
public interface ItemRect {

    /**
     * tableview 行高固定
     * @return
     */
    int getHeight();

    /**
     * tableview 列宽根据需求自定义返回
     * 将实体对象的属性进行序列化，从 0 开始
     * @param column
     * @return
     */
    int getWidth(int column);

}
