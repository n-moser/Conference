Einführung
==========

Es soll eine Applikation für Konferenzen programmiert werden. Die Hauptanwender sind Gäste, die diese Applikation über das Web oder über mobile Endgeräte nutzen sollen. Eine Konferenz (Conference) hat eine Dauer von einem bis mehrere Tage und findet üblicherweise an einem Ort statt. Beispiele für Konferenzen sind W-JAX oder die PRODYNA Hausmesse. Auf einer Konferenz findet Vorträge (Talks) statt. Die Gäste sollen sich informieren können, welche Talks überhaupt existieren, wann sie sind, in welchem Raum (Room) sie stattfinden und von wem (Speaker) dieser Talk gehalten wird. Ein Talk kann von mehreren Speakern gehalten werden. Zusätzlich soll es ein Backoffice-Zugang geben, über welche die Stammdaten (als Conference, Speaker, Room, Talk) gepflegt werden können.

 - Eine Conference hat einen Namen, eine Kurzbeschreibung, ein Anfangs- und ein Enddatum.
 - Ein Speaker hat einen Namen und eine Kurzbeschreibung
 - Ein Room hat einen Namen (z.B. "Adria") und eine Kapazität
 - Ein Talk hat einen Namen, eine Kurzbeschreibung, eine Länge (in Minuten), einen Room und einen oder mehrere Speaker und gehört zu einer Conference. 

Anforderungen
-------------
Der Auftraggeber stellt die folgenden Anforderungen:
 - Als Endanwender erwarte ich die folgenden Sichten In der Applikation
 ..- Startseite: Liste aller Konferenzen
 ..- Konferenz: Liste der Tage mit der Anzahl der Talks pro Tag
 ..- Talk: Details und Verweise auf Room und Speaker
 ..- Speaker: Details zu Verweise zu Talks
 ..- Room: Liste aller Talks, die in diesem Room stattfinden, nach Tagen getrennt, zeitlich sortiert
 ..- Konferenzübersicht: Darstellung einer Tabelle mit allen Tagen und aller Talks an diesem Tag.

 - Als Backoffice möchte ich die folgenden Stammdaten pflegen
 ..- Conferences (CRUD)
 ..- Speaker (CRUD)
 ..- Rooms (CRUD)
 ..- Talks (CRUD), damit müssen aber Konflikte bemerkt und angezeigt werden
 ..- Speaker können nur einen Talk gleichzeitig halten
 ..- Ein Room kann nur von einem Talk gleichzeitig genutzt werden
 ..- Ein Talk kann nur innerhalb der Conference (zeitlich) stattfinden.

 - Als Kunde möchte ich, dass ein Dokument "Service Architecture" verfasst wird, welches folgende Standard beschreibt
 ..- Verwendete Technologien
 ..- Aufbau der Quellen im Repository
 ..- Standard für Coding
 ..- Namenstandards für Projekte, Packages, Interfaces, Services, Klassen, Exceptions
 
 - Als Kunde möchte ich, dass für das Backend Java EE >=6 (Session Beans, JPA) auf einem JBoss 7 verwendet wird, weil das Kundenstandard ist 
 ..- Andere Application Server sind auch ok, z.B. Glassfish
 
 - Als Kunde möchte ich, dass das Backend mit Hilfe von Arquillian getestet wird, und zwar so, dass jede Geschäftlogik abgedeckt ist, weil ich eine hohe Qualität erwarte
 - Als Kunde möchte ich, dass für das Frontend JSF auf einem JBoss 7 verwendet wird, weil das Kundenstandard ist. 
 ..- Ich akzeptiere andere Web-Frameworks, solange der Architekt diese verantworten kann.
 
 - Als Kunde möchte ich, dass die Geschäftslogik als REST-Interface zur Verfügung gestellt wird, damit ich das System besser an meine vorhandenen Systeme integrieren kann.
 - Als Kunde möchte ich, dass die einzelnen Komponenten möglichst feingranular geschnitten werden und die Sichtbarkeiten auf ein Minimum reduziert werden, weil ich wenig erfahrene Entwickler habe und diese Software von meinen Leuten weiterentwickelt werden wird
 - Als Kunde möchte ich, dass Maven als Build- und Dependancy Management verwendet wird, weil das Kundenstandard ist
 - Als Kunde möchte ich, dass beim Build alle Tests automatisch ausgeführt werden, weil ich später alles auf einem Jenkins/Travis-CI laufen lassen möchte
 - Als Kunde möchte ich, dass GitHub (public) als Repository verwendet wird, weil das Kundenstandard ist
 - Als Kunde möchte ich, dass MySQL als Datenbank verwendet wird, weil das Kundenstandard ist. 
 ..- Als Alternative akzeptiere ich MongoDB, weil das zukunftsträchtig ist.
 
 - Als Kunde möchte ich, dass wenn sich am Talk etwas ändert (z.B. anderer Raum) eine asynchrone Nachricht in eine Queue mit diesen Informationen geschrieben wird, damit ich eine Push-Notification/SMS verschicken kann
 ..- Als vorübergehende Lösung erwarte ich eine MDB, die diese Informationen ins Log schreibt
 ..- Es ist wichtig, dass diese Komponente gut getestet ist
 
 - Als Ops möchte ich die Antwortzeiten der Applikation auf Methodenebene überwachen können, damit ich Performanceprobleme schnell orten und beheben kann. 
 ..- Ich erwarte auf Service und Methodenebene Informationen über die Laufzeiten (Min, Max, Anzahl, Summe der Zeit, Durschnitt)
 ..- Der Zugriff auf diese Daten muss per JMX erfolgen können
 
 - Als Ops möchte ich, dass alle Datenbankzugriffe als Prepared Statements ausgeführt werden, damit die Belastung der Datenbank geringer ist.
 - Als Ops möchte ich, dass zu allen Service-Aufrufen ein Log-Eintrag erfolgt, wo man den Service, die Methode, die Parameter und die Antwortzeiten sehen kann
 - Als Ops möchte ich, dass die Applikation nicht direkt die Datenbanktabellen anlegt oder manipuliert, sondern ein SQL-Script bereit liegt, weil bei uns Applikationen keine DDL-Logik enthalten dürfen

 Hinweise
 ---------------
 - Wir verzichten auf Security, wer sowas implementiert erhält einen Bonus
 - Die Qualität der GUI (Ergonomie) spielt keine Rolle, es geht nur um Funktionalität

 Entscheidungen
----------------
Als Architekt des Projekt müssen sie diverse Entscheidungen treffen
 - Wie viele Release Units soll es geben?
 - Welche Komponenten wird es geben?
 - Wie soll die Maven-Struktur aussehen?
 - Welche Datenbank soll verwendet werden (MySQL oder MongoDB)
 - Welche Standards sollen gelten
 
Deliverables
---------------
 - Ein Dokument "Service Architekture"
 - Ein oder mehrere git-Repositories mit jeweils eine Maven-Projekt
 - Das Build des Maven-Projekts liefert ein Assembly, das alles enthält, um die Applikation zum Laufen zu bringen
