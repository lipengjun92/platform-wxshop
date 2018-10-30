package com.platform.entity;

/**
 * 复合类型的按钮
 *
 * @author liepngjun
 * @email 939961241@qq.com
 * @date 2018-10-30 13:01:47
 */
public class ComplexButton extends Button {
    private Button[] sub_button;

    public Button[] getSub_button() {
        return sub_button;
    }

    public void setSub_button(Button[] sub_button) {
        this.sub_button = sub_button;
    }
}
