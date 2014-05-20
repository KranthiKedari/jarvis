CREATE TABLE IF NOT EXISTS `user_details`
(
    `id` int(11) not null auto_increment,
    `username` varchar(45) not null,
    `pwd` varchar(64)  not null,
    `addtime` timestamp not null,
    `email` varchar(64),
    primary key (`id`)
);

create table if not exists `user_category`
(
	`id` int(11) not null auto_increment,
	`user_id` int(11) not null,
	`category` varchar(40) not null,
	`sub_category` varchar(40) default '',
	primary key (id)
);

create table if not exists `user_current_stats`
(
	`id` int(11) not null auto_increment,
	`user_id` int(11) not null,
	`key` varchar(40) not null,
	`value` varchar(40) not null,
	`sub_category` varchar(40) default '',
	primary key (id)
);

create table if not exists `user_definitions`
(
	`id` int(11) not null auto_increment,
	`user_id` int(11) not null,
	`key` varchar(40) not null,
	`value` varchar(40) not null,
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
	`add_time` int(11) not null,
	primary key (id)
);