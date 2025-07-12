import java.util.concurrent.TimeUnit;
import java.util.Random;

public class gol {
	public static void main (String[] args) throws InterruptedException {
		game main = new game(50, 50, 2);
		printer.clear();
		for (int i = 0; i < 1000; i ++) {
			main.render();
			main.tick();
			TimeUnit.MILLISECONDS.sleep(20);
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
	public void render () {
		for (int x = 0; x < this.width; x ++) {
			for (int y = 0; y < this.height; y ++) {
				if (this.board[x][y] == 1) {
					printer.printat(x, y, '█');
				} else if (this.board[x][y] == 0) {
					printer.printat(x, y, ' ');
				} else {
					printer.printat(x, y, '?');
				}
			}
		}
	}
}

class printer {
	int lin;
	int col;
	public printer () {
		this.clear();
		this.lin = 1;
		this.col = 1;
	}
	public static void clear () {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
	public void println (String in) {
		System.out.print("\033[" + String.valueOf(this.lin) + ";" + String.valueOf(this.col) + "H" + in);
		this.lin += 1;
		this.col = 0;
	}
	public void print (String in) {
		System.out.print("\033[" + String.valueOf(this.lin) + ";" + String.valueOf(this.col) + "H" + in);
		this.col += in.length();
	}
	public static void printat (int lin, int col, char in) {
		System.out.print("\033[" + String.valueOf(lin) + ";" + String.valueOf(col) + "H" + Character.toString(in));
	}
}
