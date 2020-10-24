package com.example.api_app;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MyAsyncTask extends AsyncTask<Object,Void,MyAdapter> {

    @Override
    protected MyAdapter doInBackground(Object[] objects) {
        ArrayList<Personnage>localList = (ArrayList<Personnage>) objects[0];
        MyAdapter ma = (MyAdapter) objects[1];


        for(int j=1;j<=2;j++) {
            try {

                URL url = new URL("https://rickandmortyapi.com/api/character/?page=" + j);

                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                    InputStreamReader isr = new InputStreamReader(urlConnection.getInputStream());
                    BufferedReader input = new BufferedReader(isr);
                    String jsonStr = input.readLine();

                    JSONObject jsonroot = new JSONObject(jsonStr);
                    JSONObject info = jsonroot.getJSONObject("info");
                    int count = info.getInt("count");


                    JSONArray arrayResult = jsonroot.getJSONArray("results");
                    int i;
                    for (i = 0; i < 20; i++) {
                        JSONObject unPerso = arrayResult.getJSONObject(i);
                        int id = unPerso.getInt("id");
                        String name = unPerso.getString("name");
                        String status = unPerso.getString("status");
                        String species = unPerso.getString("species");
                        String type = unPerso.getString("type");
                        String gender = unPerso.getString("gender");
                        String location = unPerso.getJSONObject("location").getString("name");
                        String urlImage = unPerso.getString("image");
                        JSONArray tabEpisodes = unPerso.getJSONArray("episode");

                        ArrayList<Integer> list = new ArrayList();
                        if (tabEpisodes != null) {
                            int len = tabEpisodes.length();
                            for (int k=0;k<len;k++){
                                String unEp = tabEpisodes.get(k).toString();
                                String[] link = unEp.split("/");
                                int ep = Integer.parseInt(link[5]);
                                list.add(ep);
                            }
                        }

                        Personnage perso = new Personnage(id,name,status,species,type,gender,location,urlImage,list);
                        Log.d("image", perso.getImage());
                        Log.d("nom", perso.getNomPerso());

                        localList.add(perso);



                    }
                    ma.setList(localList);

                }
                else
                {
                    ma = null;
                }


                urlConnection.disconnect();

            } catch (MalformedURLException e) {
                Log.d("Erreur : Probleme URL", e.getMessage());
            } catch (IOException e) {
                Log.d("Erreur:Prob connexion", e.getMessage());

            } catch (JSONException e) {
                Log.d("Erreur:Prob JSON", e.getMessage());
            }



        }

        return ma;
    }

    public void onPostExecute(MyAdapter ma)
    {

        ma.notifyDataSetChanged();


    }





}
