# fleet-state

### Features

The fleet-state application contains the following features:

- Create vehicle
- Create station
- Create depot
- Transitions
    - Vehicle to station
    - Vehicle from station
    - Vehicle to depot
    - Vehicle from depot
- State
    - Stationed vehicles
    -  Parked vehicles

### Known bugs

There are currently no known bugs.

### Environment variables

| Name                       | Description                                                                   | Required | Release |
|----------------------------|-------------------------------------------------------------------------------|----------|---------|
| SPRING_DATASOURCE_URL      | URL to the database. Supported driver: JDBC. Supported database: Postgres, H2 |    Yes   |  1.0.0  |
| SPRING_DATASOURCE_USERNAME | Username for accessing the database.                                          |    Yes   |  1.0.0  |
| SPRING_DATASOURCE_PASSWORD | Password for accessing the database.                                          |    Yes   |  1.0.0  | 

### Docker images:

- Current version: `ghcr.io/jaranddev/fleet-state:1.0.0`
- Latest: `ghcr.io/jaranddev/fleet-state:latest`
