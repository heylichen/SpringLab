CREATE TABLE `user_account`
(
    `user_id` INT            NOT NULL,
    `balance` decimal(18, 2) NOT NULL DEFAULT 0,
    PRIMARY KEY (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE car
(
    id     bigint unsigned AUTO_INCREMENT PRIMARY KEY,
    car_no varchar(20) not null default ''
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;