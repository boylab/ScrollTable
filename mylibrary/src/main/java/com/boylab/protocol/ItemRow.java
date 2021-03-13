package com.boylab.protocol;

/**
 * 1、新建标题类 实现ItemRow接口，作为第一行
 * 2、实体类实现ItemRow接口，作为数据
 * 3、size()返回的是列数，标题类列数 >= 实体类列数
 */
public interface ItemRow {

    /**
     * 实体对象有几个属性显示成列数
     * @return
     */
    int size();

    /**
     * 将实体对象的属性进行序列化，从 0 开始
     * @param column
     * @return
     */
    String get(int column);

}
