package main;
public class IndexBall {
	private int index;
	private int ball;
	
	public IndexBall(int index, int ball) {
		this.index = index;
		this.ball = ball;
	}
	public int getIndex() {
		return index;
	}
	public int getBall() {
		return ball;
	}
	public String print() {
		String output = "(" + index + ", " + ball + ")";
		return output;
	}
}
