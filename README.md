# Tents & Trees Solver
Tents & Trees Solver is program which solves all the levels of the Tents & Tree puzzle application.


## How it works ?
When you run the application, you will arrive on a menu which contains Play, Tutorial and Quit (which allows you to quit the application).

#### Play
On this page, you must first enter the size of your board and then click on load.
Once done, a grid that you will have to fill will appear.
When you have finished filling it out, click on Solve and the solution will appear.

#### Tutorials
The tutorial just show you how the game works.
It's actually the same tutorial as in the Tents & Trees application.

(I know it's useless, I just did it for fun)

## How was it done ?

/!\ All the logic that was implemented in this program was done by me.
I was not inspired by any already existing algorithm or existing code.
I myself thought of a way to solve these puzzles and implement it.
It was a challenge for me, a challenge for myself, to prove to myself that I was capable of doing it.


The logic rests on some point:

1. First of all, we fill the rows and columns that have 0 garden tent.
2. Then we put garden on all the boxes that do not touch trees.
3. And now on loop :
    1. We put garden on all the boxes which, if we put a tent there, would make the game impossible.
    2. We look for each row and column, the boxes that can only be filled by a tent.
    3. We put a tent next to all the trees that have only one empty box next to them.

To help me, i have a set of all trees on the board and 2 map of all the empty box in x and y (coordinate).

#

### Info & Links
Tents & Trees Puzzles is a puzzle application made by [Frozax Games](https://www.frozax.com/).

You can download it here :

Play Store : [https://play.google.com/store/apps/details?id=com.frozax.tentsandtrees](https://play.google.com/store/apps/details?id=com.frozax.tentsandtrees)

App Store : [https://apps.apple.com/app/tents-and-trees-puzzles/id1279378379](https://apps.apple.com/app/tents-and-trees-puzzles/id1279378379)

Amazon : [https://www.amazon.com/Frozax-Games-Tents-Trees-Puzzles/dp/B07SZP5FK2](https://www.amazon.com/Frozax-Games-Tents-Trees-Puzzles/dp/B07SZP5FK2)
