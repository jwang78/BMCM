package model;

import javafx.scene.paint.Color;

public class Tile {
	private int x;
	private int y;
	
	public static final int TILE_SIZE_IN_PIXELS = 32;
	private double _defense = -1;
	private double _unitStrength = -1;
	private double _dglass = -1;
	private double _totalStrength = -1;
	private boolean _isSea = false;
	private boolean _whiteControlled = false;
	private boolean _isCity = false;
	private Color[][] _raw;
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public boolean isCity() {
		return _isCity;
	}
	public void setCity(boolean b) {
		_isCity = b;
	}
	public double getDragonGlass() {
		return _dglass;
	}
	public void setDragonGlass(double _dglass) {
		this._dglass = _dglass;
	}
	public double getUnitStrength() {
		return _unitStrength;
	}
	public double getStrength() {
		// TODO: FIX THIS
		return _totalStrength;
	}
	public void setStrength(double a) {
		_totalStrength = a;
	}
	public void setUnitStrength(double d) {
		_unitStrength = d;
	}
	
	public void setSea(boolean b) {
		_isSea = b;
	}
	
	public boolean isSea() {
		return _isSea;
	}
	
	public Tile(int x, int y, double defense, double unit, double dglass, double totalStrength) {
		this.x = x;
		this.y = y;
		_defense = defense;
		_unitStrength = unit;
		_dglass = dglass;
		_totalStrength = totalStrength;
		_raw = new Color[TILE_SIZE_IN_PIXELS][TILE_SIZE_IN_PIXELS];
	}
	public Color[][] getRawArray() {
		return _raw;
	}
	public void setWWControl(boolean b) {
		_whiteControlled = b;
	}
	public boolean isWWControl() {
		return _whiteControlled;
	}
	public void setRawArray(int x, int y, Color c) {
		_raw[x][y] = c;
	}
	public void setDefense(double res) {
		_defense = res;
	}
	public double getDefense() {
		return _defense;
	}
}
