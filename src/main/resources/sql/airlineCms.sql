CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       name VARCHAR(255),
                       last_name VARCHAR(255),
                       mobile BIGINT,
                       profile_image VARCHAR(255),
                       registration_date DATETIME,
                       last_login DATETIME
);
CREATE TABLE roles (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL UNIQUE
);
CREATE TABLE user_roles (
                            user_id BIGINT NOT NULL,
                            role_id BIGINT NOT NULL,
                            PRIMARY KEY (user_id, role_id),
                            FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                            FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);
