package com.streamavailability.Adapter.moviesResult;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.streamavailability.Model.Genre;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class SpinnerAdapterGeneric<T>   extends  ArrayAdapter<T> {

    // Your sent context
    private final Context context;


    // Your custom values for the spinner (User)
    private ArrayList<T> values;
    private String fieldValue;


    public SpinnerAdapterGeneric(Context context, int textViewResourceId,
                       ArrayList<T> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount(){
        return values.size();
    }

    @Override
    public T getItem(int position){
        return values.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.WHITE);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)

        //read a field value and set label
        T value = values.get(position);
        Class cls = value.getClass();
        Field field = null;
        try {

            field = cls.getDeclaredField(fieldValue);
            field.setAccessible(true);

            String fieldValue2 = (String) field.get(value);

            label.setText(fieldValue2);
        } catch (NoSuchFieldException e) {
            Log.e("Adapter NoSuchFieldException",e.getMessage());
        } catch (IllegalAccessException e) {
            Log.e("Adapter IllegalAccessException",e.getMessage());
        }



        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);

        T value = values.get(position);
        Class cls = value.getClass();
        Field field = null;
        try {

            field = cls.getDeclaredField(fieldValue);
            field.setAccessible(true);
            String fieldValue2 = (String) field.get(value);

            label.setText(fieldValue2);
        } catch (NoSuchFieldException e) {
            Log.e("Adapter NoSuchFieldException",e.getMessage());
        } catch (IllegalAccessException e) {
            Log.e("Adapter IllegalAccessException",e.getMessage());
        }


        return label;
    }

    public void setValues(ArrayList<T> values) {
        this.values = values;
    }

    public void setField(String fieldValue){
        this.fieldValue = fieldValue;
    }

}