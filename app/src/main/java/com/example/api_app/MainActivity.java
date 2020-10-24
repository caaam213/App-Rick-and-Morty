package com.example.api_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Personnage> liste = new ArrayList<>();
    private ArrayList<Personnage> listeFav = new ArrayList<>();
    private Switch mySwitch;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mySwitch = findViewById(R.id.switch2);
        final ListView listePerso = findViewById(R.id.listview);

        if(listeFav.size()==0)
        {
            recupTabFavori();
        }

        adapter = new MyAdapter(MainActivity.this, liste);

        if(mySwitch.isChecked())
        {
            recupTabFavori();
            adapter.setList(listeFav);
        }
        else
        {
            

            new MyAsyncTask().execute(liste,adapter);
        }

        listePerso.setAdapter(adapter);




        listePerso.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1 = new Intent(MainActivity.this,InformationActivity.class);
                Personnage perso = (Personnage) listePerso.getItemAtPosition(i);
                intent1.putExtra("Perso selec", perso);
                startActivity(intent1);

            }
        });

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    recupTabFavori();
                    adapter.setList(listeFav);

                }
                else
                {
                    adapter.setList(liste);
                }

                adapter.notifyDataSetChanged();
            }
        });

    }


    @Override
    protected void onStop() {
        super.onStop();
        FileOutputStream fos = null;
        ObjectOutputStream out = null;

        File directory = this.getFilesDir();
        File file = new File(directory, "listePerso");

        if (liste.size() != 0)

                try {
                    assert fos != null;
                    fos = openFileOutput("listePerso", Context.MODE_PRIVATE);
                    out = new ObjectOutputStream(fos);
                    out.writeObject(liste);
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

        private void recupTab()
        {
            File directory = this.getFilesDir();
            File file = new File(directory, "listePerso");
            if(file.exists()) {
                FileInputStream fis = null;
                ObjectInputStream in = null;
                try {
                    fis = openFileInput("listePerso");
                    in = new ObjectInputStream(fis);
                    liste = (ArrayList<Personnage>) in.readObject();
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



        public void recupTabFavori()
        {
            File directory = this.getFilesDir();
            File file = new File(directory, "listeFavori");


            if(file.exists()) {
                FileInputStream fis = null;
                ObjectInputStream in = null;
                try {
                    fis = openFileInput("listeFavori");
                    in = new ObjectInputStream(fis);
                    listeFav = (ArrayList<Personnage>) in.readObject();

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
    }



