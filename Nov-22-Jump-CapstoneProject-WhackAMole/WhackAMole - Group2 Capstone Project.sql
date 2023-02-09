use whackamole;
drop table user;
drop table score;
create table user(
	userID int auto_increment not null,
	userName varchar(10) not null unique,
    password varchar(50) not null unique,
    primary key(userID)
);

create table score(
	scoreID int not null auto_increment,
    userID int,
    difficulty varchar(10),
    scoreValue int not null,
    primary key (scoreID),
    foreign key (userID) references user(userID)
);


    