package com.example.api_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


public class MyAdapter extends BaseAdapter {
    Context context;
    ArrayList<Personnage> listePerso;

    public MyAdapter(Context c, ArrayList<Personnage> listePerso)
    {
        this.context = c;
        this.listePerso = listePerso;
    }

    public void setList(ArrayList<Personnage> liste) {
        this.listePerso = liste;
    }

    public ArrayList<Personnage> getListePerso() {
        return listePerso;
    }

    @Override
    public int getCount() {
        return listePerso.size();
    }

    @Override
    public Object getItem(int i) {
        return listePerso.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.row, parent, false);
        TextView tv = row.findViewById(R.id.textView);
        ImageView image = row.findViewById(R.id.imageView);


        tv.setText(listePerso.get(position).getNomPerso());
        String imageUrl = listePerso.get(position).getImage();
        Picasso.get().load(imageUrl).into(image);




        return row;
    }




    }






