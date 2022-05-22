# Programmentwurf

# ASE Reinforcement Learning

Name: Quast, Johannes
Martrikelnummer: 6897847

Abgabedatum: [DATUM]

## _Allgemeine Anmerkungen:_

- _es darf nicht auf andere Kapitel als Leistungsnachweis verwiesen werden (z.B. in der Form &quot;XY wurde schon in Kapitel 2 behandelt, daher hier keine Ausführung&quot;)_
- _alles muss in UTF-8 codiert sein (Text und Code)_
- _sollten mündliche Aussagen den schriftlichen Aufgaben widersprechen, gelten die schriftlichen Aufgaben (ggf. an Anpassung der schriftlichen Aufgaben erinnern!)_
- _alles muss ins Repository (Code, Ausarbeitung und alles was damit zusammenhängt)_
- _die Beispiele sollten wenn möglich vom aktuellen Stand genommen werden_
    - _finden sich dort keine entsprechenden Beispiele, dürfen auch ältere Commits unter Verweis auf den Commit verwendet werden_
    - _Ausnahme: beim Kapitel &quot;Refactoring&quot; darf von vorne herein aus allen Ständen frei gewählt werden (mit Verweis auf den entsprechenden Commit)_
- _falls verlangte Negativ-Beispiele nicht vorhanden sind, müssen entsprechend mehr Positiv-Beispiele gebracht werden_
    - _Achtung: werden im Code entsprechende Negativ-Beispiele gefunden, gibt es keine Punkte für die zusätzlichen Positiv-Beispiele_
    - _Beispiele_
        - &quot;_Nennen Sie jeweils eine Klasse, die das SRP einhält bzw. verletzt.&quot;_
            - _Antwort: Es gibt keine Klasse, die SRP verletzt, daher hier 2 Klassen, die SRP einhalten: [Klasse 1], [Klasse 2]_
            - _Bewertung: falls im Code tatsächlich keine Klasse das SRP verletzt: volle Punktzahl ODER falls im Code mind. eine Klasse SRP verletzt: halbe Punktzahl_
- _verlangte Positiv-Beispiele müssen gebracht werden_
- _Code-Beispiel = Code in das Dokument kopieren_

# ​Kapitel 1: **Einführung**

### ​Übersicht über die Applikation

Das Projekt "ASE Reinforcement Learning" soll ein einfaches CLI zur Verfügung stellen, mit dem jeder typische Beispiele
(aktuell nur eins) des Reinforcement Learnings ausprobieren und über einige Parameter anpassen kann.
Das Ergebnis des Trainings wird als CSV zur Verfügung gestellt und kann anschließend für andere Projekte genutzt werden.
Auch können, basierend auf bestehenden Beispielen, eigene Beispiele erstellt werden. 

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

​Kapitel 2: Clean Archite **cture**

### ​Was ist Clean Architecture?

_[allgemeine Beschreibung der Clean Architecture in eigenen Worten]_

### ​Analyse der Dependency Rule

_[(1 Klasse, die die Dependency Rule einhält und eine Klasse, die die Dependency Rule verletzt); jeweils UML der Klasse und Analyse der Abhängigkeiten in beide Richtungen (d.h., von wem hängt die Klasse ab und wer hängt von der Klasse ab) in Bezug auf die Dependency Rule]_

#### ​Positiv-Beispiel: Dependency Rule

#### ​Negativ-Beispiel: Dependency Rule

### ​ **Analyse der Schichten**

_[jeweils 1 Klasse zu 2 unterschiedlichen Schichten der Clean-Architecture: jeweils UML der Klasse (ggf. auch zusammenspielenden Klassen), Beschreibung der Aufgabe, Einordnung mit Begründung in die Clean-Architecture]_

#### ​Schicht: [Name]

#### ​Schicht: [Name]

# ​Kapitel 3: SOLID

### ​Analyse Single-Responsibility-Principle (**SRP)**

_[jeweils eine Klasse als positives und negatives Beispiel für SRP; jeweils UML der Klasse und Beschreibung der Aufgabe bzw. der Aufgaben und möglicher Lösungsweg des Negativ-Beispiels (inkl. UML)]_

#### ​Positiv-Beispiel

#### ​Negativ-Beispiel

### ​Analyse Open-Closed-Principle (OCP)

_[jeweils eine Klasse als positives und negatives Beispiel für OCP; jeweils UML der Klasse und Analyse mit Begründung, warum das OCP erfüllt/nicht erfüllt wurde – falls erfüllt: warum hier sinnvoll/welches Problem gab es? Falls nicht erfüllt: wie könnte man es lösen (inkl. UML)?]_

#### ​Positiv-Beispiel

#### ​_Negativ-Beispiel_

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

| **Bezeichung** | **Bedeutung** | **Begründung** |
| --- | --- | --- |

### ​Entities

_[UML, Beschreibung und Begründung des Einsatzes einer Entity; falls keine Entity vorhanden: ausführliche Begründung, warum es keines geben kann/hier nicht sinnvoll ist]_

### ​Value Objects

_[UML, Beschreibung und Begründung des Einsatzes eines Value Objects; falls kein Value Object vorhanden: ausführliche Begründung, warum es keines geben kann/hier nicht sinnvoll ist]_

### ​Repositories

_[UML, Beschreibung und Begründung des Einsatzes eines Repositories; falls kein Repository vorhanden: ausführliche Begründung, warum es keines geben kann/hier nicht sinnvoll ist]_

### ​Aggregates

_[UML, Beschreibung und Begründung des Einsatzes eines Aggregates; falls kein Aggregate vorhanden: ausführliche Begründung, warum es keines geben kann/hier nicht sinnvoll ist]_

# ​Kapitel 7: Refactoring

### ​Code Smells

#### Code-Smell 1: Duplicated Code

Commit-ID: 28d6e5b4b81c28e6eb989d5d95b44936ac9e6813

Dieser Code Smell ist während der Einführung der Klasse `ActionValueStore` aufgefallen.
An zwei Stellen im Code wurde für einen gegebenen Zustand die Aktion mit dem maximalen Value benötigt.
Diese Funktionalität war davor separat an zwei Stellen zu finden (Klasse `QLearning` + `EpislonGreedyPolicy`).
Behoben durch das verschieden des Codes in eine gemeinsame Methode in der Klasse `ActionValueStore`.

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

| **Refactoring** | **Begründung**                                                                                                                                                                        | **Commit** |
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

Wird genutzt, um z.B. konkrete Visualizer zu erstellen, die eine Policy, abhängig vom Environment, visualisieren.
Da die Logik zum Erzeugen in diesem Fall recht umfangreich ist, wird diese Logik in einer eigenen Klasse gekapselt.
Somit ruft beispielsweise eine Repository nur noch die Factory über das Interface auf und die konkrete Implementierung kümmert
sich um das Erzeugen.

![Factory Pattern](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/jatsqi/ASE-Reinforcement-Learning/master/uml/factoryPattern.puml)

### Entwurfsmuster 2: Observer

Da das Training bzw. die Evaluation eines Agenten mitunter länger dauern kann und der Nutzer über den Fortschritt
informiert werden soll, wird in diesem Projekt ein Observer eingesetzt.
Der Nutzer startet ein Szenario über den Service und spezifiziert einen Observer vom Typ `SzenarioExecutionObserver`. 
Der Observer wird danach in einen anderen Observer gewrapped.
Der Grund dafür ist recht simpel: Aus Sicht des UI möchte man sich nicht um das Speichern der Trainierten Policy kümmern,
sondern nur darüber informiert werden, sobald diese gespeichert wurde. Der Wrapper wartet auf den Aufruf von `onTrainingEnd`
, speichert daraufhin den `ActionValueStore` und ruft vom gewrappten die Methode `onActionStorePersisted` auf und übergibt die
Info des gespeicherten Stores. Alle übrigen aufrufe werden ebenfalls an den gewrappten durchgeleitet.
Damit sich die `SzenarioSession` nicht um das Speichern in irgendeiner Form kümmern muss, werden auch zwei verschiedene Observer genutzt.
Die Session kümmert sich *ausschließlich* um das Training und muss gar nicht wissen, dass die Policy später gespeichert wird, während der Service den Rest bearbeitet.

das Szenario übergeben, welche den Observer bei folgenden Ereignissen informiert:

* Vor dem Beginn des Szenarios.
* Vor jedem Schritt, der innerhalb des Szenarios ausgeführt wird.
* Nach jedem Schritt, der innerhalb des Szenarios ausgeführt wird.
* Nach dem Ende des Szenarios.

Der im UML Diagramm zu sehende Observer ist nicht der, der vom User erstellt wird.


![Observer Pattern](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/jatsqi/ASE-Reinforcement-Learning/master/uml/observerPattern.puml)