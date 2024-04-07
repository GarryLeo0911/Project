package WizardTD;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.*;

import processing.data.JSONObject;

public class Monster {
    public PApplet pApplet;

    private JSONObject monster;
    private App app;
    private String type;
    private int max_hp;
    public int hp;
    private float speed;
    private float armour;
    public boolean reach_the_house;
    public boolean be_freezed;
    public int freezing_time;
    int mana_gained_on_kill;
    boolean monster_is_dead;
    boolean whether_give_mana;
    boolean subtract_mana;
    //load the monster's image
    PImage germlin;
    PImage germlin1;
    PImage germlin2;
    PImage germlin3;
    PImage germlin4;
    PImage germlin5;
    PImage worm;
    PImage beetle;

    //create variables represented the monster's spawan location
    int startX;
    int startY;
    //create variables represented the monsters' spawn location
    ArrayList<ArrayList<Integer>> monster_spawn_location  = new ArrayList<>();
    ArrayList<Integer> monster_location = new ArrayList<>();
    ArrayList<int[]> path = new ArrayList<>();

    //create a 2D array to store the map
    private char[][] mapArray;

    //create two variables to store the house's location
    int houseX;
    int houseY;

    //create two variables to store the monsters' location
    private float monsterX;
    private float monsterY;
    private int currentPoint;

    //set a variable to tell the monster whether to display
    public boolean whether_spawn_monster=false;

    //set an ArrayList to store all the fireball will hit the monster
    ArrayList<Fireball> upcoming_fireballs;

    //make a vairable used to display the dying monster
    public int dying_time;


    // Constructor
    public Monster(JSONObject monster, App app, PApplet pApplet) {
        this.monster = monster;
        this.app = app;
        this.pApplet = pApplet;
        //initiate the monster's attributes
        type = monster.getString("type");
        max_hp = monster.getInt("hp");
        hp = max_hp;
        speed = monster.getFloat("speed");
        be_freezed = false;
        freezing_time = 180;
        armour = monster.getFloat("armour");
        mana_gained_on_kill = monster.getInt("mana_gained_on_kill");
        //load the image
        germlin = app.gremlin;
        germlin1 = app.gremlin1;
        germlin2 = app.gremlin2;
        germlin3 = app.gremlin3;
        germlin4 = app.gremlin4;
        germlin5 = app.gremlin5;
        worm = app.worm;
        beetle = app.beetle;
        //initiate the monster's spawn location
        monster_spawn_location = monster_spawn_location();
        monster_location = generate_monster();
        //initiate the map's condition
        mapArray = getMapAsArray();
        //get the house's location
        houseX = house_location()[0];
        houseY = house_location()[1];
        //initiate the monsters' spawn loaction
        startX = monster_location.get(0);
        startY = monster_location.get(1);

        //initiate the monsters' location
        monsterX = startX;
        monsterY = startY;
        currentPoint = 0;
        reach_the_house = false;

        //set the monster's route
        path = findPath(startX, startY);

        //set the monster whether to display
        whether_spawn_monster = false;

        //set the upcoming_fireballs
        upcoming_fireballs = new ArrayList<>();

        //set the dying time
        dying_time = 0;
        monster_is_dead = false;
        whether_give_mana = false;
        subtract_mana = false;
    }

    public void setWhether_give_mana(){
        this.whether_give_mana = true;
    }

    public void setSubtract_mana(){
        this.subtract_mana = false;
    }

    public void setWhether_spawn_monster(){
        this.whether_spawn_monster = true;
    }

    public void setFreezing(){
        be_freezed = true;
    }

    //create a method to add the fireball to the upcoming_fireballs
    public void addFireball(Fireball fireball){
        upcoming_fireballs.add(fireball);
    }

    public float getMonsterX(){
        return monsterX;
    }

    public float getMonsterY(){
        return monsterY;
    }

    public int getCurrentPoint(){
        return currentPoint;
    }

    public void setMonsterX(float monsterX){
        this.monsterX = monsterX;
    }

    public void setMonsterY(float monsterY){
        this.monsterY = monsterY;
    }

    public void setCurrentPoint(int currentPoint){
        this.currentPoint = currentPoint;
    }

    //create a method to tell me where can I spawn the monsters
    public ArrayList<ArrayList<Integer>> monster_spawn_location() {
        for (int y = 0; y < app.processed_map_data.size(); y++) {
            for (int x = 0; x < app.processed_map_data.get(y).size(); x++) {
                ArrayList<Integer> coordinate = new ArrayList<>();
                if (y == 0 && app.processed_map_data.get(y).get(x) == 'X') {
                    coordinate.add(x);
                    coordinate.add(y);
                    monster_spawn_location.add(coordinate);
                } else if (x == 0 && app.processed_map_data.get(y).get(x) == 'X') {
                    coordinate.add(x);
                    coordinate.add(y);
                    monster_spawn_location.add(coordinate);
                } else if (x == 19 && app.processed_map_data.get(y).get(x) == 'X') {
                    coordinate.add(x);
                    coordinate.add(y);
                    monster_spawn_location.add(coordinate);
                } else if (y == 19 && app.processed_map_data.get(y).get(x) == 'X') {
                    coordinate.add(x);
                    coordinate.add(y);
                    monster_spawn_location.add(coordinate);
                }
            }
        }
        return monster_spawn_location;
    }

    //create a method to tell the program where to generate the monster
    public ArrayList<Integer> generate_monster() {
        if (!monster_spawn_location.isEmpty()) {
            Random random = new Random();
            int random_number = random.nextInt(monster_spawn_location.size());
            monster_location.add(monster_spawn_location.get(random_number).get(0));
            monster_location.add(monster_spawn_location.get(random_number).get(1));
            return monster_location;
        }
        return null;
    }

    //define a method to tell the program where is the house
    public int[] house_location() {
        for (int y = 0; y < app.processed_map_data.size(); y++) {
            for (int x = 0; x < app.processed_map_data.get(y).size(); x++) {
                if (app.processed_map_data.get(y).get(x) == 'W') {
                    houseX = x;
                    houseY = y;
                }
            }
        }
        int[] house_location = new int[2];
        house_location[0] = houseX;
        house_location[1] = houseY;
        return house_location;
    }

    //Begin to set the route for the monster
    // Create a method to convert the processed_map_data ArrayList to a 2D char array.
     public char[][] getMapAsArray() {
        mapArray = new char[app.processed_map_data.size()][];
        for (int i = 0; i < app.processed_map_data.size(); i++) {
            ArrayList<Character> row = app.processed_map_data.get(i);
            mapArray[i] = new char[row.size()];
            for (int j = 0; j < row.size(); j++) {
                mapArray[i][j] = row.get(j);
            }
        }
        return mapArray;
    }
    
    //create a method to find the route for the monster
    public ArrayList<int[]> findPath(int startX, int startY) {
        boolean[][] visited = new boolean[20][20];
        ArrayList<int[]> path = new ArrayList<>();
        if (depthFirstSearch(startX, startY, visited, path)){
            return path;
        } else {
            return null;
        }
    }

    //create a method for depth first search
    public boolean depthFirstSearch(int startX, int startY, boolean[][] visited, ArrayList<int[]> path) {
        if (startX<0 || startY<0 || startX>=mapArray.length || startY>=mapArray[0].length || visited[startY][startX]) {
            return false;
        }

        visited[startY][startX]=true;
        path.add(new int[]{startX, startY});

        if (mapArray[startY][startX]=='W') {
            return true;
        }

        if (mapArray[startY][startX]!='X') {
            path.remove(path.size()-1);
            return false;
        }

        if (depthFirstSearch(startX+1, startY, visited, path) ||
            depthFirstSearch(startX-1, startY, visited, path) ||
            depthFirstSearch(startX, startY+1, visited, path) ||
            depthFirstSearch(startX, startY-1, visited, path)) {
            return true;
        }

        path.remove(path.size()-1);
        return false;
    }

    public void updateLocation(boolean pressed_speed_up_button) {
        if (whether_spawn_monster) {
            if (path != null && path.size() > 0 && hp > 0) {
                if (currentPoint >= 0 && currentPoint < path.size()) {
                    int targetX = path.get(currentPoint)[0];
                    int targetY = path.get(currentPoint)[1];

                    // Calculate the distance between the monster and the target
                    float dx = targetX - monsterX;
                    float dy = targetY - monsterY;
                    float distance = (float) Math.sqrt(dx * dx + dy * dy);

                    // Check if the monster has reached the target cell
                    if (distance < 0.1) {
                        currentPoint++;  // Move to the next target
                    } else {
                        float moveX;
                        float moveY;
                        if (!pressed_speed_up_button) {
                            if (be_freezed == false) {
                                moveX = (dx / (distance * 32)) * speed;
                                moveY = (dy / (distance * 32)) * speed;
                                freezing_time = 180;
                            } else {
                                moveX = (dx / (distance * 32)) * speed / 2;
                                moveY = (dy / (distance * 32)) * speed / 2;
                                freezing_time -= 1;
                                if (freezing_time == 0) {
                                    be_freezed = false;
                                }
                            }
                        } else {
                            if (be_freezed == false) {
                                moveX = (dx / (distance * 32)) * speed * 2;
                                moveY = (dy / (distance * 32)) * speed * 2;
                                freezing_time = 180;
                            } else {
                                moveX = (dx / (distance * 32)) * speed;
                                moveY = (dy / (distance * 32)) * speed;
                                freezing_time -= 1;
                                if (freezing_time == 0) {
                                    be_freezed = false;
                                }
                            }
                        }
                        monsterX += moveX;
                        monsterY += moveY;
                    }
                }
            }
        }
    }

    //draw the hp bar for the monster
    public void display_monster_hp() {
        pApplet.fill(0);
        pApplet.rect(monsterX*32 + 2, monsterY*32 + 35, 28, 5);
        pApplet.fill(255, 0, 0);
        pApplet.rect(monsterX*32 + 2, monsterY*32 + 35, 28 * hp / max_hp, 5);
    }

    //make a method used to display the dying monster
    public void display_dying_monster() {
        if (dying_time >= 0 && dying_time < 4) {
            pApplet.image(germlin1, monsterX*32, monsterY*32 + 40, 32, 32);
        }
        if (dying_time >= 4 && dying_time < 8) {
            pApplet.image(germlin2, monsterX*32, monsterY*32 + 40, 32, 32);
        }
        if (dying_time >= 8 && dying_time < 12) {
            pApplet.image(germlin3, monsterX*32, monsterY*32 + 40, 32, 32);
        }
        if (dying_time >= 12 && dying_time < 16) {
            pApplet.image(germlin4, monsterX*32, monsterY*32 + 40, 32, 32);
        }
        if (dying_time >= 16 && dying_time < 20) {
            pApplet.image(germlin5, monsterX*32, monsterY*32 + 40, 32, 32);
        }

        dying_time++;
    }

    public void display(boolean pressed_pause_button, boolean pressed_speed_up_button) {
        pApplet.noStroke();
        if (whether_spawn_monster) {
            // make the monster be hit by the fireball
            for (int i = 0; i < upcoming_fireballs.size(); i++) {
                if (upcoming_fireballs.get(i).reachDestination) {
                    hp -= upcoming_fireballs.get(i).damage * armour;
                    if (upcoming_fireballs.get(i).freezing_ball == true) {
                        setFreezing();
                    }
                    upcoming_fireballs.remove(i);
                }
            }

            // Call the updateLocation method to update the monster's position
            if (!pressed_pause_button){
                updateLocation(pressed_speed_up_button);
            }

            // Draw the monster at its updated position
            if (path != null && path.size() > 0) {
                if (currentPoint >= 0 && currentPoint < path.size() && hp > 0) {
                    // draw the moving monster
                    pApplet.image(germlin, monsterX * 32, monsterY * 32 + 40, 32, 32);
                    display_monster_hp();
                }
                if (currentPoint >= 0 && currentPoint < path.size() && hp <= 0) {
                    // draw the dying monster
                    display_dying_monster();
                }
            }
            float dx = houseX - monsterX;
            float dy = houseY - monsterY;
            float distance = (float) Math.sqrt(dx * dx + dy * dy);
            if (distance < 0.1 && hp > 0) {
                reach_the_house = true;
                subtract_mana = true;
                monster_location = generate_monster();
                monsterX = monster_location.get(0);
                monsterY = monster_location.get(1);
                setCurrentPoint(0);
                path = findPath(monster_location.get(0), monster_location.get(1));
            } 
        }
    }
}