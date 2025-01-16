-- Insertar roles
INSERT IGNORE INTO roles (name) VALUES
                                    ('ROLE_ADMIN'),
                                    ('ROLE_USER');

-- Insertar usuarios
INSERT IGNORE INTO users (username, password) VALUES
                                                  ('admin', '$2a$12$MLVYIQ0zDzaD0knsBNkG1u7w5jTCREHJyZYT3j5ZlgkDvbMYtqgFO'), -- Contraseña encriptada
                                                  ('paloma', '$2a$12$MLVYIQ0zDzaD0knsBNkG1u7w5jTCREHJyZYT3j5ZlgkDvbMYtqgFO');

-- Insertar aeropuertos
INSERT IGNORE INTO airports (iata_code, name, country_iso_code) VALUES
                                                                    ('LAX', 'Los Angeles International Airport', 'US'),
                                                                    ('JFK', 'John F. Kennedy International Airport', 'US');

-- Insertar vuelos
INSERT IGNORE INTO flights (origin, destination, departure_time, total_seats, available_seats, is_available, airline_name) VALUES
    (1, 2, '2025-01-14 10:00:00', 200, 150, true, 'American Airlines');

-- Insertar perfiles
INSERT IGNORE INTO profiles (name, last_name, email, mobile, registration_date, last_login, user_id) VALUES
                                                                                                         ('Admin', 'User', 'admin@airboss.com', '1234567890', '2025-01-01 00:00:00', '2025-01-01 12:00:00', 1),
                                                                                                         ('Paloma', 'User', 'paloma@paloma.com', '9876543210', '2025-01-02 00:00:00', '2025-01-02 12:00:00', 2);

-- Insertar reservas
INSERT IGNORE INTO bookings (user_id, flight_id, number_of_seats, booking_date, status) VALUES
    (2, 1, 2, '2025-01-14 08:00:00', 'CONFIRMED');

-- Insertar aviones
INSERT IGNORE INTO airplanes (model, capacity, airline_name) VALUES
                                                                 ('Boeing 737', 189, 'American Airlines'),
                                                                 ('Airbus A320', 180, 'Delta Airlines');

-- Insertar rutas
INSERT IGNORE INTO routes (origin, destination, duration_minutes) VALUES
    (1, 2, 300); -- Ruta de LAX a JFK con duración de 300 minutos

-- Asignar roles a usuarios
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES
                                                     (1, 1), -- admin con ROLE_ADMIN
                                                     (2, 2); -- paloma con ROLE_USER

-- Crear tabla de Roles
CREATE TABLE IF NOT EXISTS roles (
                                     role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(255) NOT NULL UNIQUE
);

-- Crear tabla de Usuarios
CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(255) NOT NULL UNIQUE,
                                     password VARCHAR(255) NOT NULL
);

-- Crear tabla para relación Usuario-Rol (Muchos a Muchos)
CREATE TABLE IF NOT EXISTS user_roles (
                                          user_id BIGINT NOT NULL,
                                          role_id BIGINT NOT NULL,
                                          PRIMARY KEY (user_id, role_id),
                                          CONSTRAINT fk_user_role FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                                          CONSTRAINT fk_role_user FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE
);

-- Crear tabla de Perfiles
CREATE TABLE IF NOT EXISTS profiles (
                                        profile_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
                                        last_name VARCHAR(255) NOT NULL,
                                        email VARCHAR(255) NOT NULL,
                                        mobile VARCHAR(15),
                                        registration_date DATETIME NOT NULL,
                                        last_login DATETIME,
                                        user_id BIGINT NOT NULL UNIQUE,
                                        CONSTRAINT fk_user_profile FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Crear tabla de Aeropuertos
CREATE TABLE IF NOT EXISTS airports (
                                        airport_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
                                        iata_code VARCHAR(10) NOT NULL UNIQUE,
                                        country_iso_code VARCHAR(3) NOT NULL
);

-- Crear tabla de Vuelos
CREATE TABLE IF NOT EXISTS flights (
                                       flight_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       origin BIGINT NOT NULL,
                                       destination BIGINT NOT NULL,
                                       departure_time DATETIME NOT NULL,
                                       total_seats INT NOT NULL,
                                       available_seats INT NOT NULL,
                                       is_available BOOLEAN NOT NULL,
                                       airline_name VARCHAR(255),
                                       CONSTRAINT fk_flight_origin FOREIGN KEY (origin) REFERENCES airports(airport_id) ON DELETE CASCADE,
                                       CONSTRAINT fk_flight_destination FOREIGN KEY (destination) REFERENCES airports(airport_id) ON DELETE CASCADE
);

-- Crear tabla de Aviones
CREATE TABLE IF NOT EXISTS airplanes (
                                         airplane_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         model VARCHAR(255) NOT NULL,
                                         capacity INT NOT NULL,
                                         airline_name VARCHAR(255) NOT NULL
);

-- Crear tabla de Reservas
CREATE TABLE IF NOT EXISTS bookings (
                                        booking_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        user_id BIGINT NOT NULL,
                                        flight_id BIGINT NOT NULL,
                                        number_of_seats INT NOT NULL,
                                        booking_date DATETIME NOT NULL,
                                        status ENUM('CONFIRMED', 'PENDING', 'CANCELLED') NOT NULL,
                                        CONSTRAINT fk_user_booking FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                                        CONSTRAINT fk_flight_booking FOREIGN KEY (flight_id) REFERENCES flights(flight_id) ON DELETE CASCADE
);

-- Crear tabla de Rutas
CREATE TABLE IF NOT EXISTS routes (
                                      route_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      origin BIGINT NOT NULL,
                                      destination BIGINT NOT NULL,
                                      duration_minutes INT NOT NULL,
                                      CONSTRAINT fk_route_origin FOREIGN KEY (origin) REFERENCES airports(airport_id) ON DELETE CASCADE,
                                      CONSTRAINT fk_route_destination FOREIGN KEY (destination) REFERENCES airports(airport_id) ON DELETE CASCADE
);
