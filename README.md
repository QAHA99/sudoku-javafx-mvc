# Sudoku (JavaFX)

![Java](https://img.shields.io/badge/LANGUAGE-JAVA%2017%2B-orange?style=for-the-badge) ![JavaFX](https://img.shields.io/badge/FRAMEWORK-JAVAFX-blue?style=for-the-badge) ![Pattern](https://img.shields.io/badge/PATTERN-MVC-green?style=for-the-badge)

## Overview

A fully interactive Sudoku desktop application built with **JavaFX**. The project is architected using the **MVC (Model-View-Controller)** design pattern to ensure separation of concerns between the game logic, the user interface, and input handling.

## Architectural Design (MVC)

* **Model:** Manages the game state, logic, and validation (e.g., `Boxes`, `SudokuLevel`).
* **View:** Renders the UI components including the grid, buttons, and menu (e.g., `GridView`, `Buttons`).
* **Controller:** Acts as the mediator, processing user inputs and updating the model/view accordingly.

## Key Features
* **Interactive UI:** Smooth JavaFX-based graphical interface.
* **Difficulty Levels:** Support for varying game difficulty (e.g., Easy, Medium, Hard).
* **State Management:** Real-time validation and board updates.

## Stack
* **Language:** Java
* **UI Toolkit:** JavaFX
* **Architecture:** MVC
