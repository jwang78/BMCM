package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.scene.paint.Color;

public class UnitMap extends ColorMap {
	private Tile[][] _tiles;
	public static final double BOAT_SPEED = 10;
	public static final double FOREST_SPEED = 1;
	public static final double ROAD_SPEED = 5;
	public static final double PLAINS_SPEED = 5;
	public static final double SIGMA = 3;
	private boolean _processed = false;
	private double _average = 0;
	private int _width;
	private int _height;
	private double _max = -1;
	private double _min = 100000;
	private List<City> cities;

	public UnitMap(Tile[][] t) {
		_tiles = t;
		_width = t.length;
		_height = t[0].length;
		cities = new ArrayList<>();
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
				
				double temp = calculateValue(i, j);
				//System.out.println(temp);
				_tiles[i][j].setUnitStrength(temp);
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
		if (_tiles[x][y].getUnitStrength() > 0) {
			return _tiles[x][y].getUnitStrength();
		}
		double res = calculateValue(x, y);
		
		
		_tiles[x][y].setUnitStrength(res);
		//System.out.println(res);
		return res;
	}


	private double calculateValue(int x, int y) {
		double res = 0;
		if (_tiles[x][y].isSea()) {
			return Double.NaN;
		}
		for (City c : cities) {
			int pop = c.getGarrison();
			double val;
			if (c.getX() == x && c.getY() == y) {
				val = c.getGarrison()/2;
			}
			else {
				double r = Math.hypot(x - c.getX(), y - c.getY());
				val = Math.min(c.getGarrison()/r, c.getGarrison()/Parameters.WWTOHUMAN);
			}
			//System.out.println();
			if (!Double.isNaN(_tiles[c.getX()][c.getY()].getDragonGlass())) {
				val *= _tiles[c.getX()][c.getY()].getDragonGlass();
			}
			
			res += val;
		}
		
		
		return res;
	}

	@Override
	public boolean isProcessed() {
		// TODO Auto-generated method stub
		return _processed;
	}

	public void addCity(int x, int y, int unit, int population) {
		City c = new City(x, y, unit, population);
		cities.add(c);
		process();
		_tiles[x][y].setCity(true);
	}


	


	public boolean hasCity(int x, int y) {
		for (City c : cities) {
			if (c.getX() == x && c.getY() == y) {
				return true;
			}
		}
		return false;
	}
	
	City getCity(int x, int y) {
		for (City c: cities) {
			if (c.getX() == x && c.getY() == y) {
				return c;
			}
		}
		return null;
	}

	public void removeCity(int x, int y) {
		Iterator<City> cit = cities.iterator();
		while (cit.hasNext()) {
			City ne = cit.next();
			if (ne.getX() == x && ne.getY() == y) {
				cit.remove();
				_tiles[x][y].setCity(false);
				process();
				return;
			}
		}
		process();
		throw new RuntimeException("Tried to remove non-existent city");
	}
	class City {
		private int x;
		private int y;
		private int population;
		private int garrison;
		public int getX(){return x;}
		public int getY(){return y;}
		public int getPopulation(){return population;}
		public int getGarrison(){return garrison;}
		public City(int x, int y, int garrison, int population) {
			this.x = x; this.y = y; this.population = population; this.garrison = garrison;
		}
		
	}
}
