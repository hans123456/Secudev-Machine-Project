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
	salutation varchar(50) not null,
	primary key (id)
);

INSERT INTO `secudevcase1`.`salutations` (`id`, `salutation`) VALUES (NULL, 'Mr'), (NULL, 'Sir'), (NULL, 'Senior'), (NULL, 'Count'), (NULL, 'Miss'), (NULL, 'Ms'), (NULL, 'Mrs'), (NULL, 'Madame'), (NULL, 'Majesty'), (NULL, 'Seniora');

create table if not exists users (
	id int unsigned not null auto_increment,
	role_id int unsigned not null,
	firstname varchar(50) not null,
	lastname varchar(50) not null,
	gender_id int unsigned not null,
	salutation_id int unsigned not null,
	birthdate date not null,
	username varchar(50) unique not null,
	password varchar(255) not null,
	about_me varchar(1000),
	foreign key (role_id) references roles (id),
	foreign key (gender_id) references genders (id),
	foreign key (salutation_id) references salutations (id),
	primary key (id)
);

INSERT INTO `users` (`id`, `role_id`, `firstname`, `lastname`, `gender_id`, `salutation_id`, `birthdate`, `username`, `password`, `about_me`) VALUES (NULL, '1', 'admin', 'admin', '1', '1', '1990-01-01', 'admin', '$shiro1$SHA-256$500000$F25RGo3voV7NjYrWv83w5g==$OBFlfQAHy8cSroRH7a5m966aphc8Y/Zv+jAGD0NlaQM=', 'something');
INSERT INTO `users` (`id`, `role_id`, `firstname`, `lastname`, `gender_id`, `salutation_id`, `birthdate`, `username`, `password`, `about_me`) VALUES (NULL, '2', 'user', 'user', '1', '1', '1990-01-01', 'user', '$shiro1$SHA-256$500000$F25RGo3voV7NjYrWv83w5g==$OBFlfQAHy8cSroRH7a5m966aphc8Y/Zv+jAGD0NlaQM=', 'something');