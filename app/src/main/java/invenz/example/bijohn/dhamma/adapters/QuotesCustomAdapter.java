package invenz.example.bijohn.dhamma.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import invenz.example.bijohn.dhamma.R;

public class QuotesCustomAdapter extends BaseAdapter {

    private Context context;
    private String[] quotes;

    public QuotesCustomAdapter(Context context, String[] quotes) {
        this.context = context;
        this.quotes = quotes;
    }

    @Override
    public int getCount() {
        return quotes.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.single_quote, parent, false);
        }

        TextView tvHeading = convertView.findViewById(R.id.idQuoteText_singleQuote);
        tvHeading.setText(quotes[position]);

        //Log.d("ROY", "getView: "+quotes[position]);

        return convertView;
    }
}
