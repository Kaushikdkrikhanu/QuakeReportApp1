package com.deka.myapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomArrayAdapter extends ArrayAdapter<Earthquake> {

    public CustomArrayAdapter(@NonNull Context context, @NonNull ArrayList<Earthquake> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        if(listView==null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_activity,parent,false);
        }
        Earthquake currentEarthquake = getItem(position);

        TextView textView = listView.findViewById(R.id.magnitude);
        GradientDrawable magnitudeCircle = (GradientDrawable) textView.getBackground();
        int magnitudeColor = magnitudeColor(currentEarthquake.magnitude);

        magnitudeCircle.setColor(magnitudeColor);
        Log.i("colorId",magnitudeColor+"");
        textView.setText(String.valueOf(currentEarthquake != null ? currentEarthquake.magnitude : 0));

        String originalLocation = currentEarthquake.location;
        String primaryLocation;
        String locationOffset;
        String LOCATION_SEPARATOR = " of ";
        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }

        TextView primaryLocationView = (TextView) listView.findViewById(R.id.primary_location);
        primaryLocationView.setText(primaryLocation);

        TextView locationOffsetView = (TextView) listView.findViewById(R.id.location_offset);
        locationOffsetView.setText(locationOffset);

        Date dateObject = new Date(currentEarthquake.timeInMilliSeconds);
        String date = formateDate(dateObject);
        String time = formateTime(dateObject);

        TextView textView2 =  listView.findViewById(R.id.date);
        textView2.setText(date);

        TextView textView3 = listView.findViewById(R.id.time);
        textView3.setText(time);

        return listView;
    }

    private String formateDate(Date dateObject){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("LLL dd, YYYY");
        return simpleDateFormat.format(dateObject);
    }

    private String formateTime(Date dateObject){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
        return simpleDateFormat.format(dateObject);
    }
    private int magnitudeColor(double magnitude){
        int mag = (int) Math.floor(magnitude);

        int magnitudeColorResourceId;

        switch (mag){
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;

        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);

    }
}
