# Mankomania
![Travis Badge](https://travis-ci.org/HYPEO/Mankomania.svg?branch=master)
![Sonarcloud Badge](https://sonarcloud.io/api/project_badges/measure?project=Mankomania&metric=alert_status)

![](./android/assets/hypeo.ai?raw=true "Hypeo Game Studios")

## Description
Mankomania is a software project that was created university students. The five contributors founded the group **Hypeo Game Studios**.
Mankomania, their first product, is an Android application for Android mobile phones. 

!Show a screenshot of the start-screen!

## The Game

### Origin
The game Mankomania already exists as a board game and was used as a template for this mobile application.

### Concept
The original game concept has been included but also developed further.

The credo behind the original game is "How to blow a Million Euro". For that purpose, a maximum of four players try to get rid of their as fast as possible. The player who is the first to lose all his money is the winner of the game.

We adopted the original game's turn-based concept and extended it by the following features:

### Features
* Accelerometer: Is used for the cube functionality
* Minigames

	+ Build hotels
	+ Horse race
	+ Lottery
	+ Roulette
	+ Slot machine

## Technologies
The following techniques, technologies and tools were used:

### Build management tool
Gradle

### Coding Language
Java v1.8

### Graphics Framework
LibGDX

### Network Framework
Kryonet

### Requirements
Android version 16

## Installation
The game is very easy to install, simply follow the instructions.

### Clone
Clone the `master` branch from github.

### Gradle build
Run gradle build in project root directory by `gradlew clean && gradlew build`.

### Tests
Tests are going to execute automatically when `gradlew build` is executed.
But test execution can be triggered separatly by `gradlew test`.
Or run a specified test by `gradlew -Dtest.single=PlayerNTTest`.

### Contributing
Please read [CONTRIBUTING.md](CONTRIBUTING.md) carefully befor start contributing process.

0. Change into `develop` branch by `git checkout develop`
1. Fork a new feature branch from develop branch by `git checkout -b feature/my-new-feature`
2. Make your changes
3. Commit your changes by `git commit -am "Add some feature"`
4. Push to the branch by `git push origin feature/my-new-feature`
5. Submit a pull request on github page

## Developers

* Christiane Bergner
* Florian Biermann
* Manuel Egger
* Christian Motz
* Marc Pichler

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

