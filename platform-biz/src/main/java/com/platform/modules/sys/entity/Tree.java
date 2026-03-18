package com.platform.modules.sys.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 名称：Tree <br>
 * 描述：<br>
 *
 * @author 李鹏军
 * @version 1.0
 * @since 1.0.0
 */
@Data
public class Tree<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**********iview tree属性**************/
    /**
     * 标题
     */
    private String title;

    /**
     * 是否展开直子节点
     */
    private boolean expand = false;

    /**
     * 禁掉响应
     */
    private boolean disabled = false;
    /**
     * 禁掉 checkbox
     */
    private boolean disableCheckbox = false;
    /**
     * 是否选中子节点
     */
    private boolean selected = false;
    /**
     * 是否勾选(如果勾选，子节点也会全部勾选)
     */
    private boolean checked = false;

    private boolean leaf = false;
    /**
     * ztree属性
     */
    private Boolean open;

    private List<?> list;

    /**
     * 子节点属性数组
     */
    private List<?> children;
    private String value;
    private String label;
}
