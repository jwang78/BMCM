package control;

import javafx.scene.image.Image;
import model.ColorMap;
import model.CombatMap;
import model.DragonGlassMap;
import model.Parameters;
import model.StrengthMap;
import model.Tile;
import util.ImageUtils;
import visualizer.Controls;
import visualizer.Visualizer;
import model.UnitMap;
import model.WhiteWalkerAI;

public class Controller {

	private UnitMap _tmap;
	private CombatMap _cmap;
	private DragonGlassMap _dgmap;
	private StrengthMap _smap;
	private Visualizer _viz;
	private Image _img;
	private Tile[][] _tiles;
	private int _width;
	private int _height;
	private Controls _conts;
	private WhiteWalkerAI ai;

	public Controller(Visualizer viz) {
		_viz = viz;
	}

	// TODO: Simulate the white walkers' advance
	public void process(Image img) {
		_width = (int) (img.getWidth() / Tile.TILE_SIZE_IN_PIXELS);
		_height = (int) (img.getHeight() / Tile.TILE_SIZE_IN_PIXELS);
		_tiles = grid(img);

		_viz.initializeGridPane(_tiles, this);
		_tmap = new UnitMap(_tiles);
		_dgmap = new DragonGlassMap(_tiles, 1);
		_dgmap.process();
		_cmap = new CombatMap(_tiles);

		_smap = new StrengthMap(_tiles);
		ai = new WhiteWalkerAI(_tiles);
		initCities();
		_viz.displayColorMap(_cmap, this.getWidth(), this.getHeight());
		_tiles[Parameters.INIT_WW_X][Parameters.INIT_WW_Y].setWWControl(true);
	}
	public void initCities() {
		addCity((int)Parameters.KINGS_LANDING[0], (int)Parameters.KINGS_LANDING[1], 80000, 500000);
		addCity((int)Parameters.DRAGONSTONE[0], (int)Parameters.DRAGONSTONE[1], 80000, 60000);
		addCity((int)Parameters.CASTERLY_ROCK[0], (int)Parameters.CASTERLY_ROCK[1], 8000, 250000);
		addCity((int)Parameters.OLDTOWN[0], (int)Parameters.OLDTOWN[1], 0, 500000);
		addCity((int)Parameters.WINTERFELL[0], (int)Parameters.WINTERFELL[1], 26500, 100);
		addCity((int)Parameters.IRON_ISLANDS[0], (int)Parameters.IRON_ISLANDS[1], 30000, 20000);
	}

	public int getWidth() {
		return _width;
	}

	public int getHeight() {
		return _height;
	}

	private Tile[][] grid(Image i) {
		Tile[][] grid = new Tile[this.getWidth()][this.getHeight()];
		ImageUtils.toPixelArray(i, grid, this.getWidth(), this.getHeight(),
		        Tile.TILE_SIZE_IN_PIXELS);
		return grid;

	}

	public void displayMap(Object userData) {
		String type = (String) userData;
		if (type.equals("dgmap")) {
			_viz.displayColorMap(_dgmap, this.getWidth(), this.getHeight());
		}
		if (type.equals("pwrmap")) {
			_viz.displayColorMap(_tmap, this.getWidth(), this.getHeight());
		}
		if (type.equals("strengthmap")) {
			_viz.displayColorMap(_smap, this.getWidth(), this.getHeight());
		}
		if (type.equals("defmap")) {
			_viz.displayColorMap(_cmap, this.getWidth(), this.getHeight());
		}
		if (type.equals("wwmap")) {
			_viz.displayColorMap(ai, this.getWidth(), this.getHeight());
		}
	}

	public void addCity(int x, int y) {
		
		int unit = _conts.getUnitField();
		int population = _conts.getPopField();
		if (unit == -1 || population == -1) {
			System.out.println("Failed to create city");
			return;
		}
		addCity(x, y, unit, population);
		
	}
	
	public void addCity(int x, int y, int unit, int population) {
		_viz.toggleCity(x, y);
		if (!_tmap.hasCity(x, y)) {
			_tmap.addCity(x, y, unit, population);
		} else {
			_tmap.removeCity(x, y);
		}
		_smap.process();
		displayMap(_conts.getSelectedRButton());
	}

	public void setControls(Controls conts) {
		_conts = conts;

	}

	public void advanceWW(int n) {
		for (int i = 0; i < n; i++) {
			ai.process(_tmap, this);
		}
		_dgmap.incrTime();
		_dgmap.process();
		_tmap.process();
		_smap.process();
		

		displayMap(_conts.getSelectedRButton());
	}

	public void updateMap(int x, int y) {
		_viz.toggleCity(x, y);
		System.out.println("Removed city");
	}

	public void reset() {
		for (Tile[] t : _tiles) {
			for (Tile ti : t) {
				ti.setWWControl(false);
			}
		}
		_tiles[Parameters.INIT_WW_X][Parameters.INIT_WW_Y].setWWControl(true);
		displayMap(_conts.getSelectedRButton());
	}
}
