package model;

import javafx.scene.paint.Color;

public class CombatMap extends ColorMap {
	private Tile[][] _tiles;
	
	private boolean _processed = false;
	private double _average = 0;
	private int _width;
	private int _height;
	private double _max = -1;
	private double _min = 100000;

	public CombatMap(Tile[][] t) {
		_tiles = t;
		_width = t.length;
		_height = t[0].length;
	}

	private int getWidth() {
		return _width;
	}

	private int getHeight() {
		return _height;
	}


	public double getMax() {
		return _max;
	}

	public double getMin() {
		return _min;
	}

	public void setMax(double v) {
		_max = v;
	}

	public void setMin(double v) {
		_min = v;
	}

	public void process() {
		for (int i = 0; i < this.getWidth(); i++) {
			for (int j = 0; j < this.getHeight(); j++) {
				
				double temp = getValue(i, j);
				_average += temp;
				if (temp > this.getMax()) {
					this.setMax(temp);
				}
				if (temp < this.getMin()) {
					this.setMin(temp);
				}
			}
		}
		_average /= this.getWidth() * this.getHeight();
		_processed = true;
		System.out.println("PFINISHED");
	}

	@Override
	public double getValue(int x, int y) {
		//System.out.println("x" + x + "y" + y +"mx" + this.getWidth() + "my" + this.getHeight());
		if (_tiles[x][y].getDefense() > 0) {
			return _tiles[x][y].getDefense();
		}
		double THRESHOLD = 200;
		double res = 0;
		
		Color[][] raw = _tiles[x][y].getRawArray();
		int dividend = raw.length * raw[0].length;
		Color sea = Color.rgb(164, 190, 223);
		Color forest = Color.rgb(203, 211, 187);
		Color plains = Color.rgb(243, 242, 240);
		Color road = Color.rgb(223, 117, 116);
		Color mountains = Color.rgb(255, 242, 0);
		Color text = Color.BLACK;
		Color city = Color.RED;
		double avgHue = 0;
		for (int i = 0; i < raw.length; i++) {
			for (int j = 0; j < raw[0].length; j++) {
				Color c = raw[i][j];
				
				if (distance(c, text) < THRESHOLD) {
					dividend--;
					continue;
				}
				if (distance(c, city) < THRESHOLD) {
					dividend--;
					continue;
				}
				if (Math.abs(c.getHue() - sea.getHue()) < 4) {
					dividend--;
					continue;
				}
				avgHue += c.getHue();
				
				/*
				if (Math.abs(c.getHue() - road.getHue()) < 10 && c.getBrightness() < 75.0/255) {
					return ROAD_SPEED;
				}*/
				/*if (c.getSaturation() > 150) {
					dividend--;
					continue;
				}*/
				/*if (distance(c, plains) > THRESHOLD && grayDistance(c) < THRESHOLD*0) {
					continue;
				}*/
				double mountainM = gaussian(distance(c, mountains));
				double forestM = gaussian(distance(c, forest));
				/*if (distance(c, forest) < 10) {
					System.out.println(distance(c, forest));
				}*/
				
				double plainsM = gaussian(distance(c, plains));
				res += forestM * Parameters.FOREST_DEF
				        + plainsM * Parameters.PLAINS_DEF + mountainM * Parameters.MOUNTAIN_DEF;
				
			}
		}
		res = res / dividend;
		avgHue = avgHue / dividend;
		if (dividend*1.0/raw.length/raw[0].length < Parameters.SEA_CRITERION) {
			_tiles[x][y].setSea(true);
		}
		_tiles[x][y].setDefense(res);
		//System.out.println(res);
		return res;
	}

	public static double distance(Color c, Color d) {
		double diffx = (c.getRed() - d.getRed()) * 255;
		double diffy = (c.getGreen() - d.getGreen()) * 255;
		double diffz = (c.getBlue() - d.getBlue()) * 255;
		return Math.hypot(Math.hypot(diffx, diffy), diffz);
	}
	
	public static double grayDistance(Color c) {
		return Math.hypot(Math.hypot(c.getBlue() - c.getGreen(), c.getRed() - c.getGreen()), c.getRed() - c.getBlue());
	}

	public static double gaussian(double distance) {
		return /*1 / (Math.sqrt(2 * Math.PI * SIGMA))
		        * */Math.exp(-distance / (2 * Parameters.SIGMA * Parameters.SIGMA));
	}

	@Override
	public boolean isProcessed() {
		// TODO Auto-generated method stub
		return _processed;
	}



}
