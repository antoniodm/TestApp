package semi.test;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static java.lang.Integer.parseInt;
import static semi.test.Config.SERVER_URL;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    public static Sensor[] sensorsArray;
    public static String address;
    public  TextView TextViewIp;
    private Button buttonGet;
    private Button buttonChangeIp;

    private ListView listView;
    List<String> li;        //per stampare la lista senza usare custom adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextViewIp = (TextView) findViewById(R.id.textViewIp);
        buttonGet = (Button) findViewById(R.id.buttonGet);
        buttonChangeIp = (Button) findViewById(R.id.buttonChangeIp);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString("address", "10.0.2.2");
        if (restoredText != null) {
            address = prefs.getString("address", "10.0.2.2");//"10.0.2.2" is the default value.
        }
        TextViewIp.setText(String.format(getString(R.string.text_ip), address));


        //buttonGraph = (Button) findViewById(R.id.buttonGraph);
        listView = (ListView)  findViewById(R.id.listView);
        //avvio i listener sui bottoni
        buttonGet.setOnClickListener(this);
        buttonChangeIp.setOnClickListener(this);





    }

    @Override
    public void onClick(View v) {

        if (v == buttonChangeIp){

            final EditText ip = new EditText(this);

            new AlertDialog.Builder(this)
                    .setTitle("Server Connection")
                    .setMessage("Insert the Server Address")
                    .setView(ip)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            address = ip.getText().toString();

                            //salvo la preferenza dell'Ip per non reinserirlo in futuro
                            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                            editor.putString("address", address);
                            editor.apply();

                            TextViewIp.setText(String.format(getString(R.string.text_ip), address));

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    })
                    .show();
            }
            if (v == buttonGet){
                getSensors();

            }



    }

    private void getSensors(){
        String url = "http://"+address+SERVER_URL;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                parseSensors(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void parseSensors(String res){
        JSONObject jsonObject;
        JSONArray jasarray;

        try {

            jsonObject = new JSONObject(res);
            jasarray = jsonObject.getJSONArray(Config.SENSORS_ARRAY);

            sensorsArray = new Sensor[jasarray.length()];
            li = new ArrayList<>();

            for(int i = 0; i< jasarray.length(); i++){

                JSONObject jo = jasarray.getJSONObject(i);

                int id = parseInt(jo.getString(Config.KEY_ID));
                String type = jo.getString(Config.KEY_TYPE);
                int code = parseInt(jo.getString(Config.KEY_CODE));
                boolean active = jo.getBoolean(Config.KEY_FLAG);

                sensorsArray[i] = new Sensor(id,type,code, active,null);
                if (sensorsArray[i].getFlag()){
                    li.add("Sensor: "+Integer.toString(id)+", Seeds: "+type+", Code: "+code+", Active: "+String.valueOf(active));
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adp = new ArrayAdapter<>(this,R.layout.listitem, li);
        adp.setDropDownViewResource(R.layout.listitem);
        listView.setAdapter(adp);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // Launching new Activity on selecting single List Item
                Intent i = new Intent(getApplicationContext(), ListMisurationActivity.class);
                // sending data to new activity
                String recipes = String.valueOf(parent.getItemAtPosition(position));
                String str = recipes.split(", ")[0].split(": ")[1];
                int k = parseInt(str);
                i.putExtra("id", k);
                startActivity(i);
            }
        });
    }


    /*public Date stringToDate(String str){
        Date d = null;
        try {
            d = sdf.parse(str);
        }catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return d;
    }


    private String formatDate(Calendar d){
        int year = d.get(Calendar.YEAR);
        int mon = d.get(Calendar.MONTH)+1;
        int day = d.get(Calendar.DAY_OF_MONTH);
        String str = Integer.toString(year)+"-"+Integer.toString(mon)+"-"+Integer.toString(day);
        return str;
    }*/

}
