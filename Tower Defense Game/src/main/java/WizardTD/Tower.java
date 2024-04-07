package WizardTD;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;

public class Tower {
    private JSONObject jsonData;
    private App app;
    public PApplet pApplet;
    private int initial_tower_range;
    private int tower_range;
    private float initial_tower_firing_speed;
    private float tower_firing_speed;
    private int initial_tower_damage;
    private int tower_damage;
    private int tower_cost;

    public int tower_range_grade;
    public int tower_firing_speed_grade;
    public int tower_damage_grade;
    public boolean initial_freezing;
    private boolean freezing;

    //get the tower location
    private int towerX;
    private int towerY;

    //load the tower image
    PImage tower0;
    PImage tower1;
    PImage tower2;

    //decide whether the tower can be built
    boolean canBuild;

    //decide whether show the range
    boolean display_range;

    //get all the monsters in the game
    ArrayList<ArrayList<Monster>> monsters;

    //calculate the distance between the tower and the monsters
    ArrayList<ArrayList<Float>> monsters_distance;

    //get the nearest monster
    Monster nearestMonster;

    //begin to set an ArrayList to store the fireballs
    ArrayList<Fireball> fireballs;

    //set the related time
    float related_time;

    //Eazy for testing
    public int tower_range_test;
    public int tower_firing_speed_test;
    public int tower_damage_test;
    public boolean tower_display_range_test;

    public Tower (JSONObject jsonObject, App app, PApplet pApplet, int towerX, int towerY){
        this.jsonData = jsonObject;
        this.app = app;
        this.pApplet = pApplet;

        tower_range_test = 0;
        tower_firing_speed_test = 0;
        tower_damage_test = 0;
        
        initial_tower_range = jsonData.getInt("initial_tower_range");
        initial_tower_firing_speed = jsonData.getFloat("initial_tower_firing_speed");
        initial_tower_damage = jsonData.getInt("initial_tower_damage");
        initial_freezing = false;
        tower_range = initial_tower_range;
        tower_firing_speed = initial_tower_firing_speed;
        tower_damage = initial_tower_damage;
        tower_cost = jsonData.getInt("tower_cost");
        tower_range_grade = 0;
        tower_firing_speed_grade = 0;
        tower_damage_grade = 0;
        freezing = initial_freezing;

        tower0 = app.tower0;
        tower1 = app.tower1;
        tower2 = app.tower2;

        canBuild = false;
        this.towerX = towerX;
        this.towerY = towerY;

        //initiate the monsters
        monsters = app.monsters;

        //calculate the distance between the tower and the monsters
        monsters_distance = calculate_distance();

        //initiate teh related time
        related_time = 0;

        //initiate the fireballs
        fireballs = new ArrayList<>();

        display_range = false;
        tower_display_range_test = false;
    }

    public int getTower_range() {
        return tower_range;
    }

    public float getTower_firing_speed() {
        return tower_firing_speed;
    }

    public int getTower_damage() {
        return tower_damage;
    }

    public int getTower_cost() {
        return tower_cost;
    }

    public void setWhether_can_build_tower() {
        canBuild = true;
    }

    public void setWhether_display_range() {
        display_range = true;
    }

    //let the tower to be upgraded
    public void setTower_range_grade() {
        tower_range_grade += 1;
        if (tower_range_grade == 1) {
            tower_range = initial_tower_range +32;
        } else if (tower_range_grade == 2) {
            tower_range = initial_tower_range + 64;
        } else if (tower_range_grade == 3) {
            tower_range = initial_tower_range + 96;
        }
    }

    public void setTower_firing_speed_grade() {
        tower_firing_speed_grade += 1;
        if (tower_firing_speed_grade == 1) {
            tower_firing_speed = initial_tower_firing_speed + 0.5f;
        } else if (tower_firing_speed_grade == 2) {
            tower_firing_speed = initial_tower_firing_speed + 1.0f;
        } else if (tower_firing_speed_grade == 3) {
            tower_firing_speed = initial_tower_firing_speed + 1.5f;
        }
    }

    public void setTower_damage_grade() {
        tower_damage_grade += 1;
        if (tower_damage_grade == 1) {
            tower_damage = initial_tower_damage + (initial_tower_damage)/2;
        }
        else if (tower_damage_grade == 2) {
            tower_damage = initial_tower_damage + (initial_tower_damage*2)/2;
        }
        else if (tower_damage_grade == 3) {
            tower_damage = initial_tower_damage + (initial_tower_damage*3)/2;
        }
    }

    public void setFreezing() {
        freezing = true;
    }

    public boolean getFreezing() {
        return freezing;
    }


    //calculate the distance between the tower and the monsters
    public ArrayList<ArrayList<Float>> calculate_distance() {
        if (monsters.size()>0) {
            ArrayList<ArrayList<Float>> monsters_distance1 = new ArrayList<ArrayList<Float>>();
            for (int i=0; i<monsters.size(); i++) {
                ArrayList<Float> distance = new ArrayList<Float>();
                for (int j=0; j<monsters.get(i).size(); j++) {
                    float dx = (monsters.get(i).get(j).getMonsterX() - towerX)*32;
                    float dy = (monsters.get(i).get(j).getMonsterY() - towerY)*32;
                    float distance1 = (float) Math.sqrt(dx * dx + dy * dy);
                    distance.add(distance1);
                }
                monsters_distance1.add(distance);
            }
            return monsters_distance1;
        }
        return null;
    }

    //target at the nearest monster
    public Monster target_nearest_monster() {
        Monster nearestMonster1 = null;
        float min_distance = 1000000;
        monsters_distance = calculate_distance();
        if (monsters_distance!=null) {
            for (int x=0; x<monsters_distance.size(); x++) {
                for (int y=0; y<monsters_distance.get(x).size(); y++) {
                    if (monsters_distance.get(x).get(y) < min_distance && monsters_distance.get(x).get(y) < tower_range && monsters.get(x).get(y).hp > 0) {
                        min_distance = monsters_distance.get(x).get(y);
                        nearestMonster1 = monsters.get(x).get(y);
                    }
                }
            }
        }
        return nearestMonster1;
    }

    //begin to fire
    public void fire(boolean pressed_speed_up_button) {
        nearestMonster = target_nearest_monster();
        if (nearestMonster != null) {
            float time_to_shoot_fireball;
            if (pressed_speed_up_button == false){
                time_to_shoot_fireball = 1.0f/tower_firing_speed;
            } else {
                time_to_shoot_fireball = 1.0f/(tower_firing_speed*2);
            }
            if (related_time % time_to_shoot_fireball < (1.0f/60)) {
                Fireball fireball = new Fireball(jsonData, app, pApplet, towerX, towerY, nearestMonster);
                fireballs.add(fireball);
                nearestMonster.addFireball(fireball);
            }
        }
        related_time += 1.0f / 60;
    }

    public void display_tower(boolean pressed_pause_button, boolean pressed_speed_up_button) {
        if (canBuild) {
            //begin to draw the tower by their grades
            if (tower_damage_grade==0 || tower_range_grade==0 || tower_firing_speed_grade==0){
                pApplet.image(tower0, towerX*32, 40+towerY*32, 32, 32);

                if (tower_range_grade >= 1) {
                    pApplet.noFill();
                    pApplet.stroke(138,43,226);
                    pApplet.ellipse((towerX*32)+16-12, (40+towerY*32)+16-12, 5, 5);
                    pApplet.noStroke();
                    tower_range_test = 1;
                }
                if (tower_range_grade >= 2) {
                    pApplet.noFill();
                    pApplet.stroke(138,43,226);
                    pApplet.ellipse((towerX*32)+16, (40+towerY*32)+16-12, 5, 5);
                    pApplet.noStroke();
                    tower_range_test = 2;
                }
                if (tower_range_grade >= 3) {
                    pApplet.noFill();
                    pApplet.stroke(138,43,226);
                    pApplet.ellipse((towerX*32)+16+12, (40+towerY*32)+16-12, 5, 5);
                    pApplet.noStroke();
                    tower_range_test = 3;
                }

                if (tower_damage_grade >= 1) {
                    pApplet.stroke(138,43,226);
                    pApplet.line((towerX*32)+16-12-3, (40+towerY*32)+16+12-4, (towerX*32)+16-12+3, (40+towerY*32)+16+12+4);
                    pApplet.line((towerX*32)+16-12+3, (40+towerY*32)+16+12+4, (towerX*32)+16-12-3, (40+towerY*32)+16+12-4);
                    tower_damage_test = 1;
                }
                if (tower_damage_grade >= 2) {
                    pApplet.stroke(138,43,226);
                    pApplet.line((towerX*32)+16-3, (40+towerY*32)+16+12-4, (towerX*32)+16+3, (40+towerY*32)+16+12+4);
                    pApplet.line((towerX*32)+16+3, (40+towerY*32)+16+12+4, (towerX*32)+16+-3, (40+towerY*32)+16+12-4);
                    tower_damage_test = 2;
                }
                if (tower_damage_grade >= 3) {
                    pApplet.stroke(138,43,226);
                    pApplet.line((towerX*32)+16+12-3, (40+towerY*32)+16+12-4,(towerX*32)+16+12+3, (40+towerY*32)+16+12+4);
                    pApplet.line((towerX*32)+16+12+3, (40+towerY*32)+16+12+4,(towerX*32)+16+12-3, (40+towerY*32)+16+12-4);
                    tower_damage_test = 3;
                }

                if (tower_firing_speed_grade >= 1) {
                    pApplet.noFill();
                    pApplet.stroke(135,206,250);
                    pApplet.rect((towerX*32)+4, (40+towerY*32)+4, 24, 24);
                    tower_firing_speed_test = 1;
                }
                if (tower_firing_speed_grade >= 2) {
                    pApplet.noFill();
                    pApplet.stroke(135,206,250);
                    pApplet.rect((towerX*32)+3, (40+towerY*32)+3, 26, 26);
                    tower_firing_speed_test = 2;
                }
                if(tower_firing_speed_grade >= 3) {
                    pApplet.noFill();
                    pApplet.stroke(135,206,250);
                    pApplet.rect((towerX*32)+2, (40+towerY*32)+2, 28, 28);
                    tower_firing_speed_test = 3;
                }

            }
            if ((tower_damage_grade>0 && tower_firing_speed_grade>0 && tower_range_grade>0) && (tower_damage_grade==1 || tower_range_grade==1 || tower_firing_speed_grade==1)){
                pApplet.image(tower1, towerX*32, 40+towerY*32, 32, 32);

                if (tower_range_grade >= 2) {
                    pApplet.noFill();
                    pApplet.stroke(138,43,226);
                    pApplet.ellipse((towerX*32)+16-12, (40+towerY*32)+16-12, 5, 5);
                    pApplet.noStroke();
                    tower_range_test = 2;
                }
                if (tower_range_grade >= 3) {
                    pApplet.noFill();
                    pApplet.stroke(138,43,226);
                    pApplet.ellipse((towerX*32)+16, (40+towerY*32)+16-12, 5, 5);
                    pApplet.noStroke();
                    tower_range_test = 3;
                }

                if (tower_damage_grade >= 2) {
                    pApplet.stroke(138,43,226);
                    pApplet.line((towerX*32)+16-12-3, (40+towerY*32)+16+12-4, (towerX*32)+16-12+3, (40+towerY*32)+16+12+4);
                    pApplet.line((towerX*32)+16-12+3, (40+towerY*32)+16+12+4, (towerX*32)+16-12-3, (40+towerY*32)+16+12-4);
                    tower_damage_test = 2;
                }
                if (tower_damage_grade >= 3) {
                    pApplet.stroke(138,43,226);
                    pApplet.line((towerX*32)+16-3, (40+towerY*32)+16+12-4, (towerX*32)+16+3, (40+towerY*32)+16+12+4);
                    pApplet.line((towerX*32)+16+3, (40+towerY*32)+16+12+4, (towerX*32)+16+-3, (40+towerY*32)+16+12-4);
                    tower_damage_test = 3;
                }

                if (tower_firing_speed_grade >= 2) {
                    pApplet.noFill();
                    pApplet.stroke(135,206,250);
                    pApplet.rect((towerX*32)+4, (40+towerY*32)+4, 24, 24);
                    tower_firing_speed_test = 2;
                }
                if (tower_firing_speed_grade >= 3) {
                    pApplet.noFill();
                    pApplet.stroke(135,206,250);
                    pApplet.rect((towerX*32)+3, (40+towerY*32)+3, 26, 26);
                    tower_firing_speed_test = 3;
                }

            }

            if (tower_damage_grade>1 && tower_firing_speed_grade>1 && tower_range_grade>1) {
                pApplet.image(tower2, towerX*32, 40+towerY*32, 32, 32);
                if (tower_range_grade >= 3) {
                    pApplet.noFill();
                    pApplet.stroke(138,43,226);
                    pApplet.ellipse((towerX*32)+16-12, (40+towerY*32)+16-12, 5, 5);
                    pApplet.noStroke();
                    tower_range_test = 3;
                }

                if (tower_damage_grade >= 3) {
                    pApplet.stroke(138,43,226);
                    pApplet.line((towerX*32)+16-12-3, (40+towerY*32)+16+12-4, (towerX*32)+16-12+3, (40+towerY*32)+16+12+4);
                    pApplet.line((towerX*32)+16-12+3, (40+towerY*32)+16+12+4, (towerX*32)+16-12-3, (40+towerY*32)+16+12-4);
                    tower_damage_test = 3;
                }

                if (tower_firing_speed_grade >= 3) {
                    pApplet.noFill();
                    pApplet.stroke(135,206,250);
                    pApplet.rect((towerX*32)+4, (40+towerY*32)+4, 24, 24);
                    tower_firing_speed_test = 3;
                }
            }

            if (freezing == true) {
                pApplet.fill(30,144,255);
                pApplet.ellipse((towerX*32)+16, (40+towerY*32)+16, 10, 10);
            }
            //begin to display the fireballs
            if (!pressed_pause_button){
                fire(pressed_speed_up_button);
            }
            if (fireballs != null) {
                for (int i=0; i<fireballs.size(); i++) {
                    fireballs.get(i).drawFireball();
                    if (fireballs.get(i).reachDestination){
                        fireballs.remove(i);
                    }
                }
            }
        }
    }

    public void display_tower_range() {
        if (canBuild) {
            pApplet.noFill();
            pApplet.stroke(255,255,0);
            pApplet.ellipse((towerX*32)+16, (40+towerY*32)+16, tower_range, tower_range);
            tower_display_range_test = true;
        }
    }
}
