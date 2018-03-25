--liquibase formatted sql

--changeset root:schema.create.table.stock author:nikita.biloshytskyi contexts: init
CREATE TABLE `STOCK` (
  `ID`              BIGINT          NOT NULL AUTO_INCREMENT,
  `VERSION`         INT             NOT NULL DEFAULT 0,
  `NAME`            VARCHAR(50)     NOT NULL,
  `CURRENT_PRICE`   DECIMAL(20,2)   NOT NULL,
  `LAST_UPDATED`    TIMESTAMP       NOT NULL,
   PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset root:schema.create.table.user author:nikita.biloshytskyi contexts: init
CREATE TABLE IF NOT EXISTS `USER` (
  `ID`          BIGINT          NOT NULL AUTO_INCREMENT,
  `VERSION`     INT             NOT NULL DEFAULT 0,
  `USER_NAME`   VARCHAR(50)     NOT NULL,
  `EMAIL`       VARCHAR(100)    NOT NULL,
  `PASSWORD`    VARCHAR(200)    DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `USER_NAME_UNIQUE` (`USER_NAME`),
  UNIQUE KEY `EMAIL_UNIQUE` (`EMAIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

