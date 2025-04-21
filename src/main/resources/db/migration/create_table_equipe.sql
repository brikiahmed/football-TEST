-- Création de la table "equipes"
CREATE TABLE equipes (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         acronym VARCHAR(10) NOT NULL,
                         budget DOUBLE NOT NULL
);
