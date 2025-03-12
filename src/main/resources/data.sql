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
                                                                  ('Ana', 22, 'P004', '555-777-8888'),
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
                                                                  ('Renee', 27, 'P017', '555-464-7878');


-- Insertar Citas
INSERT INTO cita (fecha, hora, motivo, paciente_id, medico_id) VALUES
                                                                   ('2024-12-05', '09:00:00', 'Consulta general', 1, 1),
                                                                   ('2024-12-05', '10:30:00', 'Revisión', 2, 2),
                                                                   ('2024-12-05', '14:00:00', 'Examen de la vista', 3, 5),
                                                                   ('2024-12-05', '16:00:00', 'Consulta dermatológica', 6, 3),
                                                                   ('2024-12-06', '11:00:00', 'Consulta neurológica', 7, 4),
                                                                   ('2024-12-06', '15:00:00', 'Revisión pediátrica', 10, 2),
                                                                   ('2024-12-07', '09:30:00', 'Seguimiento cardiología', 1, 1),
                                                                   ('2024-12-07', '12:00:00', 'Consulta general', 4, 3),
                                                                   ('2024-03-10', '14:00:00', 'Control', 3, 3),
                                                                   ('2024-01-15', '10:00:00', 'Primera consulta', 2, 2);

-- Primero inserta los historiales
INSERT INTO historial_medico (paciente_id) VALUES (1), (2), (3);

-- Luego inserta las enfermedades
INSERT INTO enfermedad (nombre, descripcion) VALUES
                                                 ('Gripe', 'Infección viral común'),
                                                 ('Hipertensión', 'Presión arterial alta'),
                                                 ('Dermatitis', 'Inflamación de la piel'),
                                                 ('Diabetes tipo 2', 'Resistencia a la insulina'),
                                                 ('Asma', 'Enfermedad respiratoria crónica');


-- Luego inserta los medicamentos (vinculados a los historiales)
-- María González (Historial 1)
INSERT INTO medicamento (nombre, descripcion, tipo, dosis, frecuencia, duracion, fecha_inicio, fecha_fin, historial_medico_id) VALUES
                                                                                                                                   ('Paracetamol', 'Analgésico y antipirético', 'Oral', '500mg', 'Cada 8 horas', '5 días', '2024-01-10', '2024-01-15', 1),
                                                                                                                                   ('Loratadina', 'Antihistamínico', 'Oral', '10mg', 'Cada 24 horas', '7 días', '2024-01-12', '2024-01-19', 1);

-- José Ramírez (Historial 2)
INSERT INTO medicamento (nombre, descripcion, tipo, dosis, frecuencia, duracion, fecha_inicio, fecha_fin, historial_medico_id) VALUES
                                                                                                                                   ('Enalapril', 'Antihipertensivo', 'Oral', '10mg', 'Cada 12 horas', 'Indefinido', '2024-02-01', null, 2),
                                                                                                                                   ('Metformina', 'Antidiabético', 'Oral', '850mg', 'Cada 12 horas', 'Indefinido', '2024-02-15', null, 2);

-- Sofía Castro (Historial 3)
INSERT INTO medicamento (nombre, descripcion, tipo, dosis, frecuencia, duracion, fecha_inicio, fecha_fin, historial_medico_id) VALUES
                                                                                                                                   ('Salbutamol', 'Broncodilatador', 'Inhalador', '100mcg', 'Según necesidad', 'Indefinido', '2024-03-05', null, 3),
                                                                                                                                   ('Fluticasona', 'Corticoide inhalado', 'Inhalador', '250mcg', 'Cada 12 horas', 'Indefinido', '2024-03-05', null, 3);

-- Insertar las enfermedades en la tabla de unión
INSERT INTO historial_medico_enfermedades (historial_medico_id, enfermedades) VALUES
                                                                                  (1, 'Gripe'),
                                                                                  (2, 'Hipertensión'),
                                                                                  (2, 'Diabetes tipo 2'),
                                                                                  (3, 'Asma');