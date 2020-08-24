# MiniMax Tic Tac Toe   


## Tic Tac Toe

The game GUI is implemented using JavaFX and follows a Model-View-Controller (MVC) structure where the Board and Tile classes comprise the Model and the TicTacToe class comprises the View and Controller. Varying board sizes can be played by changing the BOARD_WIDTH constant in the Board class. However, boards of size 4x4 (or larger) have a maximum search depth over 6 have very poor performance when using the vanilla MiniMax algorithm making them essentially unplayable, this is addressed with Alpha-Beta pruning. Below are examples of varying game sizes.

<img align="center" src="https://github.com/DavidHurst/MiniMax-TicTacToe-Java/blob/master/Images/3x3Board.PNG" width="226" height="262"> <img align="center" src="https://github.com/DavidHurst/MiniMax-TicTacToe-Java/blob/master/Images/4x4Board.PNG" width="270.9" height="303.75"> <img align="center" src="https://github.com/DavidHurst/MiniMax-TicTacToe-Java/blob/master/Images/5x5Board.PNG" width="300.8" height="330.4">
 

## MiniMax

The Minimax algorithm uses backtracking to recursively find the next best move by assigning a value to each board configuration and evaluating each of these configurations using a heuristic evaluation function. In the vanilla implementation of MiniMax ([MiniMax.java](http://minimax.java)) the evaluation function returns a heuristic value for terminal nodes and nodes at the predetermined maximum search depth but the heuristic only takes into account winning, losing and draw configurations returning +10 for winning configurations, -10 for losing and 0 for a draw which slightly hinders the performance of the algorithm in terms of time to win, this is addressed in MiniMaxImproved.  

This implementation also explores every possible board configuration it can, even when it is redundant to do so resulting in a time complexity of O(b^d) where *b* is how many legal moves there are from a board configuration (i.e. the branching factor of the game tree) and *d* is the maximum depth of the tree, this inefficiency is addressed with the Alpha-Beta optimisation.   

## MiniMax Improved

The vanilla MiniMax algorithm's heuristic function sometimes results in a slower victory or a faster loss due to the heuristic not taking into account how many moves it would take to realise a certain configuration. MiniMaxImproved addresses this by adding the depth to maximising evaluations and taking depth away from minimising evaluations, this has the effect of making wins which can be achieved in fewer moves and loses which can be achieved in the most moves more favourable.   
Below are demonstrations of a victory not being seized immediately where the vanilla algorithm is being used (left gif) and the improved version that takes the win immediately where the improved algorithm is being used (right gif).   

<img src="https://github.com/DavidHurst/MiniMax-TicTacToe-Java/blob/master/Images/SlowVictory.gif" width="309" height="360"> <img align="right" src="https://github.com/DavidHurst/MiniMax-TicTacToe-Java/blob/master/Images/FastVictory.gif" width="309" height="360">   

## Alpha-Beta Pruning

Alpha-Beta optimises the Minimax algorithm by not evaluating a node's children when at least one possibility has been found that proves the node to be worse than a previously examined move, this is known as pruning.   

- Alpha is associated with the *max* nodes and represents the minimum score that the maximising player is assured of i.e. the best alternative for the maximising player.
- Beta is associated with *min* nodes and represents the maximum score that the minimising player is assured of i.e. the best alternative for the minimising player.   

Pruning from a minimising node is done when alpha is greater than or equal to the node's value which represents the node being worse for the maximising player than its best alternative and that the maximising player would never choose this node and the children of this node will never actually be reached in play. Pruning from a maximising node is done when beta is less than or equal to the node's value, representing the minimising player having a better alternative and never choosing this node. 

Alpha-Beta improves MiniMax's efficiency from O(b^d) to O(sqrt(b^d)) by drastically reducing the branching factor of the game tree. The efficiency increase comes from the pruning of branches explained above and works essentially by using the second player's best move to counter all of the first player's move instead of evaluating every single move of both players.   

---

### Resources
[Minimax with Alpha Beta Pruning - John Levine](https://www.youtube.com/watch?v=zp3VMe0Jpf8)   
[Search: Games, Minimax, and Alpha-Beta - MIT OpenCourseWare](https://www.youtube.com/watch?v=STjW3eH0Cik)   
[Alpha-Beta Pruning - Wikipedia](https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning)   
[Minimax - Wikipedia](https://en.wikipedia.org/wiki/Minimax)   
[What is the Minimax Algorithm? - Gaurav Sen](https://www.youtube.com/watch?v=KU9Ch59-4vw)   
[MiniMax and Alpha-Beta Pruning - Sebastian Lague](https://www.youtube.com/watch?v=l-hh51ncgDI)   
[Coding Challenge 154: Tic Tac Toe AI with Minimax Algorithm - The Coding Train](https://www.youtube.com/watch?v=trKjYdBASyQ)   
[Minimax Algorithm in Game Theory - Geeks for Geeks](https://www.geeksforgeeks.org/minimax-algorithm-in-game-theory-set-3-tic-tac-toe-ai-finding-optimal-move/?ref=lbp)
