CREATE TABLE IF NOT EXISTS user_profile (
  id   INT AUTO_INCREMENT PRIMARY KEY,
  role ENUM('ADMIN', 'USER') DEFAULT 'USER' NOT NULL
);

CREATE TABLE IF NOT EXISTS persistent_logins (
  series    VARCHAR(256) PRIMARY KEY NOT NULL,
  login     VARCHAR(128)             NOT NULL,
  token     TEXT,
  last_used TIMESTAMP
);

CREATE TABLE IF NOT EXISTS users (
  id              INT AUTO_INCREMENT PRIMARY KEY,
  login           VARCHAR(128) NOT NULL,
  email           VARCHAR(128) NOT NULL,
  last_name       VARCHAR(128),
  first_name      VARCHAR(128),
  patronymic      VARCHAR(128),
  password        VARCHAR(256) NOT NULL,
  user_profile_id INT          NOT NULL,
  FOREIGN KEY (user_profile_id) REFERENCES user_profile (id),
  UNIQUE (login),
  UNIQUE (email)
);

#set default values
INSERT INTO `cableroutes`.`user_profile` (`id`, `role`) VALUES ('1', 'ADMIN');
INSERT INTO `cableroutes`.`user_profile` (`id`, `role`) VALUES ('2', 'USER');

#login = admin, password = abc125
INSERT INTO `cableroutes`.`users`
(`id`, `login`, `email`, `last_name`, `first_name`, `patronymic`, `password`, `user_profile_id`)
VALUES
  ('1', 'admin', 'a@a.a', 'testLastName', 'testFirstName', 'testPatronymic',
   '$2a$10$4eqIF5s/ewJwHK1p8lqlFOEm2QIA0S8g6./Lok.pQxqcxaBZYChRm', '1');

