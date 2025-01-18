-- Insertar roles
INSERT IGNORE INTO roles (name) VALUES
                                    ('ROLE_ADMIN'),
                                    ('ROLE_USER');

-- Insertar usuarios
INSERT IGNORE INTO users (username, email, password) VALUES
                                                         ('admin', 'admin@example.com', '$2a$12$MLVYIQ0zDzaD0knsBNkG1u7w5jTCREHJyZYT3j5ZlgkDvbMYtqgFO'), -- Contraseña encriptada
                                                         ('paloma', 'paloma@example.com', '$2a$12$MLVYIQ0zDzaD0knsBNkG1u7w5jTCREHJyZYT3j5ZlgkDvbMYtqgFO');

-- Insertar aeropuertos
INSERT IGNORE INTO airports (iata_code, name, country_iso_code) VALUES
                                                                    ('LAX', 'Los Angeles International Airport', 'US'),
                                                                    ('JFK', 'John F. Kennedy International Airport', 'US');

-- Insertar vuelos
INSERT IGNORE INTO flights (origin, destination, departure_time, total_seats, available_seats, is_available, airline_name) VALUES
    (1, 2, '2025-01-14 10:00:00', 200, 150, true, 'American Airlines');

-- Insertar perfiles
INSERT IGNORE INTO profiles (name, last_name, mobile, registration_date, last_login, user_id) VALUES
                                                                                                  ('Admin', 'User', '1234567890', '2025-01-01 00:00:00', '2025-01-01 12:00:00', 1),
                                                                                                  ('Paloma', 'User', '9876543210', '2025-01-02 00:00:00', '2025-01-02 12:00:00', 2);

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