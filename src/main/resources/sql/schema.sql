CREATE TABLE IF NOT EXISTS session
(
    id uuid NOT NULL,
    machine_id uuid NOT NULL,
    start TIMESTAMP without time zone NOT NULL,
    stop TIMESTAMP without time zone,
    CONSTRAINT session_session_id_pk PRIMARY KEY (id)
    );

create INDEX session_machine_id_index ON session USING btree (machine_id);

CREATE TABLE IF NOT EXISTS event
(
    id uuid NOT NULL,
    session_id uuid NOT NULL,
    date TIMESTAMP without time zone,
    type TEXT,
    value numeric(10, 2),
    CONSTRAINT event_session_id FOREIGN KEY (session_id) REFERENCES session(id),
    CONSTRAINT event_id_pk PRIMARY KEY (id)
    );

create INDEX session_session_id_index ON event USING btree (session_id);

CREATE TABLE IF NOT EXISTS event_sum(
                                        id bigserial NOT NULL,
                                        session_id uuid NOT NULL,
                                        machine_id uuid NOT NULL,
                                        type TEXT,
                                        total numeric(10, 2),
                                        CONSTRAINT event_sum_id_pk PRIMARY KEY (id),
    CONSTRAINT event_uniq_session_id_machine_id_and_type UNIQUE (session_id, machine_id, type)
    );

create INDEX event_sum_session_and_machine_id_index ON event_sum USING btree (session_id, machine_id);

CREATE OR REPLACE FUNCTION func_sum_event_type()  RETURNS trigger AS
$BODY$
BEGIN
INSERT INTO event_sum (session_id, machine_id, type, total)
SELECT id, machine_id, NEW.type, NEW.value from session where id = NEW.session_id
    ON CONFLICT (session_id,machine_id, type) DO UPDATE
                                                     SET total =  event_sum.total + NEW.value;
RETURN NEW;
END;
$BODY$
language plpgsql;

CREATE TRIGGER update_total
    AFTER INSERT ON event
    FOR EACH ROW
    EXECUTE PROCEDURE func_sum_event_type();