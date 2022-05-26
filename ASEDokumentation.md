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

#### Positiv-Beispiel

![DI Config](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/jatsqi/ASE-Reinforcement-Learning/master/uml/dependencyRulePositiv.puml)

Wie im UML Diagramm zu sehen ist, ist die Klasse `ConfigServiceImpl` aus dem Application-Layer nicht von der konkreten Implementierung der
Repository `PropertiesConfigRepository`, welche in der Plugin-Schicht implementiert ist, abhängig, sondern von dem deklarierten Interface `ConfigRepository`, welches
eine einheitliche und technologisch unabhängige Schnittstelle definiert.
Somit kann, in diesem Fall der Service, mit beliebigen Ausprägungen der `ConfigRepository` genutzt werden.
Würde `ConfigServiceImpl` die konkrete Implementierung nutzen, wäre zusötzlich die Depdendency Rule verletzt.

#### ​Negativ-Beispiel

![DI Exec Service](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/jatsqi/ASE-Reinforcement-Learning/master/uml/dependencyInversionNegative.puml)

Die Klasse `SzenarioSession`, die die Logik für das Ausführen des Trainings bzw. der Evaluation beinhaltet,
ist eine direkte Abhängigkeit des `ExecutionService`.
Sollte das Verhalten, wie das Training durchlaufen werden soll, später angepasst werden, muss zuerst die Struktur umgebaut werden.
In diesem einfachen Projekt ist dies nicht der Fall, weswegen es auf diese Weise gelöst wurde.
Gelöst werden könnte dies genauso wie bei den Repositories, indem ein Interface eingeführt wird und eine äußere Schicht
sich um die Details kümmert. Für diesen konkreten Fall wäre unter Umständen eine weitere Factory nötig, damit der Service die
verschiedenen Ausprägungen der Sessions auch erstellen kann bzw. die Factory beauftragen kann, diese zu erstellen.

# ​Kapitel 4: Weitere Prinzipien

### ​Analyse GRASP: Geringe Kopplung

_[jeweils eine bis jetzt noch nicht behandelte Klasse als positives und negatives Beispiel geringer Kopplung; jeweils UML Diagramm mit zusammenspielenden Klassen, Aufgabenbeschreibung und Begründung für die Umsetzung der geringen Kopplung bzw. Beschreibung, wie die Kopplung aufgelöst werden kann]_

#### Positiv-Beispiel

![Low Coupling Positive](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/jatsqi/ASE-Reinforcement-Learning/master/uml/lowCouplingPositve.puml)

Die Klasse `InMemoryAgentRepository` ist durchaus ein gutes Beispiel für lose Kopplung.
Um ihre Funktionalität zu erfüllen sind von außen betrachtet nur ein Service und eine weitere Factory relevant.
Alle Methodenaufrufe auf die beiden Abhängigkeiten innerhalb von `InMemoryAgentRepository` geschehen über das jeweilige Interface (virtualler Methodenaufruf über Interface),
wie im UML Diagramm dargestellt.
Die konkreten Ausprägungen der Interfaces sind sehr leicht austauschbar und auch in den Tests dadurch leicht mockbar.
Somit kann die Repository auch sehr isoliert getestet werden.

#### Negativ-Beispiel

![High Coupling Negative](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/jatsqi/ASE-Reinforcement-Learning/master/uml/lowCouplingNegative.puml)

Ein negativ-Beispiel für geringe Kopplung zeigt sich der Beziehung zwischen den Klassen `Szenario` und `SzenarioSession`.
Die Session weist eine direkte Abhängigkeit zu Objekten der Klasse `Szenario` auf.
Sollte sich der Aufbau eines Szenarios ändern, z.B. das anstatt den konkreten Descriptoren nun die Namen dieser gespeichert werden (z.B. statt AgentDescriptor nun den Namen des Agenten),
könnte es zu Probleme kommen, wenn die SzenarioSession diesen benötigt.
Die Klasse müsste sich nun selbst darum kümmern, wie es an den Descriptor kommt.
**Kurz:** Selbst kleinere Änderungen in `Szenario` können ebenfalls umfangreichere Änderungen in `Szenario` bedeuten. Die Module wären
sind länger wirklich isoliert voneinander.
Besser wäre in diesem Fall ein Interface, welches für dieses Beispiel eine Methode `getAgentDescriptor()` anbieten könnte.
Konkrete Ausprägungen von `Szenario` müsste sich dann damit beschäftigen, wie sie an den Descriptor gelangen. 

### ​Analyse GRASP: Hohe **Kohäsion**

_[eine Klasse als positives Beispiel hoher Kohäsion; UML Diagramm und Begründung, warum die Kohäsion hoch ist]_

![High Cohesion](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/jatsqi/ASE-Reinforcement-Learning/master/uml/highCohesion.puml)

Als Beispiel für hohe Kohäsion wurde hier die konkrete Aisprägung eines Environments ausgewählt: Das `KArmedBanditEnvironment`. 
Das Environment besitzt prinzipiell, wie auch im UML Diagramm dargestellt, keine weiteren Abhängigkeiten nach Außen.
Alle Methoden und Attribute, die die Klasse besitzt, beziehen sich einzug und alleine auf *dieses konkrete* Environment und sind
alle unabdingbar, damit dieses seine korrekte Funktionalität gewährleisten kann.

### ​**Don&#39;t Repeat Yourself (DRY)**

_[ein Commit angeben, bei dem duplizierter Code/duplizierte Logik aufgelöst wurde; Code-Beispiele (vorher/nachher); begründen und Auswirkung beschreiben]_

# ​

# ​Kapitel 5: **Unit Tests**

### ​10 Unit Tests

_[Nennung von 10 Unit-Tests und Beschreibung, was getestet wird]_

In der folgenden Tabelle ist eine kleine Auswahl aus unterschiedelichen Unit-Tests zusammengefasst:

| Unit Test                                                                 | Beschreibung                                                                                                                                                                                                |
|---------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| ExecutionServiceTest#startSzenarioShouldCallAllObserverMethods            | Testet, ob die beiden Methoden der Klasse `ExecutionServiceImpl` alle Observer Methoden häufig gebug aufrufen bzw. ob die Anzahl der Aufrufe stimmen.                                                       |
| ExecutionServiceTest#startEvaluationShouldNotCallPersistStore             | Testet, ob die Methode `startEvaulation` den evaulierten ActionValueStore auch tatsächlich **nicht** speichert.                                                                                             |
| ExecutionServiceTest#startSzenarioWithUnknownDescriptorsShouldThrow       | Testet, ob die korrekten Fehlermeldungen als Exception geworfen werden, sofern Eingaben nicht korrekt sind.                                                                                                 |
| EnvironmentMapperTest#dtoAttributesShouldHaveSameValue                    | Testet, ob das Mapping zwischen dem Domain-Objekt `EnvironmentDescriptor` und dem DTO `EnvironmentDescriptorDto` korrekt funktioniert.                                                                      |
| ActionValueStoreTest#getMaxActionValueShouldReturnMaximumEntryOfState     | Testet, ob die `getMaxActionValue` der Klasse `ActionValueStore` immer korrekt die aktuell Beste Aktion für den übergebenen Zustand findet. **Diese Funktion bildet den Grundstein für viele Algorithmen**. |
| GridWorldEnvironmentTest#environmentShouldNotBeAbleToMoveToForbiddenState | Testet, ob das Environment in einen Zustand tranferiert werden kann, in dem der Agent auf einem verbotenen Zustand steht, sofern er sich bewegt.                                                            |
| GridWorldEnvironmentTest#positionShouldResetOnTerminalOrBombState         | Testet, ob die Umgebung in einen Zustand tranferiert werden kann, in dem der Agent das Grid verlässt.                                                                                                       |
| GridWorldEnvironmentTest#actionsShouldMoveAgent                           | Testet, ob die Umgebung für alle unterstützten Aktionen den korrekten Zustand annimmt.                                                                                                                      |
| KArmedBanditEnvironmentTest#environmentMoveActionShouldNotLeaveBoundary   | Testet, ob die Umgebung mit Bewegungsbefehlen nach rechts/links in einen Zustand überführt werden kann, der außerhalb der Anzahl an zu verfügung stehenden Bandits liegt.                                   |
| KArmedBanditEnvironmentTest#getRewardShouldMatchPrecomputedArray          | Testet, ob die `getReward` Methode des `KArmedBanditEnvironment` je nach gezogenem Hebel den korrekt Reward zurückgibt.                                                                                     |

### ​ATRIP: Automatic

_[Begründung/Erläuterung, wie &#39;Automatic&#39; realisiert wurde]_

**Automatic** wurde über die Testing-Bibliothek JUnit realisiert, die automatisch alle Testklassen sucht und alle darin befindlichen Tests ausführt.
Die Tests selber können über `mvn test` automatisch ausgeführt werden und der Nutzer wird entsprechend benachrichtigt, sofern Tests fehlgeschlagen sind.
Über GitHub Action können zusätzlich, nach jedem Commit, die Tests ausgeführt werden. So ist gewährleistet, dass bei Änderungen schnell erkannt werden kann, dass
die Änderungen eventuell unerwünschte Effekte hatten.

### ​ATRIP: Thorough

_[jeweils 1 positives und negatives Beispiel zu &#39;Thorough&#39;; jeweils Code-Beispiel, Analyse und Begründung, was professionell/nicht professionell ist]_

#### Positiv Thorough:

#### Negativ Thorough:

### ​ATRIP: Professional

_[jeweils 1 positives und negatives Beispiel zu &#39;Professional&#39;; jeweils Code-Beispiel, Analyse und Begründung, was professionell/nicht professionell ist]_

#### Professional Positiv:
```java
@Test
void actionsShouldTransitionEnvironmentToNewState() {
    environment.executeAction(Action.MOVE_X_DOWN, 1);
    assertEquals(0, environment.getCurrentState());

    environment.executeAction(Action.MOVE_Y_UP, 1);
    assertEquals(2, environment.getCurrentState());

    environment.executeAction(Action.MOVE_Y_DOWN, 1);
    assertEquals(0, environment.getCurrentState());

    environment.executeAction(Action.MOVE_X_UP, 1);
    assertEquals(1, environment.getCurrentState());

    environment.executeAction(Action.DO_NOTHING, 1);
    assertEquals(1, environment.getCurrentState());
}
```

Positiv an diesem Beispiel ist der klare Name des Tests `actionsShouldTransitionEnvironmentToNewState`.
Sobald man den Namen liest und die Zusammenhänge in der Domäne verstanden hat, sollte klar sein, dass hier
die verschiedenen Aktionen getestet werden, die das Environment unterstützt.
Als Konsequenz auf jede Aktion geht die Umgebung in einen neuen Zustand über, den es zu prüfen gilt.
Genutzt werden wohlbekannte Assertions von JUnit wie z.B. `assertEquals`, in dem der erwartete Zustand mit dem aktuellen
Zustand der Umgang nach der Aktion verglichen wird.

#### Professional Negativ:

Ein unter Umständen wenig problematisches, allerdings dennoch vorhandene Negativbeispiel ist ein klassisches Beispiel
der Code-Duplication (hier an zwei Beispielen, taucht allerdings noch öfters auf):

_GreedyPolicy.java_

```java
@BeforeEach
void prepare() {
    ActionValueStore store = new ActionValueStore(
            new double[][]{
                    { 0.0, 1.0, 2.0 },
                    { 0.0, 3.0, 1.0 },
                    { -1.0, -2.0, -4.0 }
            }
    );

    policy = new GreedyPolicy(store, new RLSettings(
            0.0, 0.0, 1000, 0.0
    ));
}
```

_PolicyTest.java_

```java
@BeforeEach
void prepare() {
    store = new ActionValueStore(new double[][]{
            { 0.0, 1.0, 2.0 },
            { 0.0, 3.0, 1.0 },
            { -1.0, -2.0, -4.0 }
    });

    settings = new RLSettings(
            0.0, 0.0, 0.0, 0.0
    );

    policy = new FakePolicy(store, settings);
}
```

In beiden Beispielen werden sehr ähnliche Code-Abschnitte genutzt, vor allem wenn es um das Initialisieren
des `RLSettings` Objektes geht.
Auch wird zwei bzw. mehrmals derselbe oder ein ähnlicher ActionValueStore genutzt.
Gelöst werden könnte dies über eine separate Klasse mit statischen Methoden, die solche "Default" Objekte bereitstellt (z.B. eine leeres `RLSettings` Objekt).
Auch könnten unter Umständen das _Builder_-Pattern genutzt werden, um die Erstellung von solchen Objekten abzukürzen, damit diese
nicht mehr Platz einnehmen als nötig.

### ​Code Coverage

_[Code Coverage im Projekt analysieren und begründen]_

In der Folgenden Analyse der Code-Coverage sind nur die Module des tatsächlichen Reinforcement Learning Projektes
beinhaltet. Das Utils Modul, welches den CLI parser und die Dependency Injection zur Verfügung
stellt wurde bewusst davon ausgenommen, da dieses nicht wirklich Logik für das Reinforcement Learning ansich
bereitstellt und "nur" als Exkurs zum Lernen prgrammiert wurde.
Dennoch sind selbstverständlich einige Aspekte dieses Moduls getestet, allem voran 
die zahlreichen Funktionen, die die Reflection durchführen.
Auch ist der `InjectionContext` selbst mit Tests versehen, um das Mapping der Interfaces auf konkrete Klassen zu testen.

| Modul/Layer | Coverage | Begründung                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
|-------------|----------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Domain      | ca. 60%  | Die Tests decken alle wichtigen Kernfunktionalitäten ab, d.h. die Implementierten Environments, Algorithmen sowie verschiedenen Policy-Varianten. Kurz: Alles, was Logik beinhaltet und Funktionalität nach außen bereitstellt. Teilweise nicht durch Tests abgedeckt sind die Factories, die die Domain-Objekte erstellen.                                                                                                                                                                                                                                                                                                                                                                                                 |
| Applikation | ca. 84%  | Die einzigen Services im Applikationslayer, die tatsächlich wichtige Logik beinhalten, sind der `ExecutionService` und der `ConfigService`  bzw. deren Implementierung. Beide Services werden entsprechend soweit wie möglich mit gemockten Dependencies isoliert getestet. Die übrigen Services wie z.B. `AgentService` oder `EnvironmentService` reichen alle Calls momentan 1 zu 1 an die Repository weiter. Sie existieren aus dem Grund, dass wenn die Services später erweitert werden sollen, nicht erst in anderen Klassen auf einen Service gewechselt werden muss.                                                                                                                                                |
| Adapters    | ca. 60%  | In der Adapter-Schicht werden momentan nur die wichtigsten Klassen getestet, welche im Moment die Mapper sind. Diese mappen ein bestimmtes Domain-Objekt auf den entsprechenden DTO. Zusätzlich wird für die `ExecutionServiceFacade` getestet, ob die `startTraining` bzw. `startEvaluation` Methoden die richtige Methode im gemockten Service aufrufen. Die übrigenden Fassaden sind momentan ungetestet, da diese ausschließlich den entsprechenden Mapper aufrufen und keine testenswerte Logik beinhalten.  |
| Plugin      | ca. 15%  | Das aktuell am wenigsten getestete Modul ist das Plugin-Modul, welches das CLI beinhaltet. Auch hier wird im Moment nur für den `RunCommand` getestet, ob je nach Parameter die richtige Methode in der `ExecutionServiceFacade` aufgerufen wird. Dies ist sehr wichtig, da diese beiden Methode den Eintrittspunkt für die gesamte Funktionalität darstellen. Alle übrigen Commands printen die DTOs eins zu eins in die Konsole. Die genaue Ausgabe in der Konsole zu testen erschien nicht sinnvoll bzw. das Aufwand-Nutzen Verhätnis ist hier nicht wirklich gegeben.                                                                                                                                                                                                                                                                                                                                                             |

### ​Fakes und Mocks

_[Analyse und Begründung des Einsatzes von 2 Fake/Mock-Objekten; zusätzlich jeweils UML Diagramm der Klasse]_

#### Mock 1:

![Mock Environment](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/jatsqi/ASE-Reinforcement-Learning/master/uml/mockEnvironment.puml)

Ein Interface, welches häufig gemockt wird, damit keine konkrete Implementierung erforderlich ist, ist das Interface `Environment`.
Den Gettern werden dabei spezifische Rückgabewerte zugewiesen, die teilweise von den übergebenen Parametern an die Methoden abhängig sind.
Zusätzlich dazu ist das Mocken dieses Interfaces recht einfach, da es sehr simpel aufgebaut ist und keine komplizierten Eingaben bzw. Ausgaben besitzt.
Beispielsweise wird dieser Mock in der Klasse `SimpleAgentFactoryTest` genutzt, um die `SimpleAgentFactory` zu testen.
Für die Funktionalität ist zunächst kein konkretes Environment erforderlich, allerdings sollte es, auch im Zuge späterer Erweiterungen auch nicht
NULL sein.

#### Mock 2:

![Mock Observer](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/jatsqi/ASE-Reinforcement-Learning/master/uml/mockObserver.puml)

Ein weiteres gemocktes Interface ist `SzenarioExecutionObserver` welches in der Klasse `ExecutionServiceTest` dazu genutzt wird genau zu überprüfen,
wie oft die verschiedenen Methoden aufgerufen werden.
Beispielsweise sollte die Methoden `onSzenarioStart` und `onSzenarioEnd`, per Definition, nur einmal aufgerufen werden.
Genauso verhält es sich mit den Methoden `preSzenarioStep` und `postSzenarioStep`, die so oft wie die Anzahl der Szenarioschritte aufgerufen werden sollten. 
Mockito bietet dafür die Methoden `verify()` und `times()` an, mit denen sich die genaue Anzahl der Aufrufe ermitteln lässt, wie unten am Codebeispiel zu erkennen ist.

```java
 @Test
void startSzenarioShouldCallAllObserverMethods() throws StartSzenarioException {
    executionService.startTraining(
            "best-agent",
            "best-environment",
            "",
            10,
            0,
            Optional.of(observer));

    verify(observer, times(1)).onSzenarioStart(any());
    verify(observer, times(1)).onSzenarioEnd(any(), anyDouble());
    verify(observer, times(10)).preSzenarioStep(any(), anyLong(), anyDouble());
    verify(observer, times(10)).postSzenarioStep(any(), anyLong(), anyDouble());
    verify(observer, times(1)).onActionStorePersisted(any());
}
```

# ​Kapitel 6: Domain Driven Design

### ​Ubiquitous Language

_[4 Beispiele für die Ubiquitous Language; jeweils Bezeichung, Bedeutung und kurze Begründung, warum es zur Ubiquitous Language gehört]_

| **Bezeichung**       | **Bedeutung**                                                                                                                                                                                                                                                                                                                                                                                                                      | **Begründung** |
|----------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------| --- |
| Umgebung/Environment | Eine Umgebung bzw. Environment stellt ein Umfeld für Agenten zur Verfügung. Jede Umgebung erlaubt dabei ein bestimmtes Subset an Aktionen. Zur Vereinfachung sind die Environments in diesem Projekt eine Art Controller, der zugleich den Zustand über z.B. die aktuelle Position des Agenten beinhaltet. In klassischen Implementierung würde dies eine separate Engine tun, was allerdings das Projekt nur verkomplizieren würde. |     |
| Agent                | Ein Agent ist ein Akteur innerhalb einer Umgebung. Der Agent kann verschiedene Aktionen ausführem. die die Umgebung auf eine bestimmte Beweise beinflussen und ihren Zustand ändern.                                                                                                                                                                                                                                               |     |
| Policy               | Eine Policy ist prinzipiell nur ein Hinweisgeber, der einem Agenten sagt, welche Aktion in welchem Zustand wie sinnvoll ist.                                                                                                                                                                                                                                                                                                       |     |
| Algorithmus          | Ein Algorithmus modifiziert eine Policy, damit diese bessere Ergebnisse erzielt. Der Algorithmus macht einen Agenten somit lernfähig.                                                                                                                                                                                                                                                                                              |     |
| Grid-World           | Eine Umgebung, die aus einem 2D-Feld besteht. Es existieren verschiedene Felder die unterschiedliche Belohnungen geben.                                                                                                                                                                                                                                                                                                            |     |
| K-Armed-Bandit       | Ein Armed Bandit, auch einarmiger Bandit oder einfach nur Spieleautomat (bei dem an einem Hebel gezogen wird) genannt, ist eine Umgebung bestehend aus K Spieleautomaten. Jeder Automat schüttet eine unterschiedliche Belohnung beim Ziehen des Hebels aus.                                                                                                                                                                       |     |

### ​Entities

_[UML, Beschreibung und Begründung des Einsatzes einer Entity; falls keine Entity vorhanden: ausführliche Begründung, warum es keines geben kann/hier nicht sinnvoll ist]_

Innerhalb dieses Projektes gibt es leider keine Entities im klassischen Sinne, die einen typischen Lifecycle aufweisen.
Die einzigen Objekte die eine wirkliche "Identität" besitzen und auch über diese Abgerufen bzw. abgespeichert werden können,
sind die Descriptoren, wie am Beispiel der Klasse `AgentDescriptor` dargestellt.
Alle Descriptoren beinhalten Metadaten über einen anderen Typ.
In diesem Fall beschreibt ein `AgentDescriptor`, dass ein bestimmter Agent mit dem Namen `name` existiert und dieser 
z.B. `actionSpace` Aktionen zur Verfügung hat. Dadurch muss keine konreket Instanz eines Agenten vorliegen, um seine Eigenschaften zu beschreiben.
Alle Descriptoren sind über einen Namen eindeutig identifizierbar und können über diesen durch die entsprechende Repository
abgerufen werden.
Da die Descriptoren über die Laufzeit des Programms nicht gelöscht werden können, exisitieren diese quasi "ewig".

![Agent Descriptor Entity](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/jatsqi/ASE-Reinforcement-Learning/master/uml/entityAgentDescriptor.puml)

### ​Value Objects

_[UML, Beschreibung und Begründung des Einsatzes eines Value Objects; falls kein Value Object vorhanden: ausführliche Begründung, warum es keines geben kann/hier nicht sinnvoll ist]_

Ein häufig eingesetztes Value Object sind Objekte der Klasse `RLSettings`.
Objekte der Klassen repräsentieren eine einfache, identitätslose Instanz, welche globale Einstellungen zum Reinforcement Learning beinhaltet.
Jedes Objekt ist Read-Only. Sollte eine Änderung nötig sein, so wird ein neues erstellt.

![Settings Value Object](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/jatsqi/ASE-Reinforcement-Learning/master/uml/valueObjectRLSettings.puml)


### ​Repositories

_[UML, Beschreibung und Begründung des Einsatzes eines Repositories; falls kein Repository vorhanden: ausführliche Begründung, warum es keines geben kann/hier nicht sinnvoll ist]_

Abgeleitete Klasse des Interfaces `ConfigRepository` sind dafür zuständig, die gespeicherten Einträge der Config zu verwalten.
Die Aufgaben sind sowohl das Einlesen als auch das Modifizieren (Hinzufügen).
Da für alle Schichten die konkrete Herkunft der Config-Items egal ist, wird dieses unwichtige Detail über das Interface abstrahiert.

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

| **Refactoring** | **Begründung**| **Commit** |
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

![Observer Pattern](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/jatsqi/ASE-Reinforcement-Learning/master/uml/observerPattern.puml)