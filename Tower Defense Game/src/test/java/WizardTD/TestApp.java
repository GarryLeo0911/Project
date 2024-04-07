package WizardTD;


import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import processing.event.MouseEvent;
import static org.junit.jupiter.api.Assertions.*;

public class TestApp {

    @Test
    public void testManaIncreaseByTime() {
        // Create an instance of your App class or any relevant setup
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);

        // Test case 1: When time is a multiple of 60
        int mana = app.mana_increase_by_time(60);
        assertEquals(2, mana); // Adjust the expected value as per your logic

        // Test case 2: When time is not a multiple of 60
        mana = app.mana_increase_by_time(30);
        assertEquals(0, mana); // Adjust the expected value as per your logic
    }

    //Test whether the key is been pressed
    @Test
    public void testKeyPressed() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);

        app.key = 102;
        app.keyPressed();
        assertTrue(app.pressed_speed_up_button);

        app.key = 112;
        app.keyPressed();
        assertTrue(app.pressed_pause_button);

        app.key = 114;
        app.game_lose = true;
        app.keyrPressed = false;
        app.keyPressed();
        assertTrue(app.keyrPressed);
        assertFalse(app.game_lose);

        app.key = 116;
        app.keyPressed();
        assertTrue(app.pressed_Build_button);

        app.key = 49;
        app.keyPressed();
        assertTrue(app.pressed_upgrade_range_button);

        app.key = 50;
        app.keyPressed();
        assertTrue(app.pressed_upgrade_speed_button);

        app.key = 51;
        app.keyPressed();
        assertTrue(app.pressed_upgrade_damage_button);

        app.key = 109;
        app.keyPressed();
        assertTrue(app.pressed_mana_pool_button);

        app.key = 99;
        app.keyPressed();
        assertTrue(app.pressed_freeze_button);
    }

    //Test whether the key is been released
    @Test
    public void testKeyReleased() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);

        app.key = 102;
        app.keyReleased();
        assertFalse(app.pressed_speed_up_button);

        app.key = 112;
        app.keyReleased();
        assertFalse(app.pressed_pause_button);

        app.key = 114;
        app.keyReleased();
        assertFalse(app.keyrPressed);

        app.key = 116;
        app.keyReleased();
        assertFalse(app.pressed_Build_button);

        app.key = 49;
        app.keyReleased();
        assertFalse(app.pressed_upgrade_range_button);

        app.key = 50;
        app.keyReleased();
        assertFalse(app.pressed_upgrade_speed_button);

        app.key = 51;
        app.keyReleased();
        assertFalse(app.pressed_upgrade_damage_button);

        app.key = 109;
        app.keyReleased();
        assertFalse(app.pressed_mana_pool_button);

        app.key = 99;
        app.keyReleased();
        assertFalse(app.pressed_freeze_button);
    }

    //Test mouseClicked method
    @Test
    public void testMouseClicked() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);

        assertFalse(app.mousePressed_in_map);
        app.mouseX = 600;
        app.mouseY = 60;
        MouseEvent event1 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseClicked(event1);
        assertTrue(app.mousePressed_in_map);
        app.mousePressed_in_map = false;

        assertFalse(app.mousePressed_in_map);
        app.mouseX = -1;
        app.mouseY = 20;
        MouseEvent event2 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseClicked(event2);
        assertFalse(app.mousePressed_in_map);
        app.mousePressed_in_map = false;

        assertFalse(app.mousePressed_in_map);
        app.mouseX = 660;
        app.mouseY = 690;
        MouseEvent event3 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseClicked(event3);
        assertFalse(app.mousePressed_in_map);
    }

    @Test
    public void testMouseReleased1() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);

        // Initially, all buttons are assumed to be false
        assertFalse(app.pressed_speed_up_button);
        assertFalse(app.pressed_pause_button);
        assertFalse(app.pressed_Build_button);
        assertFalse(app.pressed_upgrade_range_button);
        assertFalse(app.pressed_upgrade_speed_button);
        assertFalse(app.pressed_upgrade_damage_button);
        assertFalse(app.pressed_mana_pool_button);
        assertFalse(app.pressed_freeze_button);

        app.mouseX = 675;
        app.mouseY = 60;
        MouseEvent event1 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event1);
        assertTrue(app.pressed_speed_up_button);

        app.mouseX = 675;
        app.mouseY = 120;
        MouseEvent event2 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event2);
        assertTrue(app.pressed_pause_button);

        app.mouseX = 675;
        app.mouseY = 180;
        MouseEvent event3 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event3);
        assertTrue(app.pressed_Build_button);

        app.mouseX = 675;
        app.mouseY = 240;
        MouseEvent event4 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event4);
        assertTrue(app.pressed_upgrade_range_button);

        app.mouseX = 675;
        app.mouseY = 310;
        MouseEvent event5 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event5);
        assertTrue(app.pressed_upgrade_speed_button);

        app.mouseX = 675;
        app.mouseY = 380;
        MouseEvent event6 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event6);
        assertTrue(app.pressed_upgrade_damage_button);

        app.mouseX = 675;
        app.mouseY = 450;
        MouseEvent event7 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event7);
        assertTrue(app.pressed_mana_pool_button);

        app.mouseX = 675;
        app.mouseY = 520;
        MouseEvent event8 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event8);
        assertTrue(app.pressed_freeze_button);
    }

    //testcase for when we do not click on the button
    @Test
    public void testMouseReleased2() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);

        // Initially, all buttons are assumed to be false
        assertFalse(app.pressed_speed_up_button);
        assertFalse(app.pressed_pause_button);
        assertFalse(app.pressed_Build_button);
        assertFalse(app.pressed_upgrade_range_button);
        assertFalse(app.pressed_upgrade_speed_button);
        assertFalse(app.pressed_upgrade_damage_button);
        assertFalse(app.pressed_mana_pool_button);
        assertFalse(app.pressed_freeze_button);

        app.mouseX = 600;
        app.mouseY = 40;
        MouseEvent event1 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event1);
        assertFalse(app.pressed_speed_up_button);

        app.mouseX = 600;
        app.mouseY = 40;
        MouseEvent event2 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event2);
        assertFalse(app.pressed_pause_button);

        app.mouseX = 600;
        app.mouseY = 40;
        MouseEvent event3 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event3);
        assertFalse(app.pressed_Build_button);

        app.mouseX = 600;
        app.mouseY = 40;
        MouseEvent event4 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event4);
        assertFalse(app.pressed_upgrade_range_button);

        app.mouseX = 600;
        app.mouseY = 40;
        MouseEvent event5 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event5);
        assertFalse(app.pressed_upgrade_speed_button);

        app.mouseX = 600;
        app.mouseY = 40;
        MouseEvent event6 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event6);
        assertFalse(app.pressed_upgrade_damage_button);

        app.mouseX = 600;
        app.mouseY = 40;
        MouseEvent event7 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event7);
        assertFalse(app.pressed_mana_pool_button);

        app.mouseX = 600;
        app.mouseY = 40;
        MouseEvent event8 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event8);
        assertFalse(app.pressed_freeze_button);
    }

    @Test
    public void testMouseReleased3() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);

        // Initially, all buttons are assumed to be false
        assertFalse(app.pressed_speed_up_button);
        assertFalse(app.pressed_pause_button);
        assertFalse(app.pressed_Build_button);
        assertFalse(app.pressed_upgrade_range_button);
        assertFalse(app.pressed_upgrade_speed_button);
        assertFalse(app.pressed_upgrade_damage_button);
        assertFalse(app.pressed_mana_pool_button);
        assertFalse(app.pressed_freeze_button);

        app.mouseX = 700;
        app.mouseY = 600;
        MouseEvent event1 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event1);
        assertFalse(app.pressed_speed_up_button);

        app.mouseX = 700;
        app.mouseY = 600;
        MouseEvent event2 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event2);
        assertFalse(app.pressed_pause_button);

        app.mouseX = 700;
        app.mouseY = 600;
        MouseEvent event3 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event3);
        assertFalse(app.pressed_Build_button);

        app.mouseX = 700;
        app.mouseY = 600;
        MouseEvent event4 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event4);
        assertFalse(app.pressed_upgrade_range_button);

        app.mouseX = 700;
        app.mouseY = 600;
        MouseEvent event5 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event5);
        assertFalse(app.pressed_upgrade_speed_button);

        app.mouseX = 700;
        app.mouseY = 600;
        MouseEvent event6 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event6);
        assertFalse(app.pressed_upgrade_damage_button);

        app.mouseX = 700;
        app.mouseY = 600;
        MouseEvent event7 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event7);
        assertFalse(app.pressed_mana_pool_button);

        app.mouseX = 700;
        app.mouseY = 600;
        MouseEvent event8 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event8);
        assertFalse(app.pressed_freeze_button);
    }

    @Test
    public void testMouseReleased4() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);

        // Initially, all buttons are assumed to be false
        app.pressed_speed_up_button = true;
        app.pressed_pause_button = true;
        app.pressed_Build_button = true;
        app.pressed_upgrade_range_button = true;
        app.pressed_upgrade_speed_button = true;
        app.pressed_upgrade_damage_button = true;
        app.pressed_mana_pool_button = true;
        app.pressed_freeze_button = true;

        app.mouseX = 675;
        app.mouseY = 60;
        MouseEvent event1 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event1);
        assertFalse(app.pressed_speed_up_button);

        app.mouseX = 675;
        app.mouseY = 120;
        MouseEvent event2 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event2);
        assertFalse(app.pressed_pause_button);

        app.mouseX = 675;
        app.mouseY = 180;
        MouseEvent event3 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event3);
        assertFalse(app.pressed_Build_button);

        app.mouseX = 675;
        app.mouseY = 240;
        MouseEvent event4 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event4);
        assertFalse(app.pressed_upgrade_range_button);

        app.mouseX = 675;
        app.mouseY = 310;
        MouseEvent event5 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event5);
        assertFalse(app.pressed_upgrade_speed_button);

        app.mouseX = 675;
        app.mouseY = 380;
        MouseEvent event6 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event6);
        assertFalse(app.pressed_upgrade_damage_button);

        app.mouseX = 675;
        app.mouseY = 450;
        MouseEvent event7 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event7);
        assertFalse(app.pressed_mana_pool_button);

        app.mouseX = 675;
        app.mouseY = 520;
        MouseEvent event8 = new MouseEvent(null, 0, MouseEvent.CLICK, 0, app.mouseX, app.mouseY, 0, 0);
        app.mouseReleased(event8);
        assertFalse(app.pressed_freeze_button);
    }

    //Test whether the game is lose when mana less than 0
    @Test
    public void TestGameLose() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(100);

        app.mana = -3;
        app.draw();
        assertTrue(app.game_lose);
    }

    //Test whether the game is win, when all the monster is killed
    @Test
    public void TestGameWin() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(100);

        for (int y = 0; y < app.monsters.size(); y++) {
            for (int x = 0; x < app.monsters.get(y).size(); x++) {
                app.monsters.get(y).get(x).hp = 0;
            }
        }
        app.draw();
        app.delay(100);
        assertTrue(app.game_win);
    }
}
