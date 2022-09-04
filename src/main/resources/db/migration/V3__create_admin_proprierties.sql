create table admins(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role varchar(255) NOT NULL
);

INSERT INTO admins VALUES(
    NULL, "botAdmin", "$2a$12$hdn1MC6fUM0d/6ztlfHhjOYlPjo3I0oz6kmj2q8gszmJ4lGD2NoCm", "ROLE_ADMIN"
);