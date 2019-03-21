
Clear all

ALTER TABLE `upmureport`.`attachment` DROP PRIMARY KEY;

ALTER TABLE `upmureport`.`user` DROP PRIMARY KEY;

ALTER TABLE `upmureport`.`logattachment` DROP PRIMARY KEY;

ALTER TABLE `upmureport`.`employeesystem` DROP PRIMARY KEY;

ALTER TABLE `upmureport`.`upmulog` DROP PRIMARY KEY;

ALTER TABLE `upmureport`.`userprojectdir` DROP PRIMARY KEY;

ALTER TABLE `upmureport`.`dir` DROP PRIMARY KEY;

ALTER TABLE `upmureport`.`upmucontents` DROP PRIMARY KEY;

ALTER TABLE `upmureport`.`userproject` DROP PRIMARY KEY;

ALTER TABLE `upmureport`.`project` DROP PRIMARY KEY;

DROP TABLE `upmureport`.`user`;

DROP TABLE `upmureport`.`userprojectdir`;

DROP TABLE `upmureport`.`upmucontents`;

DROP TABLE `upmureport`.`hibernate_sequence`;

DROP TABLE `upmureport`.`dir`;

DROP TABLE `upmureport`.`attachment`;

DROP TABLE `upmureport`.`userproject`;

DROP TABLE `upmureport`.`upmulog`;

DROP TABLE `upmureport`.`employeesystem`;

DROP TABLE `upmureport`.`project`;

DROP TABLE `upmureport`.`logattachment`;