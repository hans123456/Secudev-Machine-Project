create table if not exists store_items (
	id int unsigned not null auto_increment,
	name varchar(100) not null,
	description varchar(1000) not null,
	image varchar(100) not null,
	price int unsigned not null,
	donation boolean not null default false,
	deleted boolean not null default false,
	primary key (id)
);

INSERT INTO `store_items` (`id`, `name`, `description`, `image`, `price`, `donation`, `deleted`) VALUES (NULL, 'Donation Pack $5', '', '', '5', true, false), (NULL, 'Donation Pack $10', '', '', '10', true, false), (NULL, 'Donation Pack $20', '', '', '20', true, false);

create table if not exists store_cart_status (
	id int unsigned not null auto_increment,
	status varchar(100) not null,
	primary key (id)
);

INSERT INTO `store_cart_status` (`id`, `status`) VALUES (NULL, 'Paid'), (NULL, 'Not Paid'), (NULL, 'Cancelled');

create table if not exists store_cart (
	id int unsigned not null auto_increment,
	user_id int unsigned not null,
	uuid varchar(32) default null,
	payment_details varchar(5000),
	datetime_updated datetime,
	store_cart_status_id int unsigned default 2,
	primary key (id),
	foreign key (user_id) references users (id),
	foreign key (store_cart_status_id) references store_cart_status (id)
);

create table if not exists store_cart_items (
	store_cart_id int unsigned not null,
	store_item_id int unsigned not null,
	quantity int unsigned not null,
	primary key (store_cart_id, store_item_id),
	foreign key (store_cart_id) references store_cart (id),
	foreign key (store_item_id) references store_items (id)
);

/* total donation */
SELECT SUM(`store_items`.`price`*`store_cart_consolidated`.`quantity`) as `total_donation` FROM `store_items`, (SELECT `store_item_id`, SUM(`quantity`) AS `quantity` FROM `store_cart_items` WHERE `store_cart_id` IN (SELECT `id` FROM `store_cart` WHERE `user_id` = (SELECT `id` FROM `users` WHERE `username` = ? LIMIT 1) AND `store_cart_status_id` = 1) AND `store_item_id` IN (SELECT `id` FROM `store_items` WHERE `donation` = true) GROUP BY `store_item_id`) as `store_cart_consolidated` WHERE `store_items`.`id` = `store_cart_consolidated`.`store_item_id`

/* get cart id */
SELECT `store_cart`.`id` FROM `store_cart` WHERE `store_cart`.`user_id` = (SELECT `id` FROM `users` WHERE `username` = ? LIMIT 1) AND `store_cart`.`store_cart_status_id` = 2 LIMIT 1

/* insert else edit */
INSERT INTO `store_cart_items` (`store_cart_id`,`store_item_id`,`quantity`) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE quantity = ?

/* delete */
DELETE FROM `store_cart_items` WHERE `store_cart_id` = ? AND `store_item_id` = ?

/* list of donation for store page */
SELECT `id`, `name`, `description`, `image`, `price`, `donation` FROM `store_items` WHERE `deleted` = false ORDER BY `donation` DESC, `price` ASC

/* For List of Purchases */
SELECT `store_cart_with_user`.`user_id`, `store_cart_with_user`.`username`, `store_cart_with_user`.`firstname`, `store_cart_with_user`.`lastname`, `store_cart_with_user`.`store_cart_id`, `store_cart_with_user`.`status`, IFNULL(`store_cart_items_multiplied`.`price`, 0) AS `price`, `store_cart_with_user`.`datetime_updated` FROM (SELECT `users`.`id` as `user_id`, `users`.`username`, `users`.`firstname`, `users`.`lastname`, `store_cart`.`id` as `store_cart_id`, `store_cart_status`.`status`, `store_cart`.`datetime_updated` FROM `users`, `store_cart`, `store_cart_status` WHERE `users`.`id` = `store_cart`.`user_id` AND `store_cart`.`store_cart_status_id` = `store_cart_status`.`id` ) AS `store_cart_with_user` LEFT JOIN (SELECT `store_cart_items`.`store_cart_id`, SUM(`store_cart_items`.`quantity`*`store_items`.`price`) as `price` FROM `store_cart_items`, `store_items` WHERE `store_cart_items`.`store_item_id` = `store_items`.`id` GROUP BY `store_cart_items`.`store_cart_id`) AS `store_cart_items_multiplied` ON `store_cart_with_user`.`store_cart_id`=`store_cart_items_multiplied`.`store_cart_id` ORDER BY `datetime_updated` ASC

/* get list items in user cart */
SELECT `store_items`.`id`, `name`, `store_cart_items`.`quantity`, `store_items`.`price` as `price` FROM `store_cart_items`, `store_items` WHERE `store_cart_items`.`store_item_id` = `store_items`.`id` AND `store_cart_items`.`store_cart_id` = ?