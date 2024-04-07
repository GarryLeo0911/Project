package WizardTD;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import processing.core.PApplet;

public class TestMap {
    @Test
    public void TestChooseMap() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);

        Map map = new Map("level2.txt", app);

        assertSame(app.straight_lane, map.choose_path_type(0, 9));
        assertSame(app.straight_lane, map.choose_path_type(1, 9));
        assertSame(app.straight_lane, map.choose_path_type(3, 0));
        assertSame(app.straight_lane, map.choose_path_type(3, 1));
        assertSame(app.straight_lane, map.choose_path_type(5, 19));
        assertSame(app.straight_lane, map.choose_path_type(5, 18));
        assertSame(app.straight_lane, map.choose_path_type(19, 10));

        assertSame(app.corner_lane, map.choose_path_type(3, 4));
        assertSame(app.corner_lane, map.choose_path_type(7, 15));
        assertSame(app.corner_lane, map.choose_path_type(8, 16));
        assertSame(app.corner_lane, map.choose_path_type(15, 9));

        assertSame(app.T_lane, map.choose_path_type(5, 4));
        assertSame(app.T_lane, map.choose_path_type(5, 16));
        assertSame(app.T_lane, map.choose_path_type(12, 10));
        assertSame(app.T_lane, map.choose_path_type(14, 10));

        assertSame(app.crossing_lane, map.choose_path_type(5, 6));
    }
}
