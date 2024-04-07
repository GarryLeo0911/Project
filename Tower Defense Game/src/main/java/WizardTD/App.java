package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.MouseEvent;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


import java.util.*;


public class App extends PApplet {
    public static final int CELLSIZE = 32;
    public static final int SIDEBAR = 120;
    public static final int TOPBAR = 40;
    public static final int BOARD_WIDTH = 20;

    public static int WIDTH = CELLSIZE*BOARD_WIDTH+SIDEBAR;
    public static int HEIGHT = BOARD_WIDTH*CELLSIZE+TOPBAR;

    public static final int FPS = 60;

    public String configPath;

    public Random random = new Random();

    //load the map condition
    public ArrayList<ArrayList<Character>> processed_map_data;

    //initiate the json object
    JSONObject jsonData;
    String layout;

    //initiate the lane grid
    PImage straight_lane;
    PImage corner_lane;
    PImage T_lane;
    PImage crossing_lane;

    //initiate the environment grid
    PImage grass;
    PImage shrub;

    //initiate the Wizard's house
    PImage house;

    //initiate the monster's image
    PImage gremlin;
    PImage gremlin1;
    PImage gremlin2;
    PImage gremlin3;
    PImage gremlin4;
    PImage gremlin5;
    PImage worm;
    PImage beetle;
	
    //initiate the tower's image
    PImage tower0;
    PImage tower1;
    PImage tower2;

    //initiate the fireball's image
    PImage fireball;

    //initiate the monster's parameters
    String type;
    int hp;
    float speed;
    float armour;
    int mana_gained_on_kill;

    //initiate the monsters in wave
    ArrayList<ArrayList<Monster>> monsters;
    Wave wave;
    java.util.ArrayList<Integer> quantity_per_wave;
    JSONArray wave_Array;
    ArrayList<ArrayList<Integer>> monster_per_wave_per_type;
    //initiate the wave's parameters
    ArrayList<Float> duration_per_wave;
    ArrayList<Float> pre_wave_pause;
    float total_time;

    //initate the bar
    Bar bar;

    //initiate the primary tower's parameters
    ArrayList<ArrayList<Tower>> towers;
    Multiple_tower multiple_tower;

    //initiate the primary mana parameters
    int initial_mana;
    int mana;
    int initial_mana_cap;
    int mana_cap;
    int initial_mana_gained_per_second;
    int mana_gained_per_second;
    int mana_pool_spell_initial_cost;
    int mana_pool_spell_cost;
    int mana_pool_spell_cost_increase_per_use;
    float mana_pool_spell_cap_multiplier;
    float mana_pool_spell_mana_gained_multiplier;
    int mana_upgrade_times;

    //initiate time
    int time;
    float time_in_second;
    int related_time;
    float related_time_in_second;

    //load the map condition
    Map map_example;

    //variables represent whether the button is pressed
    boolean pressed_speed_up_button;
    boolean pressed_pause_button;
    boolean pressed_Build_button;
    boolean pressed_upgrade_range_button;
    boolean pressed_upgrade_speed_button;
    boolean pressed_upgrade_damage_button;
    boolean pressed_mana_pool_button;
    boolean pressed_freeze_button;
    boolean mousePressed_in_map;
    boolean keyrPressed;

    //check whether the game is win or lose
    boolean game_win;
    boolean game_lose;



    public App() {
        this.configPath = "config.json";
        time = 0;

        //initiate the monsters in wave
        monsters = new ArrayList<>();
        quantity_per_wave = new ArrayList<>();
        wave_Array = new JSONArray();
        duration_per_wave = new ArrayList<>();
        total_time = 0;
        pre_wave_pause = new ArrayList<>();
        time_in_second = 0;
        towers = new ArrayList<>();
        keyrPressed = true;
        
        
    }

    /**
     * Initialise the setting of the window size.
     */
	@Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
     */
	@Override
    public void setup() {
        frameRate(FPS);
        time = 0;

        pressed_speed_up_button = false;
        pressed_pause_button = false;
        pressed_Build_button = false;
        pressed_upgrade_range_button = false;
        pressed_upgrade_speed_button = false;
        pressed_upgrade_damage_button = false;
        pressed_mana_pool_button = false;
        pressed_freeze_button = false;

        mousePressed_in_map = false;
        game_win = false;
        game_lose = false;

        // Load images during setup
        //Load the path grid
        straight_lane = loadImage("src/main/resources/WizardTD/path0.png");
        corner_lane = loadImage("src/main/resources/WizardTD/path1.png");
        T_lane = loadImage("src/main/resources/WizardTD/path2.png");
        crossing_lane = loadImage("src/main/resources/WizardTD/path3.png");

        //Load the environment grid
        grass = loadImage("src/main/resources/WizardTD/grass.png");
        shrub = loadImage("src/main/resources/WizardTD/shrub.png");

        //Load the wizzard house
        house = loadImage("src/main/resources/WizardTD/wizard_house.png");

        //Load the monsters
        gremlin = loadImage("src/main/resources/WizardTD/gremlin.png");
        gremlin1 = loadImage("src/main/resources/WizardTD/gremlin1.png");
        gremlin2 = loadImage("src/main/resources/WizardTD/gremlin2.png");
        gremlin3 = loadImage("src/main/resources/WizardTD/gremlin3.png");
        gremlin4 = loadImage("src/main/resources/WizardTD/gremlin4.png");
        gremlin5 = loadImage("src/main/resources/WizardTD/gremlin5.png");
        worm = loadImage("src/main/resources/WizardTD/worm.png");
        beetle = loadImage("src/main/resources/WizardTD/beetle.png");

        //Load the towers' image
        tower0 = loadImage("src/main/resources/WizardTD/tower0.png");
        tower1 = loadImage("src/main/resources/WizardTD/tower1.png");
        tower2 = loadImage("src/main/resources/WizardTD/tower2.png");

        //Load the fireball's image
        fireball = loadImage("src/main/resources/WizardTD/fireball.png");

        //load the json file
        jsonData = loadJSONObject(configPath);
        layout = jsonData.getString("layout");

        //load the map condition
        map_example = new Map(layout, this);
        processed_map_data = map_example.processed_map_data;

        //initiate the wave parameters
        wave = new Wave(jsonData, this, this);
        monsters = wave.getMonsters();
        wave_Array = jsonData.getJSONArray("waves");
        quantity_per_wave = wave.get_total_quantity_ArrayList();
        duration_per_wave = wave.get_time_per_wave();
        total_time = wave.get_total_time();
        pre_wave_pause = wave.get_pre_wave_pause();

        //initiate the bar
        bar = new Bar(this, mouseX, mouseY, mousePressed, pressed_speed_up_button, pressed_pause_button, pressed_Build_button, pressed_upgrade_range_button, pressed_upgrade_speed_button, pressed_upgrade_damage_button, pressed_mana_pool_button);

        //initiate the initial multiple tower
        multiple_tower = new Multiple_tower(this, this);
        towers = multiple_tower.get_towers();

        //initiate the primary mana parameters
        initial_mana = jsonData.getInt("initial_mana");
        mana = initial_mana;
        initial_mana_cap = jsonData.getInt("initial_mana_cap");
        mana_cap = initial_mana_cap;
        initial_mana_gained_per_second = jsonData.getInt("initial_mana_gained_per_second");
        mana_gained_per_second = initial_mana_gained_per_second;
        mana_pool_spell_initial_cost = jsonData.getInt("mana_pool_spell_initial_cost");
        mana_pool_spell_cost = mana_pool_spell_initial_cost;
        mana_pool_spell_cost_increase_per_use = jsonData.getInt("mana_pool_spell_cost_increase_per_use");
        mana_pool_spell_cap_multiplier = jsonData.getFloat("mana_pool_spell_cap_multiplier");
        mana_pool_spell_mana_gained_multiplier = jsonData.getFloat("mana_pool_spell_mana_gained_multiplier")-1;
        mana_upgrade_times = 0;
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
	@Override
    public void keyPressed(){
        if (key == 102) {
            pressed_speed_up_button = true;
        }
        if (key == 112) {
            pressed_pause_button = true;
        }
        if (key == 116) {
            pressed_Build_button = true;
        }
        if (key == 49) {
            pressed_upgrade_range_button = true;
        }
        if (key == 50) {
            pressed_upgrade_speed_button = true;
        }
        if (key == 51) {
            pressed_upgrade_damage_button = true;
        }
        if (key == 109) {
            pressed_mana_pool_button = true;
        }
        if (key == 99) {
            pressed_freeze_button = true;
        }
        if (key == 114 && game_lose && !keyrPressed) {
            game_lose = false;
            setup();
            keyrPressed = true;
        }
    }

    /**
     * Receive key released signal from the keyboard.
     */
	@Override
    public void keyReleased(){
        if (key == 102) {
            pressed_speed_up_button = false;
        }
        if (key == 112) {
            pressed_pause_button = false;
        }
        if (key == 116) {
            pressed_Build_button = false;
        }
        if (key == 49) {
            pressed_upgrade_range_button = false;
        }
        if (key == 50) {
            pressed_upgrade_speed_button = false;
        }
        if (key == 51) {
            pressed_upgrade_damage_button = false;
        }
        if (key == 109) {
            pressed_mana_pool_button = false;
        }
        if (key == 99) {
            pressed_freeze_button = false;
        }
        if (key == 114) {
            keyrPressed = false;
        }
    }



    @Override
    public void mouseReleased(MouseEvent e) {
        if (mouseX > 650 && mouseX < 700 && mouseY > 47 && mouseY < 97) {
            pressed_speed_up_button = !pressed_speed_up_button;
        }
        if (mouseX > 650 && mouseX < 700 && mouseY > 111 && mouseY < 161) {
            pressed_pause_button = !pressed_pause_button;
        }
        if (mouseX > 650 && mouseX < 700 && mouseY > 175 && mouseY < 225) {
            pressed_Build_button = !pressed_Build_button;
        }
        if (mouseX > 650 && mouseX < 700 && mouseY > 239 && mouseY < 289) {
            pressed_upgrade_range_button = !pressed_upgrade_range_button;
        }
        if (mouseX > 650 && mouseX < 700 && mouseY > 303 && mouseY < 353) {
            pressed_upgrade_speed_button = !pressed_upgrade_speed_button;
        }
        if (mouseX > 650 && mouseX < 700 && mouseY > 367 && mouseY < 417) {
            pressed_upgrade_damage_button = !pressed_upgrade_damage_button;
        }
        if (mouseX > 650 && mouseX < 700 && mouseY > 431 && mouseY < 481) {
            pressed_mana_pool_button = !pressed_mana_pool_button;
        }
        if (mouseX > 650 && mouseX < 700 && mouseY > 495 && mouseY < 545) {
            pressed_freeze_button = !pressed_freeze_button;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (mouseX > 0 && mouseX < 640 && mouseY > 40 && mouseY < 680) {
            mousePressed_in_map = true;
        }
    }   

    /*@Override
    public void mouseDragged(MouseEvent e) {

    }*/

    /**
     * Draw all elements in the game by current frame.
     */
	@Override
    public void draw() {
        background(210,180,140);
        mouseClicked();
        mouseReleased();
        keyPressed();
        if (!keyPressed){
            keyReleased();
        }
        
        if (!game_lose && !game_win) {

            if (!pressed_pause_button && !pressed_speed_up_button) {
                time++;
            } else if (pressed_speed_up_button && !pressed_pause_button) {
                time += 2;
            } else {
                time = time;
            }
            time_in_second = time/60;

            //initiate the map
            for (int y=0; y<processed_map_data.size(); y++){
                for (int x=0; x<processed_map_data.get(y).size(); x++){
                    float xPos = x * CELLSIZE;
                    float yPos = 40 + y * CELLSIZE;
                    char symbol = processed_map_data.get(y).get(x);
                    PImage imageToDraw = null;
                    switch (symbol){
                        case 'S':
                            imageToDraw = shrub;
                            image(imageToDraw, xPos, yPos, 32, 32);
                            break;
                        case 'X':
                            PImage original_imageToDraw = map_example.choose_path_type(y, x);
                            double rotate_angle = map_example.rotate_angle(y, x);
                            imageToDraw = rotateImageByDegrees(original_imageToDraw, rotate_angle);
                            image(imageToDraw, xPos, yPos, 32, 32);
                            break;
                        case 'W':
                            imageToDraw = grass;
                            image(imageToDraw, xPos, yPos, 32, 32);
                            break;
                        case ' ':
                            imageToDraw = grass;
                            image(imageToDraw, xPos, yPos, 32, 32);
                            break;

                    }
                }
            }
            for (int y=0; y<processed_map_data.size(); y++){
                for (int x=0; x<processed_map_data.get(y).size(); x++){
                    if (processed_map_data.get(y).get(x) == 'W'){
                        float xPos = x * CELLSIZE;
                        float yPos = 40 + y * CELLSIZE;
                        image(house, xPos, yPos, 48, 48);
                    }
                }
            }

            
            //about the mana
            draw_mana_bar(mana, mana_cap);
            if (!pressed_pause_button) {
                mana += mana_increase_by_time(time);
            }
            if (pressed_mana_pool_button) {
                upgrade_mana_pool();
            }

            //draw the bar
            bar.drawBar(pressed_speed_up_button, pressed_pause_button, pressed_Build_button, pressed_upgrade_range_button, pressed_upgrade_speed_button, pressed_upgrade_damage_button, pressed_mana_pool_button, pressed_freeze_button);

            //draw the monsters in wave
            if (!pressed_pause_button) {
                wave.display_monster_in_wave(monsters, time_in_second, pressed_speed_up_button);
            }

            //build the tower and upgrade the tower
            if (mousePressed_in_map) {
                mana = multiple_tower.build_tower(towers, mana, mouseX, mouseY, mousePressed_in_map, pressed_Build_button, pressed_upgrade_range_button, pressed_upgrade_speed_button, pressed_upgrade_damage_button, pressed_freeze_button);
                mousePressed_in_map = false;
            }

            //display the tower's range
            multiple_tower.display_tower_range(towers, mouseX, mouseY);

            //display the tower
            for (int i=0; i < towers.size(); i++){
                for (int j=0; j < towers.get(i).size(); j++){
                    towers.get(i).get(j).display_tower(pressed_pause_button, pressed_speed_up_button);
                }
            }

            //display the monsters
            for (int i=0; i<monsters.size(); i++){
                for (int j=0; j<monsters.get(i).size(); j++){
                    monsters.get(i).get(j).display(pressed_pause_button, pressed_speed_up_button);
                    if (monsters.get(i).get(j).hp <= 0 && !monsters.get(i).get(j).whether_give_mana){
                        if (mana < mana_cap) {
                            mana += monsters.get(i).get(j).mana_gained_on_kill * (1 + mana_upgrade_times*mana_pool_spell_mana_gained_multiplier);
                        } else {
                            mana = mana_cap;
                        }
                        monsters.get(i).get(j).setWhether_give_mana();
                        monsters.get(i).get(j).monster_is_dead = true;
                    }
                    if (monsters.get(i).get(j).reach_the_house && !monsters.get(i).get(j).subtract_mana == false) {
                        mana -= monsters.get(i).get(j).hp;
                        monsters.get(i).get(j).setSubtract_mana();
                    }
                }
            }
            noStroke();    
        }
        if (mana < 0) {
            game_lose = true;
        }
        if (game_lose) {
            fill(0);
            textSize(50);
            String lose = "You Lost!";
            text(lose, 100, 300);
            fill(0);
            textSize(30);
            String restart = "Press 'r' to restart";
            text(restart, 100, 400);
        }

        int totalMonsters = 0;
        int defeatedMonsters = 0;

        for (int y = 0; y < monsters.size(); y++) {
            for (int x = 0; x < monsters.get(y).size(); x++) {
                totalMonsters++;
                if (monsters.get(y).get(x).hp<=0) {
                    defeatedMonsters++;
                }
            }
        }

        if (defeatedMonsters == totalMonsters) {
            game_win = true;
            fill(0);
            textSize(50);
            String win = "You Win!";
            text(win, 100, 300);
        }
    }


    public static void main(String[] args) {
        PApplet.main("WizardTD.App");
    }

    /**
     * Source: https://stackoverflow.com/questions/37758061/rotate-a-buffered-image-in-java
     * @param pimg The image to be rotated
     * @param angle between 0 and 360 degrees
     * @return the new rotated image
     */
    public PImage rotateImageByDegrees(PImage pimg, double angle) {
        BufferedImage img = (BufferedImage) pimg.getNative();
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        PImage result = this.createImage(newWidth, newHeight, ARGB);
        //BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        BufferedImage rotated = (BufferedImage) result.getNative();
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        for (int i = 0; i < newWidth; i++) {
            for (int j = 0; j < newHeight; j++) {
                result.set(i, j, rotated.getRGB(i, j));
            }
        }

        return result;
    }


    //make a function to let the mana increase by time
    public int mana_increase_by_time(int time){
        if (time % 60 == 0){
            if (mana < mana_cap) {
                return mana_gained_per_second;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }


    //make a method to upgrade the mana pool
    public void upgrade_mana_pool(){
        if (mana >= mana_pool_spell_cost){
            mana_upgrade_times += 1;
            mana -= mana_pool_spell_cost;
            mana_pool_spell_cost += mana_pool_spell_cost_increase_per_use;
            mana_cap = (int) (mana_cap * mana_pool_spell_cap_multiplier);
            mana_gained_per_second = (int) (mana_gained_per_second * (1 + mana_upgrade_times*mana_pool_spell_mana_gained_multiplier));
        }
    }


    //draw the bar for mana
    public void draw_mana_bar(int mana, int mana_cap){
        fill(0);
        textSize(30);
        String title = "Mana:";
        text(title, 300, 30);

        fill(255);
        rect(400, 10, 300, 20);

        fill(135,206,250);
        rect(400, 10, 300 * mana / mana_cap, 20);

        fill(0);
        textSize(20);
        String mana_string = Integer.toString(mana) + "/" + Integer.toString(mana_cap);
        text(mana_string, 500, 27);
    }
}
