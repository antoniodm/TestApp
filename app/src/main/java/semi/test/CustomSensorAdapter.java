package semi.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by antonio on 14/12/17.
 */
public class CustomSensorAdapter extends ArrayAdapter<Sensor>/* implements View.OnClickListener*/{

    private ArrayList<Sensor> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtid;
        TextView txtida;
        TextView txtnome;
        TextView txtactive;
    }

    public CustomSensorAdapter(ArrayList<Sensor> data, Context context) {
        super(context, R.layout.list_sensor, data);
        this.dataSet = data;
        this.mContext=context;

    }


    //private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Sensor dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        //final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_sensor, parent, false);
            viewHolder.txtid = (TextView) convertView.findViewById(R.id.idSensor);
            viewHolder.txtida = (TextView) convertView.findViewById(R.id.idArduino);
            viewHolder.txtnome = (TextView) convertView.findViewById(R.id.idNome);
            viewHolder.txtactive = (TextView) convertView.findViewById(R.id.idActive);

            //result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            //result=convertView;
        }

       // Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_down : R.anim.down_from_top);
        //result.startAnimation(animation);
        //lastPosition = position;
        String id = String.valueOf(dataModel.getId());
        viewHolder.txtid.setText(id);
        viewHolder.txtnome.setText(dataModel.getSeedsType());
        viewHolder.txtactive.setText(Boolean.toString(dataModel.getActive()));
        //viewHolder.info.setOnClickListener(this);
        viewHolder.txtida.setText(String.valueOf(dataModel.getCode()));
        // Return the completed view to render on screen
        return convertView;
    }
}