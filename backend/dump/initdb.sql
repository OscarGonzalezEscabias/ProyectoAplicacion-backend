-- Crear la base de datos (si no existe)
CREATE DATABASE IF NOT EXISTS dbUsers;
USE dbUsers;

-- Crear la tabla 'users'
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Tabla de usuarios';

-- Insertar datos de ejemplo
INSERT INTO users (username, email, password) VALUES
    ('user1', 'user1@example.com', '$2a$12$nTeIMaCnIcvBJlug2YLwGOzVd9gB9bSYN7wZZnZL4/KaOWJqeVkcK'), -- password: password1
    ('user2', 'user2@example.com', '$2a$12$jkxtj76TpGMKlhzH/FjX6./f4uZ8EWD.fJunzhWdS1PbYebRSd1P6'), -- password: password2
    ('user3', 'user3@example.com', '$2a$12$h7OELt9pBczNOP/FYnlo4OBJsDyd47CYFVzyTEiFgqc5eER/1fyNq'); -- password: password3