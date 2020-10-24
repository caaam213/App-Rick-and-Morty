package com.example.api_app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

public class InformationActivity extends AppCompatActivity{

    ArrayList<Personnage> persoFav = new ArrayList<>();
    Personnage perso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        perso = (Personnage) getIntent().getSerializableExtra("Perso selec");
        afficherInfo(perso);
        recupTab();
        boutonChanger();


    }

    public void onBackPressed()
    {
        Intent intent = new Intent(InformationActivity.this,MainActivity.class);
        startActivity(intent);

    }

    private void afficherInfo(Personnage perso)
    {
        TextView nP = findViewById(R.id.nomPerso);
        TextView sta = findViewById(R.id.status);
        TextView spe = findViewById(R.id.species);
        TextView gen = findViewById(R.id.gender);
        TextView loc = findViewById(R.id.location);
        TextView ep = findViewById(R.id.episodes);
        ImageView iV = findViewById(R.id.im);

        nP.setText(perso.getNomPerso());
        sta.setText(perso.getStatus());
        spe.setText(perso.getSpecies());
        gen.setText(perso.getGender());
        loc.setText(perso.getLocation());
        ep.setText(Integer.toString(perso.getEpisodes().size()));
        Picasso.get().load(perso.getImage()).into(iV);

    }




    public void fav(View view)
    {
        boolean etre = false;
        ImageButton button = findViewById(R.id.imageButton2);
        persoFav.clear();
        recupTab();

        for(Personnage p:persoFav)
        {
            if(p.getId() == perso.getId())
            {
                etre = true;
            }
        }


        if(etre)
        {
            for(int i=0;i<persoFav.size();i++)
            {
                if(persoFav.get(i).getId() == perso.getId())
                {
                    persoFav.remove(i);
                    refresh();
                }
            }

            button.setImageResource(android.R.drawable.btn_star_big_off);
        }
        else
        {

            persoFav.add(perso);
            button.setImageResource(android.R.drawable.btn_star_big_on);
        }
        refresh();
    }


    public void recupTab()
    {

        File directory = this.getFilesDir();
        File file = new File(directory, "listeFavori");


        if(file.exists()) {
            FileInputStream fis = null;
            ObjectInputStream in = null;
            try {
                fis = openFileInput("listeFavori");
                in = new ObjectInputStream(fis);
                persoFav = (ArrayList<Personnage>) in.readObject();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void refresh()
    {

        FileOutputStream fos = null;
        ObjectOutputStream out = null;

        File directory = this.getFilesDir();
        File file = new File(directory, "listeFavori");

        try {
            assert fos != null;
            fos = openFileOutput("listeFavori", Context.MODE_PRIVATE);
            out = new ObjectOutputStream(fos);
            out.writeObject(persoFav);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void boutonChanger() {
        boolean etre = false;
        ImageButton button = findViewById(R.id.imageButton2);
        for (Personnage p : persoFav) {
            if (p.getId() == perso.getId()) {
                etre = true;
            }
        }

        if (etre) {
            button.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            button.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }




}
