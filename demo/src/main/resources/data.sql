INSERT INTO barberia (nombre, direccion, telefono, horario, activo)
VALUES ('Barbería de Rocío', 'Calle 45 #12-30, Bogotá', '6012345678', 'Lunes a Sábado 9:00 - 19:00', true);

INSERT INTO barberia (nombre, direccion, telefono, horario, activo)
VALUES ('Estilo Urbano', 'Carrera 7 #80-15, Bogotá', '6019876543', 'Martes a Domingo 10:00 - 20:00', true);

INSERT INTO usuario (nombre, correo, password, telefono, rol, barberia_id, activo)
VALUES ('Santiago Silva', 'admin@barberia.com', 'admin123', '3001112233', 'ADMINISTRADOR', NULL, true);

INSERT INTO usuario (nombre, correo, password, telefono, rol, barberia_id, activo)
VALUES ('Rocío Herrera', 'rocio@barberia.com', 'gerente123', '3009998877', 'GERENTE', 1, true);

INSERT INTO usuario (nombre, correo, password, telefono, rol, barberia_id, activo)
VALUES ('Javier Mora', 'javier@barberia.com', 'gerente123', '3008887766', 'GERENTE', 2, true);

INSERT INTO usuario (nombre, correo, password, telefono, rol, barberia_id, activo)
VALUES ('Carlos Ramírez', 'carlos@barberia.com', 'barbero123', '3002223344', 'BARBERO', 1, true);

INSERT INTO usuario (nombre, correo, password, telefono, rol, barberia_id, activo)
VALUES ('Miguel Torres', 'miguel@barberia.com', 'barbero123', '3003334455', 'BARBERO', 1, true);

INSERT INTO usuario (nombre, correo, password, telefono, rol, barberia_id, activo)
VALUES ('Laura Peña', 'laura@barberia.com', 'barbero123', '3004445566', 'BARBERO', 2, true);

INSERT INTO usuario (nombre, correo, password, telefono, rol, barberia_id, activo)
VALUES ('Ana Gómez', 'ana@cliente.com', 'cliente123', '3019876543', 'CLIENTE', NULL, true);

INSERT INTO usuario (nombre, correo, password, telefono, rol, barberia_id, activo)
VALUES ('Santiago Martínez', 'santiago@cliente.com', 'cliente123', '3001234567', 'CLIENTE', NULL, true);

INSERT INTO espacio (barberia_id, nombre, capacidad, activo)
VALUES (1, 'Silla Barbería Principal', 1, true);

INSERT INTO espacio (barberia_id, nombre, capacidad, activo)
VALUES (1, 'Estación Lavacabezas', 1, true);

INSERT INTO espacio (barberia_id, nombre, capacidad, activo)
VALUES (1, 'Sillón Grooming / VIP', 1, true);

INSERT INTO espacio (barberia_id, nombre, capacidad, activo)
VALUES (2, 'Cabina de Estética / Spa', 1, true);

INSERT INTO servicio (barberia_id, nombre, descripcion, precio, activo)
VALUES (1, 'Corte de Cabello Tradicional', 'Corte con tijera o máquina y acabado con navaja', 30000.0, true);

INSERT INTO servicio (barberia_id, nombre, descripcion, precio, activo)
VALUES (1, 'Arreglo y Perfilado de Barba', 'Toalla caliente, perfilado y aplicación de aceites', 20000.0, true);

INSERT INTO servicio (barberia_id, nombre, descripcion, precio, activo)
VALUES (1, 'Combo Barbería (Corte + Barba)', 'Servicio completo de corte de cabello y diseño de barba', 45000.0, true);

INSERT INTO servicio (barberia_id, nombre, descripcion, precio, activo)
VALUES (2, 'Corte Urbano con Diseño', 'Corte moderno con degradado y diseño a mano alzada', 38000.0, true);

INSERT INTO reserva (usuario_id, barbero_id, servicio_id, espacio_id, fecha, hora)
VALUES (8, 4, 1, 1, '2027-03-15', '10:00:00');

INSERT INTO reserva (usuario_id, barbero_id, servicio_id, espacio_id, fecha, hora)
VALUES (7, 5, 3, 3, '2027-03-15', '14:30:00');

INSERT INTO reserva (usuario_id, barbero_id, servicio_id, espacio_id, fecha, hora)
VALUES (7, 4, 3, 2, '2027-03-16', '11:00:00');

INSERT INTO reserva (usuario_id, barbero_id, servicio_id, espacio_id, fecha, hora)
VALUES (8, 6, 4, 4, '2027-03-16', '15:00:00');

INSERT INTO reserva (usuario_id, barbero_id, servicio_id, espacio_id, fecha, hora)
VALUES (8, 5, 2, 3, '2027-03-17', '09:00:00');

INSERT INTO pago (reserva_id, monto, metodo_pago, estado)
VALUES (1, 30000.0, 'Efectivo', 'COMPLETADO');

INSERT INTO pago (reserva_id, monto, metodo_pago, estado)
VALUES (2, 45000.0, 'Tarjeta de Crédito', 'COMPLETADO');

INSERT INTO pago (reserva_id, monto, metodo_pago, estado)
VALUES (3, 45000.0, 'Transferencia', 'COMPLETADO');

INSERT INTO pago (reserva_id, monto, metodo_pago, estado)
VALUES (4, 38000.0, 'Efectivo', 'COMPLETADO');

INSERT INTO pago (reserva_id, monto, metodo_pago, estado)
VALUES (5, 20000.0, 'Efectivo', 'PENDIENTE');
