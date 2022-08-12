create table bot_config (
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    version VARCHAR(255) NOT NULL,
    all_messages_send INTEGER NOT NULL
);

create table trainings(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    question VARCHAR(255) NOT NULL,
    answer VARCHAR(255) NOT NULL
);