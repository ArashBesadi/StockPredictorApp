package commons.stockmarket.com.stockpredictor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private String[] title;

    public CustomAdapter(Context context, String[] title) {
        this.context = context;
        this.title = title;
    }

    public int getCount() {
        return title.length;
    }

    public Object getItem(int position) {
        return title[position];
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View row = inflater.inflate(R.layout.listview_row, parent, false);
        final ImageView icon = (ImageView) row.findViewById(R.id.imgIcon);
        final TextView title = (TextView) row.findViewById(R.id.txtTitle);
        title.setText(this.title[position]);
        icon.setImageResource(R.drawable.stock);
        return row;
    }
}