package com.example.autores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autores.model.Libro;

import java.util.ArrayList;

public class LibroAdapter extends RecyclerView.Adapter<LibroAdapter.Viewholder> {

    private Context context;
    private ArrayList<Libro> libroModelArrayList;

    public LibroAdapter(Context context, ArrayList<Libro> courseModelArrayList) {
        this.context = context;
        this.libroModelArrayList = courseModelArrayList;
    }

    @NonNull
    @Override
    public LibroAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibroAdapter.Viewholder holder, int position) {
        Libro model = libroModelArrayList.get(position);
        holder.libroTitleTV.setText(model.getTitle());
        holder.libroDetailsTV.setText("Autores:" + model.getAuthors() + " Fecha de Publicacion: " + model.getPublishedDate() + " Tipo: " + model.getPrintType());

        new DownloadImageTask(holder.libroIV).execute(model.getImageLink());
    }

    @Override
    public int getItemCount() {
        return libroModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView libroIV;
        private TextView libroTitleTV, libroDetailsTV;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            libroIV = itemView.findViewById(R.id.idIVCourseImage);
            libroTitleTV = itemView.findViewById(R.id.idTVTitle);
            libroDetailsTV = itemView.findViewById(R.id.idTVDetails);
        }
    }
}
