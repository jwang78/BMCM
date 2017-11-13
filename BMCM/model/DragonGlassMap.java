package model;

import javafx.scene.paint.Color;

public class DragonGlassMap extends ColorMap {
	private Tile[][] _tiles;
	private int _time;
	private double _min = Double.POSITIVE_INFINITY;
	private double _max = Double.NEGATIVE_INFINITY;
	public DragonGlassMap(Tile[][] t, int time) {
		_tiles = t;
		_time = time;
	}
	
	
	public void setTime(int t) {
		_time = t;
	}
	
	public void incrTime() {
		_time += 1;
	}
	

	public double calculateValue(int x, int y) {
		if (_tiles[x][y].isSea()) {
			return Double.NaN;
		}
		double val = Parameters.DRAGONGLASS_AVAIL*Math.atan(_time) /Math.sqrt(Math.hypot(x - Parameters.DRAGONSTONE[0], y - Parameters.DRAGONSTONE[1]));
		val = 2 / Math.PI * Math.atan(val);
		return val;
	}
	
	public double getValue(int x, int y) {
		double val = calculateValue(x, y);
		
		_tiles[x][y].setDragonGlass(val);
		return val;
	}
	@Override
	public double getMin() {
		return _min;
	}
	@Override
	public double getMax() {
		return _max;
	}
	@Override
	public boolean isProcessed() {
		return false;
	}
	@Override
	public void process() {
		for (int i = 0; i < _tiles.length; i++) {
			for (int j = 0; j < _tiles[0].length; j++) {
				double val = getValue(i, j);
				if (val < _min) {
					_min = val;
				}
				if (val > _max) {
					_max = val;
				}
			}
		}
	}

}
