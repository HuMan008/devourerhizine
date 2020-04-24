-- 添加CHAN字段
ALTER TABLE `devourerhizine`.`de_cnpc_order`
ADD COLUMN `chan` varchar(60) NULL COMMENT 'chan' AFTER `failure_reason`;