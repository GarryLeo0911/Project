package WizardTD;

import org.checkerframework.checker.units.qual.s;

import processing.core.PApplet;

public class Bar {
    private PApplet app; // Reference to the PApplet

    // Create variables representing the size of the button
    private int buttonSize;
    private int xCoordinate;
    private int x_intervalBetweenButtons;
    private int y_intervalBetweenButtons;

    //parameter for the speed button
    private int speed_button_yCoordinate;

    //parameter for the pause button
    private int pause_button_yCoordinate;

    //parameter for the build_tower_button
    private int build_tower_button_yCoordinate;

    //parameter for upgrade_range_button
    private int upgrade_range_button_yCoordinate;

    //parameter for upgrade_speed_button
    private int upgrade_speed_button_yCoordinate;

    //parameter for upgrade_damage_button
    private int upgrade_damage_button_yCoordinate;

    //parameter for mana_pool_button
    private int mana_pool_button_yCoordinate;

    //parameter for freezing_button
    private int freezing_button_yCoordinate;

    public int mouseX;
    public int mouseY;

    //check whether press the button
    boolean pressed_speed_up_button;
    boolean pressed_pause_button;
    boolean pressed_Build_button;
    boolean pressed_upgrade_range_button;
    boolean pressed_upgrade_speed_button;
    boolean pressed_upgrade_damage_button;
    boolean pressed_mana_pool_button;
    boolean pressed_freeze_button;

    //Convenient to test
    boolean speed_up_button_color_change;
    boolean pause_button_color_change;
    boolean build_tower_button_color_change;
    boolean upgrade_range_button_color_change;
    boolean upgrade_speed_button_color_change;
    boolean upgrade_damage_button_color_change;
    boolean mana_pool_button_color_change;
    boolean freezing_button_color_change;

    public Bar(PApplet app, int mouseX, int mouseY, boolean mousePressed, boolean pressed_speed_up_button, boolean pressed_pause_button, boolean pressed_Build_button, boolean pressed_upgrade_range_button, boolean pressed_upgrade_speed_button, boolean pressed_upgrade_damage_button, boolean pressed_mana_pool_button) {
        this.app = app ;
        buttonSize = 50;
        x_intervalBetweenButtons = 10;
        y_intervalBetweenButtons = 14;
        xCoordinate = 640 + x_intervalBetweenButtons;

        // Set the y coordinate of the speed button
        speed_button_yCoordinate = 40 + (y_intervalBetweenButtons/2);

        // Set the y coordinate of the pause button
        pause_button_yCoordinate = speed_button_yCoordinate + y_intervalBetweenButtons + buttonSize;

        // Set the y coordinate of the build_tower button
        build_tower_button_yCoordinate = pause_button_yCoordinate + y_intervalBetweenButtons + buttonSize;
        
        //set the y coordinate of the upgrade_range_button
        upgrade_range_button_yCoordinate = build_tower_button_yCoordinate + y_intervalBetweenButtons + buttonSize;

        //set the y coordinate of the upgrade_speed_button
        upgrade_speed_button_yCoordinate = upgrade_range_button_yCoordinate + y_intervalBetweenButtons + buttonSize;

        //set the y coordinate of the upgrade_damage_button
        upgrade_damage_button_yCoordinate = upgrade_speed_button_yCoordinate + y_intervalBetweenButtons + buttonSize;

        //set the y coordinate of the mana_pool_button
        mana_pool_button_yCoordinate = upgrade_damage_button_yCoordinate + y_intervalBetweenButtons + buttonSize;

        //set the y coordinate of the freezing_button
        freezing_button_yCoordinate = mana_pool_button_yCoordinate + y_intervalBetweenButtons + buttonSize;

        //initiate the boolean variable
        this.pressed_speed_up_button = pressed_speed_up_button;
        this.pressed_pause_button = pressed_pause_button;
        this.pressed_Build_button = pressed_Build_button;
        this.pressed_upgrade_range_button = pressed_upgrade_range_button;
        this.pressed_upgrade_speed_button = pressed_upgrade_speed_button;
        this.pressed_upgrade_damage_button = pressed_upgrade_damage_button;
        this.pressed_mana_pool_button = pressed_mana_pool_button;

        //initiate the boolean variable for color change
        speed_up_button_color_change = false;
        pause_button_color_change = false;
        build_tower_button_color_change = false;
        upgrade_range_button_color_change = false;
        upgrade_speed_button_color_change = false;
        upgrade_damage_button_color_change = false;
        mana_pool_button_color_change = false;
        freezing_button_color_change = false;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

    public void drawSpeedButton(boolean pressed_speed_up_button) {
        // Draw the speed button as a square using rect()
        if (pressed_speed_up_button == false) {
            app.fill(0,191,255);
            app.rect(xCoordinate, speed_button_yCoordinate, buttonSize, buttonSize);
            speed_up_button_color_change = false;
        } else {
            app.fill(0,255,0);
            app.rect(xCoordinate, speed_button_yCoordinate, buttonSize, buttonSize);
            speed_up_button_color_change = true;
        }
        
        //Add the text
        app.fill(0);
        app.textSize(12);
        String message = "2x speed";
        app.text(message, xCoordinate + buttonSize + 5, speed_button_yCoordinate + buttonSize/2);
    
        //draw the icon
        app.fill(0);
        app.textSize(30);
        String icon = "FF";
        app.text(icon, xCoordinate + 5, speed_button_yCoordinate + buttonSize/2 + 10);
    }

    public void drawPauseButton(boolean pressed_pause_button) {
        // Draw the pause button as a square using rect()
        if (pressed_pause_button == false) {
            app.fill(0,191,255);
            app.rect(xCoordinate, pause_button_yCoordinate, buttonSize, buttonSize);
            pause_button_color_change = false;
        } else {
            app.fill(0,255,0);
            app.rect(xCoordinate, pause_button_yCoordinate, buttonSize, buttonSize);
            pause_button_color_change = true;
        }

        //Add the text
        app.fill(0);
        app.textSize(12);
        String message = "PAUSE";
        app.text(message, xCoordinate + buttonSize + 5, pause_button_yCoordinate + buttonSize/2);
        
        //draw the icon
        app.fill(0);
        app.textSize(30);
        String icon = "P";
        app.text(icon, xCoordinate + 5, pause_button_yCoordinate + buttonSize/2 + 10);
    }

    public void drawBuildTowerButton(boolean pressed_Build_button) {
        // Draw the build_tower button as a square using rect()
        if (pressed_Build_button == false) {
            app.fill(0,191,255);
            app.rect(xCoordinate, build_tower_button_yCoordinate, buttonSize, buttonSize);
            build_tower_button_color_change = false;
        } else {
            app.fill(0,255,0);
            app.rect(xCoordinate, build_tower_button_yCoordinate, buttonSize, buttonSize);
            build_tower_button_color_change = true;
        }

        //Add the text
        app.fill(0);
        app.textSize(12);
        String message = "Build";
        app.text(message, xCoordinate + buttonSize + 5, build_tower_button_yCoordinate + buttonSize/2 -6);
        String message2 = "Tower";
        app.text(message2, xCoordinate + buttonSize + 5, build_tower_button_yCoordinate + buttonSize/2 + 6);
        
        //draw the icon
        app.fill(0);
        app.textSize(30);
        String icon = "T";
        app.text(icon, xCoordinate + 5, build_tower_button_yCoordinate + buttonSize/2 + 10);
    }

    public void drawUpgradeRangeButton(boolean pressed_upgrade_range_button) {
        // Draw the upgrade_range button as a square using rect()
        if (pressed_upgrade_range_button == false) {
            app.fill(0,191,255);
            app.rect(xCoordinate, upgrade_range_button_yCoordinate, buttonSize, buttonSize);
            upgrade_range_button_color_change = false;
        } else {
            app.fill(0,255,0);
            app.rect(xCoordinate, upgrade_range_button_yCoordinate, buttonSize, buttonSize);
            upgrade_range_button_color_change = true;
        }

        //Add the text
        app.fill(0);
        app.textSize(12);
        String message = "Upgrade";
        app.text(message, xCoordinate + buttonSize + 5, upgrade_range_button_yCoordinate + buttonSize/2 -6);
        String message2 = "Range";
        app.text(message2, xCoordinate + buttonSize + 5, upgrade_range_button_yCoordinate + buttonSize/2 + 6);
        
        //draw the icon
        app.fill(0);
        app.textSize(30);
        String icon = "U1";
        app.text(icon, xCoordinate + 5, upgrade_range_button_yCoordinate + buttonSize/2 + 10);
    }

    public void drawUpgradeSpeedButton(boolean pressed_upgrade_speed_button) {
        // Draw the upgrade_speed button as a square using rect()
        if (pressed_upgrade_speed_button == false) {
            app.fill(0,191,255);
            app.rect(xCoordinate, upgrade_speed_button_yCoordinate, buttonSize, buttonSize);
            upgrade_speed_button_color_change = false;
        } else {
            app.fill(0,255,0);
            app.rect(xCoordinate, upgrade_speed_button_yCoordinate, buttonSize, buttonSize);
            upgrade_speed_button_color_change = true;
        }

        //Add the text
        app.fill(0);
        app.textSize(12);
        String message = "Upgrade";
        app.text(message, xCoordinate + buttonSize + 5, upgrade_speed_button_yCoordinate + buttonSize/2 - 6);
        String message2 = "Speed";
        app.text(message2, xCoordinate + buttonSize + 5, upgrade_speed_button_yCoordinate + buttonSize/2 + 6);
    
        //draw the icon
        app.fill(0);
        app.textSize(30);
        String icon = "U2";
        app.text(icon, xCoordinate + 5, upgrade_speed_button_yCoordinate + buttonSize/2 + 10);
    }

    public void drawUpgradeDamageButton(boolean pressed_upgrade_damage_button) {
        // Draw the upgrade_damage button as a square using rect()
        if (pressed_upgrade_damage_button == false) {
            app.fill(0,191,255);
            app.rect(xCoordinate, upgrade_damage_button_yCoordinate, buttonSize, buttonSize);
            upgrade_damage_button_color_change = false;
        } else {
            app.fill(0,255,0);
            app.rect(xCoordinate, upgrade_damage_button_yCoordinate, buttonSize, buttonSize);
            upgrade_damage_button_color_change = true;
        }
        //Add the text
        app.fill(0);
        app.textSize(12);
        String message = "Upgrade";
        app.text(message, xCoordinate + buttonSize + 5, upgrade_damage_button_yCoordinate + buttonSize/2 - 6);
        String message2 = "Damage";
        app.text(message2, xCoordinate + buttonSize + 5, upgrade_damage_button_yCoordinate + buttonSize/2 + 6);
        
        //draw the icon
        app.fill(0);
        app.textSize(30);
        String icon = "U3";
        app.text(icon, xCoordinate + 5, upgrade_damage_button_yCoordinate + buttonSize/2 + 10);
    }

    public void drawManaPoolButton(boolean pressed_mana_pool_button) {
        // Draw the mana_pool button as a square using rect()
        if (pressed_mana_pool_button == false) {
            app.fill(0,191,255);
            app.rect(xCoordinate, mana_pool_button_yCoordinate, buttonSize, buttonSize);
            mana_pool_button_color_change = false;
        } else {
            app.fill(0,255,0);
            app.rect(xCoordinate, mana_pool_button_yCoordinate, buttonSize, buttonSize);
            mana_pool_button_color_change = true;
        }

        //Add the text
        app.fill(0);
        app.textSize(11);
        String message = "Mana Pool";
        app.text(message, xCoordinate + buttonSize + 5, mana_pool_button_yCoordinate + buttonSize/2);
        String message2 = "cost: 100";
        app.text(message2, xCoordinate + buttonSize + 5, mana_pool_button_yCoordinate + buttonSize/2 + 12);
        
        //draw the icon
        app.fill(0);
        app.textSize(30);
        String icon = "M";
        app.text(icon, xCoordinate + 5, mana_pool_button_yCoordinate + buttonSize/2 + 10);
    }

    public void draw_freezing_button(boolean pressed_freeze_button) {
        // Draw the freezing button as a square using rect()
        if (pressed_freeze_button == false) {
            app.fill(0,191,255);
            app.rect(xCoordinate, freezing_button_yCoordinate, buttonSize, buttonSize);
            freezing_button_color_change = false;
        } else {
            app.fill(0,255,0);
            app.rect(xCoordinate, freezing_button_yCoordinate, buttonSize, buttonSize);
            freezing_button_color_change = true;
        }

        //Add the text
        app.fill(0);
        app.textSize(12);
        String message = "Freezing";
        app.text(message, xCoordinate + buttonSize + 5, freezing_button_yCoordinate + buttonSize/2);
        String message2 = "cost: 50";
        app.text(message2, xCoordinate + buttonSize + 5, freezing_button_yCoordinate + buttonSize/2 + 12);
        
        //draw the icon
        app.fill(0);
        app.textSize(30);
        String icon = "C";
        app.text(icon, xCoordinate + 5, freezing_button_yCoordinate + buttonSize/2 + 10);
    }

    public void drawBar(boolean pressed_speed_up_button, boolean pressed_pause_button, boolean pressed_Build_button, boolean pressed_upgrade_range_button, boolean pressed_upgrade_speed_button, boolean pressed_upgrade_damage_button, boolean pressed_mana_pool_button, boolean pressed_freeze_button) {
        // Draw the bar as a rectangle using rect()
        drawBuildTowerButton(pressed_Build_button);
        drawSpeedButton(pressed_speed_up_button);
        drawPauseButton(pressed_pause_button);
        drawUpgradeRangeButton(pressed_upgrade_range_button);
        drawUpgradeSpeedButton(pressed_upgrade_speed_button);
        drawUpgradeDamageButton(pressed_upgrade_damage_button);
        drawManaPoolButton(pressed_mana_pool_button);
        draw_freezing_button(pressed_freeze_button);
    }
}