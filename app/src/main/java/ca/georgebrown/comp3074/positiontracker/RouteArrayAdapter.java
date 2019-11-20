package ca.georgebrown.comp3074.positiontracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ca.georgebrown.comp3074.positiontracker.model.Route;
import ca.georgebrown.comp3074.positiontracker.sql.DbHelper;

public class RouteArrayAdapter extends ArrayAdapter<Route> implements Filterable {

    private static ArrayList<Route> objects;

    private int layout;

    public RouteArrayAdapter(@NonNull Context context, int resource, @NonNull List<Route> objects){
        super(context, resource, objects);

        layout = resource;

        this.objects = (ArrayList<Route>)objects;

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, null);
        }

        Route item = getItem(position);
        TextView t = convertView.findViewById(R.id.txtRoute);
        t.setText(item.getRouteName());

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                ArrayList<Route> tempList = new ArrayList<>();

                DbHelper dbHelper = new DbHelper(getContext());

                if(charSequence!=null || objects.size() > 0){
                    for(Route r : objects){
                        String sequence = charSequence.toString().toLowerCase();
                        String tags = dbHelper.stringTag(r);
                        if(r.getRouteName().toLowerCase().contains(sequence) || tags.toLowerCase().contains(sequence)){
                            tempList.add(r);
                        }
                    }
                    filterResults.values = tempList;
                    filterResults.count = tempList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                objects = (ArrayList<Route>)filterResults.values;
                notifyDataSetChanged();
                clear();
                for(Route r : RouteArrayAdapter.objects){
                    add(r);
                }
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
