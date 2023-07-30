ef is_board_full(board):
    return all(board[i][j] != " " for i in range(3) for j in range(3))

def get_ai_move(board):
    for i in range(3):
        for j in range(3):
            if board[i][j] == " ":
                return i, j

def play_game():
    board = [[" " for _ in range(3)] for _ in range(3)]
    player = "X"
    ai = "O"

    while True:
        print_board(board)

        if check_win(board, player):
            print(f"Congratulations! Player {player} wins!")
            break
        elif check_win(board, ai):
            print(f"AI wins! Better luck next time.")
            break
        elif is_board_full(board):
            print("It's a tie!")
            break

        if player == "X":
            row = int(input("Enter row (0, 1, or 2): "))
            col = int(input("Enter column (0, 1, or 2): "))
        else:
            row, col = get_ai_move(board)

        if 0 <= row < 3 and 0 <= col < 3 and board[row][col] == " ":
            board[row][col] = player
            player = "O" if player == "X" else "X"
        else:
            print("Invalid move. Try again.")

if __name__ == "__main__":
    play_game()
