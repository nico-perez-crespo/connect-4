# Connect 4 Game

A web-based implementation of the classic Connect 4 game. Players can input their names, choose the number of rows and columns for the game board, and start playing. The game allows two players to take turns dropping colored tokens (red and yellow) into a grid, with the goal of being the first to form a line of four consecutive tokens either vertically, horizontally, or diagonally.

## Features

- **Customizable grid size**: Players can choose the number of rows (5-7) and columns (5-13) before starting the game.
- **Two-player mode**: Players can input their names and take turns to play.
- **Hover effect**: The current column highlights where the token will drop.
- **Victory detection**: The game detects when a player has successfully aligned four tokens in a row, column, or diagonal and announces the winner.
- **Responsive design**: The game layout adjusts for different screen sizes, providing a good experience on both desktop and mobile devices.

## Technologies Used

- **HTML**: The structure of the game board and input form.
- **CSS**: Custom styles, including responsive layout and hover effects.
- **JavaScript**: Core game logic, including:
  - Validating player inputs and ensuring the grid is within the allowed size.
  - Handling game turns and switching between players.
  - Detecting and announcing the winner.

## Getting Started

To run the game, simply open the `index.html` file in your web browser. The game does not require any server-side setup.

### Prerequisites

- A modern web browser (e.g., Chrome, Firefox, Safari) that supports JavaScript and CSS.

### How to Play

1. Enter the names for both players.
2. Specify the number of columns (5-13) and rows (5-7) for the grid.
3. Click "START GAME" to initialize the board.
4. Players take turns clicking on a column to drop their colored token into the grid.
5. The first player to form a horizontal, vertical, or diagonal line of four tokens wins.
6. When a player wins, the game will display the winner and the option to play again.

## Folder Structure
```
project/
│
├── html/
│   └── index.html          # Main HTML file containing the game structure.
│
├── css/
│   └── style.css           # Styles for the game layout and elements.
│
├── js/
│   └── script.js           # Game logic and interactivity.
│
└── fonts/
    └── Poppins-Medium.ttf  # Custom font used in the game.
```

## Future Improvements

- Add animations when tokens fall into the grid.
- Implement an AI opponent for single-player mode.
- Add sound effects for a more immersive experience.
- Enhance the design with additional themes or color schemes.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

