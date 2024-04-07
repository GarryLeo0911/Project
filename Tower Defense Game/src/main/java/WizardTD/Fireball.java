package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;

public class Fireball extends Tower{
    int damage;
    boolean freezing_ball;

    //variable represent the fireball's location
    int fireballX;
    int fireballY;

    //variable represent the fireball's made location
    int fireballMadeX;
    int fireballMadeY;
    
    //variable represent the fireball's destination
    float fireballDestinationX;
    float fireballDestinationY;

    //variable represent whether 

    //variable represent whether the fireball reach the destination
    boolean reachDestination;

    //initiate the fireball's image
    PImage fireball;


    public Fireball(JSONObject jsonObject, App app, PApplet pApplet, int towerX, int towerY, Monster monster) {
        super(jsonObject, app, pApplet, towerX, towerY);
        fireballMadeX = towerX;
        fireballMadeY = towerY;
        reachDestination = false;

        //initiate the fireball's location
        fireballX = (fireballMadeX*32)+16;
        fireballY = (fireballMadeY*32)+56;

        //get the destination coordinate
        fireballDestinationX = ((monster.getMonsterX())*32)+16;
        fireballDestinationY = ((monster.getMonsterY())*32)+56;

        //get the fireball's damage
        damage = getTower_damage();
        freezing_ball = getFreezing();

        //get the fireball's image
        fireball = app.fireball;
    }


    //update the fireball's location
    public void updateFireballLocation(){
        //calculate the distance between the fireball and the destination
        float dx = fireballDestinationX - fireballX;
        float dy = fireballDestinationY - fireballY;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        //if the fireball reach the destination, set the reachDestination to true
        if (distance < 16){
            reachDestination = true;
        } else {
            //calculate the fireball's speed
            float speed = 4;
            //calculate the fireball's moving distance
            float moveDistance = speed / distance;
            //calculate the fireball's moving direction
            float xMove = moveDistance * dx;
            float yMove = moveDistance * dy;
            //update the fireball's location
            fireballX += xMove;
            fireballY += yMove;
        }
    }

    //draw the fireball
    public void drawFireball(){
        updateFireballLocation();
        if (reachDestination == false){
            pApplet.image(fireball, fireballX, fireballY, 10, 10);
        }
    }
}
