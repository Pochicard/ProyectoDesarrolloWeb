INSERT INTO usuario (nombre, correo, telefono, activo)
VALUES ('Santiago Martínez', 'santiago@example.com', '3001234567', true);

INSERT INTO usuario (nombre, correo, telefono, activo)
VALUES ('Ana Gómez', 'ana@example.com', '3019876543', true);

INSERT INTO espacio (nombre, capacidad, precio_base, activo)
VALUES ('Silla Barbería Principal', 1, 25000.0, true);

INSERT INTO espacio (nombre, capacidad, precio_base, activo)
VALUES ('Estación Lavacabezas', 1, 15000.0, true);

INSERT INTO espacio (nombre, capacidad, precio_base, activo)
VALUES ('Sillón Grooming / VIP', 1, 40000.0, true);

INSERT INTO espacio (nombre, capacidad, precio_base, activo)
VALUES ('Cabina de Estética / Spa', 1, 35000.0, true);

INSERT INTO servicio (nombre, descripcion, precio)
VALUES ('Corte de Cabello Tradicional', 'Corte con tijera o máquina y acabado con navaja', 30000.0);

INSERT INTO servicio (nombre, descripcion, precio)
VALUES ('Arreglo y Perfilado de Barba', 'Toalla caliente, perfilado y aplicación de aceites', 20000.0);

INSERT INTO servicio (nombre, descripcion, precio)
VALUES ('Combo Barbería (Corte + Barba)', 'Servicio completo de corte de cabello y diseño de barba', 45000.0);

INSERT INTO reserva (usuario_id, espacio_id, fecha, hora)
VALUES (1, 1, '2026-10-15', '10:00:00');

INSERT INTO reserva (usuario_id, espacio_id, fecha, hora)
VALUES (2, 3, '2026-10-15', '14:30:00');

INSERT INTO pago (reserva_id, monto, metodo_pago, estado)
VALUES (1, 45000.0, 'Efectivo', 'COMPLETADO');

INSERT INTO pago (reserva_id, monto, metodo_pago, estado)
VALUES (2, 40000.0, 'Tarjeta de Crédito', 'PENDIENTE');