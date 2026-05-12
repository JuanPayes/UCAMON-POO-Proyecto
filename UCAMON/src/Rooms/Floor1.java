package Rooms;

import Controller.PlayerController;
import Entities.Camara;
import Entities.RenderableEntity;
import GameState.ExploringState;
import Tiles.TileMap;
import Util.AnimationSet;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

public class Floor1 extends Room{

    private Texture floorTexture;
    private TextureRegion carpet, floorWall, wall1, wall2, wall3, wall4, wall5, wall6;
    private List<TextureRegion> stairs, plants, table, sofa, bookShelf;
    private TileMap floor;

    public Floor1(List<RenderableEntity> entities){
        super(null, entities);
    }


    @Override
    public void initialize() {
        floorTexture = new Texture("resources/Tiles/Floors/LibFloor.png");
        carpet = new TextureRegion(new Texture("resources/Tiles/Floors/carpet_0.png"));
        wall1 = new TextureRegion(new Texture("resources/Tiles/Floors/Wall/LibWall_0.png"));
        wall2 = new TextureRegion(new Texture("resources/Tiles/Floors/Wall/LibWall_1.png"));
        wall3 = new TextureRegion(new Texture("resources/Tiles/Floors/Wall/LibWall_2.png"));
        wall4 = new TextureRegion(new Texture("resources/Tiles/Floors/Wall/LibWall_3.png"));
        wall5 =  new TextureRegion(new Texture("resources/Tiles/Floors/Wall/LibWall_4.png"));
        wall6 =  new TextureRegion(new Texture("resources/Tiles/Floors/Wall/LibWall_5.png"));
        floorWall = new TextureRegion(new Texture("resources/Tiles/Floors/LibFloor_1.png"));

        plants = new ArrayList();
        for (int i = 0; i < 2; i++) {
            plants.add(new TextureRegion(new Texture("resources/Tiles/Floors/Plant/plant_" + i + ".png")));
        }
        stairs = new ArrayList<TextureRegion>();
        for (int i = 0; i < 6; i++) {
            stairs.add(new TextureRegion(new Texture("resources/Tiles/Floors/Stairs/stairway_"+i+".png")));
        }
        table = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            table.add(new TextureRegion(new Texture("resources/Tiles/Floors/Table/computerTable_"+i+".png")));
        }
        sofa = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            sofa.add(new TextureRegion(new Texture("resources/Tiles/Floors/Sofa/sofa_"+i+".png")));
        }
        bookShelf = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            bookShelf.add(new TextureRegion(new Texture("resources/Tiles/Floors/BookShelf/bookshelf_"+i+".png")));
        }

        floor = new TileMap(13, 21, floorTexture);

        this.map = floor;
        map.setTile(6, 0, carpet);

        for (int x = 0; x < map.getWidth(); x++) {
            addTile(floor, x, 20, wall1);
            addTile(floor, x, 19, wall2);
        }
        for (int x = 0; x < 4; x++) {
            addTile(floor, x, 10, wall2);
            addTile(floor,x, 11, wall1);
            addTile(floor, x, 12, floorWall);
        }
        addTile(floor,4, 10, wall4);
        addTile(floor, 4, 11, wall3);
        addTile(floor, 4, 12, floorWall);
        for (int x = 8; x < 13; x++) {
            addTile(floor, x, 10, wall2);
            addTile(floor,x, 11, wall1);
            addTile(floor, x, 12, floorWall);
        }
        addTile(floor,8, 10, wall6);
        addTile(floor,8, 11, wall5);
        addTile(floor,8, 12, floorWall);

        int[][] plantPosition = {{0, 2},{0, 5}, {0, 8}, {0, 15}, {0, 18},
                {12, 2}, {12, 5}, {12, 8}, {12, 15}, {12, 18}};
        for (int[] pos : plantPosition) {
            addPlant(map, pos[0], pos[1]);
        }
        int[][] tablePosition = {{1, 8}, {1, 5}, {1, 2}, {2, 16}};
        for (int[] pos : tablePosition){
            addTable(map, pos[0], pos[1]);
        }

        int[][] sofaPosition = {{9, 10}};
        for(int[] pos : sofaPosition){
            addSofa(map, pos[0], pos[1]);
        }

        int[][] bookShelfPosition = {{2,19},{9,19}};
        for (int[] pos : bookShelfPosition){
            addBookShelf(map, pos[0], pos[1]);
        }
    }

    private void addPlant(TileMap map, int startX, int startY) {
        int [][] plantLayout ={
                {0},
                {1}
        };

        for (int y = 0; y < plantLayout.length; y++) {
            for (int x = 0; x < plantLayout[y].length; x++) {
                int tileIndex = plantLayout[y][x];
                if (tileIndex >= 0) {
                    addTile(map, startX + x, startY - y, plants.get(tileIndex));
                }
            }
        }
    }

    private void addTable(TileMap map, int startX, int startY){
        int [][] tableLayout = {
                {3, 4, 5},
                {0, 1, 2}
        };
        for (int y = 0; y < tableLayout.length; y++) {
            for (int x = 0; x < tableLayout[y].length; x++) {
                int tileIndex = tableLayout[y][x];
                if (tileIndex >= 0) {
                    addTile(map, startX + x, startY - y, table.get(tileIndex));
                }
            }
        }
    }

    private void addSofa(TileMap map, int startX, int startY){
        int [][] sofaLayout = {
                {3, 4, 5},
                {0, 1, 2}
        };
        for (int y = 0; y < sofaLayout.length; y++) {
            for (int x = 0; x < sofaLayout[y].length; x++) {
                int tileIndex = sofaLayout[y][x];
                if (tileIndex >= 0) {
                    addTile(map, startX + x, startY - y, sofa.get(tileIndex));
                }
            }
        }
    }

    private void addBookShelf(TileMap map, int startX, int startY){
        int [][] booklayout = {
                {3,2},
                {0, 1}
        };
        for (int y = 0; y < booklayout.length; y++) {
            for (int x = 0; x < booklayout[y].length; x++) {
                int tileIndex = booklayout[y][x];
                if (tileIndex >= 0) {
                    addTile(map, startX + x, startY - y, bookShelf.get(tileIndex));
                }
            }
        }
    }

    @Override
    public String getID() {
        return "Floor1";
    }

    private void addTile(TileMap map, int x, int y, TextureRegion tile) {
        RenderableEntity buildingTile = new RenderableEntity(map, x, y, tile);
        entities.add(buildingTile);
        map.getTile(x, y).setEntity(buildingTile);
    }

    public List<RenderableEntity> getEntities(){
        return entities;
    }

}

