package com.example.api_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private ArrayList<Personnage> listeDead = new ArrayList<>();
    private ArrayList<Personnage> listeAlive= new ArrayList<>();
    private Menu menu;
    private boolean checked = false;
    private boolean deadChecked = false;
    private boolean aliveChecked = false;
    MyAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        this.menu = menu;
        inflater.inflate(R.menu.menu_fav,menu);

        final ListView listePerso = findViewById(R.id.listview);

        if(listeFav.size()==0)
        {
            recupTabFavori();
        }

        adapter = new MyAdapter(MainActivity.this, liste);

        new MyAsyncTask().execute(liste,adapter);

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

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final ListView listePerso = findViewById(R.id.listview);
        switch (item.getItemId())
        {
            case R.id.item1:
                if(checked)
                {
                    menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_baseline_star_border_24));
                    checked = false;
                    adapter.setList(liste);
                }
                else
                {
                    menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_baseline_star_24));
                    checked = true;
                    recupTabFavori();

                    adapter.setList(listeFav);

                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.item3:
                if(deadChecked)
                {
                    menu.getItem(1).setChecked(true);
                    new RecupDeadCharacters().execute(listeDead,adapter);
                    listePerso.setAdapter(adapter);
                    deadChecked = false;
                    menu.getItem(2).setChecked(false);

                }
                else
                {
                    menu.getItem(1).setChecked(false);
                    new MyAsyncTask().execute(liste,adapter);;
                    listePerso.setAdapter(adapter);
                    deadChecked = true;
                }
                break;

            case R.id.item4:
                if(aliveChecked)
                {
                    menu.getItem(2).setChecked(true);
                    new RecupAliveCharacters().execute(listeAlive,adapter);
                    listePerso.setAdapter(adapter);
                    aliveChecked = false;
                    menu.getItem(1).setChecked(false);

                }
                else
                {
                    menu.getItem(2).setChecked(false);
                    new MyAsyncTask().execute(liste,adapter);
                    listePerso.setAdapter(adapter);
                    aliveChecked = true;
                }
                break;



        }
        return super.onOptionsItemSelected(item);
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



