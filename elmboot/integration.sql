DROP TABLE IF EXISTS `integration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `integration` (
  `integrationId` int(11) NOT NULL AUTO_INCREMENT COMMENT '积分编号',
  `userId` varchar(20) NOT NULL COMMENT '用户编号',
  `points` int(11) NOT NULL COMMENT '积分值',
  `usedPoints`int(11) NOT NULL DEFAULT '0' COMMENT '使用积分值',
  `integrationDate` varchar(20) NOT NULL COMMENT '积分日期',
  `integrationState` int(11) NOT NULL DEFAULT '0' COMMENT '积分状态（0：未过期； 1：已过期 2: 已使用完）',
  PRIMARY KEY (`integrationId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
