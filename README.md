# Randomitil

## Authors
David Chamberlain & Remy Mujynya

## Description
Generates and displays random domino tilings for aztec diamonds. For most circumstances this generation results in a circular region of chaos called an arctic circle. The relevant math and demonstration of the generation algorithm can be found [here](https://youtu.be/Yy7Q8IWNfHM). This program allows the user to experiment with variables controlling how the diamond is generated and displayed resulting in unique distortions of the default case. This program only applies to the case of aztec diamonds tiled randomly in four directions with rectangular dominoes taking up two square spaces, meaning it does not have a separate mode investigating the hexagon case with rhombus tiles.

We mostly did this project to challenge our abilities in building graphical interfaces in Java and translating mathmetical models into usable code. Along the way we also found interesting ways to extrapolate new problems from the standard case (such as different expansion rates). Additionally we've allowed some mathematically unrealistic cases and included purely aesthetic options in the interest of having more interesting effects things to explore.

## Instructions
1. Have Java 15 or higher installed
1. Navigate to the *randomitil* folder using "cd"
1. Run the terminal command "gradlew run"

## Generation Bias Rules
1. Bias ranges from 0 to 1
1. A bias of 0 generates only right and left facing dominoes
1. A bias of 1 generates only up and down facing dominoes

## Expansion Rate Rules
1. Expansion rates can be any odd positive integer
1. Expansion rates will result in proper diamonds when:
    1. Horizontal and vertical expansion rates are equal
    1. Either expansion rate is equal to 1
