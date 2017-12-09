package semi.test;
import android.support.v4.app.DialogFragment;
/*
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
*/


public class DatePickerFragment extends DialogFragment {
/*
    private DatePicker datePicker;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date,null);
        datePicker = (DatePicker) v.findViewById(R.id.dialog_date_date_picker);

        return new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int year = datePicker.getYear();
                                int mon = datePicker.getMonth();
                                int day = datePicker.getDayOfMonth();
                                String str = Integer.toString(year)+"-"+Integer.toString(mon)+1+"-"+Integer.toString(day);
                               //Date date = new GregorianCalendar(year,mon,day).getTime();
                                if (MainActivity.scelta == 0) MainActivity.editTextData1.setText(str);
                                else MainActivity.editTextData2.setText(str);
                                dismiss();
                            }
                        })
                .create();
    }*/
}