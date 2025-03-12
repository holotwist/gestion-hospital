DROP TABLE IF EXISTS historial_medico_enfermedades; -- Primero tablas de unión
DROP TABLE IF EXISTS medicamento;
DROP TABLE IF EXISTS enfermedad;
DROP TABLE IF EXISTS historial_medico;
DROP TABLE IF EXISTS cita;  -- Eliminar tablas en orden inverso a las dependencias
DROP TABLE IF EXISTS medico;
DROP TABLE IF EXISTS paciente;

CREATE TABLE paciente (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(255),
                          edad TINYINT,
                          identificacion VARCHAR(255),
                          telefono VARCHAR(255)
);

CREATE TABLE medico (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        nombre VARCHAR(255),
                        edad TINYINT,
                        identificacion VARCHAR(255),
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

CREATE TABLE historial_medico (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  paciente_id BIGINT,
                                  FOREIGN KEY (paciente_id) REFERENCES paciente(id)
    -- No incluyas aquí las listas de enfermedades ni medicamentos.
);

CREATE TABLE enfermedad (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            nombre VARCHAR(255),
                            descripcion VARCHAR(255)
);

CREATE TABLE medicamento (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             nombre VARCHAR(255),
                             descripcion VARCHAR(255),
                             tipo VARCHAR(255),
                             dosis VARCHAR(255),
                             frecuencia VARCHAR(255),
                             duracion VARCHAR(255),
                             fecha_inicio DATE,
                             fecha_fin DATE,
                             historial_medico_id BIGINT,
                             FOREIGN KEY (historial_medico_id) REFERENCES historial_medico(id)
);

-- Tabla de unión para la relación many-to-many entre historial_medico y enfermedades.
CREATE TABLE historial_medico_enfermedades (
                                               historial_medico_id BIGINT NOT NULL,
                                               enfermedades VARCHAR(255), -- Guarda el *nombre* de la enfermedad
                                               FOREIGN KEY (historial_medico_id) REFERENCES historial_medico(id)
    -- No es necesario FK a enfermedad, ya que usas String
);