package model;

public class StrengthMap extends ColorMap{
	private Tile[][] _tiles;
	private boolean _processed = false;
	private double _min = Double.POSITIVE_INFINITY;
	private double _max = Double.NEGATIVE_INFINITY;
	public StrengthMap(Tile[][] tiles) {
		_tiles = tiles;
	}

	public double calculateValue(int x, int y) {
		Tile t = _tiles[x][y];
		return t.getDefense() * t.getUnitStrength();
	}
	public double getValue(int x, int y) {
		double v = calculateValue(x, y);
		_tiles[x][y].setStrength(v);
		return v;
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
		return _processed;
	}

	@Override
	public void process() {
		for (int i = 0; i < _tiles.length; i++) {
			for (int j = 0; j < _tiles[0].length; j++) {
				double v = getValue(i, j);
				if (_min > v) {
					_min = v;
				}
				if (_max < v) {
					_max = v;
				}
			}
		}
		_processed = true;
	}

}
