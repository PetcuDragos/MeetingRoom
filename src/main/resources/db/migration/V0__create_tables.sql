CREATE TABLE rooms (
    id bigint not null,
    name varchar(255),
    primary key (id)
);

CREATE TABLE seats (
    id bigint not null,
    available boolean,
    room_id bigint,
    primary key (id)
);

alter table if exists seats
   add constraint FK_seats_rooms
   foreign key (room_id)
   references rooms;

-- test data for now, should be removed
INSERT INTO ROOMS VALUES (1, 'Room1'), (2, 'Room2');
INSERT INTO SEATS VALUES (1, true, 1), (2, true, 1);