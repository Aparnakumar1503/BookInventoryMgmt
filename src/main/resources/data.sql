-- =============================================================
-- data.sql - Insert in correct Foreign Key dependency order
-- =============================================================

-- -------------------------------------------------------------
-- 1. STATE (no dependencies)
-- -------------------------------------------------------------
INSERT IGNORE INTO `state` VALUES
('AK','ALASKA'),('AL','ALABAMA'),('AR','ARKANSAS'),('AS','AMERICAN SAMOA'),
('AZ','ARIZONA'),('CA','CALIFORNIA'),('CO','COLORADO'),('CT','CONNECTICUT'),
('DC','DISTRICT OF COLUMBIA'),('DE','DELAWARE'),('FL','FLORIDA'),
('FM','FEDERATED STATES OF MICRONESIA'),('GA','GEORGIA'),('GU','GUAM'),
('HI','HAWAII'),('IA','IOWA'),('ID','IDAHO'),('IL','ILLINOIS'),
('IN','INDIANA'),('KS','KANSAS'),('KY','KENTUCKY'),('LA','LOUISIANA'),
('MA','MASSACHUSETTS'),('MD','MARYLAND'),('ME','MAINE'),('MH','MARSHALL ISLANDS'),
('MI','MICHIGAN'),('MN','MINNESOTA'),('MO','MISSOURI'),('MP','NORTHERN MARIANA ISLANDS'),
('MS','MISSISSIPPI'),('MT','MONTANA'),('NC','NORTH CAROLINA'),('ND','NORTH DAKOTA'),
('NE','NEBRASKA'),('NH','NEW HAMPSHIRE'),('NJ','NEW JERSEY'),('NM','NEW MEXICO'),
('NV','NEVADA'),('NY','NEW YORK'),('OH','OHIO'),('OK','OKLAHOMA'),
('OR','OREGON'),('PA','PENNSYLVANIA'),('PR','PUERTO RICO'),('PW','PALAU'),
('RI','RHODE ISLAND'),('SC','SOUTH CAROLINA'),('SD','SOUTH DAKOTA'),('TN','TENNESSEE'),
('TX','TEXAS'),('UT','UTAH'),('VA','VIRGINIA'),('VI','VIRGIN ISLANDS'),
('VT','VERMONT'),('WA','WASHINGTON'),('WI','WISCONSIN'),('WV','WEST VIRGINIA'),
('WY','WYOMING');

-- -------------------------------------------------------------
-- 2. CATEGORY (no dependencies)
-- -------------------------------------------------------------
INSERT IGNORE INTO `category` VALUES
(1,'Romance'),(2,'Comics'),(3,'Programming'),(4,'Database'),
(5,'Networking'),(6,'Library Science'),(7,'Science'),(8,'Geography'),(9,'Sports');

-- -------------------------------------------------------------
-- 3. BOOKCONDITION (no dependencies)
-- -------------------------------------------------------------
INSERT IGNORE INTO `bookcondition` VALUES
(1,'Bad','Not much is left of the book. Parts are not legible, cover may be missing.', 5.00),
(2,'Poor','Pages may be missing. Many pages may be marked.', 10.00),
(3,'Average','Less than 3 pages missing. Small markings. Cover intact.', 15.00),
(4,'Good','No pages missing, may be marked on less than 5 pages, not obscuring text or diagrams. Cover is in good condition', 20.00),
(5,'Excellent','Like new.', 25.00),
(6,'New','Brand new.', 30.00);

-- -------------------------------------------------------------
-- 4. PERMROLE (no dependencies)
-- -------------------------------------------------------------
INSERT IGNORE INTO `permrole` VALUES
(1,'Guest'),(2,'RegisteredUser'),(3,'StoreOwner'),(4,'Admin');

-- -------------------------------------------------------------
-- 5. AUTHOR (no dependencies)
-- -------------------------------------------------------------
INSERT IGNORE INTO `author` VALUES
(1,'Machogeek','Hero',''),(2,'Stratton','Bill',''),(3,'Kennedy','Dan',''),
(4,'Sonstein','Jeff',''),(5,'Troell','Luther',''),(6,'Lutz','Peter',''),
(7,'Heartthrob','Danielle',''),(8,'Lawley','Elizabeth',''),(9,'Weeden','Elissa',''),
(10,'Johnson','Daryl',''),(11,'Garrison','Dan',''),(12,'Hartpence','Bruce',''),
(13,'Leone','James',''),(14,'Phelps','Andrew',''),(15,'Henderson','Tona',''),
(17,'Webguy','Smerdley',''),(18,'Jacobs','Steve',''),(19,'Niemi','Rayno',''),
(20,'Kang','Jai',''),(21,'Perez-Hardy','Sylvia',''),(22,'Mason','Sharon',''),
(23,'Perry','Ron',''),(24,'Whittington','Keith',''),(25,'Bierre','Kevin',''),
(26,'Holden','Ed',''),(27,'Axelrod','Mike','');

-- -------------------------------------------------------------
-- 6. REVIEWER (no dependencies)
-- -------------------------------------------------------------
INSERT IGNORE INTO `reviewer` VALUES
(1,'Jacobs','Gadget Boy'),(2,'Troell','Dallas Underground'),(3,'Bills','The RIT Reporter'),
(4,'Hill','Microsoft News'),(5,'Kurtz','MacIntosh News'),(6,'Leone',NULL),
(7,'Doubleday','Media Mania'),(8,'Wells','The RIT Reporter'),(9,'Worthington','The Annex'),
(10,'Lawson','The Last Word'),(11,'Lasky',NULL),(12,'Rozanski',NULL),
(13,'Whittington','JAVA Rocks'),(14,'Holden',NULL),(15,'Stratton','Harvard Lampoon'),
(16,'Johnson','NRA Weekly'),(17,'Mason','BMW Owners Quarterly'),(18,'Goodman','MacIntosh News'),
(19,'Phelps','Detroit News'),(20,'Yacci','The Learning Channel'),(24,'Weeden','Scrye'),
(25,'McVea','Programming Central'),(26,'Perez-Hardy','The Network Voice'),
(27,'Heartthrob','Journal of Romance'),(28,'Bierre','JAVA Rocks'),(29,'Axelrod','JAVA Rocks');

-- -------------------------------------------------------------
-- 7. PUBLISHER (depends on: state)
-- -------------------------------------------------------------
INSERT IGNORE INTO `publisher` VALUES
(1,'CovertoCover Publishing','Rochester','NY'),
(2,'ReadMeFirst House of Publishing','New York','NY'),
(3,'TitlesRUs Book Company','Rochester','MN'),
(4,'ReadEmandWeep Romance Publishing','Chicago','IL'),
(5,'ScanALot Paperback Books','Chicago','IL'),
(6,'EZRead Masterpiece Comics','New York','NY'),
(7,'CumbersomeText Press','New York','NY'),
(8,'PrintingPress Magic','Rochester','NY'),
(9,'Guttenberg and Associates','Chicago','IL'),
(10,'ReadMeNext House of Publishing','New York','NY'),
(11,'ReadEmandSleep Publishing House','Hartford','CT'),
(12,'WebTime Publishing Incorporated','Boston','MA'),
(13,'The JAVA Press','San Francisco','CA');

-- -------------------------------------------------------------
-- 8. USER (depends on: permrole)
-- -------------------------------------------------------------
INSERT IGNORE INTO `users` (`LastName`, `FirstName`, `PhoneNumber`, `UserName`, `Password`) VALUES
('Fribblestone','Gus','(717) 555-0975','GusBuster@arl.com','democrat'),
('Scott','Francis','(284) 555-4567','FranTheMan@arl.com','Habib'),
('Corplestein','Leslie','(989) 555-4567','LesTheLass@hopmail.com','Amit'),
('Corplestein','Ira','(716) 555-4567','IraBeForever@maybe.com','Peng'),
('Francis','Scott','(210) 555-0840','scotty@wherever.com','scotty'),
('Ling','Wei','(347) 555-4567','wxl0101@rit.edu','Cao'),
('Burblemonger','Mary','(983) 555-8865','GerbilLover@hatmail.com','mymy!'),
('Rabblewok','George','(223) 555-1989','ForRent@wherever.com','yourmine'),
('Tripplewander','Martha','(716) 555-2877','TakeMeFirst@quicksend.com','imfirst'),
('Lumberworker','George','(882) 555-5977','RachetMan@toolbox.net','timthetoolman'),
('Ferblemander','Frederick','(315) 555-6778','FerbieTheGreat@webSent.net','whoknows'),
('Ribblemaze','Ronda','(716) 555-2443','RibbleIsHere@webSent.net','idon');

INSERT IGNORE INTO `users` (`LastName`, `FirstName`, `PhoneNumber`, `UserName`, `Password`, `RoleNumber`) VALUES
('Thunderbird','Manager','(828) 555-2443','thunderbird@mail.net','password', 2),
('Discord','Manager','(664) 555-2443','discord@cats.net','password', 3),
('Slack','Manager','(593) 555-2443','slack@aol.net','password', 4);

-- -------------------------------------------------------------
-- 9. BOOK (depends on: category, publisher)
-- -------------------------------------------------------------
INSERT IGNORE INTO `book` VALUES
('1-111-11111-4','Women are From Venus ORACLE is from Beyond Pluto','New York Times Best Seller 27 weeks',1,'4',1),
('1-222-32443-7','Calculus for Phys Ed Majors','Carry our 200 lb text book: learn integrals and keep in Shape',7,'3',7),
('1-295-84547-1','My Love\'s Last Longing','Volume 1 of 4',1,'1',4),
('1-533-73363-8','From the Shores of Lake Erie to IT','One man\'s Journey to IT',8,'2',1),
('1-559-68777-1','My Love\'s Last Lingering Lost','Volume 2 of 4',1,'1',4),
('1-932-87447-6','The Science of Literature Searching','A Book Worth a Look is Worth a Good Look Up',6,'1',5),
('2-089-77869-5','Yes! Networking is for Bills Fans','How to Use a Spanning Tree to Exit Rich Stadium',9,'1',8),
('2-122-29058-5','How to Keep your Cellular Bill Down','How to carry 500 Miles of Fiber in Your Wallet',5,'2',2),
('2-221-11341-5','Tired of wired? Infrared instead!','500 Ways to pull out your hair while pulling out all your cable',5,'1',2),
('3-444-95595-4','ER, SOM, NF, DK/NF, SQL, JDBC, ODBC, and RELVAR','How U can avoid being trapped in Database acronyms',4,'1',3),
('3-445-43341-2','Master Wireless Through The Classic Comics','Infrared man triumphs over Cable Guy and CoAx NastyMan',2,'1',6),
('3-445-58668-5','How to Keep your Cable Bill Down','How to Avoid Tripping on 500 Miles of Fiber in your wallet',5,'1',10),
('4-344-22221-1','I Lasted my Love\'s Last Lingering Longing','Volume 3 of 4',1,'2',4),
('4-668-39283-8','From Deep in the Heart of Texas to IT','One man\'s Journey to IT',8,'1',1),
('5-538-55746-1','My Lost Love\'s Long Last Lingering','Volume 4 of 4',1,'1',4),
('6-449-68668-8','From Brockport to IT','Prof. Johnson\'s Journey to IT (about 25 min via 490)',8,'1',1),
('7-999-14432-2','Master HTML Through The Classic Comics','Using his Super Web thrower, Spiderman creates HTML docs',2,'2',6),
('8-330-69586-6','The Shortest Book in the World','A Guide to how Detroit will win the World Series in 2001',9,'1',3),
('9-333-44441-1','My Love\'s at Long Last Lost his Lingering','Volume 5 of 4',1,'1',4),
('9-337-55467-3','How to add Class to your Programming','How to write programs in VB instead of C',3,'3',10),
('9-400-55555-2','Master C++ Through The Classic Comics','GeekMan triumphs over C++ program infected with Kryptonite virus',2,'1',6),
('9-444-43356-3','A language without Pointers? Priceless.','For Everything Else, there\'s C++',3,'1',13),
('9-455-61223-3','JAVA: It\'s more than Just a Programming Language','Achieve enlightenment through the mystery of the Swing package',3,'1',7);

-- -------------------------------------------------------------
-- 10. INVENTORY (depends on: book, bookcondition)
-- -------------------------------------------------------------
INSERT IGNORE INTO `inventory` (`ISBN`, `Ranks`) VALUES
('1-111-11111-4',1),('1-111-11111-4',2),('1-111-11111-4',3),
('1-222-32443-7',4),('1-222-32443-7',5),('1-222-32443-7',6),('1-222-32443-7',1),('1-222-32443-7',2),
('1-295-84547-1',6),('1-295-84547-1',6),('1-295-84547-1',6),('1-295-84547-1',3),('1-295-84547-1',5),
('1-533-73363-8',6),('1-533-73363-8',1),('1-533-73363-8',6),
('1-559-68777-1',2),('1-559-68777-1',6),('1-559-68777-1',3),
('1-932-87447-6',6),('1-932-87447-6',4),('1-932-87447-6',6),
('2-089-77869-5',5),('2-089-77869-5',6),('2-089-77869-5',1),
('2-122-29058-5',2),('2-122-29058-5',3),('2-122-29058-5',4),
('2-221-11341-5',6),('2-221-11341-5',6),('2-221-11341-5',6),
('3-444-95595-4',5),('3-444-95595-4',6),('3-444-95595-4',1),
('3-445-43341-2',6),('3-445-43341-2',2),('3-445-43341-2',6),
('3-445-58668-5',6),('3-445-58668-5',6),
('4-344-22221-1',3),('4-344-22221-1',4),('4-344-22221-1',5),('4-344-22221-1',1),
('4-668-39283-8',6),('4-668-39283-8',2),
('5-538-55746-1',3),('5-538-55746-1',6),
('6-449-68668-8',6),('6-449-68668-8',4),('6-449-68668-8',6),
('7-999-14432-2',6),('7-999-14432-2',5),
('8-330-69586-6',1),('8-330-69586-6',6),('8-330-69586-6',2),('8-330-69586-6',6),('8-330-69586-6',6),
('9-333-44441-1',3),('9-333-44441-1',4),
('9-337-55467-3',6),
('9-400-55555-2',6),('9-400-55555-2',6),('9-400-55555-2',5),
('9-444-43356-3',1),
('9-455-61223-3',6);

-- -------------------------------------------------------------
-- 11. BOOKAUTHOR (depends on: book, author)
-- -------------------------------------------------------------
INSERT IGNORE INTO `bookauthor` VALUES
('1-111-11111-4',9,'Y'),('1-222-32443-7',5,'N'),('1-222-32443-7',11,'N'),
('1-222-32443-7',12,'Y'),('1-295-84547-1',7,'Y'),('1-533-73363-8',2,'Y'),
('1-559-68777-1',7,'Y'),('1-932-87447-6',8,'Y'),('1-932-87447-6',15,'N'),
('2-089-77869-5',6,'Y'),('2-089-77869-5',13,'N'),('2-089-77869-5',14,'N'),
('2-122-29058-5',12,'Y'),('2-221-11341-5',12,'Y'),('3-444-95595-4',2,'Y'),
('3-445-43341-2',12,'Y'),('3-445-58668-5',12,'Y'),('4-344-22221-1',7,'Y'),
('4-668-39283-8',5,'Y'),('5-538-55746-1',7,'Y'),('6-449-68668-8',10,'Y'),
('7-999-14432-2',1,'N'),('7-999-14432-2',17,'Y'),('8-330-69586-6',14,'Y'),
('9-333-44441-1',7,'Y'),('9-337-55467-3',19,'N'),('9-337-55467-3',20,'Y'),
('9-400-55555-2',1,'N'),('9-400-55555-2',17,'Y'),('9-444-43356-3',25,'Y'),
('9-444-43356-3',27,'N'),('9-455-61223-3',4,'Y');

-- -------------------------------------------------------------
-- 12. BOOKREVIEW (depends on: book, reviewer)
-- -------------------------------------------------------------
INSERT IGNORE INTO `book_review` VALUES
('1-111-11111-4',19,10,'Hold on to your hats, gang! If you think a cookbook or the yellow pages is exciting reading, you gotta read Professor Spada\'s remarkable treatise on enriching you personal relationships through your knowledge of ORACLE 8.1.6. In an easy-to-read fashion, s'),
('1-295-84547-1',27,10,'This is one of the most outstanding books ever written. Five thumbs up. Five stars. A must-read for everyone.'),
('1-533-73363-8',20,4,'A rather boring autobiography of a northern Ohio native who migrates to Rochester New York where he becomes a professor of Information Technology at RIT. Other than the chapters devoted to the Cleveland Indians (chapters 1, 2, 3, 6, 10, 11, 12, 13), The C'),
('1-559-68777-1',2,8,'A wonderful romance novel about Matt, a successful editor for the New York Times, who resigns and retreats to a Dude Ranch in New Mexico to find himself.  Matt meets Margaret, a person with cowgirl charm in touch with her masculine self. Matt falls in lov'),
('1-559-68777-1',19,9,'A wonderful romance novel about Margaret, a successful editor for the New York Times, who resigns and retreats to a Dude Ranch in New Mexico to find herself.  Margaret meets Matt, a person with tough cowboy swagger in touch with his feminine self. Margare'),
('2-089-77869-5',5,5,'Definitely don\'t buy this book if you are a Giants fan.  Two Giants fans tried this technique at the Giants game several weeks ago and they aren\'t back yet.'),
('2-089-77869-5',13,8,'For any networking buff/Bills fan, Dr. Lutz\' treatise on the use of spanning trees to exit a Buffalo Bills game is a must. It is difficult to slog through in spots, but he shows you that by applying his minimalization technique you can get back to Rochest'),
('2-089-77869-5',15,6,'Don\'t buy this book unless your are  Bills fan.  Fans of any other team will find their departure time from a Bills game actually triples when the technique is applied. In fact, it probably accounts for why Dr. Lutz (and any other Bills fan who applies it'),
('2-122-29058-5',6,7,'Professor Hartpence shows you how to carry 500 miles of fiber in your wallet.  Warning to the unwary: don\'t buy this book unless you have deep pockets.'),
('2-122-29058-5',13,8,'Watch out Jim Carey .. this non-cable guy may leave you out of work. In his book, Professor Hartpence combines his cellular and networking skills with his experience in both the military and the commercial world and the practice of Zen to show you how to'),
('3-444-95595-4',3,4,'U lose if you ever bother to read this book.'),
('3-445-58668-5',6,7,'This book is a follow-up to Professor Hartpence\'s book on how to carry 500 miles of fiber in your wallet.'),
('4-344-22221-1',24,7,'A wonderful romance novel about Nelson, a successful editor for the New York Times, who resigns and retreats to a Dude Ranch in New Mexico to find himself.  Nelson meets Laura, a person with cowgirl charm in touch with her masculine self. Nelson falls in'),
('4-668-39283-8',6,6,'A sometimes boring autobiography of a Texan transplanted to Rochester New York where he becomes a professor of Information Technology at RIT. Other than the chapters devoted to the Dallas Cowboys (chapters 1, 2, 3, 6, 10, 11, 12, 13), The Presidential ele'),
('5-538-55746-1',15,9,'A wonderful romance novel about Laura, a successful editor for the New York Times, who resigns and retreats to a Dude Ranch in New Mexico to find herself.  Laura meets Nelson, a person with tough cowboy swagger in touch with his feminine self. Laura falls'),
('6-449-68668-8',20,7,'The funny, often racy, autobiography of Daryl Johnson and his escapades to and from RIT. The book is largely about the excitement of commuting from the west side to RIT every day and how much fun it can be. A great book for those who live on the west side'),
('6-449-68668-8',26,10,'An absolutely outstanding autobiography of a Saint John Fisher Graduate and how he has adapted to his 20 minute ride (mostly on 490) to RIT every morning and his 20 ride (mostly on 490) home from RIT every night. The author uses thirteen chapters to vibra'),
('8-330-69586-6',5,10,'Geez .. no pages, no paragraphs, no words .. not even an illustration. But he did dedicate the book to Tony Clark. Says something for the fervor of this Detroit Tigers fan.'),
('8-330-69586-6',15,10,'Very accurate analysis of a problem that is not NP complete.'),
('9-333-44441-1',24,7,'Throwing the usual themes and twists of a romance novel to the winds, the author tells the store of Laura, a successful Dude Ranch owner in New Mexico, who resigns and moves to New York to seek her fortune as an editor for the New York Times. Laura meets'),
('9-337-55467-3',25,9,'These authors know their way around a coding pad, let me tell you! In clear, bell-shaped tones, with succinct wording and pinpoint accuracy, they nail this subject matter to the wall. Do you like inheritance? How about information hiding? Maybe you like e'),
('9-455-61223-3',13,9,'Professor Sonstein shows how oneness with the spiritual universe can be achieved by bringing the soul into rhytmn with the JAVA runtime machine. He shows that through various forms of self denial, such as writing code without pointers and heavy weight com');

-- -------------------------------------------------------------
-- 13. SHOPPINGCART (depends on: user, book)
-- (empty by default - populated at runtime by users)
-- -------------------------------------------------------------

-- -------------------------------------------------------------
-- 14. PURCHASELOG (depends on: user, inventory)
-- (empty by default - populated at runtime by purchases)
-- -------------------------------------------------------------