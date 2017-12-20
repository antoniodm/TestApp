package semi.test;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import static semi.test.Config.SERVER_URL;
import static semi.test.MainActivity.sensorsArray;
import static semi.test.MainActivity.address;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{
    int id, position;
    Button buttonChange;
    Button buttonArc;
    TextView textViewId;
    TextView textViewType;
    TextView textViewArd;
    TextView textViewFlag;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();


        buttonChange = (Button) findViewById(R.id.buttonChange);
        buttonArc = (Button) findViewById(R.id.buttonArc);
        buttonChange.setOnClickListener(this);
        buttonArc.setOnClickListener(this);

        textViewId = (TextView) findViewById(R.id.textViewId);
        textViewType = (TextView) findViewById(R.id.textViewType);
        textViewArd = (TextView) findViewById(R.id.textViewArd);
        textViewFlag = (TextView) findViewById(R.id.textViewFlag);

        id = bd.getInt("id");
        for (int g = 0; g<sensorsArray.size(); g++){
            if (sensorsArray.get(g).getId() == id) {
                position = g;
                textViewId.setText(String.format(getString(R.string.text_sensor_id), id));
                textViewArd.setText(String.format(getString(R.string.text_arduino_id), sensorsArray.get(g).getCode()));
                textViewType.setText(String.format(getString(R.string.text_type), sensorsArray.get(g).getSeedsType()));
                textViewFlag.setText(String.format(getString(R.string.text_flag), String.valueOf(sensorsArray.get(g).getActive())));

            }
        }

    }

        public void onClick(View v) {

            if (v == buttonArc){
                if (!sensorsArray.get(position).getSeedsType().equals("indefinito")){
                    archive();
                    sensorsArray.get(position).setType("indefinito");
                    sensorsArray.get(position).setActive(false);
                    textViewFlag.setText(String.format(getString(R.string.text_flag), String.valueOf(sensorsArray.get(position).getActive())));
                    textViewType.setText(String.format(getString(R.string.text_type), sensorsArray.get(position).getSeedsType()));


                }else{
                    Toast.makeText(SettingsActivity.this, "Please, set the seed name!",
                            Toast.LENGTH_LONG).show();
                }
            }
            if (v == buttonChange){
                final EditText txt = new EditText(this);

                // Set the default text to a link of the Queen
                txt.setHint(sensorsArray.get(position).getSeedsType());
                new AlertDialog.Builder(this)
                        .setTitle("Seed Type")
                        .setMessage("Insert the seed type")
                        .setView(txt)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String name = txt.getText().toString();
                                setSeedName(name);
                                sensorsArray.get(position).setType(name);
                                textViewType.setText(String.format(getString(R.string.text_type), sensorsArray.get(position).getSeedsType()));

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .show();

            }

        }

        public void setSeedName(String name){

            String url = "http://"+address+SERVER_URL+"id="+id+"&setName="+name;
            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (response.equals("200")) {
                        Toast.makeText(SettingsActivity.this, "The name is changed",
                                Toast.LENGTH_LONG).show();

                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SettingsActivity.this,error.toString(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }


        public void archive(){
            String request = "http://"+address+SERVER_URL+"id="+id+"&archive=true"+"&code="+sensorsArray.get(position).getCode();
            StringRequest stringRequest = new StringRequest(request, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (response.equals("200")) {
                        Toast.makeText(SettingsActivity.this, "Misurations correctly archived",
                                Toast.LENGTH_LONG).show();

                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SettingsActivity.this,error.toString(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }
