package com.example.katiuskagonzalez.finalaa;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.katiuskagonzalez.finalaa.models.agregar;

import java.util.List;

public class mAdapterView extends RecyclerView.Adapter<mAdapterView.imageViewHolder> {


    private  List<agregar> items;
    private  Context context;

    public mAdapterView(List<agregar> items, Context context){
        this.items = items;
        this.context = context;

    }
    @Override
    public int getItemViewType(int position) {
//        if( position == 0){
//            return R.layout.activity_normal_card;
//        }
//        else {
//            return R.layout.activity_big_card;
//        }
        return  0;
    }

    @Override
    public imageViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_home, parent, false);

        imageViewHolder holder = new imageViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder( imageViewHolder holder, int position) {

       holder.txtNombreView.setText(items.get(position).getNombre());
       holder.txtUbicacionView.setText(items.get(position).getUbicacion());
       holder.txtDescripcionView.setText(items.get(position).getDescripcion());
       Glide.with(context).load(items.get(position).getImagen()).into(holder.imgView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class imageViewHolder extends RecyclerView.ViewHolder{

        TextView txtNombreView;
        TextView txtDescripcionView;
        TextView txtUbicacionView;
        ImageView imgView;

        public imageViewHolder(View itemView) {
            super(itemView);

            txtNombreView = itemView.findViewById(R.id.nombre_view);
            txtDescripcionView = itemView.findViewById(R.id.descripcion_view);
            txtUbicacionView = itemView.findViewById(R.id.ubicacion_view);
            imgView = itemView.findViewById(R.id.image_view_upload);
        }
    }
}
