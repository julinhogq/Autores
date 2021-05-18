package com.example.autores;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.autores.model.Libro;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView libroRV;

    private EditText mInputLibro;
    private Spinner spFilterType;
    private Spinner spOrder;
    private FloatingActionButton mFabSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        libroRV = findViewById(R.id.idRVLibro);

        mInputLibro = findViewById(R.id.et_book);
        spFilterType = findViewById(R.id.spFilterType);
        spOrder = findViewById(R.id.spOrder);
        mFabSearch = findViewById(R.id.fab_search);

        ArrayAdapter ad1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new String[]{"Menos texto", "Ver todo el texto", "eBooks gratuitos", "eBooks de Paga", "eBooks de Google"});
        ad1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFilterType.setAdapter(ad1);

        ArrayAdapter ad2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new String[]{"Relevancia", "El más nuevo"});
        ad2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOrder.setAdapter(ad2);

        mFabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cadenaBusqueda = mInputLibro.getText().toString();
                String filtro = getFilter(spFilterType.getSelectedItemPosition());
                String orden = getOrder(spOrder.getSelectedItemPosition());

                new ConseguirLibro(libroRV)
                        .execute(cadenaBusqueda, filtro, orden);
            }
        });
    }

    private String getFilter(int position){
        switch (position){
            case 0: return "partial";
            case 1: return "full";
            case 2: return "free-ebooks";
            case 3: return "paid-ebooks";
            case 4: return "ebooks";
        }
        return "";
    }

    private String getOrder(int position){
        switch (position){
            case 0: return "relevance";
            case 1: return "newest";
        }
        return "";
    }

    public class ConseguirLibro extends AsyncTask<String,Void,String>{
        private WeakReference<RecyclerView> mlibroRV;

        public ConseguirLibro(RecyclerView mlibroRV) {
            this.mlibroRV = new WeakReference<>(mlibroRV);
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This will normally run on a background thread. But to better
         * support testing frameworks, it is recommended that this also tolerates
         * direct execution on the foreground thread, as part of the {@link #execute} call.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param strings The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected String doInBackground(String... strings) {
            try {
                return UtilidadesRed.obtenerInformacionLibro(strings[0], strings[1], strings[2]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ArrayList<Libro> libroModelArrayList = new ArrayList<>();

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray itemsArray = jsonObject.getJSONArray("items");

                for(int i = 0; i < itemsArray.length(); i++){
                    JSONObject libroJson = itemsArray.getJSONObject(i);
                    JSONObject volumeInfo = libroJson.getJSONObject("volumeInfo");
                    JSONObject imageLinksJson = volumeInfo.getJSONObject("imageLinks");

                    try{
                        Libro libro = new Libro();
                        libro.setTitle(volumeInfo.getString("title"));
                        libro.setAuthors(volumeInfo.getString("authors"));
                        libro.setPublishedDate(volumeInfo.getString("publishedDate"));
                        libro.setPrintType(volumeInfo.getString("printType"));
                        libro.setImageLink(imageLinksJson.getString("smallThumbnail"));

                        libroModelArrayList.add(libro);

                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }

                LibroAdapter courseAdapter = new LibroAdapter(MainActivity.this, libroModelArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                libroRV.setLayoutManager(linearLayoutManager);
                libroRV.setAdapter(courseAdapter);
            } catch (Exception e){
                e.printStackTrace();
            }
    }
}
}