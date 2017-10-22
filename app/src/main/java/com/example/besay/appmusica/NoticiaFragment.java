package com.example.besay.appmusica;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.List;

/*Me he cargado la mayoria para simplificar*/

public class NoticiaFragment extends Fragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_noticia, container, false);

        Button botonElvis = (Button) rootView.findViewById(R.id.botonElvis);
        botonElvis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ElvisActivity.class);
                startActivity(intent);
            }
        });

        Button botontop10 = (Button) rootView.findViewById(R.id.botonTop10);
        botontop10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Top10Activity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
