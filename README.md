# MultiBackend et Client gRPC  

Ce projet contient :  
1. **SpringbootBackend** : Backend multi-services en Spring Boot.  
2. **AndroidFrontEnd** : Client Android gRPC pour tester les services backend.  

## Structure du Projet

### SpringbootBackend
- **Technologies** : Spring Boot, Maven, H2 Database  
- **Fonctionnalités** :  
   - Exposition des services CRUD via **REST** **SOAP** **GraphQL** et **gRPC**  
   - Configuration simple avec `application.properties`

### AndroidFrontEnd
- **Technologies** : Android, gRPC, Protobuf  
- **Fonctionnalités** :  
   - Envoi des requêtes **Créer**, **Consulter**, **Modifier**, **Supprimer** via gRPC  
   - Affichage des résultats des appels  

## Prérequis
- **JDK 17+**  
- **Android Studio**  
- **Maven**  

