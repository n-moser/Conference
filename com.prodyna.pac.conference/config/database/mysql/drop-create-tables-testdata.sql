-- ----------------------------------------------------------
--
-- MySQL DDL Script (including test data)
--
-- Server Version:               5.1.49-community-log - MySQL Community Server (GPL)
-- Author:                       Nicolas Moser
-- ----------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- -------------------
-- Conference DDL  --
-- -------------------

DROP TABLE IF EXISTS `conference`;

CREATE TABLE IF NOT EXISTS `conference` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` longtext,
  `endDate` date NOT NULL,
  `location` varchar(255) NOT NULL,
  `name` varchar(50) NOT NULL,
  `startDate` date NOT NULL,
  `version` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

-- Conference Data

/*!40000 ALTER TABLE `conference` DISABLE KEYS */;
INSERT INTO `conference` (`id`, `description`, `endDate`, `location`, `name`, `startDate`, `version`) VALUES
	(4, 'JavaOne is an annual conference inaugurated in 1996 by Sun Microsystems to discuss Java technologies, primarily among Java developers. JavaOne is held in San Francisco, California typically running from Monday to Thursday. Technical sessions on a variety of topics are held during the day. In the evening, Birds of a Feather (BOF) sessions are held. BOF sessions allow people to focus on a particular aspect of Java technology.', '2014-09-24', 'San Francisco', 'JavaOne 2014', '2014-09-21', 1),
	(5, 'JavaOne is an annual conference inaugurated in 1996 by Sun Microsystems to discuss Java technologies, primarily among Java developers. JavaOne is held in San Francisco, California typically running from Monday to Thursday. Technical sessions on a variety of topics are held during the day. In the evening, Birds of a Feather (BOF) sessions are held. BOF sessions allow people to focus on a particular aspect of Java technology.', '2015-09-23', 'San Francisco', 'JavaOne 2015', '2015-09-20', 2),
	(6, 'Die JAX bildet mit der W-JAX Europas führende Konferenz-Serie für Enterprise-Technologien, agile Methoden und Software-Architekturen. Gemeinsam mit den begleitenden Business Technology Days verleiht sie IT-Professionals führender Unternehmen aller Branchen die entscheidenden Impulse für digitale Wertschöpfung und Innovation - zwei Mal im Jahr.', '2014-05-16', 'Rheingoldhalle Mainz', 'JAX 2014', '2014-05-12', 0),
	(7, 'Die JAX bildet mit der W-JAX Europas führende Konferenz-Serie für Enterprise-Technologien, agile Methoden und Software-Architekturen. Gemeinsam mit den begleitenden Business Technology Days verleiht sie IT-Professionals führender Unternehmen aller Branchen die entscheidenden Impulse für digitale Wertschöpfung und Innovation - zwei Mal im Jahr.', '2015-05-15', 'Rheingoldhalle Mainz', 'JAX 2015', '2015-05-11', 0),
	(8, 'Die W-JAX ist die führende Konferenz für die Java-Plattform. Seien Sie dabei, wenn sich auch in diesem Jahr hochkarätige Speaker aus dem In- und Ausland treffen, um ihr pragmatisches Wissen für den Erfolg\r\nin IT-Projekten mit Ihnen zu teilen. Das Themenspektrum reicht von Java-Technologien über Softwarearchitektur bis hin zu agilen Managementmethoden und Fragen der Enterprise-Architektur.', '2013-11-06', 'Westin Grand Hotel München', 'W-JAX 2013', '2013-11-04', 1),
	(9, 'Die W-JAX ist die führende Konferenz für die Java-Plattform. Seien Sie dabei, wenn sich auch in diesem Jahr hochkarätige Speaker aus dem In- und Ausland treffen, um ihr pragmatisches Wissen für den Erfolg\r\nin IT-Projekten mit Ihnen zu teilen. Das Themenspektrum reicht von Java-Technologien über Softwarearchitektur bis hin zu agilen Managementmethoden und Fragen der Enterprise-Architektur.', '2014-11-05', 'Westin Grand Hotel München', 'W-JAX 2014', '2014-11-03', 1),
	(10, 'Das Java Forum Stuttgart (JFS) ist eine eintägige Konferenz mit Java als Leitmotiv. Das JFS ist in erster Linie für Entwickler konzipiert, ist aber ebenso der Entscheidungskompetenz der Besucher förderlich. Das Forum bietet den Teilnehmern die Möglichkeit, sich umfassend über Themen zu Java bzw. im Java-Umfeld zu informieren. Die Breite wird erreicht durch Grundlagenvorträge, Erfahrungsberichte und Informationen über konkrete Produkte. Produkte werden sowohl in Form von Vorträgen als auch als Demonstrationen an den Ausstellungsständen präsentiert.', '2014-07-04', 'Kultur- & Kongresszentrum Liederhalle Stuttgart', 'Java Forum Stuttgart 2014', '2014-07-03', 1),
	(11, 'Das Java Forum Stuttgart (JFS) ist eine eintägige Konferenz mit Java als Leitmotiv. Das JFS ist in erster Linie für Entwickler konzipiert, ist aber ebenso der Entscheidungskompetenz der Besucher förderlich. Das Forum bietet den Teilnehmern die Möglichkeit, sich umfassend über Themen zu Java bzw. im Java-Umfeld zu informieren. Die Breite wird erreicht durch Grundlagenvorträge, Erfahrungsberichte und Informationen über konkrete Produkte. Produkte werden sowohl in Form von Vorträgen als auch als Demonstrationen an den Ausstellungsständen präsentiert.', '2015-07-03', 'Kultur- & Kongresszentrum Liederhalle Stuttgart', 'Java Forum Stuttgart 2015', '2015-07-02', 1);
/*!40000 ALTER TABLE `conference` ENABLE KEYS */;


-- -------------------
-- Room DDL        --
-- -------------------

DROP TABLE IF EXISTS `room`;

CREATE TABLE IF NOT EXISTS `room` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `capacity` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `version` bigint(20) DEFAULT NULL,
  `conference_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`conference_id`) REFERENCES conference(id),
  KEY `FK26F4FB1953212F` (`conference_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;

-- Room Data

/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` (`id`, `capacity`, `name`, `version`, `conference_id`) VALUES
	(3, 50, 'Red Room', 0, 4),
	(4, 70, 'Blue Room', 0, 4),
	(5, 80, 'Yellow Room', 0, 4),
	(6, 40, 'Green Room', 0, 4),
	(7, 50, 'Red Room', 0, 5),
	(8, 60, 'Blue Room', 0, 5),
	(9, 70, 'Green Room', 0, 5),
	(10, 110, 'Yellow Room', 0, 5),
	(11, 50, 'Raum 512', 0, 6),
	(12, 80, 'Raum 14a', 0, 6),
	(13, 100, 'Raum 14b', 0, 6),
	(14, 40, 'Raum 334', 0, 6),
	(15, 130, 'Raum 434', 0, 6),
	(16, 50, 'Raum 14a', 0, 7),
	(17, 90, 'Raum 14b', 1, 7),
	(18, 90, 'Raum 334', 1, 7),
	(19, 30, 'Raum 434', 1, 7),
	(20, 70, 'Raum 512', 0, 7),
	(21, 80, '113', 0, 8),
	(22, 50, '114', 0, 8),
	(23, 30, '115', 0, 8),
	(24, 100, '116', 0, 8),
	(25, 100, '117', 0, 8),
	(26, 50, '113', 0, 9),
	(27, 70, '114', 0, 9),
	(28, 50, '115', 0, 9),
	(29, 90, '116', 0, 9),
	(30, 90, '117', 0, 9),
	(31, 60, 'Sumatra', 1, 10),
	(32, 80, 'Madagascar', 0, 10),
	(33, 150, 'Grönland', 0, 10),
	(34, 100, 'Neuguinea', 0, 10),
	(35, 50, 'Java', 0, 10),
	(36, 60, 'Sumatra', 0, 11),
	(37, 80, 'Madagascar', 0, 11),
	(38, 50, 'Java', 0, 11),
	(39, 130, 'Neuguinea', 0, 11),
	(40, 220, 'Grönland', 0, 11);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;


-- -------------------
-- Speaker DDL     --
-- -------------------

DROP TABLE IF EXISTS `speaker`;

CREATE TABLE IF NOT EXISTS `speaker` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` longtext,
  `name` varchar(50) NOT NULL,
  `version` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

-- Speaker Data

/*!40000 ALTER TABLE `speaker` DISABLE KEYS */;
INSERT INTO `speaker` (`id`, `description`, `name`, `version`) VALUES
	(4, 'Java Champion Adam Bien is a self-employed consultant, lecturer, software architect, developer, and author in the enterprise Java sector in Germany who implements Java technology on a large scale. He is also the author of several books and articles on Java and J2EE technology, as well as distributed Java programming. His books include J2EE Patterns, J2EE HotSpots, Java EE 5 Architectures, Enterprise Architectures, Enterprise Java Frameworks, SOA Expert Knowledge, and Struts, all published in German.', 'Adam Bien', 0),
	(5, 'Lars Vogel is the founder and CEO of the vogella GmbH and works as an Eclipse, Git and Android consultant, trainer and book author.\r\nHe is a regular speaker at international conferences, as for example EclipseCon, Devoxx, OOP, Droidcon and O\'Reilly\'s Android Open and has presented at the Google Headquarters in Mountain View.', 'Lars Vogel', 0),
	(6, 'Java Engineer/Architect. JCP Expert Group Member. Jacob is an independent member of the JSF EG. He\'s also a very active java community member, posting frequently on TheServerSide.com. Jacob is a self described "Cubicle Jockey" at his online blogspot.', 'Jacob Hookom', 0),
	(7, 'Java Engineer/Architect & Consultant. Gerardo has 9 years of experience in Java Developing. He has worked on some of the most important Java related projects in Mexico. He is a very active member of the Java Community in Mexico. Gerardo is also involved in the jrMan project jrMan is a project to write an open source version of the REYES rendering algorithm used by Pixar\'s? PhotoRealistic Renderman?.(Renderman is a registered trademark of Pixar) Almost all digital production work in the film industry is rendered using the REYES algorithm. Gerardo is also involved in educational project called IENJINIA. The IENJINIA Virtual Console and Devkit emulate hardware similar to an early 80\'s video games console to provide a better environment for learning how to write software.', 'Gerardo Horvilleur', 0),
	(8, 'Michael is a freelancer on Java/J2EE and agile development with nearly ten years of Java experience having thorough knowledge of other languages and systems like C++, RPG/400, .NET, Cobol etc. He is an architect, developer, coach, author and tutor on Java. He is a Java evangelist pushing the Java platform and his community in many complementary ways. He is the founder of the JUG Cologne http://jugcologne.org five years ago. His website: http://huettermann.net/', 'Michael Huettermann', 0),
	(9, 'Java Engineer. Valere is well known NetBeans/Creator champion. Valere resides in France.', 'Valere Dejardin', 0),
	(10, 'Java Community Leader. Daniel works tirelessly to promote the use of Java in Brazil through his leadership of one of the world\'s biggest JUGs, DFJUG in Brasilia. In addition he helps run an open source project: rybena. The rybena project uses Java technology to help deaf people communicate using J2ME enabled devices. The project has recently been renamed the "Rybena Solution" and has been adopted by the national telecom operator in Brazil: Brasil Telecom. This marks the first national telecom company to offer the Rybena Solution to the deaf.', 'Daniel de Oliveira', 0),
	(11, 'Bruce Eckel is the author of numerous books and articles about computer programming. He also gives frequent lectures and seminars for computer programmers. His best known works are Thinking in Java and Thinking in C++, aimed at programmers wanting to learn the Java or C++ programming languages, particularly those with little experience of object-oriented programming. His consulting company, Mindview Inc., concentrates mainly in object-oriented design assistance, but they also offer many Java related training courses and a monthly newsletter. Bruce also organizes conferences, workshops and seminars for software developers.', 'Bruce Eckel', 0),
	(12, 'Mr. Elizarov is a part-time professor at the St. Petersberg Institute of Fine Mechanics and Optics (St. Petersberg IFMO) where he engages his students as an faculty advisor for the ACM International Collegiate Programming Contest. "St. Petersberg IFMO" was the ACM 2004 World Champion. He is the Jury Chairman of ACM-ICPC Northeastern European Regional Contest. Mr. Elizarov also supports his local JUG (JUG.RU) as a volunteer presenter: ""Developing High Performance Distributed Systems for Event Processing"; topic first presented at the Software Engineering Conference SEC(R) in Oct. 2005. Mr. Elizarov is also a notable TopCoder competitor.', 'Roman Elizarov', 0);
/*!40000 ALTER TABLE `speaker` ENABLE KEYS */;


-- -------------------
-- Talk DDL        --
-- -------------------

DROP TABLE IF EXISTS `talk`;

CREATE TABLE IF NOT EXISTS `talk` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` longtext,
  `duration` int(11) NOT NULL,
  `endDate` datetime NOT NULL,
  `name` varchar(50) NOT NULL,
  `startDate` datetime NOT NULL,
  `version` bigint(20) NOT NULL,
  `conference_id` bigint(20) NOT NULL,
  `room_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`room_id`) REFERENCES room(id),
  FOREIGN KEY (`conference_id`) REFERENCES conference(id),
  KEY `FK27A8CC3118308F` (`room_id`),
  KEY `FK27A8CC1953212F` (`conference_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;

-- Talk Data

/*!40000 ALTER TABLE `talk` DISABLE KEYS */;
INSERT INTO `talk` (`id`, `description`, `duration`, `endDate`, `name`, `startDate`, `version`, `conference_id`, `room_id`) VALUES
	(2, 'Once you have your M2M application developed and proven out on the Orion platform, how do you get an optimized version to market? This session explores ways in which commercially available cellular modules compatible with the Orion specifications can be used to create mass deployable solutions. Hardware and software developers, along with design service companies, are invited to participate in this session on how best to grow the ecosystem.', 60, '2014-09-22 13:00:00', 'Commercializing Applications', '2014-09-22 12:00:00', 1, 4, 3),
	(3, 'Implementing a programming language atop an existing platform brings up some curious integration challenges with said platform. In the process of designing Nashorn’s Java platform integration features, the designers had to figure out lots of things: How can you subclass Java classes in script? What interfaces should script objects expose to Java: map, list, both, none? What consequences do the different decisions bring? How to reconcile differences in array representation? What’s a correct JSON representation of a POJO? (Is this a trick question?) Should Java packages exist as objects? (Trick question again?) How much plumbing should we hide as a convenience to developers, and what should remain exposed lest they stumble across it in the dark? All is revealed in this session.', 60, '2014-09-22 11:00:00', 'JavaScript on the JVM', '2014-09-22 10:00:00', 0, 4, 4),
	(4, 'This session examines one of the key new features of Java EE 7: the Java WebSocket API. It focuses on how to create WebSocket applications and enhance existing Web applications with Java WebSocket to make them richer, more engaging, and more efficient.', 90, '2014-09-22 15:30:00', 'Inside the Java WebSocket API', '2014-09-22 14:00:00', 1, 4, 6),
	(5, 'Objectwheel is a new user-definable social media platform and rapid development framework that includes search and a browser that’s based on JavaFX. The platform uses intelligent templates to create content that can be shared via social media with a client app or URL load. Inside the screen designer, end users or programmers drag and drop rich controls and fields onto a blank template and create powerful GUIs. The primary goal is to create a new social media platform that encourages interaction and data sharing between users. Plans include incorporating W3C Semantic Web intelligence into everything that touches Objectwheel. The platform is private and secure and runs as a client or app, not as a website. Learn more in this session.', 60, '2014-09-22 17:00:00', 'Social Media Platform', '2014-09-22 16:00:00', 1, 4, 5),
	(6, 'This presentation describes how to implement multitouch technologies by using the TUIO framework with the JavaFX rich internet application (RIA) framework. A step-by-step guide shows a real implementation in JavaFX to demonstrate its applicability in more detail. ', 60, '2014-09-23 13:00:00', 'Java FX', '2014-09-23 12:00:00', 0, 4, 5),
	(7, 'The world is not the same place it was when Java started. It’s 2013, and attackers are intensely motivated, sophisticated, and organized. Java security is a significant concern across all organizations as well as for individuals. Oracle has made a lot of progress in Java security since JavaOne 2012, including the addition of this new security track for JavaOne. Attend this Java security track opening presentation to learn about the progress over the last year and some details about Oracle’s Java security plans for the future. Additionally, the session highlights some key “Securing Java” track sessions to help plan your schedule for the week.', 120, '2014-09-23 17:00:00', 'Securing Java', '2014-09-23 15:00:00', 0, 4, 4),
	(8, 'The small-embedded and M2M space is exploding and provides huge opportunities for growth. Currently, the embedded development community is highly fragmented. By using the Java ME 8 platform, the community can rely on Java’s core values of productivity, consistency, security, portability, a rich feature set, preintegration, and standardization to enable a whole new generation of embedded development. This session reviews the features of the Java ME 8 platform and how it can be used for embedded software development for a wide range of increasingly ubiquitous small embedded devices.', 180, '2014-09-24 13:00:00', 'Java ME 8 Overview', '2014-09-24 10:00:00', 1, 4, 5),
	(9, 'The Internet of Things and the rise of an M2M ecosystem have long been expected. As this ecosystem converges with big data, we need a seamless device-to-data-center platform. Oracle Java Embedded Suite, targeting data gateways, brings middleware capabilities down to the embedded device level. It can collect data and share it with data centers in real time for immediate processing or save it for analysis. Oracle Event Processing for Oracle Java Embedded, designed to be deployed with Oracle Java Embedded Suite, enables processing data closer to the source. In this session, a Oracle Java Embedded Suite and Oracle Event Processing port on Freescale’s i.MX6D processor, based on ARM Cortex A9, is used to demo how the software platform data processing capabilities can be combined with hardware.', 60, '2015-09-21 13:00:00', 'Java Embedded Suite', '2015-09-21 12:00:00', 1, 5, 7),
	(10, 'The Internet of Things and the rise of an M2M ecosystem have long been expected. As this ecosystem converges with big data, we need a seamless device-to-data-center platform. Oracle Java Embedded Suite, targeting data gateways, brings middleware capabilities down to the embedded device level. It can collect data and share it with data centers in real time for immediate processing or save it for analysis. Oracle Event Processing for Oracle Java Embedded, designed to be deployed with Oracle Java Embedded Suite, enables processing data closer to the source. In this session, a Oracle Java Embedded Suite and Oracle Event Processing port on Freescale’s i.MX6D processor, based on ARM Cortex A9, is used to demo how the software platform data processing capabilities can be combined with hardware.', 60, '2015-09-22 13:00:00', 'Java Embedded Suite 2', '2015-09-22 12:00:00', 1, 5, 8),
	(11, 'The Internet of Things and the rise of an M2M ecosystem have long been expected. As this ecosystem converges with big data, we need a seamless device-to-data-center platform. Oracle Java Embedded Suite, targeting data gateways, brings middleware capabilities down to the embedded device level. It can collect data and share it with data centers in real time for immediate processing or save it for analysis. Oracle Event Processing for Oracle Java Embedded, designed to be deployed with Oracle Java Embedded Suite, enables processing data closer to the source. In this session, a Oracle Java Embedded Suite and Oracle Event Processing port on Freescale’s i.MX6D processor, based on ARM Cortex A9, is used to demo how the software platform data processing capabilities can be combined with hardware.', 180, '2015-09-23 15:00:00', 'Java Embedded Suite 3', '2015-09-23 12:00:00', 1, 5, 10),
	(12, 'Project Jigsaw aims to create a simple, friendly, and scalable standard module system for the Java platform. This session explains its key goals and design principles, shows the progress made in the past year, and demonstrates its use.', 60, '2014-05-12 13:00:00', 'Modular Java', '2014-05-12 12:00:00', 0, 6, 12),
	(13, 'Project Jigsaw aims to create a simple, friendly, and scalable standard module system for the Java platform. This session explains its key goals and design principles, shows the progress made in the past year, and demonstrates its use.', 120, '2014-05-13 12:00:00', 'Modular Java 2', '2014-05-13 10:00:00', 0, 6, 14),
	(14, 'Java was built from the ground up with security clearly in mind and is now the engine powering a huge number of business-critical systems. With this visibility and opportunity come attacks, and this session goes through the state of security in Java in 2013 and discusses some of the attack vectors. It presents a couple of real-world examples and also addresses the real-world challenges in getting security fixes out quickly. Finally, it touches on hardware cryptography. Come learn more about the reality of security today and take away a better awareness of exactly how Java helps protect you.', 60, '2014-05-13 14:00:00', 'Securing Java', '2014-05-13 13:00:00', 0, 6, 12),
	(15, 'ava was built from the ground up with security clearly in mind and is now the engine powering a huge number of business-critical systems. With this visibility and opportunity come attacks, and this session goes through the state of security in Java in 2013 and discusses some of the attack vectors. It presents a couple of real-world examples and also addresses the real-world challenges in getting security fixes out quickly. Finally, it touches on hardware cryptography. Come learn more about the reality of security today and take away a better awareness of exactly how Java helps protect you.', 60, '2014-05-14 13:00:00', 'Securing Java 2', '2014-05-14 12:00:00', 0, 6, 12),
	(16, 'This session presents a hardware and software reference design to serve the new markets of the Internet of Things, with a particular focus on home automation, smart energy, and e-health. The STMicroelectronics SmartHome reference design comprises a gateway in a production-ready form factor and a set of sensor/actuator nodes, along with a Java Virtual Machine/OSGi software stack that exposes this kit as a single system to application developers. The session details the features of this reference design, shows some example applications, and discusses its availability for application developers.', 300, '2014-05-15 15:00:00', 'Internet of Things', '2014-05-15 10:00:00', 0, 6, 15),
	(17, 'Introducing the JavaOne conference including a programming language atop an existing platform brings up some curious integration challenges with said platform. In the process of designing Nashorn’s Java platform integration features, the designers had to figure out lots of things: How can you subclass Java classes in script? What interfaces should script objects expose to Java: map, list, both, none? What consequences do the different decisions bring? How to reconcile differences in array representation? What’s a correct JSON representation of a POJO? (Is this a trick question?) Should Java packages exist as objects? (Trick question again?) How much plumbing should we hide as a convenience to developers, and what should remain exposed lest they stumble across it in the dark? All is revealed in this session.', 60, '2014-09-21 13:00:00', 'Introduction', '2014-09-21 12:00:00', 1, 4, 4),
	(18, 'With the new major revision of the JAX-RS 2.0 API, a completely reworked reference implementation provided by Jersey 2.0 was released earlier this year. However, Jersey is not just a JAX-RS reference implementation. It also provides many additional features such as JSON binding support; integration with various containers and client transports (Grizzly, AHC, and so on); support for server-sent events (SSEs); MVC view templates (analogous to .NET Razor templates); and EclipseLink MOXy integration, with all its bells and whistles (JSON and XML bindings, custom object graphs, externalized descriptors, and so on). This session takes an in-depth look at these cool Jersey features that address additional use cases not covered by the standard JAX-RS API.', 135, '2015-09-20 14:15:00', 'RESTful Web Services', '2015-09-20 12:00:00', 0, 5, 9),
	(19, 'With the new major revision of the JAX-RS 2.0 API, a completely reworked reference implementation provided by Jersey 2.0 was released earlier this year. However, Jersey is not just a JAX-RS reference implementation. It also provides many additional features such as JSON binding support; integration with various containers and client transports (Grizzly, AHC, and so on); support for server-sent events (SSEs); MVC view templates (analogous to .NET Razor templates); and EclipseLink MOXy integration, with all its bells and whistles (JSON and XML bindings, custom object graphs, externalized descriptors, and so on). This session takes an in-depth look at these cool Jersey features that address additional use cases not covered by the standard JAX-RS API.', 60, '2015-09-22 13:00:00', 'Java ME 8 Overview', '2015-09-22 12:00:00', 0, 5, 9),
	(20, 'With the new major revision of the JAX-RS 2.0 API, a completely reworked reference implementation provided by Jersey 2.0 was released earlier this year. However, Jersey is not just a JAX-RS reference implementation. It also provides many additional features such as JSON binding support; integration with various containers and client transports (Grizzly, AHC, and so on); support for server-sent events (SSEs); MVC view templates (analogous to .NET Razor templates); and EclipseLink MOXy integration, with all its bells and whistles (JSON and XML bindings, custom object graphs, externalized descriptors, and so on). This session takes an in-depth look at these cool Jersey features that address additional use cases not covered by the standard JAX-RS API.', 60, '2013-11-04 16:00:00', 'RESTful Web Services', '2013-11-04 15:00:00', 0, 8, 21),
	(21, 'Join the Oracle Java Deployment team in this BOF session for an interactive discussion that covers new deployment features, feature requests, recent Java security changes, and developer best practices for taking advantage of these changes.', 90, '2014-07-03 13:30:00', 'Development', '2014-07-03 12:00:00', 0, 10, 33),
	(22, 'Brain/computer interfaces (BCIs) are an emerging technology with a wide range of applications, from gaming and robot control to medical purposes. They read EEG signals (electroencephalography, brain waves), using specialized hardware; feed them into recognition software; and map recognized signals to actions in an application. This session explains how to use available Java open source tools for BCI signal processing, visualization, and recognition. It briefly covers steps in application-specific BCI development and gives general guidelines on signal recognition, along with a demonstration of how to use the Java neural network framework Neuroph for signal classification. You will also learn how to add BCI to your applications with Java. ', 120, '2014-07-03 12:00:00', 'Java Mind', '2014-07-03 10:00:00', 1, 10, 32);
/*!40000 ALTER TABLE `talk` ENABLE KEYS */;


-- -------------------
-- TalkSpeaker DDL --
-- -------------------

DROP TABLE IF EXISTS `talkspeaker`;

CREATE TABLE IF NOT EXISTS `talkspeaker` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) DEFAULT NULL,
  `speaker_id` bigint(20) NOT NULL,
  `talk_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`speaker_id`) REFERENCES speaker(id),
  FOREIGN KEY (`talk_id`) REFERENCES talk(id),
  KEY `FK4E5C119377461485` (`speaker_id`),
  KEY `FK4E5C119382D652EF` (`talk_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=latin1;


-- TalkSpeaker Data

/*!40000 ALTER TABLE `talkspeaker` DISABLE KEYS */;
INSERT INTO `talkspeaker` (`id`, `version`, `speaker_id`, `talk_id`) VALUES
	(3, 0, 7, 2),
	(4, 0, 11, 2),
	(5, 0, 6, 3),
	(6, 0, 11, 3),
	(7, 0, 12, 3),
	(8, 0, 8, 4),
	(9, 0, 6, 4),
	(10, 0, 7, 5),
	(11, 0, 8, 5),
	(12, 0, 4, 6),
	(13, 0, 8, 6),
	(14, 0, 7, 7),
	(15, 0, 7, 8),
	(16, 0, 7, 9),
	(17, 0, 8, 10),
	(18, 0, 10, 10),
	(19, 0, 7, 11),
	(20, 0, 5, 12),
	(21, 0, 5, 13),
	(22, 0, 7, 13),
	(23, 0, 7, 14),
	(24, 0, 9, 14),
	(25, 0, 12, 14),
	(26, 0, 11, 14),
	(27, 0, 9, 15),
	(28, 0, 5, 16),
	(29, 0, 12, 17),
	(30, 0, 10, 18),
	(31, 0, 11, 19),
	(32, 0, 12, 19),
	(33, 0, 5, 20),
	(34, 0, 4, 21),
	(35, 0, 7, 21),
	(36, 0, 9, 21),
	(37, 0, 10, 22),
	(38, 0, 12, 22);
/*!40000 ALTER TABLE `talkspeaker` ENABLE KEYS */;


/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
