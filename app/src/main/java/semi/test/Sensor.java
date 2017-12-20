package semi.test;

import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Boolean.TRUE;
//import java.util.Date;



class Sensor implements Serializable{
    public int id;
    private String type;
    private int code;
    private boolean active;
    private boolean pause;
    ArrayList<JSON> misurations;


    Sensor( int id , String type, int code, boolean active , boolean pause, ArrayList<JSON> misurations) {
        super();
        this.id = id;
        this.code = code;
        this.type = type;
        this.active = active;
        this.misurations = misurations;
        this.pause = pause;
    }



    public boolean getActive(){
        return active;
    }

    public boolean getPause(){
        return pause;
    }


    public int getCode(){
        return code;
    }

    public int getId(){
        return id;
    }

    public String getSeedsType(){
        return type;
    }

  //  public JSON[] getMisurations() { return misurations; }

    public void setActive(boolean active){
        this.active = active;
    }

    public void setId(int id){
        this.id=id;
    }

    public void setType(String type){
        this.type = type;
    }

    /*void setMisurations(JSON misuration, int sensor){
        misurations.add(misuration);
    }*/



    @Override
    public String toString(){
        return "Sensor Id: "+this.id +", Seeds Type: "+this.type+", Arduino Id: "+this.code+", Active: "+this.active;
    }

}
