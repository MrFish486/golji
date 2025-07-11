import java.util.random;

public class gol {
	public static void main (String[]) {
		game main = new game(20, 20, 2)
	}
}

class game {
	int[][] board;
	public game (width, height, fill) {
		Random rand = new Random();
		this.board = new int[width][height];
		for (int x = 0; x < width; x ++) {
			for (int y = 0; y < height; y ++) {
				if (fill == 0 || fill == 1) {
					this.board[x][y] = fill; 
				} else {
					this.board[x][y] = rand.nextInt(2);
				}
			}
		}
	}
}
