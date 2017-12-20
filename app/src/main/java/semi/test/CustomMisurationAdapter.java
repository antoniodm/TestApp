package semi.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CustomMisurationAdapter extends ArrayAdapter<JSON>/* implements View.OnClickListener*/{

    private ArrayList<JSON> dataSet;
    private Context mContext;
    private Format formatter = new SimpleDateFormat("dd/mm/yy hh:mm:ss");

    // View lookup cache
    private static class ViewHolder {
        TextView txtTemp;
        TextView txtHum;
        TextView txtData;
        //TextView txtId;
    }

    public CustomMisurationAdapter(ArrayList<JSON> data, Context context) {
        super(context, R.layout.list_misuration, data);
        this.dataSet = data;
        this.mContext=context;

    }

    //private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        JSON dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        //final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_misuration, parent, false);
            viewHolder.txtTemp = (TextView) convertView.findViewById(R.id.editText2);
            viewHolder.txtHum = (TextView) convertView.findViewById(R.id.editText3);
            viewHolder.txtData = (TextView) convertView.findViewById(R.id.editText4);
            //viewHolder.txtId = (TextView) convertView.findViewById(R.id.editText5);

           // result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        //    result=convertView;
        }

        // Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_down : R.anim.down_from_top);
        //result.startAnimation(animation);
        //lastPosition = position;
        viewHolder.txtTemp.setText(String.valueOf(dataModel.getTemperature()+"°C"));
        viewHolder.txtHum.setText(String.valueOf(dataModel.getHumidity()));
        String s = formatter.format(dataModel.getDate());
        viewHolder.txtData.setText(s);
        //viewHolder.info.setOnClickListener(this);
        //viewHolder.txtId.setText(String.valueOf(dataModel.getId()));
        // Return the completed view to render on screen
        return convertView;
    }
}