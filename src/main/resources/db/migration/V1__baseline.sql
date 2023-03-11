CREATE TABLE vehicle
(
    id      UUID PRIMARY KEY,
    name    VARCHAR NOT NULL,
    type    VARCHAR NOT NULL,
    created VARCHAR NOT NULL
);

CREATE TABLE depot
(
    id      UUID PRIMARY KEY,
    name    VARCHAR NOT NULL,
    created VARCHAR NOT NULL
);

CREATE TABLE station
(
    id      UUID PRIMARY KEY,
    name    VARCHAR NOT NULL,
    created VARCHAR NOT NULL
);


CREATE TABLE vehicle_station_transition
(
    id         UUID PRIMARY KEY,
    vehicle_id UUID    NOT NULL,
    station_id UUID    NOT NULL,
    type       VARCHAR NOT NULL,
    created    VARCHAR NOT NULL
);

ALTER TABLE vehicle_station_transition
    ADD CONSTRAINT fk_vehicle_station_transition_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicle (id);
ALTER TABLE vehicle_station_transition
    ADD CONSTRAINT fk_vehicle_station_transition_station FOREIGN KEY (station_id) REFERENCES station (id);

CREATE INDEX vehicle_station_transition_vehicle_id_fkey ON vehicle_station_transition (vehicle_id);
CREATE INDEX vehicle_station_transition_station_id_fkey ON vehicle_station_transition (station_id);

CREATE TABLE vehicle_depot_transition
(
    id         UUID PRIMARY KEY,
    vehicle_id UUID    NOT NULL,
    depot_id   UUID    NOT NULL,
    type       VARCHAR NOT NULL,
    created    VARCHAR NOT NULL
);

ALTER TABLE vehicle_depot_transition
    ADD CONSTRAINT fk_vehicle_depot_transition_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicle (id);
ALTER TABLE vehicle_depot_transition
    ADD CONSTRAINT fk_vehicle_depot_transition_depot FOREIGN KEY (depot_id) REFERENCES depot (id);

CREATE INDEX vehicle_depot_transition_vehicle_id_fkey ON vehicle_depot_transition (vehicle_id);
CREATE INDEX vehicle_depot_transition_depot_id_fkey ON vehicle_depot_transition (depot_id);
