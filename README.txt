# Web Ui Tests Project

## Description
This project is an automation test project that utilizes Maven, Allure, and JUnit 5  frameworks to execute automated Web Ui tests.

## Prerequisites
- Java JDK (version 8 or higher) should be installed and configured.
- Maven should be installed and configured.

## Installation
1. Clone the repository to your local machine.
   bash
   git clone <repository_url>

2. Navigate to the project directory.
   bash
   cd WebUiTests


## Execution
To execute the automated tests, follow the steps below:

1. Open a terminal or command prompt.
2. Navigate to the project directory.
   bash
   cd WebUiTests

3. Run the following command to clean the project and execute the tests.
   bash
   mvn clean test allure:report

   This command will clean the project, compile the source code, run the tests, and prepare Allure report.

## Test Report
Test report can be generated using the following command:

bash
mvn allure:serve


This command will generate and serve the Allure report in your default web browser.
The Allure report provides detailed information about the executed tests, including test results, statistics, and screenshots (if any).

## Note
Make sure to configure the necessary dependencies and plugins in the pom.xml file before executing the tests.