# CHANGELOG

Les changements notables du projet sont notés dans ce fichier.  
Nomenclature :

- Added : pour les nouvelles fonctionnalités
- Changed : pour les changements de fonctionnalités existantes
- Deprecated : pour les fonctionnalités qui seront bientôt supprimées
- Removed : pour les fonctionnalités supprimées
- Fixed : pour les corrections de bugs
- Security : en cas de vulnérabilités
- Numéro de version : major.minor.patch

### Unreleased

#### Added

- Added GET /lease-contracts with unit test
- Added lease-contract table in liquibase with some data (013)
- Added GET /lease-contracts/:id with unit test
- Added POST /lease-contracts
- Added PUT /lease-contracts/:id
- Added DELETE /lease-contracts/:id

### [0.0.9] - 2023-03- 15

#### Added

- Added GET /me (controller, service and tests)
- Added api-prefix
- Added GET /properties : TDD (service and controller)
- Added tables in liquibase : property , type and property_type (010), (011), (012)
- Added POST /properties : TDD (service and controller)
- Added GET /properties/:id and PUT /properties/:id (service and controller with TDD)
- Added DELETE /properties/:id - TDD

#### Updated

- Updated register : checked if email used to login is existing

#### Fixed

- Removed password from response

### [0.0.8] - 2023-03-01

#### Added

- GET /owners (entity - model - repository - service - controller)
- Create table owner (liquibase) : 009
- GET /owners/:id - TDD (service and controller tests)
- POST /owners - TDD (service and controller tests)
- PUT /owners/:id - TDD (service and controller tests)
- DELETE /owners/:id - TDD (service and controller tests)

### [0.0.7] - 2023-02-24

#### Added

- GET /tenants/:id with Bad Request Exception
- PUT /tenants with Bad Request Exception
- DELETE /tenants with Bad Request Exception

### [0.0.6] - 2023-02-22

#### Added

- models for AddressEntity - Client-Entity - TenantEntity
- GET /tenants
- POST /tenant (TDD)

### [0.0.5] - 2023-02-02

#### Added

- Feature client : ClientEntity + repository + service + controller : /GET clients
- AddressEntity + repository
- Create table address and client with fk

### [0.0.4] - 2023-01-24

#### Added

- Feature JWT Token

### [0.0.3] - 2023-01-18

#### Added

- Feature UserEndpoint (test, liquibase, get/users)

### [0.0.2] - 2023-01-16

#### Added

- Feature PostgresConfiguration

#### Fixed

- Prod Profile

### [0.0.2] - 2023-01-16

#### Added

- Feature CORS
- Feature Profiles
- Feature Allowed OriginByProfil

### [0.0.1] - 2023-01-13

#### Added

- Feature deployment


