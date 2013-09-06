Einf�hrung
==========

Es soll eine Applikation f�r Konferenzen programmiert werden. Die Hauptanwender sind G�ste, die diese Applikation �ber das Web oder �ber mobile Endger�te nutzen sollen. Eine Konferenz (Conference) hat eine Dauer von einem bis mehrere Tage und findet �blicherweise an einem Ort statt. Beispiele f�r Konferenzen sind W-JAX oder die PRODYNA Hausmesse. Auf einer Konferenz findet Vortr�ge (Talks) statt. Die G�ste sollen sich informieren k�nnen, welche Talks �berhaupt existieren, wann sie sind, in welchem Raum (Room) sie stattfinden und von wem (Speaker) dieser Talk gehalten wird. Ein Talk kann von mehreren Speakern gehalten werden. Zus�tzlich soll es ein Backoffice-Zugang geben, �ber welche die Stammdaten (als Conference, Speaker, Room, Talk) gepflegt werden k�nnen. 
 - Eine Conference hat einen Namen, eine Kurzbeschreibung, ein Anfangs- und ein Enddatum.
 - Ein Speaker hat einen Namen und eine Kurzbeschreibung
 - Ein Room hat einen Namen (z.B. "Adria") und eine Kapazit�t
 - Ein Talk hat einen Namen, eine Kurzbeschreibung, eine L�nge (in Minuten), einen Room und einen oder mehrere Speaker und geh�rt zu einer Conference. 

Anforderungen
-------------
Der Auftraggeber stellt die folgenden Anforderungen:
 - Als Endanwender erwarte ich die folgenden Sichten In der Applikation
 - 
 -- Startseite: Liste aller Konferenzen
 -- Konferenz: Liste der Tage mit der Anzahl der Talks pro Tag
 -- Talk: Details und Verweise auf Room und Speaker
 -- Speaker: Details zu Verweise zu Talks
 -- Room: Liste aller Talks, die in diesem Room stattfinden, nach Tagen getrennt, zeitlich sortiert
 -- Konferenz�bersicht: Darstellung einer Tabelle mit allen Tagen und aller Talks an diesem Tag.

 - Als Backoffice m�chte ich die folgenden Stammdaten pflegen
 -- Conferences (CRUD)
 -- Speaker (CRUD)
 -- Rooms (CRUD)
 -- Talks (CRUD), damit m�ssen aber Konflikte bemerkt und angezeigt werden
 -- Speaker k�nnen nur einen Talk gleichzeitig halten
 -- Ein Room kann nur von einem Talk gleichzeitig genutzt werden
 -- Ein Talk kann nur innerhalb der Conference (zeitlich) stattfinden.

 - Als Kunde m�chte ich, dass ein Dokument "Service Architecture" verfasst wird, welches folgende Standard beschreibt
 -- Verwendete Technologien
 -- Aufbau der Quellen im Repository
 -- Standard f�r Coding
 -- Namenstandards f�r Projekte, Packages, Interfaces, Services, Klassen, Exceptions
 
 - Als Kunde m�chte ich, dass f�r das Backend Java EE >=6 (Session Beans, JPA) auf einem JBoss 7 verwendet wird, weil das Kundenstandard ist 
 -- Andere Application Server sind auch ok, z.B. Glassfish
 
 - Als Kunde m�chte ich, dass das Backend mit Hilfe von Arquillian getestet wird, und zwar so, dass jede Gesch�ftlogik abgedeckt ist, weil ich eine hohe Qualit�t erwarte
 - Als Kunde m�chte ich, dass f�r das Frontend JSF auf einem JBoss 7 verwendet wird, weil das Kundenstandard ist. 
 -- Ich akzeptiere andere Web-Frameworks, solange der Architekt diese verantworten kann.
 
 - Als Kunde m�chte ich, dass die Gesch�ftslogik als REST-Interface zur Verf�gung gestellt wird, damit ich das System besser an meine vorhandenen Systeme integrieren kann.
 - Als Kunde m�chte ich, dass die einzelnen Komponenten m�glichst feingranular geschnitten werden und die Sichtbarkeiten auf ein Minimum reduziert werden, weil ich wenig erfahrene Entwickler habe und diese Software von meinen Leuten weiterentwickelt werden wird
 - Als Kunde m�chte ich, dass Maven als Build- und Dependancy Management verwendet wird, weil das Kundenstandard ist
 - Als Kunde m�chte ich, dass beim Build alle Tests automatisch ausgef�hrt werden, weil ich sp�ter alles auf einem Jenkins/Travis-CI laufen lassen m�chte
 - Als Kunde m�chte ich, dass GitHub (public) als Repository verwendet wird, weil das Kundenstandard ist
 - Als Kunde m�chte ich, dass MySQL als Datenbank verwendet wird, weil das Kundenstandard ist. 
 -- Als Alternative akzeptiere ich MongoDB, weil das zukunftstr�chtig ist.
 
 - Als Kunde m�chte ich, dass wenn sich am Talk etwas �ndert (z.B. anderer Raum) eine asynchrone Nachricht in eine Queue mit diesen Informationen geschrieben wird, damit ich eine Push-Notification/SMS verschicken kann
 -- Als vor�bergehende L�sung erwarte ich eine MDB, die diese Informationen ins Log schreibt
 -- Es ist wichtig, dass diese Komponente gut getestet ist
 
 - Als Ops m�chte ich die Antwortzeiten der Applikation auf Methodenebene �berwachen k�nnen, damit ich Performanceprobleme schnell orten und beheben kann. 
 -- Ich erwarte auf Service und Methodenebene Informationen �ber die Laufzeiten (Min, Max, Anzahl, Summe der Zeit, Durschnitt)
 -- Der Zugriff auf diese Daten muss per JMX erfolgen k�nnen
 
 - Als Ops m�chte ich, dass alle Datenbankzugriffe als Prepared Statements ausgef�hrt werden, damit die Belastung der Datenbank geringer ist.
 - Als Ops m�chte ich, dass zu allen Service-Aufrufen ein Log-Eintrag erfolgt, wo man den Service, die Methode, die Parameter und die Antwortzeiten sehen kann
 - Als Ops m�chte ich, dass die Applikation nicht direkt die Datenbanktabellen anlegt oder manipuliert, sondern ein SQL-Script bereit liegt, weil bei uns Applikationen keine DDL-Logik enthalten d�rfen

 Hinweise
 ---------------
 - Wir verzichten auf Security, wer sowas implementiert erh�lt einen Bonus
 - Die Qualit�t der GUI (Ergonomie) spielt keine Rolle, es geht nur um Funktionalit�t

 Entscheidungen
----------------
Als Architekt des Projekt m�ssen sie diverse Entscheidungen treffen
 - Wie viele Release Units soll es geben?
 - Welche Komponenten wird es geben?
 - Wie soll die Maven-Struktur aussehen?
 - Welche Datenbank soll verwendet werden (MySQL oder MongoDB)
 - Welche Standards sollen gelten
 
Deliverables
---------------
 - Ein Dokument "Service Architekture"
 - Ein oder mehrere git-Repositories mit jeweils eine Maven-Projekt
 - Das Build des Maven-Projekts liefert ein Assembly, das alles enth�lt, um die Applikation zum Laufen zu bringen
