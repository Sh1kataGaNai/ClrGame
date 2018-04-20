package com.example.a.clrgame;

import java.util.HashMap;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;


public class GameManager {

    private HashMap<String, String> colors;
    private int deadline;
    private int true_answers;
    String rand_color_hex_first_field;
    String rand_color_name_first_field;
    String rand_color_hex_second_field;
    String rand_color_name_second_field;
    boolean state;

    public GameManager(){


        // Initialize colors hashmap

        colors = new HashMap<>();
        colors.put("GREEN", "#00FF00");
        colors.put("BLUE", "#0000FF");
        colors.put("RED", "#FF0000");
        colors.put("DARK_GREEN","#004c00");
        colors.put("DARK_RED", "#890000");
        colors.put("PINK", "#ff00a7");
        colors.put("ORANGE", "#ff8d00");

        //Setting default deadline in sec

        deadline = 60;
        true_answers = 0;

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
