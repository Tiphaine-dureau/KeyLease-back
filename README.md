# KeyLease

POC (proof of concept) d'une application de gestion locative.

## Pour lancer le projet

Démarrer PostgreSQL :  
``docker-compose -f src/main/docker/docker-compose-dev.yml up -d``  
Démarrer l'application :  
``mvn spring-boot:run``  
Cela lance le profil en mode dev (logs)

## Déploiement sur Render

Utilisation de docker :   
``docker build -t key-lease .``  
``docker run -dp 8080:8080 key-lease``

Lance le profil en mode prod (vérifier sur docker)

## Swagger

- Swagger OpenAPI definition : http://localhost:8080/api/swagger-ui/index.html
- Swagger api-docs : http://localhost:8080/api/v3/api-docs 
