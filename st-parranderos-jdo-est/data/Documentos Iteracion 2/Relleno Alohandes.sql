--Relleno tabla OPERADORES

INSERT INTO OPERADORES (id, nombre, tipo)
VALUES (alohandes_sequence.NEXTVAL,'Hotel Tequendama', 'Hotel');

INSERT INTO OPERADORES (id, nombre, tipo)
VALUES (alohandes_sequence.NEXTVAL,'Hotel Inter Bogotá', 'Hotel');

INSERT INTO OPERADORES (id, nombre, tipo)
VALUES (alohandes_sequence.NEXTVAL,'Hotel Marriot', 'Hotel');

INSERT INTO OPERADORES (id, nombre, tipo)
VALUES (alohandes_sequence.NEXTVAL,'Hotel Bolivar', 'Hotel');

INSERT INTO OPERADORES (id, nombre, tipo)
VALUES (alohandes_sequence.NEXTVAL,'Hotel La Candelaria', 'Hotel');

INSERT INTO OPERADORES (id, nombre, tipo)
VALUES (alohandes_sequence.NEXTVAL,'La Pola Habitaciones', 'Hostal');

INSERT INTO OPERADORES (id, nombre, tipo)
VALUES (alohandes_sequence.NEXTVAL,'El descanso', 'Hostal');

INSERT INTO OPERADORES (id, nombre, tipo)
VALUES (alohandes_sequence.NEXTVAL,'La estancia', 'Hostal');

INSERT INTO OPERADORES (id, nombre, tipo)
VALUES (alohandes_sequence.NEXTVAL,'La casa de Magola', 'Hostal');

INSERT INTO OPERADORES (id, nombre, tipo)
VALUES (alohandes_sequence.NEXTVAL,'El Bronx', 'Hostal');

INSERT INTO OPERADORES (id, nombre, tipo)
VALUES (alohandes_sequence.NEXTVAL,'City U', 'Residencia Universitaria');

INSERT INTO OPERADORES (id, nombre, tipo)
VALUES (alohandes_sequence.NEXTVAL,'LivingX', 'Residencia Universitaria');

INSERT INTO OPERADORES (id, nombre, tipo)
VALUES (alohandes_sequence.NEXTVAL,'The Spot', 'Residencia Universitaria');

INSERT INTO OPERADORES (id, nombre, tipo)
VALUES (alohandes_sequence.NEXTVAL,'Mandarinas', 'Residencia Universitaria');

INSERT INTO OPERADORES (id, nombre, tipo)
VALUES (alohandes_sequence.NEXTVAL,'Gonzalo Jimenez de Quesada', 'Persona Natural');

INSERT INTO OPERADORES (id, nombre, tipo)
VALUES (alohandes_sequence.NEXTVAL,'Johannes Battenberg', 'Persona Natural');

INSERT INTO OPERADORES (id, nombre, tipo)
VALUES (alohandes_sequence.NEXTVAL,'Ludwig Van Beethoven', 'Persona Natural');

INSERT INTO OPERADORES (id, nombre, tipo)
VALUES (alohandes_sequence.NEXTVAL,'Max Plank', 'Persona Natural');

-- Relleno tabla ALOJAMIENTOS

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 2, 'Habitacion');

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 2, 'Habitacion');

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 2, 'Habitacion');

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 3, 'Habitacion');

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 3, 'Habitacion');

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 3, 'Habitacion');

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 3, 'Habitacion');

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 3, 'Habitacion');

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 3, 'Habitacion');

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 4, 'Habitacion');

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 4, 'Habitacion');

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 5, 'Habitacion');

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 5, 'Habitacion');

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 5, 'Habitacion');

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 6, 'Habitacion');

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 6, 'Habitacion');

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 7, 'Habitacion');

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 18, 'Habitacion');

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 9, 'Habitacion');

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 8, 'Habitacion');

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 5, 'Habitacion');

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 11, 'Habitacion');

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 12, 'Habitacion');

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 12, 'Habitacion');

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 16, 'Habitacion');

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 18, 'Habitacion');

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 12, 'Apartamento');

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 13, 'Vivienda Personal');

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 14, 'Apartamento');

INSERT INTO ALOJAMIENTO (id, idoperador, tipo)
VALUES (alohandes_sequence.NEXTVAL, 15, 'Apartamento');

-- Relleno tabla OFERTA

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 21, 2, DATE '2023-03-24', 500000, 15);

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 22, 2, DATE '2023-03-24', 500000, 15);

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 23, 2, DATE '2023-03-24', 1000000, 15);

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 28, 3, DATE '2023-03-24', 1500000, 15);

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 29, 3, DATE '2023-03-24', 100000, 5);

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 30, 3, DATE '2023-03-24', 2500000, 30);

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 31, 3, DATE '2023-03-24', 1700000, 15);

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 32, 3, DATE '2023-03-24', 700000, 15);

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 33, 3, DATE '2023-03-24', 800000, 8);

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 34, 4, DATE '2023-03-24', 1500000, 15);

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 35, 4, DATE '2023-03-24', 100000, 5);

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 36, 5, DATE '2023-03-24', 2500000, 30);

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 37, 5, DATE '2023-03-24', 1700000, 15);

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 38, 5, DATE '2023-03-24', 700000, 15);

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 39, 6, DATE '2023-03-24', 800000, 8);

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 40, 6, DATE '2023-03-24', 1500000, 15);

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 41, 7, DATE '2023-03-24', 100000, 5);

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 42, 18, DATE '2023-03-24', 2500000, 30);

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 43, 9, DATE '2023-03-24', 800000, 8);

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 44, 8, DATE '2023-03-24', 1500000, 15);

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 45, 5, DATE '2023-03-24', 100000, 5);

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 46, 11, DATE '2023-03-24', 2500000, 30);

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 47, 12, DATE '2023-03-24', 1500000, 15);

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 48, 12, DATE '2023-03-24', 100000, 5);

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 48, 16, DATE '2023-03-24', 2500000, 30);

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 46, 12, DATE '2023-03-24', 1700000, 15);

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 25, 13, DATE '2023-03-24', 700000, 15);

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 30, 14, DATE '2023-03-24', 800000, 8);

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 47, 15, DATE '2023-03-24', 1500000, 15);

INSERT INTO OFERTA (id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias)
VALUES (alohandes_sequence.NEXTVAL, 26, 18, DATE '2023-03-24', 100000, 5);

--Relleno RESIDENTE

INSERT INTO RESIDENTE (id, nombre, vinculo)
VALUES (alohandes_sequence.NEXTVAL, 'Diego Ortiz', 'Estudiante');

INSERT INTO RESIDENTE (id, nombre, vinculo)
VALUES (alohandes_sequence.NEXTVAL, 'Miguel Gomez', 'Estudiante');

INSERT INTO RESIDENTE (id, nombre, vinculo)
VALUES (alohandes_sequence.NEXTVAL, 'German Bravo', 'Profesor');

INSERT INTO RESIDENTE (id, nombre, vinculo)
VALUES (alohandes_sequence.NEXTVAL, 'Alvaro Gomez', 'Profesor');

INSERT INTO RESIDENTE (id, nombre, vinculo)
VALUES (alohandes_sequence.NEXTVAL, 'Isaac Newton', 'Profesor invitado');

INSERT INTO RESIDENTE (id, nombre, vinculo)
VALUES (alohandes_sequence.NEXTVAL, 'John von Neumann', 'Profesor invitado');

INSERT INTO RESIDENTE (id, nombre, vinculo)
VALUES (alohandes_sequence.NEXTVAL, 'Martin Santos', 'Egresado');

INSERT INTO RESIDENTE (id, nombre, vinculo)
VALUES (alohandes_sequence.NEXTVAL, 'Alejandro Linares', 'Egresado');

INSERT INTO RESIDENTE (id, nombre, vinculo)
VALUES (alohandes_sequence.NEXTVAL, 'Mamfred Von Richtofen', 'Padre');

INSERT INTO RESIDENTE (id, nombre, vinculo)
VALUES (alohandes_sequence.NEXTVAL, 'Jorge Bergoglio', 'Padre');

INSERT INTO RESIDENTE (id, nombre, vinculo)
VALUES (alohandes_sequence.NEXTVAL, 'Harry Kane', 'Empleado');

INSERT INTO RESIDENTE (id, nombre, vinculo)
VALUES (alohandes_sequence.NEXTVAL, 'Bella Haidid', 'Empleado');

INSERT INTO RESIDENTE (id, nombre, vinculo)
VALUES (alohandes_sequence.NEXTVAL, 'Gustavo Petro', 'Asistente a evento');

INSERT INTO RESIDENTE (id, nombre, vinculo)
VALUES (alohandes_sequence.NEXTVAL, 'Johan Sebastian Bach', 'Asistente a evento');

---Relleno RESERVA

INSERT INTO RESERVA (id, idresidente, fecha, duracion_dias)
VALUES (alohandes_sequence.NEXTVAL, 79, DATE '2023-03-28', 60);

INSERT INTO RESERVA (id, idresidente, fecha, duracion_dias)
VALUES (alohandes_sequence.NEXTVAL, 80, DATE '2023-03-30', 180);

INSERT INTO RESERVA (id, idresidente, fecha, duracion_dias)
VALUES (alohandes_sequence.NEXTVAL, 81, DATE '2023-03-28', 15);

INSERT INTO RESERVA (id, idresidente, fecha, duracion_dias)
VALUES (alohandes_sequence.NEXTVAL, 82, DATE '2023-03-28', 180);

INSERT INTO RESERVA (id, idresidente, fecha, duracion_dias)
VALUES (alohandes_sequence.NEXTVAL, 83, DATE '2023-03-31', 45);

INSERT INTO RESERVA (id, idresidente, fecha, duracion_dias)
VALUES (alohandes_sequence.NEXTVAL, 84, DATE '2023-03-25', 45);

INSERT INTO RESERVA (id, idresidente, fecha, duracion_dias)
VALUES (alohandes_sequence.NEXTVAL, 85, DATE '2023-03-26', 8);

INSERT INTO RESERVA (id, idresidente, fecha, duracion_dias)
VALUES (alohandes_sequence.NEXTVAL, 86, DATE '2023-03-26', 45);

INSERT INTO RESERVA (id, idresidente, fecha, duracion_dias)
VALUES (alohandes_sequence.NEXTVAL, 87, DATE '2023-03-31', 90);

INSERT INTO RESERVA (id, idresidente, fecha, duracion_dias)
VALUES (alohandes_sequence.NEXTVAL, 88, DATE '2023-03-25', 180);

INSERT INTO RESERVA (id, idresidente, fecha, duracion_dias)
VALUES (alohandes_sequence.NEXTVAL, 89, DATE '2023-03-30', 180);

INSERT INTO RESERVA (id, idresidente, fecha, duracion_dias)
VALUES (alohandes_sequence.NEXTVAL, 90, DATE '2023-03-29', 180);

INSERT INTO RESERVA (id, idresidente, fecha, duracion_dias)
VALUES (alohandes_sequence.NEXTVAL, 91, DATE '2023-03-25', 50);

INSERT INTO RESERVA (id, idresidente, fecha, duracion_dias)
VALUES (alohandes_sequence.NEXTVAL, 92, DATE '2023-03-31', 90);

-- Relleno tabla SERVICIO

INSERT INTO SERVICIO (id, nombre, costo)
VALUES (alohandes_sequence.NEXTVAL,'bañera', 50000);

INSERT INTO SERVICIO (id, nombre, costo)
VALUES (alohandes_sequence.NEXTVAL,'yacuzzi', 100000);

INSERT INTO SERVICIO (id, nombre, costo)
VALUES (alohandes_sequence.NEXTVAL,'sala', 60000);

INSERT INTO SERVICIO (id, nombre, costo)
VALUES (alohandes_sequence.NEXTVAL,'cocineta', 0);

INSERT INTO SERVICIO (id, nombre, costo)
VALUES (alohandes_sequence.NEXTVAL,'restaurante', 100000);

INSERT INTO SERVICIO (id, nombre, costo)
VALUES (alohandes_sequence.NEXTVAL,'piscina', 50000);

INSERT INTO SERVICIO (id, nombre, costo)
VALUES (alohandes_sequence.NEXTVAL,'parqueadero', 130000);

INSERT INTO SERVICIO (id, nombre, costo)
VALUES (alohandes_sequence.NEXTVAL,'WiFi', 0);

INSERT INTO SERVICIO (id, nombre, costo)
VALUES (alohandes_sequence.NEXTVAL,'TV cable', 20000);

INSERT INTO SERVICIO (id, nombre, costo)
VALUES (alohandes_sequence.NEXTVAL,'recepcion 24 horas',0);

INSERT INTO SERVICIO (id, nombre, costo)
VALUES (alohandes_sequence.NEXTVAL,'comidas', 50000);

INSERT INTO SERVICIO (id, nombre, costo)
VALUES (alohandes_sequence.NEXTVAL,'acceso a cocina', 10000);

INSERT INTO SERVICIO (id, nombre, costo)
VALUES (alohandes_sequence.NEXTVAL,'baño privado', 20000);

INSERT INTO SERVICIO (id, nombre, costo)
VALUES (alohandes_sequence.NEXTVAL,'baño compartido', 0);

INSERT INTO SERVICIO (id, nombre, costo)
VALUES (alohandes_sequence.NEXTVAL,'internet', 5000);

INSERT INTO SERVICIO (id, nombre, costo)
VALUES (alohandes_sequence.NEXTVAL,'telefono', 5000);

INSERT INTO SERVICIO (id, nombre, costo)
VALUES (alohandes_sequence.NEXTVAL,'agua', 5000);

INSERT INTO SERVICIO (id, nombre, costo)
VALUES (alohandes_sequence.NEXTVAL,'luz', 5000);

INSERT INTO SERVICIO (id, nombre, costo)
VALUES (alohandes_sequence.NEXTVAL,'servicio aseo', 100000);

INSERT INTO SERVICIO (id, nombre, costo)
VALUES (alohandes_sequence.NEXTVAL,'apoyo social y academico', 0);

INSERT INTO SERVICIO (id, nombre, costo)
VALUES (alohandes_sequence.NEXTVAL,'salas de estudio', 0);

INSERT INTO SERVICIO (id, nombre, costo)
VALUES (alohandes_sequence.NEXTVAL,'salas de esparcimiento', 0);

INSERT INTO SERVICIO (id, nombre, costo)
VALUES (alohandes_sequence.NEXTVAL,'gimnasio', 0);

--- relleno tabla TIENE_SERVICIO

INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio) 
VALUES (19,125);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio) 
VALUES (19,107);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio) 
VALUES (19,119);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio) 
VALUES (19,118);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio) 
VALUES (19,113);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio) 
VALUES (20,109);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (20,121);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (20,117);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (20,125);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (20,120);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (21,108);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (21,111);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (21,122);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (21,129);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (22,112);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (22,110);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (22,120);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (22,118);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (23,113);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (23,117);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (23,109);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (23,125);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (23,107);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (24,119);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (24,129);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (24,117);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (24,123);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (24,116);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (25,120);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (25,109);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (25,123);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (25,117);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (26,114);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (26,112);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (26,125);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (26,113);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (26,119);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (27,115);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (27,123);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (27,120);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (27,114);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (28,108);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (28,111);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (28,110);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (28,122);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (28,115);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (29,121);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (29,115);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (29,129);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (29,119);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (29,113);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (30,129);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (30,108);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (30,107);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (30,122);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (31,113);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (31,125);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (31,127);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (31,126);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (31,109);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (32,122);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (32,123);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (32,125);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (32,128);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (32,107);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (33,111);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (33,116);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (33,127);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (33,118);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (34,110);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (34,114);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (34,120);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (34,121);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (35,111);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (35,109);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (35,123);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (35,107);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (35,118);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (36,110);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (36,114);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (36,125);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (36,120);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (36,127);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (37,117);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (37,111);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (37,110);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (37,116);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (37,122);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (38,113);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (38,125);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (38,122);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (38,121);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (38,117);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (39,116);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (39,109);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (39,115);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (39,112);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (40,122);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (40,121);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (40,119);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (40,113);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (40,123);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (41,119);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (41,121);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (41,109);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (41,113);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (41,124);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (42,110);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (42,111);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (42,107);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (42,125);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (42,123);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (43,107);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (43,112);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (43,129);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (43,117);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (44,107);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (44,125);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (44,120);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (44,111);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (44,115);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (45,111);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (45,124);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (45,119);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (45,128);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (45,116);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (46,122);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (46,108);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (46,110);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (46,123);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (47,121);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (47,126);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (47,122);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (47,109);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (47,114);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (48,127);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (48,117);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (48,121);
INSERT INTO TIENE_SERVICIO (idalojamiento, idservicio)
VALUES (48,122);

-- Relleno tabla RESIDEN

INSERT INTO RESIDEN (idreserva, idoferta) 
VALUES (93,49);
INSERT INTO RESIDEN (idreserva, idoferta) 
VALUES (94,50);
INSERT INTO RESIDEN (idreserva, idoferta) 
VALUES (95,51);
INSERT INTO RESIDEN (idreserva, idoferta)
VALUES (96,52);
INSERT INTO RESIDEN (idreserva, idoferta)
VALUES (97,53);
INSERT INTO RESIDEN (idreserva, idoferta)
VALUES (98,54);
INSERT INTO RESIDEN (idreserva, idoferta)
VALUES (99,54);
INSERT INTO RESIDEN (idreserva, idoferta)
VALUES (100,55);
INSERT INTO RESIDEN (idreserva, idoferta)
VALUES (101,57);
INSERT INTO RESIDEN (idreserva, idoferta)
VALUES (102,51);
INSERT INTO RESIDEN (idreserva, idoferta)
VALUES (103,52);
INSERT INTO RESIDEN (idreserva, idoferta)
VALUES (104,60);
INSERT INTO RESIDEN (idreserva, idoferta)
VALUES (105,61);
INSERT INTO RESIDEN (idreserva, idoferta)
VALUES (106,62);

-- Relleno tablas DETALLE_OPERADORES

INSERT INTO DETALLE_OPERADORES (idoperador, registro_cc, registro_supert, vinculo) 
VALUES (1, 9474835231, 9424648571, 'Empresa');
INSERT INTO DETALLE_OPERADORES (idoperador, registro_cc, registro_supert, vinculo) 
VALUES (2, 3086644617, 7971147517, 'Empresa');
INSERT INTO DETALLE_OPERADORES (idoperador, registro_cc, registro_supert, vinculo) 
VALUES (3, 6621784164, 3868440607, 'Empresa');
INSERT INTO DETALLE_OPERADORES (idoperador, registro_cc, registro_supert, vinculo) 
VALUES (4, 8051553720, 6927207365, 'Empresa');
INSERT INTO DETALLE_OPERADORES (idoperador, registro_cc, registro_supert, vinculo) 
VALUES (5, 7139368250, 3282716916, 'Empresa');
INSERT INTO DETALLE_OPERADORES (idoperador, registro_cc, registro_supert, vinculo) 
VALUES (6, 6141194209, 3241198172, 'Empresa');
INSERT INTO DETALLE_OPERADORES (idoperador, registro_cc, registro_supert, vinculo)
VALUES (7, 4920064216, 5514072002, 'Empresa');
INSERT INTO DETALLE_OPERADORES (idoperador, registro_cc, registro_supert, vinculo)
VALUES (8, 5968000761, 4594201610, 'Empresa');
INSERT INTO DETALLE_OPERADORES (idoperador, registro_cc, registro_supert, vinculo)
VALUES (9, 9976468830, 6512638863, 'Empresa');
INSERT INTO DETALLE_OPERADORES (idoperador, registro_cc, registro_supert, vinculo)
VALUES (10, 5854272751, 7148017260, 'Empresa');
INSERT INTO DETALLE_OPERADORES (idoperador, registro_cc, registro_supert, vinculo)
VALUES (11, 9619033259, 2930433104, 'Empresa');
INSERT INTO DETALLE_OPERADORES (idoperador, registro_cc, registro_supert, vinculo)
VALUES (12, 3109285656, 9715752542, 'Empresa');
INSERT INTO DETALLE_OPERADORES (idoperador, registro_cc, registro_supert, vinculo)
VALUES (13, 3505669926, 5617584149, 'Empresa');
INSERT INTO DETALLE_OPERADORES (idoperador, registro_cc, registro_supert, vinculo)
VALUES (14, 5815641662, 4041318874, 'Empresa');
INSERT INTO DETALLE_OPERADORES (idoperador, registro_cc, registro_supert, vinculo)
VALUES (15, NULL, NULL, 'Empleado');
INSERT INTO DETALLE_OPERADORES (idoperador, registro_cc, registro_supert, vinculo)
VALUES (16, NULL, NULL, 'Padre');
INSERT INTO DETALLE_OPERADORES (idoperador, registro_cc, registro_supert, vinculo)
VALUES (17, NULL, NULL, 'Empleado');
INSERT INTO DETALLE_OPERADORES (idoperador, registro_cc, registro_supert, vinculo)
VALUES (18, NULL, NULL, 'Estudiante');

-- Relleno tabla DETALLES_ALOJ

INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado) 
VALUES (19,'Compartida',6,1,NULL, NULL,1);
INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado) 
VALUES (20,'Compartida',3,1,NULL, NULL,1);
INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado) 
VALUES (21,'Suite',4,1,NULL, NULL,1);
INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado) 
VALUES (22,'Compartida',4,1,NULL, NULL,1);
INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado) 
VALUES (23,'Estandar',10,1,NULL, NULL,1);
INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado) 
VALUES (24,'Estandar',6,1,NULL, NULL,1);
INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado)
VALUES (25,'Estandar',1,1,NULL, NULL,1);
INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado)
VALUES (26,'Compartida',7,1,NULL, NULL,1);
INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado)
VALUES (27,'Estandar',10,1,NULL, NULL,1);
INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado)
VALUES (28,'Semisuite',1,1,NULL, NULL,1);
INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado)
VALUES (29,'Individual',3,1,NULL, NULL,1);
INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado)
VALUES (30,'Individual',3,1,NULL, NULL,1);
INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado)
VALUES (31,'Semisuite',7,1,NULL, NULL,1);
INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado)
VALUES (32,'Semisuite',9,1,NULL, NULL,1);
INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado)
VALUES (33,'Compartida',4,1,NULL, NULL,1);
INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado)
VALUES (34,'Semisuite',10,1,NULL, NULL,1);
INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado)
VALUES (35,'Estandar',9,1,NULL, NULL,1);
INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado)
VALUES (36,'Compartida',2,1,NULL, NULL,1);
INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado)
VALUES (37,'Estandar',1,1,NULL, NULL,1);
INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado)
VALUES (38,'Compartida',5,1,NULL, NULL,1);
INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado)
VALUES (39,'Estandar',1,1,NULL, NULL,1);
INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado)
VALUES (40,'Estandar',6,1,NULL, NULL,1);
INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado)
VALUES (41,'Semisuite',3,1,NULL, NULL,1);
INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado)
VALUES (42,'Compartida',1,1,NULL, NULL,1);
INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado)
VALUES (43,'Individual',7,1,NULL, NULL,1);
INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado)
VALUES (44,'Compartida',9,1,32821, 11753,1);
INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado)
VALUES (45,'Compartida',5,1,109523, 213775,1);
INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado)
VALUES (46,'Compartida',5,1,228250, 75510,1);
INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado)
VALUES (47,'Compartida',7,1,57475, 129495,1);
INSERT INTO DETALLES_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado)
VALUES (48,'Compartida',9,1,188876, 101801,1);

-- Relleno Ganancias
INSERT INTO GANANCIAS (idOperador, ganancias)
VALUES (1, 0);
INSERT INTO GANANCIAS (idOperador, ganancias)
VALUES (2, 0);
INSERT INTO GANANCIAS (idOperador, ganancias)
VALUES (3, 0);
INSERT INTO GANANCIAS (idOperador, ganancias)
VALUES (4, 0);
INSERT INTO GANANCIAS (idOperador, ganancias)
VALUES (5, 0);
INSERT INTO GANANCIAS (idOperador, ganancias)
VALUES (6, 0);
INSERT INTO GANANCIAS (idOperador, ganancias)
VALUES (7, 0);
INSERT INTO GANANCIAS (idOperador, ganancias)
VALUES (8, 0);
INSERT INTO GANANCIAS (idOperador, ganancias)
VALUES (9, 0);
INSERT INTO GANANCIAS (idOperador, ganancias)
VALUES (10, 0);
INSERT INTO GANANCIAS (idOperador, ganancias)
VALUES (11, 0);
INSERT INTO GANANCIAS (idOperador, ganancias)
VALUES (12, 0);
INSERT INTO GANANCIAS (idOperador, ganancias)
VALUES (13, 0);
INSERT INTO GANANCIAS (idOperador, ganancias)
VALUES (14, 0);
INSERT INTO GANANCIAS (idOperador, ganancias)
VALUES (15, 0);
INSERT INTO GANANCIAS (idOperador, ganancias)
VALUES (16, 0);
INSERT INTO GANANCIAS (idOperador, ganancias)
VALUES (17, 0);
INSERT INTO GANANCIAS (idOperador, ganancias)
VALUES (18, 0);

UPDATE ALOJAMIENTO
SET OCUPADO = 1
WHERE idoperador 
IN (SELECT idOperador
    FROM OFERTA o, RESIDEN r
    WHERE o.id = r.idOferta);


UPDATE OFERTA
SET Reservada = 1
WHERE id IN (SELECT idoferta
            FROM Residen);


UPDATE GANANCIAS
SET ganancias = (SELECT SUM(costo)
                FROM RESIDEN r, OFERTA o
                WHERE r.idOferta = o.id 
                AND o.idOperador = GANANCIAS.idOperador)   
WHERE idOperador IN (SELECT idOperador
            FROM OFERTA o, RESIDEN r
            WHERE o.id = r.idOferta);

COMMIT;


