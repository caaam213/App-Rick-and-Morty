package com.example.api_app;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class listeEpisodes extends AppCompatActivity {
    private ArrayList<String> list;
    private ArrayList<Integer> listInt;
    Personnage perso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.listepisodes);
        perso = (Personnage) getIntent().getSerializableExtra("liste");
        listInt = perso.getEpisodes();
        list = recupListeEpisode(listInt);

        ListView lv = findViewById(R.id.listE);

        lv.setAdapter(new adapterEpisode(listeEpisodes.this,list));

    }

    public ArrayList<String> recupListeEpisode(ArrayList<Integer> listInt)
    {
        ArrayList<String> list = new ArrayList<>();
        for(Integer unEpisode:listInt)
        {
            String ep = "Episode "+unEpisode;
            Log.d("rfef",ep);
            list.add(ep);
        }
        return list;
    }
}
