package semi.test;

//import org.json.JSONArray;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


class JSON implements Serializable{

    private Date date;
    public int id;
    private double humidity;
    private double temperature;


    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    JSON(Date date, int id , double humidity, double temperature) {
        this.date = date;
        this.id = id;
        this.humidity = humidity;
        this.temperature = temperature;
    }
    Date getDate(){
        return date;
    }


    public int getId(){
        return id;
    }

    double getHumidity(){
        return humidity;
    }
    double getTemperature(){
        return temperature;
    }

}