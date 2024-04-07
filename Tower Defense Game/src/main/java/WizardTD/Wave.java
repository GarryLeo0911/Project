package WizardTD;

import processing.data.JSONObject;
import processing.data.JSONArray;
import java.util.ArrayList;

import processing.core.PApplet;

public class Wave {
    private JSONObject jsonData;
    private JSONArray wave_Array;
    private JSONObject wave;

    private ArrayList<Integer> quantity_per_wave = new ArrayList<>();
    public ArrayList<ArrayList<Monster>> monsters = new ArrayList<>();
    public ArrayList<ArrayList<Integer>> monster_per_wave_per_type = new ArrayList<>();

    public ArrayList<Float> duration_per_wave = new ArrayList<>();
    public float total_time;
    public ArrayList<Float> pre_wave_pause = new ArrayList<>();
    public ArrayList<float[]> time_interval = new ArrayList<>();

    PApplet pApplet;
    App app;
    Monster monster;
    int quantity;
    float related_time;
    float displayed_time;
    public ArrayList<float[]> displaying_time_interval = new ArrayList<>();

    public Wave(JSONObject jsonData, App app, PApplet pApplet) {
        this.jsonData = jsonData;
        this.app = app;
        this.pApplet = pApplet;
        wave_Array = jsonData.getJSONArray("waves");
        monsters = getMonsters();
        quantity_per_wave = get_total_quantity_ArrayList();
        monster_per_wave_per_type = get_monsters_quantity_per_type();
        
        duration_per_wave = get_time_per_wave();
        pre_wave_pause = get_pre_wave_pause();
        total_time = get_total_time();
        time_interval = get_time_interval();
        related_time = 0;
        displayed_time = 0;
        displaying_time_interval = get_displaying_time_interval();
    }
    
    


    public ArrayList<ArrayList<Monster>> getMonsters() {
        JSONArray waves_array = jsonData.getJSONArray("waves");
        ArrayList<ArrayList<Monster>> monster_temporary = new ArrayList<>();
        for (int i=0; i<waves_array.size(); i++) {
            JSONArray monsters_array = waves_array.getJSONObject(i).getJSONArray("monsters");
            ArrayList<Monster> monsters_in_wave = new ArrayList<>();
            for (int j=0; j<monsters_array.size(); j++){
                int monster_quantity = monsters_array.getJSONObject(j).getInt("quantity");
                for (int k=0; k<monster_quantity; k++) {
                    monsters_in_wave.add(new Monster(monsters_array.getJSONObject(j), app, pApplet));
                }
            }
            monster_temporary.add(monsters_in_wave);
        }
        return monster_temporary;
    }

    public ArrayList<Integer> get_total_quantity_ArrayList() {
        JSONArray waves_array = jsonData.getJSONArray("waves");
        //loop for per wave
        for (int i=0; i<waves_array.size(); i++) {
            wave = waves_array.getJSONObject(i);
            JSONArray monsters_array = wave.getJSONArray("monsters");
            
            int quantity_one_wave = 0;
            //loop for per type of monster
            for (int j=0; j<monsters_array.size(); j++) {
                JSONObject monster_Object = monsters_array.getJSONObject(j);
                int quantity_such_type = monster_Object.getInt("quantity");
                quantity_one_wave += quantity_such_type;
            }
            quantity_per_wave.add(quantity_one_wave);
        }
        return quantity_per_wave;
    }

    public ArrayList<ArrayList<Integer>> get_monsters_quantity_per_type() {
        //loop for per wave
        ArrayList<ArrayList<Integer>> monsters_in_wave = new ArrayList<>();
        for (int i=0; i<wave_Array.size(); i++) {
            wave = wave_Array.getJSONObject(i);
            JSONArray monsters_array = wave.getJSONArray("monsters");
            //loop for per type of monster
            ArrayList<Integer> monsters_quantity_per_type = new ArrayList<>();
            for (int j=0; j<monsters_array.size(); j++) {
                JSONObject monster_Object = monsters_array.getJSONObject(j);
                int quantity_such_type = monster_Object.getInt("quantity");
                monsters_quantity_per_type.add(quantity_such_type);
            }
            monsters_in_wave.add(monsters_quantity_per_type);
        }
        return monsters_in_wave;
    }

    public ArrayList<Float> get_time_per_wave() {
        //loop for per wave
        ArrayList<Float> duration_per_wave1 = new ArrayList<>();
        for (int i=0; i<wave_Array.size(); i++) {
            wave = wave_Array.getJSONObject(i);
            float time_one_wave = wave.getFloat("duration");
            duration_per_wave1.add(time_one_wave);
        }
        return duration_per_wave1;
    }

    public float get_total_time() {
        float total_time1 = 0;
        for (int i=0; i<wave_Array.size(); i++) {
            total_time1 += duration_per_wave.get(i);
            total_time1 += pre_wave_pause.get(i);
        }
        return total_time1;
    }

    public ArrayList<Float> get_pre_wave_pause() {
        ArrayList<Float> pre_wave_pause1 = new ArrayList<>();
        for (int i=0; i<wave_Array.size(); i++) {
            wave = wave_Array.getJSONObject(i);
            float wave_pause = wave.getFloat("pre_wave_pause");
            pre_wave_pause1.add(wave_pause);
        }
        return pre_wave_pause1;
    }

    //make a method to tell me the time interval of each wave
    public ArrayList<float[]> get_time_interval() {
        ArrayList<float[]> time_interval = new ArrayList<>();
        float start_time = 0;
        float end_time =0;
        for (int i=0; i<wave_Array.size(); i++) {
            start_time = end_time + pre_wave_pause.get(i);
            end_time += pre_wave_pause.get(i) + duration_per_wave.get(i);
            float[] time_interval_one_wave = {start_time, end_time};
            time_interval.add(time_interval_one_wave);
        }
        return time_interval;
    }

    //make a method to tell me whether the time is in the interval
    public boolean check_time_in_interval(float time) {
        for (int i=0; i<time_interval.size(); i++) {
            if (time >= time_interval.get(i)[0] && time <= time_interval.get(i)[1]) {
                return true;
            }
        }
        return false;
    }

    //make a method to tell me which wave's monster I should generate
    public int get_wave_index(float time) {
        if (check_time_in_interval(time) == true) {
            for (int i=0; i<time_interval.size(); i++) {
                if (time >= time_interval.get(i)[0] && time <= time_interval.get(i)[1]) {
                    return i;
                }
            }
        }
        return -1;
    }

    //get the the displaying time interval
    public ArrayList<float[]> get_displaying_time_interval() {
        ArrayList<float[]> time_interval1 = new ArrayList<>();
        float start_time = 0;
        float end_time =0;
        for (int i=0; i<wave_Array.size()-1; i++) {
            start_time = end_time;
            end_time += duration_per_wave.get(i) + pre_wave_pause.get(i+1);
            float[] time_interval_one_wave = {start_time, end_time};
            time_interval1.add(time_interval_one_wave);
        }
        return time_interval1;
    }

    //check the time is in the displaying time interval
    public boolean check_time_in_displaying_interval(float time) {
        for (int i=0; i<displaying_time_interval.size(); i++) {
            if (time >= displaying_time_interval.get(i)[0] && time <= displaying_time_interval.get(i)[1]) {
                return true;
            }
        }
        return false;
    }

    //get the the displaying time interval
    public int get_displaying_wave_index(float time) {
        if (check_time_in_displaying_interval(time) == true) {
            for (int i=0; i<displaying_time_interval.size(); i++) {
                if (time >= displaying_time_interval.get(i)[0] && time <= displaying_time_interval.get(i)[1]) {
                    return i;
                }
            }
        }
        return displaying_time_interval.size();
    }
    

    //make a method to display the current wave and time
    public void display_wave_time(int current_wave, int start_time) {
        app.fill(0);
        app.textSize(30);
        String wave_time = "Wave " + (current_wave + 1) + " Time: " + (int) (start_time);
        app.text(wave_time, 10, 30);
    }


    public void display_monster_in_wave(ArrayList<ArrayList<Monster>> monsters, float time_in_second, boolean pressed_speed_up_button) {
        float current_time = time_in_second;
        int current_wave = get_wave_index(current_time);
        for (int i=0; i<time_interval.size(); i++) {
            if (current_time == (int) (time_interval.get(i)[0])) {
                related_time = 0;
            }
        }
        for (int i=0; i<displaying_time_interval.size(); i++) {
            if (current_time == (int) (displaying_time_interval.get(i)[0])) {
                displayed_time = 0;
            }
        }
        if (check_time_in_interval(current_time) == true) {
            float monster_spawn_interval = duration_per_wave.get(current_wave) / quantity_per_wave.get(current_wave);
            int monster_index = (int) (related_time / monster_spawn_interval);
            if (monster_index < monsters.get(current_wave).size()) {
                monsters.get(current_wave).get(monster_index).setWhether_spawn_monster();
            }
        }

        int displaying_wave_index = get_displaying_wave_index(current_time);
        if (!pressed_speed_up_button) {
            related_time += 1.0f / 60;
            displayed_time += 1.0f / 60;
        } else {
            related_time += 1.0f / 30;
            displayed_time += 1.0f / 30;
        }
        if (check_time_in_displaying_interval(current_time) == true) {
            float temporary_interval = displaying_time_interval.get(displaying_wave_index)[1] - displaying_time_interval.get(displaying_wave_index)[0];
            int displayed_time1 = (int) (temporary_interval - displayed_time);
            display_wave_time(displaying_wave_index, displayed_time1);
        } else {
            display_wave_time(displaying_wave_index, 0);
        }

        
    }
}