package com.example.a.clrgame;

import java.util.HashMap;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;


public class GameManager {

    protected HashMap<String, String> colors;
    protected int deadline;
    protected int true_answers;
    protected int current_game_time;
    protected String rand_color_hex_first_field;
    protected String rand_color_name_first_field;
    protected String rand_color_hex_second_field;
    protected String rand_color_name_second_field;
    protected boolean state;

    public GameManager(){
    //

    }
    public void newGame(){
        initializeBaseColors();
        //Setting default deadline in sec
        deadline = 60;
        true_answers = 0;
        current_game_time = 60;
    }

    public void restoreSavedState(int deadline, int current_game_time, HashMap<String,
            String> current_colors, int true_answers){
        this.deadline = deadline;
        this.current_game_time = current_game_time;
        this.colors = current_colors;
        this.true_answers = true_answers;
    }
    public int getCurrentGameTime(){
        return current_game_time;
    }
    public void setCurrentGameTime(int game_time){
        this.current_game_time = game_time;
    }
    public void setColorsHashMap(HashMap<String, String> colors){
        colors = colors;
    }
    public HashMap<String, String> getAllColors(){
        return colors;
    }

    private void initializeBaseColors(){
        colors = new HashMap<>();
        colors.put("GREEN", "#00FF00");
        colors.put("BLUE", "#0000FF");
        colors.put("RED", "#FF0000");
        colors.put("DARK_GREEN","#004c00");
        colors.put("DARK_RED", "#890000");
        colors.put("PINK", "#ff00a7");
        colors.put("ORANGE", "#ff8d00");
    }

    public int getDeadlineSeconds() {
        return deadline;
    }

    public int getTrueAnswers(){
        return true_answers;
    }

    public void generatePairs(){

        state = false;
        Random rnd = new Random();
        List<String> keys  = new ArrayList<String>(colors.keySet());

        //Generate first pair
        rand_color_name_first_field = keys.get( rnd.nextInt(keys.size()) );
        String tmp_name_for_hex = keys.get(rnd.nextInt(keys.size()));
        rand_color_hex_first_field = this.colors.get(tmp_name_for_hex);

        //Generate for second pair
        rand_color_name_second_field = keys.get(rnd.nextInt(keys.size()));
        tmp_name_for_hex = keys.get(rnd.nextInt(keys.size()));
        rand_color_hex_second_field = this.colors.get(tmp_name_for_hex);



        // Logic state

        if(this.colors.get(rand_color_name_first_field).equals(rand_color_hex_second_field)){
            state = true;
        }
    }
    public void compareUserAnswer(boolean user_choice){
        if( user_choice == state )
            true_answers++;
    }

    public void setDeadlineSeconds(int deadline){
        this.deadline = deadline;
    }

    public String getRandomColorNameFirstField(){
        return this.rand_color_name_first_field;
    }

    public String getRandomColorHexFirstField(){
        return this.rand_color_hex_first_field;
    }

    public String getRandomColorNameSecondField(){
        return this.rand_color_name_second_field;
    }

    public String getRandomColorHexSecondField(){
        return this.rand_color_hex_second_field;
    }


}

