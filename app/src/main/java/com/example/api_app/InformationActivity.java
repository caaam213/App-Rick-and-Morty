package com.example.api_app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        TextView loc = findViewById(R.id.location);
        TextView gen = findViewById(R.id.gender);
        TextView ori = findViewById(R.id.origin);
        ImageView iV = findViewById(R.id.im);


        nP.setText(perso.getNomPerso());

        sta.setText(perso.getStatus());
        if(perso.getStatus().equals("Alive"))
        {
            sta.setTextColor(Color.GREEN);
        }
        else if(perso.getStatus().equals("Dead"))
        {
            sta.setTextColor(Color.RED);
        }
        loc.setText(perso.getLocation());
        gen.setText(perso.getGender());
        if(perso.getGender().equals("Male"))
        {
            gen.setTextColor(Color.BLUE);
        }
        else if(perso.getGender().equals("Female"))
        {
            gen.setTextColor(Color.rgb(255,192,203));
        }
        ori.setText(perso.getOrigin());
        Picasso.get().load(perso.getImage()).into(iV);

    }

    public void voirListe(View view)
    {
        Intent intent = new Intent(InformationActivity.this,listeEpisodes.class);
        intent.putExtra("liste", perso);
        startActivity(intent);
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
        File file = new File(directory, "listeFavori2");


        if(file.exists()) {
            FileInputStream fis = null;
            ObjectInputStream in = null;
            try {
                fis = openFileInput("listeFavori2");
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
        File file = new File(directory, "listeFavori2");

        try {
            assert fos != null;
            fos = openFileOutput("listeFavori2", Context.MODE_PRIVATE);
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
