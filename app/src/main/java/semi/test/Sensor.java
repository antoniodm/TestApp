package semi.test;

import java.io.Serializable;

import static java.lang.Boolean.TRUE;
//import java.util.Date;



class Sensor implements Serializable{
    public int id;
    private String type;
    private int code;
    private boolean flag;
     JSON[] misurations;

    /*public Sensor(){
        super();
    }*/

    Sensor( int id , String type, int code, boolean flag ,JSON[] misurations) {
        super();
        this.id = id;
        this.code = code;
        this.type = type;
        this.flag = flag;
        this.misurations = misurations;
    }

    public boolean getFlag(){
        return flag;
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

    public void setFlag(boolean flag){
        this.flag = flag;
    }

    public void setId(int id){
        this.id=id;
    }

    public void setType(String type){
        this.type = type;
    }

    void setMisurations(int arraylenght){
        this.misurations = new JSON[arraylenght];
    }



    @Override
    public String toString(){
        return "Sensor Id: "+this.id +", Seeds Type: "+this.type+", Arduino Id: "+this.code+", Active: "+this.flag;
    }

}
