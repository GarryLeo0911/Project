package WizardTD;

import java.util.*;

import processing.core.PApplet;

public class Multiple_tower {
    App app;
    PApplet pApplet;
    
    //get the map data
    ArrayList<ArrayList<Character>> map_data;

    //get the all the towers
    ArrayList<ArrayList<Tower>> towers = new ArrayList<>();

    //get the mouse on the map location
    private int mouseOnMapX;
    private int mouseOnMapY;


    public Multiple_tower(App app, PApplet pApplet){
        this.app = app;
        this.pApplet = pApplet;

        map_data = app.processed_map_data;
        towers = get_towers();
    }

    //get the mouse location on the map
    public int getMouseOnMapX(int mouseX) {
        if (mouseX > 0 && mouseX < 640) {
            int mouseOnMapX1 = (int) (mouseX/32);
            return mouseOnMapX1;
        }
        return -1;
    }

    public int getMouseOnMapY(int mouseY) {
        if (mouseY > 0 && mouseY < 680) {
            int mouseOnMapY1 = (int) ((mouseY-40)/32);
            return mouseOnMapY1;
        }
        return -1;
    }


    //make a method to get the towers
    public ArrayList<ArrayList<Tower>> get_towers(){
        ArrayList<ArrayList<Tower>> towers1 = new ArrayList<>();
        for(int y = 0; y < map_data.size(); y++){
            ArrayList<Tower> towers_in_line = new ArrayList<>();
            for(int x = 0; x < map_data.get(y).size(); x++){
                towers_in_line.add(new Tower(app.jsonData, app, pApplet, x, y));
            }
            towers1.add(towers_in_line);
        }
        return towers1;
    }


    //build the tower
    public int build_tower(ArrayList<ArrayList<Tower>> towers, int mana, int mouseX, int mouseY, boolean mousePressed_in_map, boolean pressed_Build_button, boolean pressed_upgrade_range_button, boolean pressed_upgrade_speed_button, boolean pressed_upgrade_damage_button, boolean pressed_freeze_button) {
        mouseOnMapX = getMouseOnMapX(mouseX);
        mouseOnMapY = getMouseOnMapY(mouseY);
        if (pressed_Build_button && mana >= 100) {
            if (mouseOnMapX != -1 && mouseOnMapY != -1) {
                if (map_data.get(mouseOnMapY).get(mouseOnMapX) == ' ' && towers.get(mouseOnMapY).get(mouseOnMapX).canBuild == false) {
                    towers.get(mouseOnMapY).get(mouseOnMapX).setWhether_can_build_tower();
                    mana -= 100;
                }
            }
        }
        if (pressed_upgrade_damage_button && mana >= 20) {
            if (mouseOnMapX != -1 && mouseOnMapY != -1) {
                if (map_data.get(mouseOnMapY).get(mouseOnMapX) == ' ' && towers.get(mouseOnMapY).get(mouseOnMapX).tower_damage_grade < 3 && towers.get(mouseOnMapY).get(mouseOnMapX).canBuild) {
                    towers.get(mouseOnMapY).get(mouseOnMapX).setTower_damage_grade();
                    mana -= 20;
                }
            }
        }
        if (pressed_upgrade_range_button && mana >= 20) {
            if (mouseOnMapX != -1 && mouseOnMapY != -1) {
                if (map_data.get(mouseOnMapY).get(mouseOnMapX) == ' ' && towers.get(mouseOnMapY).get(mouseOnMapX).tower_range_grade < 3 && towers.get(mouseOnMapY).get(mouseOnMapX).canBuild) {
                    towers.get(mouseOnMapY).get(mouseOnMapX).setTower_range_grade();
                    mana -= 20;
                }
            }
        }
        if (pressed_upgrade_speed_button && mana >= 20) {
            if (mouseOnMapX != -1 && mouseOnMapY != -1) {
                if (map_data.get(mouseOnMapY).get(mouseOnMapX) == ' ' && towers.get(mouseOnMapY).get(mouseOnMapX).tower_firing_speed_grade < 3 && towers.get(mouseOnMapY).get(mouseOnMapX).canBuild) {
                    towers.get(mouseOnMapY).get(mouseOnMapX).setTower_firing_speed_grade();
                    mana -= 20;
                }
            }
        }
        if (pressed_freeze_button && mana >= 50) {
            if (mouseOnMapX != -1 && mouseOnMapY != -1) {
                if (map_data.get(mouseOnMapY).get(mouseOnMapX) == ' ' && towers.get(mouseOnMapY).get(mouseOnMapX).canBuild && towers.get(mouseOnMapY).get(mouseOnMapX).getFreezing() == false) {
                    towers.get(mouseOnMapY).get(mouseOnMapX).setFreezing();
                    mana -= 50;
                }
            }
        }
        return mana;
    }

    public void display_tower_range(ArrayList<ArrayList<Tower>> towers, int mouseX, int mouseY) {
        mouseOnMapX = getMouseOnMapX(mouseX);
        mouseOnMapY = getMouseOnMapY(mouseY);
        if (mouseOnMapX != -1 && mouseOnMapY != -1) {
            towers.get(mouseOnMapY).get(mouseOnMapX).display_tower_range();
        }
    }
}

