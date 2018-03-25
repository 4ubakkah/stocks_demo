--liquibase formatted sql

--changeset root:data.user.test.record author:nikita.biloshytskyi contexts: init, data
INSERT INTO `USER` (`USER_NAME`,`EMAIL`,`PASSWORD`, `VERSION`) VALUES ('SophisticatedUsername','danny@doesthatdomainevenexit.com','wow_plain_text_password', '1');