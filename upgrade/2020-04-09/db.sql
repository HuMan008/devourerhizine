


--备份
create table de_cnpc_order_20200409.bak as select * from de_cnpc_order;

--  优惠修改sql
-- 1加字段
ALTER TABLE `de_cnpc_order`
ADD COLUMN `promo_list` varchar(4000) NULL COMMENT '优惠详细' AFTER `promoid`;

-- 2更新旧数据
update de_cnpc_order set promo_list = concat('{\"promo\":',promo,',\"promoid\":',promoid,"}")

-- 3去掉多余字段
ALTER TABLE `de_cnpc_order`
DROP COLUMN `promo`,
DROP COLUMN `promoid`;
