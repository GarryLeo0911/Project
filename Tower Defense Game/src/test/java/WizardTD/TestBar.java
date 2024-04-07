package WizardTD;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import processing.core.PApplet;

public class TestBar {
    //Test whetehr the mouseX and mouseY are set correctly
    @Test
    public void TestSetXY() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        Bar bar = app.bar;

        bar.setMouseX(2);
        assertEquals(2, bar.mouseX);

        bar.setMouseY(3);
        assertEquals(3, bar.mouseY);
    }

    //Test whether the button's color can be changed
    @Test
    public void TestChangeColor() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        Bar bar = app.bar;

        assertFalse(bar.speed_up_button_color_change);
        assertFalse(bar.pause_button_color_change);
        assertFalse(bar.build_tower_button_color_change);
        assertFalse(bar.upgrade_damage_button_color_change);
        assertFalse(bar.upgrade_range_button_color_change);
        assertFalse(bar.upgrade_speed_button_color_change);
        assertFalse(bar.mana_pool_button_color_change);
        assertFalse(bar.freezing_button_color_change);

        bar.drawSpeedButton(true);
        assertTrue(bar.speed_up_button_color_change);

        bar.drawPauseButton(true);
        assertTrue(bar.pause_button_color_change);

        bar.drawBuildTowerButton(true);
        assertTrue(bar.build_tower_button_color_change);

        bar.drawUpgradeDamageButton(true);
        assertTrue(bar.upgrade_damage_button_color_change);

        bar.drawUpgradeRangeButton(true);
        assertTrue(bar.upgrade_range_button_color_change);

        bar.drawUpgradeSpeedButton(true);
        assertTrue(bar.upgrade_speed_button_color_change);

        bar.drawManaPoolButton(true);
        assertTrue(bar.mana_pool_button_color_change);

        bar.draw_freezing_button(true);
        assertTrue(bar.freezing_button_color_change);
    }
}
