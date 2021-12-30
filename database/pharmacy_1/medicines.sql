-- created table medicine

CREATE TABLE `medicines`(
`id` int NOT NULL AUTO_INCREMENT ,
PRIMARY KEY (`id`),
 `name_medicine` varchar(30), 
 `price` int
 );
 
 
 -- test record added

INSERT INTO medicines VALUES (1, "Paracetamol", 30);
INSERT INTO medicines VALUES (2, "Lcz", 25);
INSERT INTO medicines VALUES (3, "Coronil", 200);
INSERT INTO medicines VALUES (4, "Cetirizine", 50); 

-- test record ended
