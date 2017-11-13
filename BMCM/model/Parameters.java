package model;

public class Parameters {
	public static final int WHITE_WALKER_MAX_DISTANCE = 240;
	public static final double FOREST_DEF = 1.5;
	public static final double PLAINS_DEF = 1;
	public static final double SIGMA = 3;
	public static final double MOUNTAIN_DEF = 2;
	public static final double WWTOHUMAN = 5;
	public static final double WIGHTTOHUMAN = 0.5;
	public static final double INIT_WHITE_WALKER_CONFIDENCE = 0.5;
	public static final int INIT_WW_X = 630/Tile.TILE_SIZE_IN_PIXELS;
	public static final int INIT_WW_Y = 125/Tile.TILE_SIZE_IN_PIXELS;
	public static final double DRAGONGLASS_AVAIL = 1;
	public static final int[] DRAGONSTONE = {850/Tile.TILE_SIZE_IN_PIXELS, 1160/Tile.TILE_SIZE_IN_PIXELS};
	public static final double SEA_CRITERION = 0.35;
	public static final double[] KINGS_LANDING = {680/Tile.TILE_SIZE_IN_PIXELS, 1240/Tile.TILE_SIZE_IN_PIXELS};
	public static final double[] OLDTOWN = {200/Tile.TILE_SIZE_IN_PIXELS, 1620/Tile.TILE_SIZE_IN_PIXELS};
	public static final double[] CASTERLY_ROCK = {200/Tile.TILE_SIZE_IN_PIXELS, 1175/Tile.TILE_SIZE_IN_PIXELS};
	public static final double[] WINTERFELL = {500/Tile.TILE_SIZE_IN_PIXELS, 445/Tile.TILE_SIZE_IN_PIXELS};
	public static final double[] IRON_ISLANDS = {210/Tile.TILE_SIZE_IN_PIXELS, 970/Tile.TILE_SIZE_IN_PIXELS};
}
