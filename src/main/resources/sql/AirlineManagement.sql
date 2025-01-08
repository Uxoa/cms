CREATE TABLE Users (
                       ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                       Nombre VARCHAR(100) NOT NULL,
                       Apellido VARCHAR(100) NOT NULL,
                       Email VARCHAR(150) UNIQUE NOT NULL,
                       Contraseña VARCHAR(255) NOT NULL,
                       Rol ENUM('ROLE_ADMIN', 'ROLE_USER') NOT NULL,
                       ImagenPerfil VARCHAR(255),
                       FechaRegistro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       UltimoLogin DATETIME
);



