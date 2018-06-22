# Mankomania
![Travis Badge](https://travis-ci.org/HYPEO/Mankomania.svg?branch=master)
![Sonarcloud Badge](https://sonarcloud.io/api/project_badges/measure?project=Mankomania&metric=alert_status)

!Show Logo of Hypeo Game Studios!

## Description

Mankomania is a software project on university that was created by students.

It is an Android application for Android mobile phones. 

!Show a screenshot of the start-screen!

## The Game

### Origin
The game Mankomania exists as board game and was the template for this mobile app.

### Concept
The original game concept is totally covered: "How to blow a Million Euro". At least four player have to play several rounds till the first player has blown all his money amount. This player wins the game.

### Features
The game contains features, that extends the origin to use sensors of modern smart phones:
* Accelerometer: Is used for the cube functionality
* Minigames

	+ Build hotels
	+ Horse race
	+ Lottery
	+ Roulette
	+ Slot machine

## Technologies

### Build management tool
Gradle

### Coding Language
Java

### Graphics Framework
LibGDX

### Network Framework
Kryonet

### Requirements

Android version 16

## Installation

### Clone
Clone the master branch from github.

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

