# Mankomania
![Travis Badge](https://travis-ci.org/HYPEO/Mankomania.svg?branch=master)
![Sonarcloud Badge](https://sonarcloud.io/api/project_badges/measure?project=Mankomania&metric=alert_status)


## Description
Mankomania is a software project that was created by university students.

![](./android/assets/Hypeo_Logo_small.png?raw=true "Hypeo Game Studios")

The five contributors founded the group **Hypeo Game Studios**.

![](./android/assets/Mankomania_MenuStage.png?raw=true "Mankomania")

Mankomania, their first product, is an Android application for Android mobile phones. 

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
[Gradle Build Tool](https://gradle.org/)

### Coding Language
[Java SE Development Kit 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

### Graphics Framework
[LibGDX](https://libgdx.badlogicgames.com/)

### Network Framework
[Kryonet - TCP/UDP client/server library for Java](https://github.com/EsotericSoftware/kryonet)

### Requirements
* Minimum: [Android SDK Version 16](https://developer.android.com/about/versions/android-4.1)
* Built for: [Android SDK Version 25](https://developer.android.com/studio/releases/platforms)

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

1. Change into `develop` branch by `git checkout develop`
2. Fork a new feature branch from develop branch by `git checkout -b feature/my-new-feature`
3. Make your changes
4. Commit your changes by `git commit -am "Add some feature"`
5. Push to the branch by `git push origin feature/my-new-feature`
6. Submit a pull request on github page

## Developers

* Christiane Bergner
* Florian Biermann
* Manuel Egger
* Christian Motz
* Marc Pichler

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

