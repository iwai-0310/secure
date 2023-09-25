--########################################################################################################
--####                                                                                                ####
--####        Author: Chaze                                                                           ####
--####        Date: September,25,2023                                                                ####
--####                                                                                                   ####
--########################################################################################################

------General Rules----
--* user underscore_names instead of CamelCase
--*Table names should be plural
--*Spell out id fields(item_id instead of id)
--*Don't use ambiguous column names
--* Name foreign key columns the same as the column they refer to
--* user caps for all sql queries

CREATE SCHEMA IF NOT EXISTS securecapita;

SET NAMES 'utf8mb4';


USE securecapita;

DROP TABLE IF EXISTS Users;

CREATE TABLE Users(
 id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
 first_name      VARCHAR(50) NOT NULL,
 last_name       VARCHAR(50) NOT NULL,
 email           VARCHAR(100) NOT NULL,
 password        VARCHAR(255) DEFAULT NULL,
 address         VARCHAR(255) DEFAULT NULL,
 phone           VARCHAR(50) DEFAULT NULL,
 title           VARCHAR(50) DEFAULT NULL,
 bio             VARCHAR(255) DEFAULT NULL,
 enabled         BOOLEAN DEFAULT FALSE,
 non_locked      BOOLEAN DEFAULT TRUE,
 using_mfa       BOOLEAN DEFAULT FALSE,
 created_at      DATETIME DEFAULT CURRENT_TIMESTAMP,
 image_url       VARCHAR(220) DEFAULT 'https://cdn.icon-icons.com/icons2/546/PNG/512/1455555019_users-9_icon-icons.com_53249.png',
 CONSTRAINT UQ_Users_Email UNIQUE(email)
);

DROP TABLE IF EXISTS Roles;

CREATE TABLE Roles(
 id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
 name            VARCHAR(50) NOT NULL,
 permission      VARCHAR(255) NOT NULL,
 CONSTRAINT UQ_Roles_Name UNIQUE (name)
);

DROP TABLE IF EXISTS UserRoles;

CREATE TABLE UserRoles(
 id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
 user_id         BIGINT UNSIGNED NOT NULL ,
 role_id         BIGINT UNSIGNED NOT NULL ,
 FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON  UPDATE CASCADE,
 FOREIGN KEY (role_id) REFERENCES Roles (id) ON DELETE RESTRICT ON  UPDATE CASCADE,
 CONSTRAINT UQ_UserRoles_User_Id UNIQUE (user_id)
);

DROP TABLE IF EXISTS Events;

CREATE TABLE Events(
 id               BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
 type             VARCHAR(50) NOT NULL CHECK(type IN ('LOGIN_ATTEMPT','LOGIN_ATTEMPT_FAILURE','LOGIN_ATTEMPT_SUCCESS','PROFILE_UPDATE','PROFILE_PICTURE_UPDATE','PASSWORD_UPDATE','ROLE_UPDATE','ACCOUNT_SETTINGS_UPDATE ','MFA-UPDATE')),
 description      VARCHAR(255) NOT NULL,
 CONSTRAINT UQ_Events_Type UNIQUE (type)
);

DROP TABLE IF EXISTS UserEvents;

CREATE TABLE UserEvents(
 id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
 user_id         BIGINT UNSIGNED NOT NULL ,
 event_id        BIGINT UNSIGNED NOT NULL ,
 device          VARCHAR(100) DEFAULT NULL,
 ip_address      VARCHAR(100) DEFAULT NULL,
 created_at      DATETIME DEFAULT CURRENT_TIMESTAMP,
 FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON  UPDATE CASCADE,
 FOREIGN KEY (event_id) REFERENCES Events (id) ON DELETE RESTRICT ON  UPDATE CASCADE
);

 DROP TABLE IF EXISTS AccountVerifications;

 CREATE TABLE AccountVerifications(
  id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id         BIGINT UNSIGNED NOT NULL ,
  url             VARCHAR(255) NOT NULL,
  FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON  UPDATE CASCADE,
  CONSTRAINT UQ_AccountVerifications_User_id UNIQUE (user_id),
  CONSTRAINT UQ_AccountVerifications_Url UNIQUE (url)
 );

 DROP TABLE IF EXISTS ResetPasswordVerification;

 CREATE TABLE ResetPasswordVerification(
  id                 BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id            BIGINT UNSIGNED NOT NULL ,
  url                VARCHAR(255) NOT NULL,
  expiration_date    DATETIME NOT NULL,
  FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON  UPDATE CASCADE,
  CONSTRAINT UQ_ResetPasswordVerification_User_id UNIQUE (user_id),
  CONSTRAINT UQ_ResetPasswordVerification_Url UNIQUE (url)
 );

 DROP TABLE IF EXISTS TwoFactorVerification;

  CREATE TABLE TwoFactorVerification(
   id                 BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
   user_id            BIGINT UNSIGNED NOT NULL ,
   code               VARCHAR(10) NOT NULL,
   expiration_date    DATETIME NOT NULL,
   FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON  UPDATE CASCADE,
   CONSTRAINT UQ_TwoFactorVerification_User_id UNIQUE (user_id),
   CONSTRAINT UQ_TwoFactorVerification_Code UNIQUE (code)
  );























