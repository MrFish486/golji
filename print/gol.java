import java.util.concurrent.TimeUnit;
import java.util.Random;

public class gol {
	public static void main (String[] args) throws InterruptedException {
		game main = new game(50, 50, 2);
		for (int i = 0; i < 1000; i ++) {
			System.out.print("\033[H\033[2J");
			System.out.flush();
			main.render(1, i);
			main.tick();
			//TimeUnit.MILLISECONDS.sleep(50);
		}
	}
}

class game {
	int[][] board;
	int width;
	int height;
	public game (int width, int height, int fill) {
		this.width = width;
		this.height = height;
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
	public int neighbors (int x, int y) {
		int sum = 0;
		try {
			sum += this.board[x - 1][y - 1];
		} catch (ArrayIndexOutOfBoundsException e) {}
		try {
			sum += this.board[x][y - 1];
		} catch (ArrayIndexOutOfBoundsException e) {}
		try {
			sum += this.board[x + 1][y - 1];
		} catch (ArrayIndexOutOfBoundsException e) {}
		try {
			sum += this.board[x - 1][y];
		} catch (ArrayIndexOutOfBoundsException e) {}
		try {
			sum += this.board[x + 1][y];
		} catch (ArrayIndexOutOfBoundsException e) {}
		try {
			sum += this.board[x - 1][y + 1];
		} catch (ArrayIndexOutOfBoundsException e) {}
		try {
			sum += this.board[x][y + 1];
		} catch (ArrayIndexOutOfBoundsException e) {}
		try {
			sum += this.board[x + 1][y + 1];
		} catch (ArrayIndexOutOfBoundsException e) {}
		return sum;
	}
	public void tick () {
		int[][] next = new int[width][height];
		for (int x = 0; x < this.width; x ++) {
			for (int y = 0; y < this.height; y ++) {
				if (this.board[x][y] == 0) {
					if (this.neighbors(x, y) == 3) {
						next[x][y] = 1;
					} else {
						next[x][y] = 0;
					}
				} else if (this.board[x][y] == 1) {
					if (this.neighbors(x, y) == 2 || this.neighbors(x, y) == 3) {
						next[x][y] = 1;
					} else {
						next[x][y] = 0;
					}
				}
			}
		}
		this.board = next;
	}
	public int life () {
		int life = 0;
		for (int x = 0; x < this.width; x ++) {
			for (int y = 0; y < this.height; y ++) {
				life += this.board[x][y];
			}
		}
		return life;
	}
	public void render (int view, int days) {
		if (view == 0) {
			System.out.println("┌" + "─".repeat(this.width) + "┬" +  "─".repeat(String.valueOf(this.width * this.height).length()) + "┐");
			for (int x = 0; x < this.width; x ++) {
				System.out.print("│");
				for (int y = 0; y < this.height; y ++) {
					if (this.board[x][y] == 1) {
						System.out.print("█");
					} else if (this.board[x][y] == 0) {
						System.out.print(" ");
					} else {
						System.out.print("?");
					}
				}
				System.out.print("│");
				if (x == 0) {
					System.out.print(String.format("%" + String.valueOf(this.width * this.height).length() + "s", String.valueOf(this.life())));
				} else {
					System.out.print(" ".repeat(String.valueOf(this.width * this.height).length()));
				}
				System.out.print("│");
				System.out.println();
			}
			System.out.println("└" + "─".repeat(this.width) + "┴" + "─".repeat(String.valueOf(this.width * this.height).length()) + "┘");
		} else if (view == 1) {
			System.out.println("┌" + "─".repeat(this.width) + "┐");
			System.out.println("│" + String.format("%" + String.valueOf(this.width) + "s", "Life:" + String.valueOf(this.life()) + ", Days:" + String.valueOf(days)) + "│");
			System.out.println("├" + "─".repeat(this.width) + "┤");
			for (int x = 0; x < this.width; x ++) {
				System.out.print("│");
				for (int y = 0; y < this.height; y ++) {
					if (this.board[x][y] == 1) {
						System.out.print("█");
					} else if (this.board[x][y] == 0) {
						System.out.print(" ");
					} else {
						System.out.print("?");
					}
				}
				System.out.print("│");
				System.out.println();
			}
			System.out.println("└" + "─".repeat(this.width) + "┘");
		}
	}
}
