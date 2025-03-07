CREATE DATABASE IF NOT EXISTS dbGoGuide;
USE dbGoGuide;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    token VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Tabla de usuarios';

CREATE TABLE IF NOT EXISTS reviews (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) UNIQUE NOT NULL,
    description VARCHAR(255) UNIQUE NOT NULL,
    image VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Tabla de reseñas';

INSERT INTO users (username, email, password, token) VALUES
    ('user1', 'user1@example.com', '$2a$12$nTeIMaCnIcvBJlug2YLwGOzVd9gB9bSYN7wZZnZL4/KaOWJqeVkcK', ''), -- password: password1
    ('user2', 'user2@example.com', '$2a$12$jkxtj76TpGMKlhzH/FjX6./f4uZ8EWD.fJunzhWdS1PbYebRSd1P6', ''), -- password: password2
    ('user3', 'user3@example.com', '$2a$12$h7OELt9pBczNOP/FYnlo4OBJsDyd47CYFVzyTEiFgqc5eER/1fyNq', ''); -- password: password3

INSERT INTO reviews (title, description, image) VALUES
    ('Xpecado', 'Una franquicia de hamburgueserias al estilo smash burguer popular en Estados Unidos.', 'imagen1.jpg'),
    ('Museo Íbero', 'Un museo dedicado a la cultura íbera, con exhibiciones fascinantes sobre su historia y arte.', 'imagen2.jpg'),
    ('Baños Árabes', 'Un espacio histórico que combina arquitectura árabe con termas tradicionales.', 'imagen3.jpg'),
    ('Catedral de la Asunción', 'Un majestuoso ejemplo de arquitectura renacentista en el corazón de la ciudad.', 'imagen4.jpg'),
    ('Mirador de la Cruz', 'Un lugar emblemático para disfrutar de vistas panorámicas impresionantes.', 'imagen5.jpg'),
    ('Castillo de Santa Catalina', 'Una fortaleza medieval con vistas espectaculares y una rica historia.', 'imagen6.jpg'),
    ('Basílica de San Ildefonso', 'Un templo impresionante que destaca por su diseño barroco y su importancia religiosa.', 'imagen7.jpg'),
    ('Parque Natural de las Sierras de Cazorla, Segura y las Villas', 'El mayor espacio natural protegido de España, ideal para los amantes de la naturaleza.', 'imagen8.jpg'),
    ('Ajo Mecánico', 'Un restaurante con terraza con gran variedad de carta y muy buen servicio.', 'imagen9.jpg'),
    ('Real Monasterio de Santa Clara', 'Un histórico monasterio que combina arte sacro con una arquitectura singular.', 'imagen10.jpg');