---SENTENCIAS SQL RELACION ALOJAMIENTO

--Insertar un alojamiento
INSERT INTO ALOJAMIENTO (id, idoperador, tipo) VALUES (?, ?, ?)
--Borrar un alojamiento por tipo
DELETE FROM ALOJAMIENTO WHERE tipo = ?
--Borrar un alojamiento por id
DELETE FROM ALOJAMIENTO WHERE id = ?
--Obtener un alojamiento por id
SELECT * FROM ALOJAMIENTO WHERE id = ?
--Obtener todos los alojamientos
SELECT * FROM ALOJAMIENTO
--Borrar el detalle de un alojamiento por id
DELETE FROM DETALLE_ALOJ WHERE idalojamiento = ?
--Obtener servicios de un alojamiento
SELECT s.id, s.nombre, s.costo 
FROM SERVICIO s, TIENE_SERVICIO ts 
WHERE ts.idalojamiento = ? 
AND s.id = ts.idservicio
--Obetner alojameintos ofertados en un rango de fechas
SELECT o.idalojamiento 
FROM OFERTA o 
WHERE o.fecha <= ? 
AND o.fecha >= ?
--Obtener la capacidad de un alojamiento
SELECT CAPACIDAD 
FROM DETALLE_ALOJ 
WHERE idalojamiento = ?
--Actualizar un alojamiento como ocupado
UPDATE ALOJAMIENTO 
SET OCUPADO = 0 
WHERE id = ?

---SENTENCIAS SQL RELACION DETALLE_ALOJ

--Insertar un detalle de alojamiento
INSERT INTO DETALLE_ALOJ (idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado) 
VALUES (?, ?, ?, ?, ?, ?, ?)

--Borrar un detalle de alojamiento por id
DELETE FROM DETALLE_ALOJ 
WHERE id = ?

--Obtener un detalle de alojamiento por id
SELECT * FROM DETALLE_ALOJ 
WHERE idAlojamiento = ?

--Obtener todos los detalles de alojamiento
SELECT * FROM DETTALE_ALOJ


---SENTENCIAS SQL RELACION OPERADOR

--Insertar un operador
INSERT INTO OPERADOR (id, nombre, telefono, direccion, email, tipo)
VALUES (?, ?, ?, ?, ?, ?)

--Eliminar un operador por nombre
DELETE FROM OPERADOR 
WHERE nombre = ?

--Eliminar un operador por id
DELETE FROM OPERADOR 
WHERE id = ?

--Obtener un operador por id
SELECT * FROM OPERADOR 
WHERE id = ?

--Obtener operadores por nombre
SELECT * FROM OPERADOR 
WHERE nombre = ?

--Obtener todos los operadores
SELECT * FROM OPERADOR

--Eliminar operador y detalle operador
DELETE FROM DETALLE_OPERADOR 
WHERE idoperador = ?

DELETE FROM OPERADOR 
WHERE id = ?

--Obetener alojamientos del operador
SELECT aloj.id, aloj.tipo, aloj.ocupado
FROM ALOJAMIENTO aloj, OPERADOR op
WHERE aloj.idoperador = ?

--Obtener ofertas del operador
SELECT o.id, o.idalojamiento, o.fecha, o.precio
FROM OFERTA o, ALOJAMIENTO aloj
WHERE aloj.idoperador = ?

--Dar ganancias año corrido
SELECT SUM(o.costo) AS anio_actual
FROM tabla_oferta o
JOIN tabla_residen r ON o.id = r.idoferta
WHERE o.idoperador = ?
AND o.fecha >= DATE '2023-01-01'
AND o.fecha <= DATE '2024-01-01'
GROUP BY o.idoperador

--Dar ganancias año actual
SELECT SUM(o.costo) AS anio_actual 
FROM OFERTA o 
JOIN RESIDEN r ON o.id = r.idoferta 
WHERE o.idoperador = ? AND o.fecha >= ? AND o.fecha <= ?
GROUP BY o.idoperador

--Actualizar ganancias
UPDATE GANANCIAS
SET ganancias = ganancias + ?
WHERE idoperador = ?


---SENTENCIAS SQL RELACION DETALLE_OPERADOR

--Insertar un detalle de operador
INSERT INTO DETALLE_OPERADOR (idoperador, tipo, costo, comision)
VALUES (?, ?, ?, ?)

--Eliminar un detalle de operador por id
DELETE FROM DETALLE_OPERADOR 
WHERE id = ?    

--Obtener un detalle de operador por id
SELECT * FROM DETALLE_OPERADOR 
WHERE id = ?

--Obtener todos los detalles de operador
SELECT * FROM DETALLE_OPERADOR

---SENTENCIAS SQL RELACION OFERTA

--Insertar una oferta
INSERT INTO OFERTA (id, idalojamiento, fecha, precio)
VALUES (?, ?, ?, ?)

--Eliminar una oferta por id
DELETE FROM OFERTA 
WHERE id = ?

--Obtener una oferta por id
SELECT * FROM OFERTA 
WHERE id = ?

--Modificar una oferta por id
UPDATE OFERTA
SET fecha = ?, precio = ?
WHERE id = ?

--Obtener todas las ofertas
SELECT * FROM OFERTA

--Dar ofertas disponibles
SELECT *
FROM OFERTA 
WHERE reserva = 0

--Actualizar oferta
UPDATE OFERTA
SET reserva = ?
WHERE id = ?

--Agregar deshabilitacion
INSERT INTO DESHABILITADAS (idoferta)
VALUES (?)

--Dar ofertas deshabilitadas por operador
SELECT *
FROM OFERTA 
WHERE ID IN (SELECT idoferta FROM DESHABILITADAS)
AND OFERTA.idoperador = ?

--Eliminar deshabilitacion
DELETE FROM DESHABILITADAS
WHERE idoferta = ?

---SENTENCIAS SQL RELACION RESERVA

--Insertar una reserva
INSERT INTO RESERVA (id, idoferta, idcliente, fecha, estado)
VALUES (?, ?, ?, ?, ?)

--Eliminar una reserva por id
DELETE FROM RESERVA 
WHERE id = ?

--Obtener una reserva por id
SELECT * FROM RESERVA 
WHERE id = ?

--Obtener todas las reservas
SELECT * FROM RESERVA

--Obtener reservas de un cliente
SELECT * FROM RESERVA 
WHERE idResidente = ?

--Actualizar reserva
UPDATE RESERVA 
SET estado = ?
WHERE id = ?

--Actualizar reserva colectiva
UPDATE RESERVA
SET colectiva = ?
WHERE id = ?

--Actualizar reserva colectiva
UPDATE RESERVA
SET colectiva = NULL
WHERE colectiva = ?

--Desagregar reserva colectiva
UPDATE RESERVA
SET colectiva = NULL
WHERE id = ?

--Obtener reservas colectivas
SELECT *
FROM RESERVA
WHERE colectiva = ?

--Retirar reserva por id
DELETE FROM RESERVA
WHERE id = ?

--Se puede reservar
SELECT COUNT(r.idreserva)
FROM RESIDEN r, RESERVA res
WHERE r.idreserva = res.id
AND res.idresidente = ?
AND res.fecha = ?

--SENTENCIAS SQL RELACION RESIDEN

--Insertar residen 
INSERT INTO RESIDEN (idreserva, idoferta)
VALUES (?, ?)

UPDATE OFERTA
SET reserva = 1
WHERE id = ?

UPDATE ALOJAMIENTO
SET ocupado = 1
WHERE id = (SELECT idAlojamiento
            FROM OFERTA
            WHERE id = ?)

--Eliminar residen por id
DELETE FROM RESIDEN
WHERE idreserva = ?

UPDATE OFERTA
SET reservada = 0
WHERE id = (SELECT idOferta
            FROM RESIDEN
            WHERE idReserva = ?)

--Eliminar residen por idOferta
DELETE FROM RESIDEN
WHERE idoferta = ?

--Dar residen
SELECT *
FROM RESIDEN

--Actualizar residen
UPDATE RESIDEN
SET idoferta = ?
WHERE idreserva = ?

--Dar ofertas mas reservadas
SELECT idOferta
FROM RESIDEN
GROUP BY idOferta
ORDER BY COUNT(idOferta) DESC

--Dar residen por IdReserva
SELECT *
FROM RESIDEN
WHERE idReserva = ?


---SENTENCIAS SQL RELACION SERVICIO

--Insertar un servicio
INSERT INTO SERVICIO (id, nombre, costo)
VALUES (?, ?, ?)

--Eliminar un servicio por id
DELETE FROM SERVICIO 
WHERE id = ?

--Obtener un servicio por id
SELECT * FROM SERVICIO 
WHERE id = ?

--Obtener todos los servicios
SELECT * FROM SERVICIO

--Obtener servicios por nombre
SELECT * FROM SERVICIO 
WHERE nombre = ?

--Actualizar servicio
UPDATE SERVICIO
SET nombre = ?, costo = ?
WHERE id = ?


--SENTENCIAS SQL RELACION TIENE_SERVICIO

--Insertar un tiene_servicio
INSERT INTO TIENE_SERVICIO (idoferta, idservicio)
VALUES (?, ?)

--Eliminar un tiene_servicio por id
DELETE FROM TIENE_SERVICIO 
WHERE idoferta = ? AND idservicio = ?

--Obtener un tiene_servicio por id
SELECT * FROM TIENE_SERVICIO 
WHERE idoferta = ? AND idservicio = ?

--Obtener todos los tiene_servicio
SELECT * FROM TIENE_SERVICIO

--actualizarTieneServicio
UPDATE TIENE_SERVICIO
SET idservicio = ?
WHERE idoferta = ? AND idservicio = ?




