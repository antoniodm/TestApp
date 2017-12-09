package semi.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
//import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.util.ArrayList;
//import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static semi.test.Config.SERVER_URL;
import static semi.test.JSON.sdf;
//import static semi.test.MainActivity.editTextData1;
//import static semi.test.MainActivity.editTextData2;
import static semi.test.MainActivity.sensorsArray;
import static semi.test.MainActivity.address;
//import static semi.test.R.id.listView;

public class ListMisurationActivity extends AppCompatActivity {

    static int ide;
    private ListView listView1;
    Button buttonGetGr;
    Button buttonSetting;
    private TextView textView;

    List<String> li;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_misuration);
        textView = (TextView) findViewById(R.id.textView);
        buttonGetGr = (Button) findViewById(R.id.buttonGetGr);
        buttonSetting = (Button) findViewById(R.id.buttonSetting);
        listView1 = (ListView)  findViewById(R.id.listView1);
        buttonGetGr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), GraphActivity.class);
                // sending data to new activity
                i.putExtra("id", ide);
                startActivity(i);            }
        });

        buttonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                // sending data to new activity
                i.putExtra("id", ide);
                startActivity(i);            }
        });

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null)
        {
            ide = bd.getInt("id");
        }
        getData();
    }

    private void getData() {
        String id = "id="+String.valueOf(ide);//+editTextData1.getText().toString().trim();
        //String date1 = "data1="+editTextData1.getText().toString().trim();
        //String date2 = "data2="+editTextData2.getText().toString().trim();
        //compongo l'URL con il parametro passato
        //String url = Config.DATA_URL+id+"&"+date1+"&"+date2;
        String url = "http://"+address+SERVER_URL+id;
        //preparo una stringrequest
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                parseJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListMisurationActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void parseJSON(String res){
        JSONObject jsonObject;
        JSONArray jasarray;
        int test=0;
        try {

            jsonObject = new JSONObject(res);
            jasarray = jsonObject.getJSONArray(Config.JSON_ARRAY);

            for (int g = 0; g<sensorsArray.length;g++){
                if (sensorsArray[g].getId() == ide) test=g;
            }
            textView.setText(String.format(getString(R.string.text_sensor_id), sensorsArray[test].getId()));

            //setto la dimensione dell'arrady di misurazioni
            sensorsArray[test].setMisurations(jasarray.length());
            li = new ArrayList<>();

            for(int i = 0; i< jasarray.length(); i++){
                JSONObject jo = jasarray.getJSONObject(i);
                int id = parseInt(jo.getString(Config.KEY_ID));
                double humidity = parseDouble(jo.getString(Config.KEY_HUMIDITY));
                double temperature = parseDouble(jo.getString(Config.KEY_TEMPERATURE));
                Date date = stringToDate(jo.getString(Config.KEY_DATA));

                sensorsArray[test].misurations[i] = new JSON(date,id,humidity,temperature);

                li.add("Hum: "+Double.toString(humidity)+"%, Temp: "+Double.toString(temperature)+"°C\n"+android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss a", date));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Collections.reverse(li);
        ArrayAdapter<String> adp = new ArrayAdapter<>(this,R.layout.listitem, li);
        adp.setDropDownViewResource(R.layout.listitem);
        listView1.setAdapter(adp);
    }

    public Date stringToDate(String str){
        Date d = null;
        try {
            d = sdf.parse(str);
        }catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return d;
    }
}


