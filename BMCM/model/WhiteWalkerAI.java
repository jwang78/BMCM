package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import control.Controller;
import javafx.scene.paint.Color;
import model.UnitMap.City;

public class WhiteWalkerAI extends ColorMap {
	private Tile[][] _tiles;
	private int NUM_WW = 1000;
	private int NUM_WIGHT = 100 * NUM_WW;
	private boolean _processed = false;
	private double confidence = Parameters.INIT_WHITE_WALKER_CONFIDENCE;
	public WhiteWalkerAI(Tile[][] tiles) {
		_tiles = tiles;
	}
	public Color getColor(int x, int y) {
		Tile t = _tiles[x][y];
		if (t.isSea()) {
			return Color.BLACK;
		}
		if (t.isWWControl()) {
			return Color.AZURE;
		}
		if (t.isCity()) {
			return Color.GREEN;
		}
		return Color.DARKRED;
	}
	@Override
	public double getValue(int x, int y) {
		return NUM_WW + NUM_WIGHT * Parameters.WIGHTTOHUMAN / Parameters.WWTOHUMAN;
	}
	@Override
	public double getMin() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public double getMax() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean isProcessed() {
		return _processed;
	}
	public void process(UnitMap umap, Controller c) {
		List<int[]> arr = new ArrayList<>();
		
		for (int i = 0; i < _tiles.length; i++) {
			for (int j = 0; j < _tiles[0].length; j++) {
				Tile t = _tiles[i][j];
				if (t.isSea()) {
					continue;
				}
				if (t.getStrength() < this.getValue(i, j) && (!t.isCity() || t.getStrength() < confidence * this.getValue(i, j))) {
					int[][] neighbors = {{i, j-1}, {i, j+1}, {i+1, j}, {i-1, j}};
					int[][] validNeighbors = Arrays.stream(neighbors).filter((int[] a) -> {
						return a[0] >= 0 && a[0] < _tiles.length && a[1] >= 0 && a[1] < _tiles[0].length;
					}).toArray(int[][]::new);
					for (int[] n : validNeighbors) {
						if (_tiles[n[0]][n[1]].isWWControl()) {
							arr.add(new int[]{i, j});
							break;
						}
					}
					confidence = Parameters.INIT_WHITE_WALKER_CONFIDENCE;
				}
			}
		}
		if (arr.isEmpty()) {
			confidence = Math.min(confidence + 0.1, Parameters.INIT_WHITE_WALKER_CONFIDENCE);
		}
		for (int[] ar : arr) {
			_tiles[ar[0]][ar[1]].setWWControl(true);
			if (umap.hasCity(ar[0], ar[1])) {
				doBattle(umap.getCity(ar[0], ar[1]));
				umap.removeCity(ar[0], ar[1]);
				c.updateMap(ar[0], ar[1]);
			}
		}
		_processed = true;
	}
	private void doBattle(City city) {
		// TODO FIGHT WITH A CITY
		double s1 = this.getValue(0, 0);
		double s2 = _tiles[city.getX()][city.getY()].getStrength();
		double r = s1/s2 - 1;
		double surviving = r/(1+r);
		NUM_WW *= surviving;
		NUM_WIGHT *= surviving;
		NUM_WIGHT += (city.getPopulation() + city.getGarrison()) * (1 - _tiles[city.getX()][city.getY()].getDragonGlass());
	}
	@Override
	public void process() {
		return;
	}
	

}
