create table if not exists roles (
	id int unsigned not null auto_increment,
	role varchar(50) not null,
	primary key (id)
);

INSERT INTO `roles` (`id`, `role`) VALUES (NULL, 'admin'), (NULL, 'user');

create table if not exists genders (
	id int unsigned not null auto_increment,
	gender varchar(50) not null,
	primary key (id)
);

INSERT INTO `genders` (`id`, `gender`) VALUES (NULL, 'Male'), (NULL, 'Female');

create table if not exists salutations (
	id int unsigned not null auto_increment,
	gender_id int unsigned not null,
	salutation varchar(50) not null,
	primary key (id),
	foreign key (gender_id) references genders (id)
);

INSERT INTO `secudevcase1`.`salutations` (`id`, `gender_id`, `salutation`) VALUES (NULL, 1, 'Mr'), (NULL, 1, 'Sir'), (NULL, 1, 'Senior'), (NULL, 1, 'Count'), (NULL, 2, 'Miss'), (NULL, 2, 'Ms'), (NULL, 2, 'Mrs'), (NULL, 2, 'Madame'), (NULL, 2, 'Majesty'), (NULL, 2, 'Seniora');

create table if not exists users (
	id int unsigned not null auto_increment,
	role_id int unsigned not null,
	firstname varchar(50) not null,
	lastname varchar(50) not null,
	salutation_id int unsigned not null,
	birthdate date not null,
	username varchar(50) unique not null,
	password varchar(255) not null,
	about_me varchar(1000),
	datetime_joined datetime not null,
	foreign key (role_id) references roles (id),
	foreign key (salutation_id) references salutations (id),
	primary key (id)
);

INSERT INTO `users` (`id`, `role_id`, `firstname`, `lastname`, `salutation_id`, `birthdate`, `username`, `password`, `about_me`, `datetime_joined`) VALUES (NULL, '1', 'admin', 'admin', '1', '1990-01-01', 'admin', '$shiro1$SHA-256$500000$F25RGo3voV7NjYrWv83w5g==$OBFlfQAHy8cSroRH7a5m966aphc8Y/Zv+jAGD0NlaQM=', 'something', now());
INSERT INTO `users` (`id`, `role_id`, `firstname`, `lastname`, `salutation_id`, `birthdate`, `username`, `password`, `about_me`, `datetime_joined`) VALUES (NULL, '2', 'user', 'user', '1', '1990-01-01', 'user', '$shiro1$SHA-256$500000$F25RGo3voV7NjYrWv83w5g==$OBFlfQAHy8cSroRH7a5m966aphc8Y/Zv+jAGD0NlaQM=', 'something', now());

create table if not exists posts (
	id int unsigned not null auto_increment,
	user_id int unsigned not null,
	datetime_created datetime not null,
	datetime_lastedited datetime,
	post varchar(5000) not null,
	deleted boolean default false,
	foreign key (user_id) references users (id),
	primary key (id)
)