package WizardTD;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import processing.core.PApplet;

public class TestMultipleTower {
    //Test build tower method
    @Test
    public void Test_build_tower1() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);

        Multiple_tower multiple_tower = app.multiple_tower;
        multiple_tower.build_tower(app.towers, 200, 42, 42, true, true, false, false, false, false);
        assertEquals(true, app.towers.get(0).get(1).canBuild);
        multiple_tower.build_tower(app.towers, 200, 42, 42, true, false, true, false, false, false);
        assertEquals(1, app.towers.get(0).get(1).tower_range_grade);
        multiple_tower.build_tower(app.towers, 200, 42, 42, true, false, false, false, true, false);
        assertEquals(1, app.towers.get(0).get(1).tower_damage_grade);
        multiple_tower.build_tower(app.towers, 200, 42, 42, true, false, false, true, false, false);
        assertEquals(1, app.towers.get(0).get(1).tower_firing_speed_grade);
        multiple_tower.build_tower(app.towers, 200, 42, 42, true, false, false, false, false, true);
        assertTrue(app.towers.get(0).get(1).getFreezing());
    }

    @Test
    public void Test_build_tower2() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        Multiple_tower multiple_tower = app.multiple_tower;

        multiple_tower.build_tower(app.towers, 200, 42, 42, true, false, false, false, false, false);
        assertNotEquals(true, app.towers.get(0).get(1).canBuild);
        multiple_tower.build_tower(app.towers, 200, 42, 42, true, false, false, false, false, false);
        assertNotEquals(1, app.towers.get(0).get(1).tower_range_grade);
        multiple_tower.build_tower(app.towers, 200, 42, 42, true, false, false, false, false, false);
        assertNotEquals(1, app.towers.get(0).get(1).tower_damage_grade);
        multiple_tower.build_tower(app.towers, 200, 42, 42, true, false, false, false, false, false);
        assertNotEquals(1, app.towers.get(0).get(1).tower_firing_speed_grade);
        multiple_tower.build_tower(app.towers, 200, 42, 42, true, false, false, false, false, false);
        assertFalse(app.towers.get(0).get(1).getFreezing());
    }

    @Test
    public void Test_build_tower3() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);

        Multiple_tower multiple_tower = app.multiple_tower;
        multiple_tower.build_tower(app.towers, 0, 42, 42, true, true, false, false, false, false);
        assertNotEquals(true, app.towers.get(0).get(1).canBuild);
        multiple_tower.build_tower(app.towers, 0, 42, 42, true, false, true, false, false, false);
        assertNotEquals(1, app.towers.get(0).get(1).tower_range_grade);
        multiple_tower.build_tower(app.towers, 0, 42, 42, true, false, false, false, true, false);
        assertNotEquals(1, app.towers.get(0).get(1).tower_damage_grade);
        multiple_tower.build_tower(app.towers, 0, 42, 42, true, false, false, true, false, false);
        assertNotEquals(1, app.towers.get(0).get(1).tower_firing_speed_grade);
        multiple_tower.build_tower(app.towers, 0, 42, 42, true, false, false, false, false, true);
        assertFalse(app.towers.get(0).get(1).getFreezing());
    }

    @Test
    public void Test_build_tower4() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);

        Multiple_tower multiple_tower = app.multiple_tower;
        multiple_tower.build_tower(app.towers, 0, 42, 42, true, false, false, false, false, false);
        assertNotEquals(true, app.towers.get(0).get(1).canBuild);
        multiple_tower.build_tower(app.towers, 0, 42, 42, true, false, false, false, false, false);
        assertNotEquals(1, app.towers.get(0).get(1).tower_range_grade);
        multiple_tower.build_tower(app.towers, 0, 42, 42, true, false, false, false, false, false);
        assertNotEquals(1, app.towers.get(0).get(1).tower_damage_grade);
        multiple_tower.build_tower(app.towers, 0, 42, 42, true, false, false, false, false, false);
        assertNotEquals(1, app.towers.get(0).get(1).tower_firing_speed_grade);
        multiple_tower.build_tower(app.towers, 0, 42, 42, true, false, false, false, false, false);
        assertFalse(app.towers.get(0).get(1).getFreezing());
    }

    @Test
    public void Test_build_tower5() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);

        Multiple_tower multiple_tower = app.multiple_tower;
        multiple_tower.build_tower(app.towers, 200, 700, 20, true, true, false, false, false, false);
        assertNotEquals(true, app.towers.get(0).get(1).canBuild);
        multiple_tower.build_tower(app.towers, 200, 700, 20, true, false, true, false, false, false);
        assertNotEquals(1, app.towers.get(0).get(1).tower_range_grade);
        multiple_tower.build_tower(app.towers, 200, 700, 20, true, false, false, false, true, false);
        assertNotEquals(1, app.towers.get(0).get(1).tower_damage_grade);
        multiple_tower.build_tower(app.towers, 200, 700, 20, true, false, false, true, false, false);
        assertNotEquals(1, app.towers.get(0).get(1).tower_firing_speed_grade);
        multiple_tower.build_tower(app.towers, 200, 700, 20, true, false, false, false, false, true);
        assertFalse(app.towers.get(0).get(1).getFreezing());
    }
}
