-- Création de la table "joueurs"
CREATE TABLE joueurs (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         position VARCHAR(100) NOT NULL,
                         equipe_id BIGINT NOT NULL,
                         CONSTRAINT fk_equipe FOREIGN KEY (equipe_id) REFERENCES equipes(id)
                             ON DELETE CASCADE                        -- Suppression en cascade des joueurs associés à une équipe
);
