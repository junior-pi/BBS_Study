CREATE DATABASE `last_bbs`;

CREATE TABLE `last_bbs`.`users`
(
    `index`             INT UNSIGNED     NOT NULL AUTO_INCREMENT,
    `email`             NVARCHAR(50)     NOT NULL,
    `password`          NVARCHAR(128)    NOT NULL,
    `nickname`          NVARCHAR(10)     NOT NULL,
    `name_first`        NVARCHAR(10)     NOT NULL,
    `name_optional`     NVARCHAR(10)     NOT NULL,
    `name_last`         NVARCHAR(10)     NOT NULL,
    `contact_first`     NVARCHAR(3)      NOT NULL,
    `contact_second`    NVARCHAR(4)      NOT NULL,
    `contact_third`     NVARCHAR(4)      NOT NULL,
    `address_post`      NVARCHAR(5)      NOT NULL,
    `address_primary`   NVARCHAR(100)    NOT NULL,
    `address_secondary` NVARCHAR(100)    NOT NULL,
    `level`             TINYINT UNSIGNED NOT NULL DEFAULT 9,
    CONSTRAINT PRIMARY KEY (`index`),
    CONSTRAINT UNIQUE (`email`),
    CONSTRAINT UNIQUE (`nickname`),
    CONSTRAINT UNIQUE (`contact_first`, `contact_second`, `contact_third`)
);

CREATE TABLE `last_bbs`.`user_auto_sign_keys`
(
    `index`        BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `user_email`   NVARCHAR(50)    NOT NULL,
    `key`          NVARCHAR(128)   NOT NULL,
    `created_at`   DATETIME        NOT NULL DEFAULT NOW(),
    `expires_at`   DATETIME        NOT NULL,
    `expired_flag` BOOLEAN         NOT NULL DEFAULT FALSE,
    CONSTRAINT PRIMARY KEY (`index`),
    CONSTRAINT FOREIGN KEY (`user_email`) REFERENCES `last_bbs`.`users` (`email`) ON DELETE CASCADE,
    CONSTRAINT UNIQUE (`key`)
);

CREATE TABLE `last_bbs`.`forgot_email_codes`
(
    `index`        BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `key`          NVARCHAR(128)   NOT NULL,
    `ip`           NVARCHAR(50)    NOT NULL,
    `email`        NVARCHAR(50)    NOT NULL,
    `code`         NVARCHAR(6)     NOT NULL,
    `created_at`   DATETIME        NOT NULL DEFAULT NOW(),
    `expires_at`   DATETIME        NOT NULL,
    `expired_flag` BOOLEAN         NOT NULL DEFAULT FALSE,
    CONSTRAINT PRIMARY KEY (`index`),
    CONSTRAINT UNIQUE (`key`),
    CONSTRAINT FOREIGN KEY (`email`) REFERENCES `last_bbs`.`users` (`email`) ON DELETE CASCADE
);

CREATE TABLE `last_bbs`.`forgot_password_codes`
(
    `index`        BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `key`          NVARCHAR(128)   NOT NULL,
    `ip`           NVARCHAR(50)    NOT NULL,
    `email`        NVARCHAR(50)    NOT NULL,
    `code`         NVARCHAR(6)     NOT NULL,
    `created_at`   DATETIME        NOT NULL DEFAULT NOW(),
    `expires_at`   DATETIME        NOT NULL,
    `expired_flag` BOOLEAN         NOT NULL DEFAULT FALSE,
    CONSTRAINT PRIMARY KEY (`index`),
    CONSTRAINT UNIQUE (`key`),
    CONSTRAINT FOREIGN KEY (`email`) REFERENCES `last_bbs`.`users` (`email`) ON DELETE CASCADE
);

CREATE TABLE `last_bbs`.`boards`
(
    `index`         INT UNSIGNED     NOT NULL AUTO_INCREMENT,
    `id`            NVARCHAR(10)     NOT NULL,
    `name`          NVARCHAR(50)     NOT NULL,
    `level_list`    TINYINT UNSIGNED NOT NULL DEFAULT 10 COMMENT '목록 조회 권한',
    `level_read`    TINYINT UNSIGNED NOT NULL DEFAULT 10 COMMENT '글 읽기 권한',
    `level_write`   TINYINT UNSIGNED NOT NULL DEFAULT 9 COMMENT '글 작성 권한',
    `level_comment` TINYINT UNSIGNED NOT NULL DEFAULT 9 COMMENT '댓글 작성 권한',
    CONSTRAINT PRIMARY KEY (`index`),
    CONSTRAINT UNIQUE (`id`)
);

CREATE TABLE `last_bbs`.`articles`
(
    `index`      INT UNSIGNED    NOT NULL AUTO_INCREMENT,
    `board_id`   NVARCHAR(10)    NOT NULL,
    `user_email` NVARCHAR(50)    NOT NULL,
    `title`      NVARCHAR(100)   NOT NULL,
    `content`    NVARCHAR(10000) NOT NULL,
    `timestamp`  DATETIME        NOT NULL DEFAULT NOW(),
    `view`       INT UNSIGNED    NOT NULL DEFAULT 0,
    CONSTRAINT PRIMARY KEY (`index`),
    CONSTRAINT FOREIGN KEY (`board_id`) REFERENCES `last_bbs`.`boards` (`id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (`user_email`) REFERENCES `last_bbs`.`users` (`email`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE `last_bbs`.`comments`
(
    `index`         INT UNSIGNED  NOT NULL AUTO_INCREMENT,
    `article_index` INT UNSIGNED  NOT NULL,
    `user_email`    NVARCHAR(50)  NOT NULL,
    `timestamp`     DATETIME      NOT NULL DEFAULT NOW(),
    `content`       NVARCHAR(100) NOT NULL,
    CONSTRAINT PRIMARY KEY (`index`),
    CONSTRAINT FOREIGN KEY (`article_index`) REFERENCES `last_bbs`.`articles` (`index`)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (`user_email`) REFERENCES `last_bbs`.`users` (`email`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);