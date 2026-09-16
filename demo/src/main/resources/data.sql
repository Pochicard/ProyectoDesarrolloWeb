
INSERT INTO usuario (nombre, correo, empresa, telefono, activo) VALUES ('Santiago Martínez', 'santiago@javeriana.edu.co', 'Javeriana', '3001234567', true);
INSERT INTO usuario (nombre, correo, empresa, telefono, activo) VALUES ('Ana Gómez', 'ana.gomez@gmail.com', 'Tech Corp', '3109876543', true);

INSERT INTO espacio (nombre, capacidad, precio_base) VALUES ('Auditorio Principal', 100, 150000.0);
INSERT INTO espacio (nombre, capacidad, precio_base) VALUES ('Sala de Reuniones A', 10, 50000.0);

INSERT INTO reserva (usuario_id, espacio_id, fecha, hora) VALUES (1, 1, '2026-10-01', '10:00');
INSERT INTO reserva (usuario_id, espacio_id, fecha, hora) VALUES (2, 2, '2026-10-05', '14:00');