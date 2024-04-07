package WizardTD;


public class primary_mana_parameter extends App{
    App app;

    //represent how many times we upgrade the mana pool
    int mana_upgrade_times;

    //initial mana pool parameter
    int initial_mana;
    int mana;
    int initial_mana_cap;
    int mana_cap;
    int initial_mana_gained_per_second;
    int mana_gained_per_second;
    int mana_pool_spell_initial_cost;
    int mana_pool_spell_cost;
    int mana_pool_spell_cost_increase_per_use;
    float mana_pool_spell_cap_multiplier;
    float mana_pool_spell_mana_gained_multiplier;

    boolean draw_mana_bar;

    public primary_mana_parameter(App app) {
        this.app = app;

        //represent how many times we upgrade the mana pool
        mana_upgrade_times = 0;

        //initial mana pool parameter
        initial_mana = app.initial_mana;
        mana = initial_mana;
        initial_mana_cap = app.initial_mana_cap;
        mana_cap = initial_mana_cap;
        initial_mana_gained_per_second = app.initial_mana_gained_per_second;
        mana_gained_per_second = initial_mana_gained_per_second;
        mana_pool_spell_initial_cost = app.mana_pool_spell_initial_cost;
        mana_pool_spell_cost = mana_pool_spell_initial_cost;
        mana_pool_spell_cost_increase_per_use = app.mana_pool_spell_cost_increase_per_use;
        mana_pool_spell_cap_multiplier = app.mana_pool_spell_cap_multiplier;
        mana_pool_spell_mana_gained_multiplier = app.mana_pool_spell_mana_gained_multiplier;
    
        draw_mana_bar = false;
    }


    //draw the bar for mana
    public void draw_mana_bar(int mana, int mana_cap){
        fill(0);
        textSize(30);
        String title = "Mana:";
        text(title, 300, 30);
        fill(255);
        rect(400, 10, 300, 20);
        fill(135,206,250);
        rect(400, 10, 300 * mana / mana_cap, 20);
        draw_mana_bar = true;
    }

    
}
