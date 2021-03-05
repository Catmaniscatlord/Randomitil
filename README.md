## Randomitil
# Authors
1. David Chamberlain
1. Remy Mujynya

# Description
Generates and displays random domino tilings for aztec diamonds of all sizes.

# Instructions
1. Have Java 15 or higher installed
1. Navigate to the *randomitil* folder using *cd*
1. Run the terminal command *gradlew run*

# Extra
1. The Default color coding:
  1. Blue for up
  1. Red for right
  1. Yellow for down
  1. Green for left
1. Generation bias rules:
  1. Bias ranges from 0 to 1
  1. A bias of 0 generations only right and left facing dominoes
  1. A bias of 1 generations only up and down facing dominoes
1. Expansion rate rules:
  1. Expansion rates can be any odd positive integer
  1. Expansion rates will result in proper diamonds when:
    1. Horizontal and vertical expansion rates are equal
    1. Either expansion rate is equal to 1
