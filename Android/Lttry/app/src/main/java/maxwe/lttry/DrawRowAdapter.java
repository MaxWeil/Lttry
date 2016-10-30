package maxwe.lttry;

/**
 * Created by maxwe on 10/22/2016.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

class DrawRowAdapter extends ArrayAdapter<String[]>{

    public DrawRowAdapter(Context context, String[][] draw) {
        super(context,R.layout.draw_row , draw);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View drawLayout = inflater.inflate(R.layout.draw_row, parent, false);

        TextView txtVTitle = (TextView) drawLayout.findViewById(R.id.txtVTitle);
        TextView txtVDraw = (TextView) drawLayout.findViewById(R.id.txtVDraw);
        TextView txtVDate = (TextView) drawLayout.findViewById(R.id.txtVDate);

        txtVTitle.setText(getItem(0)));
        txtVDraw.setText(getItem(1));
        txtVDate.setText(getItem(2));

        return drawLayout;
    }
}
