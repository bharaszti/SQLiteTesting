package com.example.bence.sqlitetesting.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bence.sqlitetesting.R;
import com.example.bence.sqlitetesting.domain.Person;

import java.util.List;

/**
 * Created by bence on 19.09.15.
 */
public class PersonArrayAdapter extends ArrayAdapter<Person> implements AdapterView.OnItemClickListener {
    private final Context context;
    private final List<Person> values;
    private Integer selectedId;


    public PersonArrayAdapter(Context context, List<Person> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);

        Person item = values.get(position);

        String prefix = "";
        if (selectedId != null && selectedId.equals(item.getId())) {
            prefix = "selected: ";
        }
        textView.setText(prefix + item);

        return rowView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Person person = (Person) getItem(position);
        selectedId = person.getId();
        view.invalidate();
    }

}
