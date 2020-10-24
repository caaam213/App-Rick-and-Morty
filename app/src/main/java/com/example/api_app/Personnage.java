package com.example.api_app;

import java.io.Serializable;
import java.util.ArrayList;

public class Personnage implements Serializable {
    int id;
    String nomPerso;
    String status;
    String species;
    String type;
    String gender;
    String location;
    String image;
    ArrayList<Integer> episodes;


    public Personnage(int id, String nomPerso, String status, String species, String type, String gender, String location, String image, ArrayList<Integer> episodes) {
        this.id = id;
        this.nomPerso = nomPerso;
        this.status = status;
        this.species = species;
        this.type = type;
        this.gender = gender;
        this.location = location;
        this.image = image;
        this.episodes = episodes;
    }

    public Personnage(String nomPerso, String image) {
        this.nomPerso = nomPerso;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomPerso() {
        return nomPerso;
    }

    public void setNomPerso(String nomPerso) {
        this.nomPerso = nomPerso;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<Integer> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(ArrayList<Integer> episodes) {
        this.episodes = episodes;
    }
}
