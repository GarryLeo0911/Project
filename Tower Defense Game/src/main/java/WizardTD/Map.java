package WizardTD;
import processing.core.PImage;
import java.io.*;
import java.util.*;

public class Map {
    private String file_path;
    private  ArrayList<String> raw_map_data;
    public ArrayList<ArrayList<Character>> processed_map_data = new ArrayList<>();
    public double rotate_angle;

    private App app;


    public Map(String file_path, App app){
        this.file_path = file_path;
        this.app = app;
        raw_map_data = raw_data(file_path);
        processed_map_data = processed_ArrayList(raw_map_data);
    }




    //make a method to read the file
    public ArrayList<String> raw_data(String file_path){
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file_path))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }


    //make a method to split the string into arrays
    public ArrayList<ArrayList<Character>> processed_ArrayList(ArrayList<String> raw_map_data){
        for(int i = 0; i < raw_map_data.size(); i++){
            ArrayList<Character> line_data = new ArrayList<>();
            for (char c : raw_map_data.get(i).toCharArray()) {
                if (c == ' ') {
                    line_data.add(' '); // Represent spaces as grass
                } else {
                    line_data.add(c); // Keep 'X' or other characters for the path
                }
            }
            processed_map_data.add(line_data);
        }
        return processed_map_data;
    }
    

    //make a function to tell me the angle of the image I should rotate
    public double rotate_angle(int y, int x) {
        double angle = 0;
    
        boolean isNorth = y > 0 && processed_map_data.get(y - 1).get(x).toString().equals("X");
        boolean isSouth = y < processed_map_data.size() - 1 && processed_map_data.get(y + 1).get(x).toString().equals("X");
        boolean isEast = x < processed_map_data.get(0).size() - 1 && processed_map_data.get(y).get(x + 1).toString().equals("X");
        boolean isWest = x > 0 && processed_map_data.get(y).get(x - 1).toString().equals("X");
    
        if (isNorth && isSouth && isEast && isWest) {
            angle = 0;
        } else if (isNorth && isSouth && isEast) {
            angle = 270;
        } else if (isNorth && isWest && isEast) {
            angle = 180;
        } else if (isNorth && isSouth && isWest) {
            angle = 90;
        } else if (isEast && isWest && isSouth) {
            angle = 0;
        } else if (isNorth && isSouth) {
            angle = 90;
        } else if (isEast && isNorth) {
            angle = 180;
        } else if (isEast && isSouth) {
            angle = 270;
        } else if (isWest && isNorth) {
            angle = 90;
        } else if (isWest && isSouth) {
            angle = 0;
        } else if (isNorth && !isSouth) {
            angle = 90;
        } else if (isSouth && !isNorth) {
            angle = 270;
        } else if (isEast && !isWest) {
            angle = 0;
        } else if (isWest && !isEast) {
            angle = 180;
        }
    
        return angle;
    }


    //make a function to tell me which type of path I should use
    public PImage choose_path_type(int y, int x){
        if (y == 0 && processed_map_data.get(y + 1).get(x).toString().equals("X") && processed_map_data.get(y).get(x + 1).toString().equals("X") == false && processed_map_data.get(y).get(x - 1).toString().equals("X") == false) {
            return app.straight_lane;
        }
        else if (y == 19 && processed_map_data.get(y - 1).get(x).toString().equals("X") && processed_map_data.get(y).get(x + 1).toString().equals("X") == false && processed_map_data.get(y).get(x - 1).toString().equals("X") == false) {
            return app.straight_lane;
        }
        else if (x == 0 && processed_map_data.get(y).get(x+1).toString().equals("X") && processed_map_data.get(y + 1).get(x).toString().equals("X") == false && processed_map_data.get(y - 1).get(x).toString().equals("X") == false) {
            return app.straight_lane;
        }
        else if (x == 19 && processed_map_data.get(y).get(x-1).toString().equals("X") && processed_map_data.get(y + 1).get(x).toString().equals("X") == false && processed_map_data.get(y - 1).get(x).toString().equals("X") == false) {
            return app.straight_lane;
        }
        else if (processed_map_data.get(y).get(x + 1).toString().equals("X") && processed_map_data.get(y).get(x - 1).toString().equals("X") && processed_map_data.get(y - 1).get(x).toString().equals("X") && processed_map_data.get(y + 1).get(x).toString().equals("X")) {
            return app.crossing_lane;
        }
        else if (processed_map_data.get(y + 1).get(x).toString().equals("X") && processed_map_data.get(y).get(x - 1).toString().equals("X") && processed_map_data.get(y - 1).get(x).toString().equals("X") && processed_map_data.get(y).get(x + 1).toString().equals("X") == false) {
            return app.T_lane;
        }
        else if (processed_map_data.get(y).get(x + 1).toString().equals("X") && processed_map_data.get(y).get(x - 1).toString().equals("X") && processed_map_data.get(y - 1).get(x).toString().equals("X") && processed_map_data.get(y + 1).get(x).toString().equals("X") == false) {
            return app.T_lane;
        }
        else if (processed_map_data.get(y - 1).get(x).toString().equals("X") && processed_map_data.get(y + 1).get(x).toString().equals("X") && processed_map_data.get(y).get(x + 1).toString().equals("X") && processed_map_data.get(y).get(x - 1).toString().equals("X") == false) {
            return app.T_lane;
        }
        else if (processed_map_data.get(y).get(x - 1).toString().equals("X") && processed_map_data.get(y).get(x + 1).toString().equals("X") && processed_map_data.get(y + 1).get(x).toString().equals("X") && processed_map_data.get(y - 1).get(x).toString().equals("X") == false) {
            return app.T_lane;
        }
        else if (processed_map_data.get(y - 1).get(x).toString().equals("X") && processed_map_data.get(y).get(x - 1).toString().equals("X") && processed_map_data.get(y).get(x + 1).toString().equals("X") == false && processed_map_data.get(y + 1).get(x).toString().equals("X") == false) {
            return app.corner_lane;
        }
        else if (processed_map_data.get(y).get(x + 1).toString().equals("X") && processed_map_data.get(y - 1).get(x).toString().equals("X") && processed_map_data.get(y).get(x - 1).toString().equals("X") == false && processed_map_data.get(y + 1).get(x).toString().equals("X") == false) {
            return app.corner_lane;
        }
        else if (processed_map_data.get(y).get(x + 1).toString().equals("X") && processed_map_data.get(y + 1).get(x).toString().equals("X") && processed_map_data.get(y).get(x - 1).toString().equals("X") == false && processed_map_data.get(y - 1).get(x).toString().equals("X") == false) {
            return app.corner_lane;
        }
        else if (processed_map_data.get(y + 1).get(x).toString().equals("X") && processed_map_data.get(y).get(x - 1).toString().equals("X") && processed_map_data.get(y - 1).get(x).toString().equals("X") == false && processed_map_data.get(y).get(x + 1).toString().equals("X") == false) {
            return app.corner_lane;
        }
        else {
            return app.straight_lane;
        }
    }
}