package model;

import javafx.scene.paint.Color;

public abstract class ColorMap {
	public Color getColor(int x, int y) {
		if (!isProcessed()) {
			process();
		}
		
		double v = this.getValue(x, y);
		double percent = (v - this.getMin()) / (this.getMax() - this.getMin());
		Color end = Color.YELLOW;
		Color begin = Color.DARKRED;
		return Color.color(
		        begin.getRed() * (1 - percent) + end.getRed() * percent,
		        begin.getGreen() * (1 - percent) + end.getGreen() * percent,
		        begin.getBlue() * (1 - percent) + end.getBlue() * percent);
	}
	public abstract double getValue(int x, int y);
	public abstract double getMin();
	public abstract double getMax();
	public abstract boolean isProcessed();
	public abstract void process();
}
