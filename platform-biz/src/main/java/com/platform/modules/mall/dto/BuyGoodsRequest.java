package com.platform.modules.mall.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 李鹏军
 */
@Data
@Schema(description = "立即购买类")
public class BuyGoodsRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "skuId", example = "1")
    private Integer productId;
    @Schema(description = "goodsId", example = "1")
    private Integer goodsId;
    @Schema(description = "数量", example = "1")
    private Integer number;
    @Schema(description = "selectedText", example = "1")
    private String selectedText;
}
