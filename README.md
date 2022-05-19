# ASE Reinforcement Learning CLI

[![Build](https://github.com/jatsqi/ASE-Reinforcement-Learning/actions/workflows/build.yml/badge.svg)](https://github.com/jatsqi/ASE-Reinforcement-Learning/actions/workflows/build.yml)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=jatsqi_ASE-Reinforcement-Learning&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=jatsqi_ASE-Reinforcement-Learning)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=jatsqi_ASE-Reinforcement-Learning&metric=bugs)](https://sonarcloud.io/summary/new_code?id=jatsqi_ASE-Reinforcement-Learning)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=jatsqi_ASE-Reinforcement-Learning&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=jatsqi_ASE-Reinforcement-Learning)

## Algorithmen

Momentan sind folgende Algorithmen implementiert:

* Q-Learning

## Environments

### Grid World

[![Test](https://www.researchgate.net/profile/Alexey-Melnikov-3/publication/262526038/figure/fig2/AS:296823253159943@1447779586992/The-grid-world-task-The-goal-of-the-game-is-to-find-the-star-At-the-beginning-of-each_W640.jpg)]

Das Grid-World Environment besteht aus einem schachbrettartigen Feld, wie oben im Bild zu sehen.
Der Agent hat das Ziel, von seinem Startpunkt aus zum Ziel zu gelangen, um eine Belohnung zu erhalten.

Umgewandelt in die repräsentation, die das CLI versteht, sieht die obere Abbildung folgendermaßen aus:
```
000000021
002000020
302000020
002000000
000002000
000000000
```

In der folgenden Tabelle sind alle möglichen Zustände mit ihrer Bedeutung aufgelistet:

| Nummer in Textdatei | Zustand in Umgebung                               |
|---------------------|---------------------------------------------------|
| 0                   | Normales Feld, welches der Agent betreten kann    |
| 1                   | Zielfeld, gibt Belohnung von +5                   |
| 2                   | Hindernis                                         |
| 3                   | Spawnpunkt                                        |
| 4                   | Gefährliches Feld, Agent erhält Bestrafung von -5 |

Ausprobiert werden kann das Szenario mittels folgendem Befehl, wobei das obrige Grid in der Datei `datei.grid` gespeichert sein muss.

```shell
<jar> run --agent 2d-moving-agent --environment grid-world --steps 5000000 --envopts from=datei.grid
```