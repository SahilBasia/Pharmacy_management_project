--created table for login

CREATE TABLE login(username VARCHAR(20), pass VARCHAR(50));

--inserting admin and dummy account

INSERT INTO login VALUES ("admin", "21232f297a57a5a743894a0e4a801fc3");       --pass is stored in hash
INSERT INTO login VALUES ("testUser", "21232f297a57a5a743894a0e4a801fc3");    --pass is stored in hash
