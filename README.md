# MiniMax TicTacToe

## Minimax:

The Minimax algorithm uses backtracking to recursively find the next move by assigning a value to each board configuration by evaluating each configuration using a heuristic evaluation function. In the [MiniMax.java](http://minimax.java) class, the evaluation function returns a heuristic value for terminal nodes and nodes at the predetermined maximum search depth but the heuristic only takes into account winning/losing configurations returning +10 for winning configurations, -10 for losing and 0 for a draw/tie.

Pseudocode (from Wikipedia):

```java
function miniMax(node, depth, maxmisingPlayer)
		if depth = maximumDepth or node is a terminal node then
				return the heuristic value of node
		if maxmisingPlayer then
				highestValue := -infinity
				for each child of node do
						highestValue := max(value, minimax(child, depth + 1, FALSE))
						return highestValue 
		else // Minimising player.
				lowestValue := +infinity
				for each child of node do
						lowestValue := min(value, minimax(child, depth + 1, TRUE))
				return lowestValue 

// Initial call.
minimax(origin, depth, TRUE)
```

The standard MiniMax algorithm's heuristic function implemented in MiniMax.java sometimes results in a slower victory or a faster loss due to the heuristic not taking into account board configurations that are favourable but not winning configurations, this is addressed in MiniMaxImproved.java. See below an example of slow victory:

---