-- schema.sql
DROP TABLE IF EXISTS cita;  -- Eliminar tablas en orden inverso a las dependencias
DROP TABLE IF EXISTS medico;
DROP TABLE IF EXISTS paciente;

CREATE TABLE paciente (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(255),
                          edad TINYINT,
                          identificacion VARCHAR(255),  -- Cambiado de ID a identificacion
                          telefono VARCHAR(255)
);

CREATE TABLE medico (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        nombre VARCHAR(255),
                        edad TINYINT,
                        identificacion VARCHAR(255),  -- Cambiado de ID a identificacion
                        telefono VARCHAR(255),
                        especialidad VARCHAR(255),
                        horario VARCHAR(255)
);

CREATE TABLE cita (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      fecha DATE,
                      hora TIME,
                      motivo VARCHAR(255),
                      paciente_id BIGINT,
                      medico_id BIGINT,
                      FOREIGN KEY (paciente_id) REFERENCES paciente(id),
                      FOREIGN KEY (medico_id) REFERENCES medico(id)
);