package WizardTD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import processing.core.PApplet;

public class TestTower {

    @Test
    public void testTowerUpgraded() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        Tower tower = app.towers.get(0).get(0);

        assertEquals(false, tower.canBuild);
        assertEquals(100, tower.getTower_damage());
        assertEquals(96, tower.getTower_range());
        assertEquals(1.5, tower.getTower_firing_speed());
        assertEquals(false, tower.getFreezing());
        assertEquals(0, tower.tower_damage_grade);
        assertEquals(0, tower.tower_range_grade);
        assertEquals(0, tower.tower_firing_speed_grade);

        tower.setWhether_can_build_tower();
        tower.setFreezing();
        tower.setTower_damage_grade();
        tower.setTower_range_grade();
        tower.setTower_firing_speed_grade();
        tower.display_tower(false, false);

        assertEquals(true, tower.canBuild);
        assertEquals(150, tower.getTower_damage());
        assertEquals(128, tower.getTower_range());
        assertEquals(2.0, tower.getTower_firing_speed());
        assertEquals(true, tower.getFreezing());
        assertEquals(1, tower.tower_damage_grade);
        assertEquals(1, tower.tower_range_grade);
        assertEquals(1, tower.tower_firing_speed_grade);

        tower.setTower_damage_grade();
        tower.setTower_range_grade();
        tower.setTower_firing_speed_grade();
        tower.display_tower(false, false);

        assertEquals(200, tower.getTower_damage());
        assertEquals(160, tower.getTower_range());
        assertEquals(2.5, tower.getTower_firing_speed());
        assertEquals(2, tower.tower_damage_grade);
        assertEquals(2, tower.tower_range_grade);
        assertEquals(2, tower.tower_firing_speed_grade);

        tower.setTower_damage_grade();
        tower.setTower_range_grade();
        tower.setTower_firing_speed_grade();
        tower.display_tower(false, false);

        assertEquals(250, tower.getTower_damage());
        assertEquals(192, tower.getTower_range());
        assertEquals(3.0, tower.getTower_firing_speed());
        assertEquals(3, tower.tower_damage_grade);
        assertEquals(3, tower.tower_range_grade);
        assertEquals(3, tower.tower_firing_speed_grade);
    }

    @Test
    public void testDisplay_tower() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        Tower tower = app.towers.get(0).get(0);

        assertEquals(false, tower.canBuild);
        assertEquals(0, tower.tower_damage_grade);
        assertEquals(0, tower.tower_firing_speed_grade);
        assertEquals(0, tower.tower_range_grade);

        tower.setWhether_can_build_tower();
        
        assertEquals(true, tower.canBuild);

        tower.setTower_damage_grade();
        tower.display_tower(false, false);
        assertEquals(1, tower.tower_damage_test);

        tower.setTower_damage_grade();
        tower.display_tower(false, false);
        assertEquals(2, tower.tower_damage_test);

        tower.setTower_damage_grade();
        tower.display_tower(false, false);
        assertEquals(3, tower.tower_damage_test);

        tower.tower_damage_grade = 0;

        tower.setTower_range_grade();
        tower.display_tower(false, false);
        assertEquals(1, tower.tower_range_test);

        tower.setTower_range_grade();
        tower.display_tower(false, false);
        assertEquals(2, tower.tower_range_test);

        tower.setTower_range_grade();
        tower.display_tower(false, false);
        assertEquals(3, tower.tower_range_test);

        tower.tower_range_grade = 0;

        tower.setTower_firing_speed_grade();
        tower.display_tower(false, false);
        assertEquals(1, tower.tower_firing_speed_test);

        tower.setTower_firing_speed_grade();
        tower.display_tower(false, false);
        assertEquals(2, tower.tower_firing_speed_test);

        tower.setTower_firing_speed_grade();
        tower.display_tower(false, false);
        assertEquals(3, tower.tower_firing_speed_test);

        tower.tower_firing_speed_grade = 1;
        tower.tower_damage_grade = 1;
        tower.tower_range_grade = 1;

        tower.setTower_damage_grade();
        tower.display_tower(false, false);
        assertEquals(2, tower.tower_damage_test);

        tower.setTower_damage_grade();
        tower.display_tower(false, false);
        assertEquals(3, tower.tower_damage_test);

        tower.tower_damage_grade = 1;

        tower.setTower_range_grade();
        tower.display_tower(false, false);
        assertEquals(2, tower.tower_range_test);

        tower.setTower_range_grade();
        tower.display_tower(false, false);
        assertEquals(3, tower.tower_range_test);

        tower.tower_range_grade = 1;

        tower.setTower_firing_speed_grade();
        tower.display_tower(false, false);
        assertEquals(2, tower.tower_firing_speed_test);

        tower.setTower_firing_speed_grade();
        tower.display_tower(false, false);
        assertEquals(3, tower.tower_firing_speed_test);
    }

    //test whether I can build the tower on the map (positive testcase)
    @Test
    public void test_build_tower1() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        app.multiple_tower.build_tower(app.towers, 200, 42, 42, true, true, false, false, false, false);
        assertEquals(true, app.towers.get(0).get(1).canBuild);
        app.multiple_tower.build_tower(app.towers, 200, 42, 42, true, false, true, false, false, false);
        assertEquals(1, app.towers.get(0).get(1).tower_range_grade);
        app.multiple_tower.build_tower(app.towers, 200, 42, 42, true, false, false, false, true, false);
        assertEquals(1, app.towers.get(0).get(1).tower_damage_grade);
        app.multiple_tower.build_tower(app.towers, 200, 42, 42, true, false, false, true, false, false);
        assertEquals(1, app.towers.get(0).get(1).tower_firing_speed_grade);
        app.multiple_tower.build_tower(app.towers, 200, 42, 42, true, false, false, false, false, true);
        assertTrue(app.towers.get(0).get(1).getFreezing());
    }

    //test whether I can build the tower on the map (negative testcase1)
    public void test_build_tower2() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        app.multiple_tower.build_tower(app.towers, 200, 42, 42, true, false, false, false, false, false);
        assertEquals(false, app.towers.get(0).get(1).canBuild);
        app.multiple_tower.build_tower(app.towers, 200, 42, 42, true, false, false, false, false, false);
        assertEquals(0, app.towers.get(0).get(1).tower_range_grade);
        app.multiple_tower.build_tower(app.towers, 200, 42, 42, true, false, false, false, false, false);
        assertEquals(0, app.towers.get(0).get(1).tower_damage_grade);
        app.multiple_tower.build_tower(app.towers, 200, 42, 42, true, false, false, false, false, false);
        assertEquals(0, app.towers.get(0).get(1).tower_firing_speed_grade);
        app.multiple_tower.build_tower(app.towers, 200, 42, 42, true, false, false, false, false, false);
        assertEquals(false, app.towers.get(0).get(1).getFreezing());
    }

    //test whether I can build the tower on the map (negative testcase2)
    public void test_build_tower3() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        app.multiple_tower.build_tower(app.towers, 10, 42, 42, true, false, false, false, false, false);
        assertEquals(false, app.towers.get(0).get(1).canBuild);
        app.multiple_tower.build_tower(app.towers, 10, 42, 42, true, false, false, false, false, false);
        assertEquals(0, app.towers.get(0).get(1).tower_range_grade);
        app.multiple_tower.build_tower(app.towers, 10, 42, 42, true, false, false, false, false, false);
        assertEquals(0, app.towers.get(0).get(1).tower_damage_grade);
        app.multiple_tower.build_tower(app.towers, 10, 42, 42, true, false, false, false, false, false);
        assertEquals(0, app.towers.get(0).get(1).tower_firing_speed_grade);
        app.multiple_tower.build_tower(app.towers, 10, 42, 42, true, false, false, false, false, false);
        assertEquals(false, app.towers.get(0).get(1).getFreezing());
    }

    //test whether I can build the tower on the map (negative testcase3)
    public void test_build_tower4() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        app.multiple_tower.build_tower(app.towers, 10, 42, 42, false, true, false, false, false, false);
        assertEquals(false, app.towers.get(0).get(1).canBuild);
        app.multiple_tower.build_tower(app.towers, 10, 42, 42, false, false, true, false, false, false);
        assertEquals(0, app.towers.get(0).get(1).tower_range_grade);
        app.multiple_tower.build_tower(app.towers, 10, 42, 42, false, false, false, false, true, false);
        assertEquals(0, app.towers.get(0).get(1).tower_damage_grade);
        app.multiple_tower.build_tower(app.towers, 10, 42, 42, false, false, false, true, false, false);
        assertEquals(0, app.towers.get(0).get(1).tower_firing_speed_grade);
        app.multiple_tower.build_tower(app.towers, 10, 42, 42, false, false, false, false, false, true);
        assertEquals(false, app.towers.get(0).get(1).getFreezing());
    }

    //Test whether it can be upgraded even though the grade is 3 already
    @Test
    public void test_build_tower5() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        app.multiple_tower.build_tower(app.towers, 200, 42, 42, true, true, false, false, false, false);
        assertEquals(true, app.towers.get(0).get(1).canBuild);

        app.towers.get(0).get(1).tower_damage_grade = 3;
        app.towers.get(0).get(1).tower_range_grade = 3;
        app.towers.get(0).get(1).tower_firing_speed_grade = 3;
        app.towers.get(0).get(1).setFreezing();

        app.multiple_tower.build_tower(app.towers, 200, 42, 42, true, false, true, false, false, false);
        assertEquals(3, app.towers.get(0).get(1).tower_range_grade);
        app.multiple_tower.build_tower(app.towers, 200, 42, 42, true, false, false, false, true, false);
        assertEquals(3, app.towers.get(0).get(1).tower_damage_grade);
        app.multiple_tower.build_tower(app.towers, 200, 42, 42, true, false, false, true, false, false);
        assertEquals(3, app.towers.get(0).get(1).tower_firing_speed_grade);
        app.mana = 200;
        app.multiple_tower.build_tower(app.towers, 200, 42, 42, true, false, false, false, false, true);
        assertEquals(200, app.mana);
    }

    //Test whether we can build the tower on the shrub
    @Test
    public void test_build_tower6() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        app.multiple_tower.build_tower(app.towers, 200, 20, 42, true, true, false, false, false, false);
        assertEquals(false, app.towers.get(0).get(1).canBuild);
        app.multiple_tower.build_tower(app.towers, 200, 20, 42, true, false, true, false, false, false);
        assertEquals(0, app.towers.get(0).get(1).tower_range_grade);
        app.multiple_tower.build_tower(app.towers, 200, 20, 42, true, false, false, false, true, false);
        assertEquals(0, app.towers.get(0).get(1).tower_damage_grade);
        app.multiple_tower.build_tower(app.towers, 200, 20, 42, true, false, false, true, false, false);
        assertEquals(0, app.towers.get(0).get(1).tower_firing_speed_grade);
        app.multiple_tower.build_tower(app.towers, 200, 20, 42, true, false, false, false, false, true);
        assertFalse(app.towers.get(0).get(1).getFreezing());
    }

    //Test whether we can build another tower on the current tower
    @Test
    public void test_build_tower7() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);

        app.mana = 200;
        app.towers.get(0).get(1).canBuild = true;
        app.multiple_tower.build_tower(app.towers, app.mana, 42, 42, true, true, false, false, false, false);
        assertEquals(200, app.mana);
        app.multiple_tower.build_tower(app.towers, 200, 42, 42, true, false, true, false, false, false);
        assertEquals(1, app.towers.get(0).get(1).tower_range_grade);
        app.multiple_tower.build_tower(app.towers, 200, 42, 42, true, false, false, false, true, false);
        assertEquals(1, app.towers.get(0).get(1).tower_damage_grade);
        app.multiple_tower.build_tower(app.towers, 200, 42, 42, true, false, false, true, false, false);
        assertEquals(1, app.towers.get(0).get(1).tower_firing_speed_grade);
        app.multiple_tower.build_tower(app.towers, 200, 42, 42, true, false, false, false, false, true);
        assertTrue(app.towers.get(0).get(1).getFreezing());
    }

    //Test whether we can build the tower outside the map
    @Test
    public void test_build_tower8() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        app.multiple_tower.build_tower(app.towers, 200, 0, 0, true, true, false, false, false, false);
        assertEquals(false, app.towers.get(0).get(1).canBuild);
        app.multiple_tower.build_tower(app.towers, 200, 0, 0, true, false, true, false, false, false);
        assertEquals(0, app.towers.get(0).get(1).tower_range_grade);
        app.multiple_tower.build_tower(app.towers, 200, 0, 0, true, false, false, false, true, false);
        assertEquals(0, app.towers.get(0).get(1).tower_damage_grade);
        app.multiple_tower.build_tower(app.towers, 200, 0, 0, true, false, false, true, false, false);
        assertEquals(0, app.towers.get(0).get(1).tower_firing_speed_grade);
        app.multiple_tower.build_tower(app.towers, 200, 0, 0, true, false, false, false, false, true);
        assertFalse(app.towers.get(0).get(1).getFreezing());
    }

    //Test display_tower_range
    @Test
    public void test_display_tower_range() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        Tower tower = app.towers.get(0).get(0);

        tower.canBuild = true;
        assertFalse(tower.tower_display_range_test);
        tower.display_tower_range();
        assertTrue(tower.tower_display_range_test);
    }

    //Test getTower_cost
    @Test
    public void test_getTower_cost() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        Tower tower = app.towers.get(0).get(0);
        assertEquals(100, tower.getTower_cost());
    }

    //Test setWhether_display_range
    @Test
    public void test_setWhether_display_range() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        Tower tower = app.towers.get(0).get(0);
        assertFalse(tower.display_range);
        tower.setWhether_display_range();
        assertTrue(tower.display_range);
    }
}
