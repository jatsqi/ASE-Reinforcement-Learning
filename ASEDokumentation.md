# Programmentwurf

# ASE Reinforcement Learning

Name: Quast, Johannes Martrikelnummer: 6897847

Abgabedatum: [DATUM]

## _Allgemeine Anmerkungen:_

- _es darf nicht auf andere Kapitel als Leistungsnachweis verwiesen werden (z.B. in der Form &quot;XY wurde schon in
  Kapitel 2 behandelt, daher hier keine Ausführung&quot;)_
- _alles muss in UTF-8 codiert sein (Text und Code)_
- _sollten mündliche Aussagen den schriftlichen Aufgaben widersprechen, gelten die schriftlichen Aufgaben (ggf. an
  Anpassung der schriftlichen Aufgaben erinnern!)_
- _alles muss ins Repository (Code, Ausarbeitung und alles was damit zusammenhängt)_
- _die Beispiele sollten wenn möglich vom aktuellen Stand genommen werden_
    - _finden sich dort keine entsprechenden Beispiele, dürfenl auch ältere Commits unter Verweis auf den Commit
      verwendet werden_
    - _Ausnahme: beim Kapitel &quot;Refactoring&quot; darf von vorne herein aus allen Ständen frei gewählt werden (mit
      Verweis auf den entsprechenden Commit)_
- _falls verlangte Negativ-Beispiele nicht vorhanden sind, müssen entsprechend mehr Positiv-Beispiele gebracht werden_
    - _Achtung: werden im Code entsprechende Negativ-Beispiele gefunden, gibt es keine Punkte für die zusätzlichen
      Positiv-Beispiele_
    - _Beispiele_
        - &quot;_Nennen Sie jeweils eine Klasse, die das SRP einhält bzw. verletzt.&quot;_
            - _Antwort: Es gibt keine Klasse, die SRP verletzt, daher hier 2 Klassen, die SRP einhalten: [Klasse 1]
              , [Klasse 2]_
            - _Bewertung: falls im Code tatsächlich keine Klasse das SRP verletzt: volle Punktzahl ODER falls im Code
              mind. eine Klasse SRP verletzt: halbe Punktzahl_
- _verlangte Positiv-Beispiele müssen gebracht werden_
- _Code-Beispiel = Code in das Dokument kopieren_

# ​Kapitel 1: **Einführung**

### ​Übersicht über die Applikation

Das Projekt "ASE Reinforcement Learning" soll ein einfaches CLI zur Verfügung stellen, mit dem jeder typische Beispiele
(aktuell nur eins) des Reinforcement Learnings ausprobieren und über einige Parameter anpassen kann. Das Ergebnis des
Trainings wird als CSV zur Verfügung gestellt und kann anschließend für andere Projekte genutzt werden. Auch können,
basierend auf bestehenden Beispielen, eigene Beispiele erstellt werden.

### ​Wie startet man die Applikation?

Requirements:

* Java 16+
* Maven
* Im Verzeichnis der JAR den Ordner `stored_values` erstellen

#### Projekt herunterladen

```shell
git clone https://github.com/jatsqi/ASE-Reinforcement-Learning.git
```

#### Projekt kompilieren

```shell
mvn package 
```

#### JAR finden

```shell
cd 4-ase-reinforcement-learning-plugin
```

Die JAR hat standardmäßig den Namen `4-ase-reinforcement-learning-plugin-1.0-SNAPSHOT-jar-with-dependencies.jar`

### ​Wie testet man die Applikation?

```shell
mvn test
```

# Kapitel 2: Clean Architecture

### ​Was ist Clean Architecture?

_[allgemeine Beschreibung der Clean Architecture in eigenen Worten]_

Als Clean-Architecture wird eine etrem vielseitige und anapssungsfähige Architektur bezeichnet, 
deren Aufbau häufig mit einer Zwiebel verglichen wird, da sich die verschiedenen Schichten ummanteln.
Primäres Ziel ist es, eine technologisch unabhängigen Kern vom Rest der Applikation zu trennen, damit
Abhängigkeiten nach außen schnell ausgetauscht werden bzw. verändert werden können und die eigentliche
Business-Logik bzw. Modellierung der Domäne keine Rücksicht auf konkrete technische Details nehmen muss.

### ​Analyse der Dependency Rule

_[(1 Klasse, die die Dependency Rule einhält und eine Klasse, die die Dependency Rule verletzt); jeweils UML der Klasse und Analyse der Abhängigkeiten in beide Richtungen (d.h., von wem hängt die Klasse ab und wer hängt von der Klasse ab) in Bezug auf die Dependency Rule]_

In dieser Applikation halten prinzipiell alle Klassen die Dependency-Rule hinsichtlich dem "Fluss" der Abhängigkeiten ein.
Klassen innerer Schichten besitzen <ins>keine</ins> Abhängigkeiten nach Außen.
Beispielsweise werden Repositories in der Domain-Schicht als Interface deklariert und erst außen konkret implementiert.

#### 1. Positiv-Beispiel: Dependency Rule

![Dependency Rule Config](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/jatsqi/ASE-Reinforcement-Learning/master/uml/dependencyRulePositiv.puml)

Wie im UML Diagramm zu sehen ist besitzt das Interface `ConfigRepository` selbst nur Dependencies innerhalb der eigenen Schicht,
wird aber in der Application schicht genutzt und von `PropertiesConfigRepository` in der PluginSchicht implementiert.
Die Klasse `ConfigServiceImpl` ist ebenfalls nicht abhängig von der Klasse aus der Plugin-Schicht, was eine Verletztung der Dependency-Rule
darstellen würde, sondern stattdessen abhängig vom Interface. 

#### 2. Positiv-Beispiel: Dependency Rule

![Dependency Rule Adapters](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/jatsqi/ASE-Reinforcement-Learning/master/uml/dependencyRulePositiv2.puml)

Selbiges gilt für den `AgentService`. Dieser hat ausschließlich Abhängigkeiten in Richtung Domain-Layer bzw. eine Vererbung auf derselben Ebene.
Nach außen Richtung Adapter bzw. Plugin Schicht besteht keinerlei Abhängigkeit.
In der Adapter-Schicht ist einzig und allein die `AgentServiceFacadeImpl` vom Service abhängig.
### ​ **Analyse der Schichten**

_[jeweils 1 Klasse zu 2 unterschiedlichen Schichten der Clean-Architecture: jeweils UML der Klasse (ggf. auch zusammenspielenden Klassen), Beschreibung der Aufgabe, Einordnung mit Begründung in die Clean-Architecture]_

#### Schicht: Domain

![Clean Arch Domain Layer](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/jatsqi/ASE-Reinforcement-Learning/master/uml/layerDomain.puml)

Die abstrakte Klasse `Agent` ist eines der Kernstücke der Domain. Der Agent kann Aktionen in einer Umgebung 
ausführen.
Die Klasse ist hier angesiedelt, da sie

1. nur das "Verhalten" definiert und keine technischen Details berücksichtigt
2. im Allgemeinen zur Domäne des Reinforcement Learnings gehört

Konkrete Agenten erben von dieser Klasse und mappen die Aktionen (Integer), die sie von der Aktion Source bekommen, auf konkrete
Aktionen `Action`, die die Umgebung versteht.

#### ​Schicht: Adapter

![Clean Arch Adapter Layer](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/jatsqi/ASE-Reinforcement-Learning/master/uml/layerAdapter.puml)

Die Klasse `EnvironmentServiceFacade` dient als Einstiegspunkt für das UI, wenn es um das Abrufen von Environment geht.
Das UI greift dabei nicht direkt auf die Services zu und arbeit damit mit Domain Objekten, sondern benutzt mehrere Facetten.
Diese sind im Adapter-Layer platziert und wandeln die Domain Objekte, die sich mitunter ändern können, in eine speziell für UI
vorgesehen Repräsentation um. Somit kann sich das Domain-Modell ändern, allerdings könnte durch das Mapping das UI unverändert bleiben.

# ​Kapitel 3: SOLID

### ​Analyse Single-Responsibility-Principle (**SRP)**

_[jeweils eine Klasse als positives und negatives Beispiel für SRP; jeweils UML der Klasse und Beschreibung der Aufgabe bzw. der Aufgaben und möglicher Lösungsweg des Negativ-Beispiels (inkl. UML)]_

#### ​Positiv-Beispiel

Das SRP wird durch so gut wie jede Repository Implementierung erfüllt. Als Beispiel wurde hier die `ConfigRepository`
mit der konkreten Implementierung `PropertiesConfigRepository` gewählt.
Die Repository hat die Aufgabe, Config Einträge aus der `.properties` Datei auszulesen und in diese zu speichern.

![SRP Positiv](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/jatsqi/ASE-Reinforcement-Learning/master/uml/srpPositiv.puml)

#### ​Negativ-Beispiel

Als eventuelles negativ-Beispiel habe ich die Klasse `SimpleEnvironmentFactory` gewählt.
Obwohl es für dieses kleine Projekt in Ordnung sein mag, ist dennoch das SRP verletzt. Die Klassen kümmert sich beispielsweise
nicht nur um das Erstellen einer Grid-World Umgebung, sondern auch um das Parsen der Datei, aus der eine Grid-World initialisiert werden könnte.
Zur Lösung könnte das Parsing in eine eigene Klasse ausgelagert werden und an die Factory könnte ein Interface übergeben werden.

![SRP Negativ](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/jatsqi/ASE-Reinforcement-Learning/master/uml/srpNegativ.puml)

### ​Analyse Open-Closed-Principle (OCP)

_[jeweils eine Klasse als positives und negatives Beispiel für OCP; jeweils UML der Klasse und Analyse mit Begründung, warum das OCP erfüllt/nicht erfüllt wurde – falls erfüllt: warum hier sinnvoll/welches Problem gab es? Falls nicht erfüllt: wie könnte man es lösen (inkl. UML)?]_

#### Negativ-Beispiel:

![OCP Negativ](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/jatsqi/ASE-Reinforcement-Learning/master/uml/openClosedNegative.puml)

Als klassisches Negativbeispiel für die Verletzung dieses Prinzips kann eine solche Factory genommen werden.
Diese entscheidet in diesem Fall, je nach Name des Agenten, welcher Agent erzeugt werden muss.
Wird ein neuer Agent im Code hinzugefügt, so muss entweder

* Die Methode `createAgent` selbst angepasst werden -> direkte Verletzung OCP
* Eine abgeleitete Klasse erstellt werden, die für alle bestehenden Agenten `super.createAgent()` aufruft. In diesem Fall muss auch die neue bzw. erweiterte Factory in die Dependency Injection oder ähnliches aufgenommen werden.

Abhilfe würde hier ein etwas dynamischeres Konzept schaffen. Über eine zentrale Registry könnte man für jeden Agenten-Namen
bestimmte `Provider` registrieren, die sich um das Erstellen eines bestimmten Agenten kümmern.
Die Factory greift auf diese Registry zu und holt sich den entsprechenden `Provider` aus der Map.
So müsste die Factory nicht angepasst werden.
In meinem Fall habe ich mich allerdings bewusst bei **allen** Factories dagegen entschieden, da mir bei dieser Projektgrö0e
**KISS** wichtiger war.

#### Positiv-Beispiel:

![OCP Positiv](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/jatsqi/ASE-Reinforcement-Learning/master/uml/openClosedPositive.puml)

Obwohl die Factory das OCP verletzt, ist der Agent selbst ein gutes Beispiel für die Nutzung dessen.
Die abstrakte Basisklasse `Agent` stellt die abstrakte Methode `transformAction` bereit und abgeleitete Klasse bzw. konkrete Agenten implementieren diese.
Die Methode wandelt den Integer der Aktion, der von der Action Source kommt in eine für das Environment verständliche Aktion um.
Die Methode `executeNextAction()`, die bereits in der Basisklasse implementiert ist und die für **alle** Agenten gleiche Logik zum Ausführen einer Aktion beinhaltet, 
ruft diese dann auf, um die konkrete Aktion zu holen.
Sämtliche Logik, die zum Trainieren des Agenten genutzt wird, bleibt unverändert, sobald ein neuer Agent hinzugefügt wird,
da diese Klassen alle entweder `transformAction` aufrufen oder `executeNextAction` direkt (siehe dazu Klasse `SzenarioSession` für konkretes Beispiel).

### ​Analyse Liskov-Substitution- (LSP), Interface-Segreggation- (ISP), Dependency-Inversion-Principle (DIP)

_[jeweils eine Klasse als positives und negatives Beispiel für entweder LSP oder ISP oder DIP); jeweils UML der Klasse und Begründung, warum man hier das Prinzip erfüllt/nicht erfüllt wird]_

_[Anm.: es darf nur ein Prinzip ausgewählt werden; es darf NICHT z.B. ein positives Beispiel für LSP und ein negatives Beispiel für ISP genommen werden]_

#### ​Positiv-Beispiel

#### ​Negativ-Beispiel

# ​Kapitel 4: Weitere Prinzipien

### ​Analyse GRASP: Geringe Kopplung

_[jeweils eine bis jetzt noch nicht behandelte Klasse als positives und negatives Beispiel geringer Kopplung; jeweils UML Diagramm mit zusammenspielenden Klassen, Aufgabenbeschreibung und Begründung für die Umsetzung der geringen Kopplung bzw. Beschreibung, wie die Kopplung aufgelöst werden kann]_

#### ​Positiv-Beispiel

#### ​_Negativ-Beispiel_

### ​Analyse GRASP: Hohe **Kohäsion**

_[eine Klasse als positives Beispiel hoher Kohäsion; UML Diagramm und Begründung, warum die Kohäsion hoch ist]_

### ​**Don&#39;t Repeat Yourself (DRY)**

_[ein Commit angeben, bei dem duplizierter Code/duplizierte Logik aufgelöst wurde; Code-Beispiele (vorher/nachher); begründen und Auswirkung beschreiben]_

# ​

# ​Kapitel 5: **Unit Tests**

### ​10 Unit Tests

_[Nennung von 10 Unit-Tests und Beschreibung, was getestet wird]_

| Unit Test | Beschreibung |
| ---       | --- |
| _Klasse#Methode_ |

### ​ATRIP: Automatic

_[Begründung/Erläuterung, wie &#39;Automatic&#39; realisiert wurde]_

### ​ATRIP: Thorough

_[jeweils 1 positives und negatives Beispiel zu &#39;Thorough&#39;; jeweils Code-Beispiel, Analyse und Begründung, was professionell/nicht professionell ist]_

### ​ATRIP: Professional

_[jeweils 1 positives und negatives Beispiel zu &#39;Professional&#39;; jeweils Code-Beispiel, Analyse und Begründung, was professionell/nicht professionell ist]_

### ​Code Coverage

_[Code Coverage im Projekt analysieren und begründen]_

### ​Fakes und Mocks

_[Analyse und Begründung des Einsatzes von 2 Fake/Mock-Objekten; zusätzlich jeweils UML Diagramm der Klasse]_

# ​Kapitel 6: Domain Driven Design

### ​Ubiquitous Language

_[4 Beispiele für die Ubiquitous Language; jeweils Bezeichung, Bedeutung und kurze Begründung, warum es zur Ubiquitous Language gehört]_

| **Bezeichung**       | **Bedeutung**                                                                                                                                                                        | **Begründung** |
|----------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------| --- |
| Umgebung/Environment | Eine Umgebung bzw. Environment stellt ein Umfeld für Agenten zur Verfügung. Jede Umgebung erlaubt dabei ein bestimmtes Subset an Aktionen.                                           |     |
| Agent                | Ein Agent ist ein Akteur innerhalb einer Umgebung. Der Agent kann verschiedene Aktionen ausführem. die die Umgebung auf eine bestimmte Beweise beinflussen und ihren Zustand ändern. |     |
| Policy               | Eine Policy ist prinzipiell nur ein Hinweisgeber, der einem Agenten sagt, welche Aktion in welchem Zustand wie sinnvoll ist.                                                         |     |
| Algorithmus          | Ein Algorithmus modifiziert eine Policy, damit diese bessere Ergebnisse erzielt. Der Algorithmus macht einen Agenten somit lernfähig.                                                |     |

### ​Entities

_[UML, Beschreibung und Begründung des Einsatzes einer Entity; falls keine Entity vorhanden: ausführliche Begründung, warum es keines geben kann/hier nicht sinnvoll ist]_

### ​Value Objects

_[UML, Beschreibung und Begründung des Einsatzes eines Value Objects; falls kein Value Object vorhanden: ausführliche Begründung, warum es keines geben kann/hier nicht sinnvoll ist]_

Ein häufig eingesetztes Value Object sind Objekte der Klasse `ConfigItem`.
Das ConfigItem repräsentiert ein einfaches, identitätsloses Key-Value Paar in der Config.
Ist read-Only. Sollte eine Änderung über die Repository geschehen, so wird ein neues erstellt.

![Config Value Object](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/jatsqi/ASE-Reinforcement-Learning/master/uml/valueObjectConfig.puml)


### ​Repositories

_[UML, Beschreibung und Begründung des Einsatzes eines Repositories; falls kein Repository vorhanden: ausführliche Begründung, warum es keines geben kann/hier nicht sinnvoll ist]_

Abgeleitete Klasse des Interfaces ConfigRepository sind dafür zuständig, die gespeicherten Einträge der Config zu verwalten.
Die Aufgaben sind sowohl das Einlesen als auch das Modifizieren (Hinzufügen).

![Config Repo](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/jatsqi/ASE-Reinforcement-Learning/master/uml/repositoryConfig.puml)


### ​Aggregates

_[UML, Beschreibung und Begründung des Einsatzes eines Aggregates; falls kein Aggregate vorhanden: ausführliche Begründung, warum es keines geben kann/hier nicht sinnvoll ist]_

# ​Kapitel 7: Refactoring

### ​Code Smells

#### Code-Smell 1: Duplicated Code

Commit-ID: 28d6e5b4b81c28e6eb989d5d95b44936ac9e6813

Dieser Code Smell ist während der Einführung der Klasse `ActionValueStore` aufgefallen. An zwei Stellen im Code wurde
für einen gegebenen Zustand die Aktion mit dem maximalen Value benötigt. Diese Funktionalität war davor separat an zwei
Stellen zu finden (Klasse `QLearning` + `EpislonGreedyPolicy`). Behoben durch das verschieden des Codes in eine
gemeinsame Methode in der Klasse `ActionValueStore`.

Vorher, an beiden Stellen:

```java
double highestValue = Double.MIN_VALUE;
int bestAction = 0;
double[] values = store.getActionValues(newState);

// Suche höchsten Wert
for (int i = 0; i < values.length; ++i) {
    // Wert größer als vorher?
    if (values[i] > highestValue) {
        highestValue = values[i];
        bestAction = i;
    }
}
```

Nachher, ausgelagert in externe Methode:

```java
ActionValueStore.ActionValueEntry maxEntry = store.getMaxActionValue(newState);
int bestAction = maxEntry.action();
int bestValue = maxEntry.value();
```

#### Code-Smell 2: TBD

_[jeweils 1 Code-Beispiel zu 2 Code Smells aus der Vorlesung; jeweils Code-Beispiel und einen möglichen Lösungsweg bzw. den genommen Lösungsweg beschreiben (inkl.__(Pseudo-)Code)]_

### ​2 Refactorings

_[2 unterschiedliche Refactorings aus der Vorlesung anwenden, begründen, sowie UML vorher/nachher liefern; jeweils auf die Commits verweisen]_

| **Refactoring** | **
Begründung**                                                                                                                                                                        | **
Commit** |
|-----------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------|
| Rename Method   | Methodename deutete darauf hin, dass<br/> ausschließlich das Training mit diesem Observer<br/>beobachtet werden kann<br/>Allerdings war dieser für alle Szenarien gedacht.            |499c5493af7518e04cb6c1e5c19ab92a38edae4f|
| Extract Method  | In der Klasse `RunCommand` wurde, neben einigen weiteren Änderungen, die Erstellung des Observers in eine eigene Methode ausgelagert, damit die `run()` Methode übersichtlich bleibt. |dbabe3845931cad4d9708778fd0c784a7cbe1ee8                                        |

#### Refactoring 1 (Vorher):

![Pre Rename Method](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/jatsqi/ASE-Reinforcement-Learning/master/uml/preRenameMethodRefactoring.puml)

#### Refactoring 1 (Nachher)

![Post Rename Method](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/jatsqi/ASE-Reinforcement-Learning/master/uml/postRenameMethodRefactoring.puml)

#### Refactoring 2 (Vorher):

![Post Extract Method](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/jatsqi/ASE-Reinforcement-Learning/master/uml/preExtractMethodRefactoring.puml)

#### Refactoring 2 (Nachher):

![Post Extract Method](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/jatsqi/ASE-Reinforcement-Learning/master/uml/postExtractMethodRefactoring.puml)

# ​Kapitel 8: Entwurfsmuster

_[2 unterschiedliche Entwurfsmuster aus der Vorlesung (oder nach Absprache auch andere) jeweils sinnvoll einsetzen, begründen und UML-Diagramm]_

### Entwurfsmuster 1: Factory Pattern

Wird genutzt, um z.B. konkrete Visualizer zu erstellen, die eine Policy, abhängig vom Environment, visualisieren. Da die
Logik zum Erzeugen in diesem Fall recht umfangreich ist, wird diese Logik in einer eigenen Klasse gekapselt. Somit ruft
beispielsweise eine Repository nur noch die Factory über das Interface auf und die konkrete Implementierung kümmert sich
um das Erzeugen.

![Factory Pattern](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/jatsqi/ASE-Reinforcement-Learning/master/uml/factoryPattern.puml)

### Entwurfsmuster 2: Observer

Da das Training bzw. die Evaluation eines Agenten mitunter länger dauern kann und der Nutzer über den Fortschritt
informiert werden soll, wird in diesem Projekt ein Observer eingesetzt. Der Nutzer startet ein Szenario über den Service
und spezifiziert einen Observer vom Typ `SzenarioExecutionObserver`. Der Observer wird danach in einen anderen Observer
gewrapped. Der Grund dafür ist recht simpel: Aus Sicht des UI möchte man sich nicht um das Speichern der Trainierten
Policy kümmern, sondern nur darüber informiert werden, sobald diese gespeichert wurde. Der Wrapper wartet auf den Aufruf
von `onTrainingEnd`
, speichert daraufhin den `ActionValueStore` und ruft vom gewrappten die Methode `onActionStorePersisted` auf und
übergibt die Info des gespeicherten Stores. Alle übrigen aufrufe werden ebenfalls an den gewrappten durchgeleitet. Damit
sich die `SzenarioSession` nicht um das Speichern in irgendeiner Form kümmern muss, werden auch zwei verschiedene
Observer genutzt. Die Session kümmert sich *ausschließlich* um das Training und muss gar nicht wissen, dass die Policy
später gespeichert wird, während der Service den Rest bearbeitet.

das Szenario übergeben, welche den Observer bei folgenden Ereignissen informiert:

* Vor dem Beginn des Szenarios.
* Vor jedem Schritt, der innerhalb des Szenarios ausgeführt wird.
* Nach jedem Schritt, der innerhalb des Szenarios ausgeführt wird.
* Nach dem Ende des Szenarios.

Der im UML Diagramm zu sehende Observer ist nicht der, der vom User erstellt wird.

![Observer Pattern](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/jatsqi/ASE-Reinforcement-Learning/master/uml/observerPattern.puml)