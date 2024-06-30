package Rooms;

import entity.TileMap;
import entity.Entity;
import Util.AnimationSet;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.ArrayList;
import java.util.List;

public class OverWorld extends Room {

    private Texture Grass1, brownGrass1, brownGrass2, brownGrass3, HighGrass, road, northRoad, southRoad, brownGrass4, brownGrass5, brownGrass6, brownGrass7;
    private Texture pea1, pea2, pea3, pea4, pea5, pea6;
    private List<TextureRegion> pilar;
    private List<TextureRegion> store;
    private List<TextureRegion> center;
    private List<TextureRegion> trees;
    private List<TextureRegion> librery;

    public OverWorld(TileMap map, List<Entity> entities) {
        super(map, entities);
    }

    @Override
    public void initialize() {
        // Inicializar texturas
        Grass1 = new Texture("resources/Tiles/grass.png");
        brownGrass1 = new Texture("resources/Tiles/Path/brownPath_west.png");
        brownGrass2 = new Texture("resources/Tiles/Path/brownPath_center.png");
        brownGrass3 = new Texture("resources/Tiles/Path/brownPath_east.png");
        brownGrass4 = new Texture("resources/Tiles/Path/brownPath_north.png");
        brownGrass5 = new Texture("resources/Tiles/Path/brownPath_south.png");
        HighGrass = new Texture("resources/Tiles/high_Grass.png");
        road = new Texture("resources/Tiles/Road/roadPath.png");
        northRoad = new Texture("resources/Tiles/Road/roadPath_North.png");
        southRoad = new Texture("resources/Tiles/Road/roadPath_South.png");
        pea1 = new Texture("resources/Tiles/Pea/pea_center.png");
        pea2 = new Texture("resources/Tiles/Pea/pea_east.png");
        pea3 = new Texture("resources/Tiles/Pea/pea_north.png");
        pea4 = new Texture("resources/Tiles/Pea/pea_west.png");
        pea5 = new Texture("resources/Tiles/Pea/pea_north_east.png");
        pea6 = new Texture("resources/Tiles/Pea/pea_north_west.png");

        store = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            store.add(new TextureRegion(new Texture("resources/Tiles/Store/pokeStore_" + i + ".png")));
        }

        center = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            center.add(new TextureRegion(new Texture("resources/Tiles/Center/pokeCenter_" + i + ".png")));
        }

        trees = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            trees.add(new TextureRegion(new Texture("resources/Tiles/Tree/tree_" + i + ".png")));
        }

        librery = new ArrayList<>();
        for (int i = 0; i < 54; i++) {
            librery.add(new TextureRegion(new Texture("resources/Tiles/UcaLib/librery_" + i + ".png")));
        }

        pilar = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            pilar.add(new TextureRegion(new Texture("resources/Tiles/Pilar/pilar_"+i+".png")));
        }

        // Inicializar el mapa
        for (int y = 0; y < map.getHeight(); y++) {
            map.setTile(10, y, brownGrass2);
            map.setTile(11, y, brownGrass2);
            map.setTile(9, y, brownGrass2);
            map.setTile(8, y, new TextureRegion(brownGrass1));
            map.setTile(12, y, new TextureRegion(brownGrass3));
        }

        int[][] highGrassRegions = {{10, 19, 13, 18}, {22, 25, 13, 18}, {22, 25, 2, 7}};
        for (int[] region : highGrassRegions) {
            int startY = region[0];
            int endY = region[1];
            int startX = region[2];
            int endX = region[3];
            for (int y = startY; y <= endY; y++) {
                for (int x = startX; x < endX; x++) {
                    map.setTile(x, y, new TextureRegion(HighGrass));
                }
            }
        }

        int[][] treePositions = {{0, 2}, {18, 2}, {0, 10}, {0, 14}, {0, 18}, {18, 10}, {18, 14}, {18, 18}, {18, 6}, {0, 6}, {0, 22}, {0, 25}, {18, 22}, {18, 25}, {18, 33}, {0, 33}};
        for (int[] pos : treePositions) {
            addTree(map, pos[0], pos[1]);
        }

        int[][] pilarPostion = {{5, 2}, {14, 2}};
        for (int[] pos : pilarPostion) {
            addPilar(map, pos[0], pos[1]);
        }

        addPokeStore(map, 2, 15);
        addPokeCenter(map, 2, 21);
        addHorizontalRoad(map, 0, map.getWidth() - 1, 28);
        addUcaLib(map, 6, 35);
        addEntrance(map, 6, 0);

        // Añadir entidad del jugador
    }

    @Override
    public String getId() {
        return "OverWorld";
    }

    private void addTree(TileMap map, int x, int y) {
        int[][] treeCoords = {
                {x, y},
                {x + 1, y},
                {x, y - 1},
                {x + 1, y - 1},
                {x, y - 2},
                {x + 1, y - 2}
        };
        for (int i = 0; i < 6; i++) {
            entities.add(new Entity(map, treeCoords[i][0], treeCoords[i][1], trees.get(i)));
        }
    }

    private void addPilar(TileMap map, int x, int y ){
        int[][] pilarCoords = {
                {x, y},
                {x + 1, y},
                {x, y - 1},
                {x + 1, y - 1},
                {x, y - 2},
                {x + 1, y - 2},
        };
        for (int i = 0; i < 6; i++) {
            entities.add(new Entity(map, pilarCoords[i][0],pilarCoords[i][1], pilar.get(i)));
        }
    }

    private void addPokeStore(TileMap map, int startX, int startY) {
        int[][] storeLayout = {
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 10, 11},
                {12, 13, 14, 15}
        };

        for (int y = 0; y < storeLayout.length; y++) {
            for (int x = 0; x < storeLayout[y].length; x++) {
                int tileIndex = storeLayout[y][x];
                if (tileIndex >= 0) {
                    addTile(map, startX + x, startY - y, store.get(tileIndex));
                }
            }
        }
    }

    private void addPokeCenter(TileMap map, int startX, int startY) {
        int[][] centerLayout = {
                {0, 1, 2, 3, 4},
                {5, 6, 7, 8, 9},
                {10, 11, 12, 13, 14},
                {15, 16, 17, 18, 19},
                {20, 21, 22, 23, 24}
        };

        for (int y = 0; y < centerLayout.length; y++) {
            for (int x = 0; x < centerLayout[y].length; x++) {
                int tileIndex = centerLayout[y][x];
                if (tileIndex >= 0) {
                    addTile(map, startX + x, startY - y, center.get(tileIndex));
                }
            }
        }
    }

    private void addUcaLib(TileMap map, int startX, int startY) {
        int[][] ucaLayout = {
                //{0, 1, 2, 3, 4, 5, 6, 7, 8},
                {9, 10, 11, 12, 13, 14, 15, 16, 17},
                {18, 19, 20, 21, 22, 23, 24, 25, 26},
                {27, 28, 29, 30, 31, 32, 33, 34, 35},
                {36, 37, 38, 39, 40, 41, 42, 43, 44},
                {45, 46, 47, 48, 49, 50, 51, 52, 53},
        };
        for (int y = 0; y < ucaLayout.length; y++) {
            for (int x = 0; x < ucaLayout[y].length; x++) {
                int tileIndex = ucaLayout[y][x];
                if (tileIndex >= 0) {
                    addTile(map, startX + x, startY - y, librery.get(tileIndex));
                }
            }
        }
    }

    private void addHorizontalRoad(TileMap map, int startX, int endX, int y) {
        for (int x = startX; x <= endX; x++) {
            map.setTile(x, y, new TextureRegion(road));        // Camino central
            map.setTile(x, y + 1, new TextureRegion(northRoad));  // Parte superior del camino
            map.setTile(x, y - 1, new TextureRegion(southRoad));  // Parte inferior del camino
        }
    }

    private void addEntrance(TileMap map, int startX, int y) {

        int[][] layout = {
                {startX, y, 4},
                {startX + 1, y, 1},
                {startX + 2, y, 1},
                {startX + 3, y, 1},
                {startX + 4, y, 1},
                {startX + 5, y, 1},
                {startX + 6, y, 1},
                {startX + 7, y, 1},
                {startX + 8, y, 2},
                {startX, y + 1, 6},
                {startX + 1, y + 1, 3},
                {startX + 2, y + 1, 3},
                {startX + 3, y + 1, 3},
                {startX + 4, y + 1, 3},
                {startX + 5, y + 1, 3},
                {startX + 6, y + 1, 3},
                {startX + 7, y + 1, 3},
                {startX + 8, y + 1, 5}
        };

        for (int[] pos : layout) {
            int x = pos[0];
            int tileY = pos[1];
            int textureIndex = pos[2];

            TextureRegion texture;
            switch (textureIndex) {
                case 1:
                    texture = new TextureRegion(pea1);
                    break;
                case 2:
                    texture = new TextureRegion(pea2);
                    break;
                case 3:
                    texture = new TextureRegion(pea3);
                    break;
                case 4:
                    texture = new TextureRegion(pea4);
                    break;
                case 5:
                    texture = new TextureRegion(pea5);
                    break;
                case 6:
                    texture = new TextureRegion(pea6);
                    break;
                default:
                    texture = new TextureRegion(pea1);
                    break;
            }
            map.setTile(x, tileY, texture);
        }
    }

    private void addTile(TileMap map, int x, int y, TextureRegion tile) {
        Entity buildingTile = new Entity(map, x, y, tile);
        entities.add(buildingTile);
    }
}
