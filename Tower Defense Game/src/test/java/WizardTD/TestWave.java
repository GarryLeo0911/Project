package WizardTD;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import processing.core.PApplet;

public class TestWave {
    // Test get_wave_index() when the time_interval is empty
    @Test
    public void TestGetWaveIndex1() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        Wave wave = app.wave;
        wave.time_interval = new ArrayList<float[]>() {};
        assertEquals(-1, wave.get_wave_index(100));
    }

    // Test get_wave_index() when the time_interval is not empty
    @Test
    public void TestGetWaveIndex2() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        Wave wave = app.wave;
        assertEquals(-1, wave.get_wave_index(100));
    }

    // Test get_displaying_wave_index()
    @Test
    public void TestGetDisplayingIndex1() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        Wave wave = app.wave;
        assertEquals(wave.displaying_time_interval.size(), wave.get_displaying_wave_index(100));
    }

    @Test
    public void TestGetDisplayingIndex2() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        Wave wave = app.wave;
        assertEquals(wave.displaying_time_interval.size(), wave.get_displaying_wave_index(-1));
    }

    // Test check_time_in_displaying_interval()
    @Test
    public void TestCheckTimeInInterval1() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        Wave wave = app.wave;
        assertEquals(false, wave.check_time_in_displaying_interval(100));
    }

    @Test
    public void TestCheckTimeInInterval2() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
        Wave wave = app.wave;
        assertEquals(false, wave.check_time_in_displaying_interval(-1));
    }
}
