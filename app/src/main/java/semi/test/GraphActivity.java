package semi.test;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import static semi.test.MainActivity.sensorsArray;

public class GraphActivity extends AppCompatActivity {
    int j;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        int id = bd.getInt("id");
        for (int g = 0; g<sensorsArray.length;g++){
            if (sensorsArray[g].getId() == id) j=g;
        }

        GraphView graph = (GraphView) findViewById(R.id.graph);
        int max = MainActivity.sensorsArray[j].misurations.length;

        //serie di datapoint per l'umidità
        LineGraphSeries<DataPoint> series;
        DataPoint[] coordinate = new DataPoint[MainActivity.sensorsArray[j].misurations.length];
        for (int i=0; i<max; i++){
            coordinate[i] = new DataPoint(MainActivity.sensorsArray[j].misurations[i].getDate(),MainActivity.sensorsArray[j].misurations[i].getHumidity());
        }
        series = new LineGraphSeries<>(coordinate);

        //serie di datapoint per la temperatura
        LineGraphSeries<DataPoint> series2;
        DataPoint[] coordinate2 = new DataPoint[MainActivity.sensorsArray[j].misurations.length];
        for (int i=0; i<max; i++){
            coordinate2[i] = new DataPoint(MainActivity.sensorsArray[j].misurations[i].getDate(),MainActivity.sensorsArray[j].misurations[i].getTemperature());
        }
        series2 = new LineGraphSeries<>(coordinate2);


        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(graph.getContext()));
        graph.getGridLabelRenderer().setNumVerticalLabels(10); // only 4 because of the space

        // set manual x bounds to have nice steps
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(MainActivity.sensorsArray[j].misurations[0].getDate().getTime());
        graph.getViewport().setMaxX(MainActivity.sensorsArray[j].misurations[max-1].getDate().getTime());

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(90);
        //graph.getViewport().setScalable(true);
        //graph.getViewport().setScalableY(true);
        series.setTitle("Humidity °C");
        graph.addSeries(series);

        graph.getSecondScale().addSeries(series2);
        graph.getSecondScale().setMinY(0);
        graph.getSecondScale().setMaxY(90);
        series2.setColor(Color.RED);
        series2.setTitle("Temperature %");
        // as we use dates as labels, the human rounding to nice readable numbers
        // is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
    }
}
