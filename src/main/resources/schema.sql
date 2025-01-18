-- Tabla de Roles
CREATE TABLE IF NOT EXISTS roles (
                                     role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(255) NOT NULL UNIQUE
);

-- Tabla de Usuarios
CREATE TABLE IF NOT EXISTS users (
                                     user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(255) NOT NULL UNIQUE,
                                     email VARCHAR(255) NOT NULL,
                                     password VARCHAR(255) NOT NULL
);

-- Relación Usuario-Rol (Muchos a Muchos)
CREATE TABLE IF NOT EXISTS user_roles (
                                          user_id BIGINT NOT NULL,
                                          role_id BIGINT NOT NULL,
                                          PRIMARY KEY (user_id, role_id),
                                          CONSTRAINT fk_user_role FOREIGN KEY (user_id)
                                              REFERENCES users(user_id) ON DELETE CASCADE,
                                          CONSTRAINT fk_role_user FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE
);

-- Tabla de Perfiles
CREATE TABLE IF NOT EXISTS profiles (
                                        profile_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
                                        last_name VARCHAR(255) NOT NULL,
                                        mobile VARCHAR(15),
                                        registration_date DATETIME NOT NULL,
                                        last_login DATETIME,
                                        user_id BIGINT NOT NULL UNIQUE,
                                        CONSTRAINT fk_user_profile FOREIGN KEY (user_id)
                                            REFERENCES users(user_id) ON DELETE CASCADE
);

-- Tabla de Aeropuertos
CREATE TABLE IF NOT EXISTS airports (
                                        airport_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
                                        iata_code VARCHAR(10) NOT NULL UNIQUE,
                                        country_iso_code VARCHAR(3) NOT NULL
);

-- Tabla de Vuelos
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

-- Tabla de Aviones
CREATE TABLE IF NOT EXISTS airplanes (
                                         airplane_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         model VARCHAR(255) NOT NULL,
                                         capacity INT NOT NULL,
                                         airline_name VARCHAR(255) NOT NULL
);

-- Tabla de Reservas
CREATE TABLE IF NOT EXISTS bookings (
                                        booking_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        user_id BIGINT NOT NULL,
                                        flight_id BIGINT NOT NULL,
                                        number_of_seats INT NOT NULL,
                                        booking_date DATETIME NOT NULL,
                                        status ENUM('CONFIRMED', 'PENDING', 'CANCELLED') NOT NULL,
                                        CONSTRAINT fk_user_booking FOREIGN KEY (user_id)
                                            REFERENCES users(user_id) ON DELETE CASCADE,
                                        CONSTRAINT fk_flight_booking FOREIGN KEY (flight_id) REFERENCES flights(flight_id) ON DELETE CASCADE
);

-- Tabla de Rutas
CREATE TABLE IF NOT EXISTS routes (
                                      route_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      origin BIGINT NOT NULL,
                                      destination BIGINT NOT NULL,
                                      duration_minutes INT NOT NULL,
                                      CONSTRAINT fk_route_origin FOREIGN KEY (origin) REFERENCES airports(airport_id) ON DELETE CASCADE,
                                      CONSTRAINT fk_route_destination FOREIGN KEY (destination) REFERENCES airports(airport_id) ON DELETE CASCADE
);