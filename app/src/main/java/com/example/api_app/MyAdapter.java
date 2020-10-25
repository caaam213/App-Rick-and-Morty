package com.example.api_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
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

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tv1.setText(listePerso.get(position).getNomPerso());
        holder.tv2.setText(listePerso.get(position).getSpecies());
        String imageUrl = listePerso.get(position).getImage();
        Picasso.get().load(imageUrl).into(holder.image);
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(context,InformationActivity.class);
                Personnage perso = (Personnage) listePerso.get(position);
                intent1.putExtra("Perso selec", perso);
                context.startActivity(intent1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listePerso.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv1,tv2;
        ImageView image;
        ConstraintLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.name);
            tv2 = itemView.findViewById(R.id.species);
            image = itemView.findViewById(R.id.imageV);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }




}






