# Gestion de l'équipe de football - API REST

Ce projet est une API REST développée avec Spring Boot permettant de gérer une équipe de football, y compris la gestion des équipes et des joueurs.  
Il utilise une base de données MySQL et expose une interface Swagger pour tester les API.

## Prérequis

Avant de commencer, assurez-vous d'avoir installé les outils suivants :

- **Java 17** ou une version supérieure - [Télécharger JDK 17](https://adoptopenjdk.net/)
- **Maven** - [Télécharger Maven](https://maven.apache.org/)
- **MySQL** version 8 ou supérieure - [Télécharger MySQL](https://www.mysql.com/)

## Installation

1. **Cloner le projet**

   ```bash
   git clone https://github.com/brikiahmed/football-TEST.git
   cd football-TEST
   git checkout master

2. **Configurer la base de données**
 Assurez-vous que la base de données football existe dans MySQL :
   ```bash
   CREATE DATABASE football;

3. **Démarrer le projet**
   ```bash
   mvn clean
   mvn compile
   mvn install
   mvn spring-boot:run

4. **Démarrer le projet**
   Une fois l'application démarrée, vous pouvez accéder à la documentation Swagger :

👉 http://localhost:8080/swagger-ui/index.html

