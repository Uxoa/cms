-- Crear tabla de usuarios
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

-- Crear tabla de roles
CREATE TABLE roles (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL UNIQUE
);

-- Crear tabla intermedia para la relación usuarios-roles
CREATE TABLE user_roles (
                            user_id BIGINT NOT NULL,
                            role_id BIGINT NOT NULL,
                            PRIMARY KEY (user_id, role_id),
                            CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
                            CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

-- Insertar roles en la tabla roles
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_USER');

-- Insertar usuarios en la tabla users
-- Nota: La contraseña debe estar encriptada (ejemplo de BCrypt: $2a$10$...)
INSERT INTO users (username, password) VALUES
                                           ('admin', '$2a$12$cCFtYo/OmI1JIj9cdrsiI.HJ06KUcfPsCPUT.MO.mSDqVproxJD2a'), -- Contraseña: admin123
                                           ('testuser', '$2a$12$cCFtYo/OmI1JIj9cdrsiI.HJ06KUcfPsCPUT.MO.mSDqVproxJD2a'); -- Contraseña: test123

-- Asignar roles a los usuarios
-- Encuentra los IDs generados automáticamente para usuarios y roles
-- Reemplaza los IDs reales si los valores son diferentes
INSERT INTO user_roles (user_id, role_id) VALUES
                                              (1, 1), -- Usuario 'admin' con ROLE_ADMIN
                                              (2, 2); -- Usuario 'testuser' con ROLE_USER

-- Verificar los datos insertados en las tablas
SELECT * FROM users;
SELECT * FROM roles;
SELECT * FROM user_roles;

-- Verificar relaciones
SELECT u.username, r.name AS role_name
FROM users u
         JOIN user_roles ur ON u.id = ur.user_id
         JOIN roles r ON ur.role_id = r.id;
