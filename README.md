# Randomitil

## Authors
David Chamberlain & Remy Mujynya

## Description
Generates and displays random domino tilings for aztec diamonds. For most circumstances this generation results in a circular region of chaos called an arctic circle. The relevant math and demonstration of the generation algorithm can be found [here](https://youtu.be/Yy7Q8IWNfHM). This program allows the user to mess with setting controlling how the diamond is generated and displayed to see unique distortions of the default case.

## Instructions
1.  Have Java 15 or higher installed
1.  Navigate to the *randomitil* folder using "cd"
1.  Run the terminal command "gradlew run"

## Generation Bias Rules
1.  Bias ranges from 0 to 1
1.  A bias of 0 generates only right and left facing dominoes
1.  A bias of 1 generates only up and down facing dominoes

## Expansion Rate Rules
1.  Expansion rates can be any odd positive integer
1.  Expansion rates will result in proper diamonds when:
    1.  Horizontal and vertical expansion rates are equal
    1.  Either expansion rate is equal to 1
