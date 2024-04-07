package WizardTD;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import processing.core.PApplet;

public class TestPrimaryMana {
    //Test constructor
    @Test
    public void TestConstructor() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);

        primary_mana_parameter primary_mana = new primary_mana_parameter(app);
        assertEquals(app.initial_mana, primary_mana.initial_mana);
        assertEquals(app.mana, primary_mana.mana);
        assertEquals(app.mana_cap, primary_mana.mana_cap);
        assertEquals(app.mana_gained_per_second, primary_mana.mana_gained_per_second);
        assertEquals(app.initial_mana_gained_per_second, primary_mana.initial_mana_gained_per_second);
        assertEquals(app.mana_pool_spell_initial_cost, primary_mana.mana_pool_spell_initial_cost);
        assertEquals(app.mana_pool_spell_cost, primary_mana.mana_pool_spell_cost);
        assertEquals(app.mana_pool_spell_cost_increase_per_use, primary_mana.mana_pool_spell_cost_increase_per_use);
        assertEquals(app.mana_pool_spell_cap_multiplier, primary_mana.mana_pool_spell_cap_multiplier);
        assertEquals(app.mana_pool_spell_mana_gained_multiplier, primary_mana.mana_pool_spell_mana_gained_multiplier);
    }

    //Test draw_mana_bar
    @Test
    public void TestDrawManaBar() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);

        primary_mana_parameter primary_mana = new primary_mana_parameter(app);
        primary_mana.draw_mana_bar(100, 100);
        assertTrue(primary_mana.draw_mana_bar);
    }
}
