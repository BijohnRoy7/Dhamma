package invenz.example.bijohn.dhamma.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import invenz.example.bijohn.dhamma.models.Event;
import invenz.example.bijohn.dhamma.R;

public class EventsCustomAdapter extends BaseAdapter {

    private List<Event> events;
    private Context context;

    public EventsCustomAdapter(List<Event> events, Context context) {
        this.events = events;
        this.context = context;
        Log.d("ROY", "EventsCustomAdapter: "+events.get(0).getDate());
    }

    @Override
    public int getCount() {
        return events.size();
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

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.single_event, parent, false);
        }

        TextView tvDate = convertView.findViewById(R.id.idEventDate_singleEvent);
        TextView tvEventName = convertView.findViewById(R.id.idEvanetName_singleEvent);

        Event event = events.get(position);

        tvDate.setText(event.getDate());
        tvEventName.setText(event.getEventName());

        return convertView;
    }
}
