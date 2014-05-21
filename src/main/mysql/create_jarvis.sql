CREATE TABLE IF NOT EXISTS `user_details`
(
    `id` int(11) not null auto_increment,
    `username` varchar(45) not null,
    `pwd` varchar(64)  not null,
    `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `email` varchar(64),
    primary key (`id`)
);

CREATE TABLE `user_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `category` varchar(40) NOT NULL,
  `sub_category` varchar(40) DEFAULT '',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);


create table if not exists `user_current_stats`
(
	`id` int(11) not null auto_increment,
	`user_id` int(11) not null,
	`key` varchar(40) not null,
	`value` varchar(40) not null,
	`sub_category` varchar(40) default '',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	primary key (id)
);

create table if not exists `user_definitions`
(
	`id` int(11) not null auto_increment,
	`user_id` int(11) not null,
	`key` varchar(40) not null,
	`value` varchar(40) not null,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	primary key (id)
);


create table if not exists `user_data`
(
	`id` int(11) not null auto_increment,
	`user_id` int(11) not null,
	`name` varchar(40) not null,
	`category` varchar(40) not null,
	`sub_category` varchar(40) not null,
	`unit` varchar(40) not null,
	`value` varchar(40) not null,
	`add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	primary key (id)
);