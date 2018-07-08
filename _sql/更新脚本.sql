/** 2018-06-21 会员优惠券添加状态字段 **/
ALTER TABLE `nideshop_user_coupon` ADD COLUMN `coupon_status` TINYINT (3) UNSIGNED DEFAULT '1' COMMENT '状态 1. 可用 2. 已用 3. 过期';
/** 2019-07-07 给足迹表添加索引字段 **/
ALTER TABLE `nideshop_footprint`
ADD INDEX `index_nideshop_footprint_user_id_goods_id` (`user_id`, `goods_id`) USING BTREE ;
