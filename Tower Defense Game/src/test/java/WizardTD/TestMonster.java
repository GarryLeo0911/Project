package WizardTD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import processing.core.PApplet;

public class TestMonster {
    @Test
    public void testMonster_spawn_location() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        Monster monster = app.monsters.get(0).get(0);
        ArrayList<ArrayList<Integer>> monster_spawn_location = new ArrayList<ArrayList<Integer>>();
        monster_spawn_location.add(new ArrayList<Integer>() {
            {
                add(3);
                add(0);
            }
        });
        monster_spawn_location.add(new ArrayList<Integer>() {
            {
                add(0);
                add(9);
            }
        });
        monster_spawn_location.add(new ArrayList<Integer>() {
            {
                add(5);
                add(19);
            }
        });
        monster_spawn_location.add(new ArrayList<Integer>() {
            {
                add(19);
                add(10);
            }
        });
        assertEquals(monster_spawn_location, monster.monster_spawn_location());
    }

    @Test
    public void testMonster_move() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        Monster monster = app.monsters.get(0).get(0);
        float initial_X = monster.getMonsterX();
        float initial_Y = monster.getMonsterY();
        monster.setWhether_spawn_monster();
        monster.updateLocation(false);
        app.delay(1000);
        float curren_X = monster.getMonsterX();
        float curren_Y = monster.getMonsterY();
        boolean whether_move = ((initial_X != curren_X) || (initial_Y != curren_Y));
        assertTrue(whether_move);
    }

    @Test
    public void testMonster_move_speed_up() {
        App app = new App();
        app.pressed_speed_up_button = true;
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        Monster monster = app.monsters.get(0).get(0);
        float initial_X = monster.getMonsterX();
        float initial_Y = monster.getMonsterY();
        monster.setWhether_spawn_monster();
        monster.updateLocation(false);
        app.delay(1000);
        float curren_X = monster.getMonsterX();
        float curren_Y = monster.getMonsterY();
        assertNotEquals(initial_X*32, curren_X*32);
        assertNotEquals(initial_Y*32, curren_Y*32);
    }

    //test whether the monster can be slowed down
    @Test
    public void testMonster_be_freezed() {
        App app = new App();
        app.pressed_speed_up_button = true;
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        Monster monster = app.monsters.get(0).get(0);
        monster.whether_spawn_monster = true;
        Tower tower = app.towers.get(0).get(0);
        tower.setFreezing();
        tower.nearestMonster = monster;
        tower.fire(false);
        monster.display(false, false);
        app.delay(1000);
        assertEquals(true, monster.be_freezed);
    }

    //Test when the hp is 0, monster will be dead
    @Test
    public void testMonster_dead() {
        App app = new App();
        app.pressed_speed_up_button = true;
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        Monster monster = app.monsters.get(0).get(0);
        assertEquals(0, monster.dying_time);
        monster.setWhether_spawn_monster();
        monster.hp = 0;
        monster.display_dying_monster();
        app.delay(1000);
        assertEquals(20, monster.dying_time);
    }

    //Test whether the monster can be hitted by tower
    @Test
    public void hitted_display1() {
        App app = new App();
        app.pressed_speed_up_button = true;
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        Monster monster = app.monsters.get(0).get(0);
        Tower tower = app.towers.get(0).get(0);
        tower.nearestMonster = monster;
        tower.fire(false);
        monster.display(false, false);
        assertEquals(100, monster.hp);
    }

    //Test whether the monster can be hitted by tower
    @Test
    public void hitted_display2() {
        App app = new App();
        app.pressed_speed_up_button = true;
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        Monster monster = app.monsters.get(0).get(0);
        monster.setMonsterX(0);
        monster.setMonsterY(1);
        Tower tower = app.towers.get(0).get(1);
        tower.canBuild = true;
        tower.setFreezing();
        tower.nearestMonster = monster;
        tower.fire(false);
        monster.display(false, false);
        assertNotEquals(100, monster.hp);
        assertEquals(true, monster.be_freezed);
    }

    //Test whether the monster can be regenerated when it reach the house
    @Test
    public void Test_Regenerate() {
        App app = new App();
        app.pressed_speed_up_button = true;
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        Monster monster = app.monsters.get(0).get(0);
        monster.setMonsterX(3);
        monster.setMonsterY(14);
        assertEquals(true, monster.reach_the_house);
        monster.display(false, false);
        assertEquals(monster.getCurrentPoint(), 0);
    }

    //Test updateLocation
    @Test
    public void testMonster_be_slowed_down1() {
        App app = new App();
        app.pressed_speed_up_button = true;
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        Monster monster = app.monsters.get(0).get(0);
        monster.setWhether_spawn_monster();
        monster.be_freezed = true;
        monster.updateLocation(app.pressed_speed_up_button);
        monster.be_freezed = true;
        assertEquals(true, monster.be_freezed);
    }

    //Test updateLocation
    @Test
    public void testMonster_be_slowed_down2() {
        App app = new App();
        app.pressed_speed_up_button = true;
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        Monster monster = app.monsters.get(0).get(0);
        monster.setWhether_spawn_monster();
        monster.be_freezed = false;
        monster.updateLocation(app.pressed_speed_up_button);
        assertEquals(false, monster.be_freezed);
    }

    //Test updateLocation
    @Test
    public void testMonster_be_slowed_down3() {
        App app = new App();
        app.pressed_speed_up_button = false;
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        Monster monster = app.monsters.get(0).get(0);
        monster.setWhether_spawn_monster();
        monster.updateLocation(app.pressed_speed_up_button);
        monster.be_freezed = true;
        assertEquals(true, monster.be_freezed);
    }

    //Test updateLocation
    @Test
    public void testMonster_be_slowed_down4() {
        App app = new App();
        app.pressed_speed_up_button = false;
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        Monster monster = app.monsters.get(0).get(0);
        monster.setWhether_spawn_monster();
        monster.updateLocation(app.pressed_speed_up_button);
        monster.be_freezed = false;
        assertEquals(false, monster.be_freezed);
    }

    //Test setMonsterX
    @Test
    public void testMonster_setMonsterX() {
        App app = new App();
        app.pressed_speed_up_button = true;
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        Monster monster = app.monsters.get(0).get(0);
        monster.setMonsterX(1);
        assertEquals(1, monster.getMonsterX());
    }

    //Test setMonsterY
    @Test
    public void testMonster_setMonsterY() {
        App app = new App();
        app.pressed_speed_up_button = true;
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        Monster monster = app.monsters.get(0).get(0);
        monster.setMonsterY(1);
        assertEquals(1, monster.getMonsterY());
    }

    //Test getCurrentPoint
    @Test
    public void testMonster_getCurrentPoint() {
        App app = new App();
        app.pressed_speed_up_button = true;
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        Monster monster = app.monsters.get(0).get(0);
        monster.setWhether_spawn_monster();
        monster.setCurrentPoint(1);

        assertEquals(1, monster.getCurrentPoint());
    }

    //Test display_monster_in_wave
    @Test
    public void testDisplay_monster_in_wave() {
        App app = new App();
        app.pressed_speed_up_button = true;
        app.loop();
        app.setup();
        app.delay(500);
        app.pressed_speed_up_button = true;
        Wave wave = app.wave;
        wave.display_monster_in_wave(app.monsters, 2, app.pressed_speed_up_button);
        assertNotEquals(0, wave.related_time);

        wave.display_monster_in_wave(app.monsters, 60, app.pressed_speed_up_button);
        assertNotEquals(0, wave.related_time);
    }

    //Test generate_monster method
    @Test
    public void TestGenerate_monster() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        Monster monster = app.monsters.get(0).get(0);
        monster.monster_spawn_location = new ArrayList<ArrayList<Integer>> ();
        assertEquals(null, monster.generate_monster());
    }

    //Test findPath method
    @Test
    public void TestFindPath() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        Monster monster = app.monsters.get(0).get(0);

        assertEquals(null, monster.findPath(0, 0));
    }

    //Test updateLocation method
    @Test
    public void TestUpdateLocation1() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);

        Monster monster = app.monsters.get(0).get(0);
        float initial_X = monster.getMonsterX();
        float initial_Y = monster.getMonsterY();
        monster.updateLocation(false);
        float curren_X = monster.getMonsterX();
        float curren_Y = monster.getMonsterY();

        boolean whether_move = ((initial_X != curren_X) || (initial_Y != curren_Y));
        assertFalse(whether_move);
    }

    @Test
    public void TestUpdateLocation2() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);

        Monster monster = app.monsters.get(0).get(0);
        float initial_X = monster.getMonsterX();
        float initial_Y = monster.getMonsterY();
        monster.setWhether_spawn_monster();
        monster.updateLocation(false);
        float curren_X = monster.getMonsterX();
        float curren_Y = monster.getMonsterY();

        boolean whether_move = ((initial_X != curren_X) || (initial_Y != curren_Y));
        assertTrue(whether_move);
    }

    @Test
    public void TestUpdateLocation3() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);

        Monster monster = app.monsters.get(0).get(0);
        float initial_X = monster.getMonsterX();
        float initial_Y = monster.getMonsterY();
        monster.setWhether_spawn_monster();
        app.pressed_speed_up_button = true;
        monster.updateLocation(app.pressed_speed_up_button);
        float curren_X = monster.getMonsterX();
        float curren_Y = monster.getMonsterY();

        boolean whether_move = ((initial_X != curren_X) || (initial_Y != curren_Y));
        assertFalse(whether_move);
    }

    @Test
    public void TestUpdateLocation4() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);

        Monster monster = app.monsters.get(0).get(0);
        float initial_X = monster.getMonsterX();
        float initial_Y = monster.getMonsterY();
        monster.setWhether_spawn_monster();
        app.pressed_speed_up_button = true;
        monster.setFreezing();
        monster.updateLocation(app.pressed_speed_up_button);
        float curren_X = monster.getMonsterX();
        float curren_Y = monster.getMonsterY();

        boolean whether_move = ((initial_X != curren_X) || (initial_Y != curren_Y));
        assertFalse(whether_move);
    }

    //Test setFreezing method
    @Test
    public void TestSetFreezing1() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);

        Monster monster = app.monsters.get(0).get(0);
        monster.setFreezing();
        assertEquals(true, monster.be_freezed);
    }
}
