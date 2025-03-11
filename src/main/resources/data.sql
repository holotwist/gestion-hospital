-- Insertar Médicos
INSERT INTO medico (nombre, edad, identificacion, telefono, especialidad, horario) VALUES
                                                                           ('Dr. Juan Pérez', 45, 'M001', '555-123-4567', 'Cardiología', 'Lunes a Viernes 9:00-17:00'),
                                                                           ('Dra. Ana García', 38, 'M002', '555-987-6543', 'Pediatría', 'Martes a Sábado 10:00-18:00'),
                                                                           ('Dr. Carlos Rodríguez', 52, 'M003', '555-246-8013', 'Dermatología', 'Lunes, Miércoles y Viernes 8:00-16:00'),
                                                                           ('Dra. Laura Martínez', 41, 'M004', '555-135-7924', 'Neurología', 'Martes y Jueves 11:00-19:00'),
                                                                           ('Dr. Miguel López', 33, 'M005', '555-864-2097', 'Oftalmología', 'Lunes a Viernes 14:00-20:00');

-- Insertar Pacientes
INSERT INTO paciente (nombre, edad, identificacion, telefono) VALUES
                                                      ('María González', 28, 'P001', '555-111-2222'),
                                                      ('José Ramírez', 62, 'P002', '555-333-4444'),
                                                      ('Sofía Castro', 35, 'P003', '555-555-6666'),
                                                      ('Ana', 22, 'P004', '555-777-8888'),  -- Nombre palíndromo
                                                      ('Luis', 48, 'P005', '555-999-0000'),
                                                      ('Elena', 19, 'P006', '555-222-1111'),
                                                      ('Ricardo', 75, 'P007', '555-444-3333'),
                                                      ('Isabel', 31, 'P008', '555-666-5555'),
                                                      ('Javier', 54, 'P009', '555-888-7777'),
                                                      ('Alejandra', 29, 'P010', '555-000-9999'),
                                                      ('Roberto', 68, 'P011', '555-121-3434'),
                                                      ('Carmen', 43, 'P012', '555-565-7878'),
                                                      ('Fernando', 16, 'P013', '555-909-1212'),
                                                      ('Paula', 81, 'P014', '555-343-5656'),
                                                      ('David', 39, 'P015', '555-787-9090'),
                                                      ('Sara', 57, 'P016',  '555-454-2323'),
                                                      ('Renee', 27, 'P017', '555-464-7878'); -- Nombre palíndromo y con dos vocales iguales


-- Insertar Citas
-- Citas para el 2024-12-05
INSERT INTO cita (fecha, hora, motivo, paciente_id, medico_id) VALUES
                                                                   ('2024-12-05', '09:00:00', 'Consulta general', 1, 1),
                                                                   ('2024-12-05', '10:30:00', 'Revisión', 2, 2),
                                                                   ('2024-12-05', '14:00:00', 'Examen de la vista', 3, 5),
                                                                   ('2024-12-05', '16:00:00', 'Consulta dermatológica', 6, 3),

-- Citas para el 2024-12-06
                                                                   ('2024-12-06', '11:00:00', 'Consulta neurológica', 7, 4),
                                                                   ('2024-12-06', '15:00:00', 'Revisión pediátrica', 10, 2),

-- Citas para el 2024-12-07
                                                                   ('2024-12-07', '09:30:00', 'Seguimiento cardiología', 1, 1),
                                                                   ('2024-12-07', '12:00:00', 'Consulta general', 4, 3);

--Citas para fechas pasadas (para probar el historial)
INSERT INTO cita (fecha, hora, motivo, paciente_id, medico_id) VALUES
                                                                   ('2024-03-10', '14:00:00', 'Control', 3, 3),
                                                                   ('2024-01-15', '10:00:00', 'Primera consulta', 2, 2);