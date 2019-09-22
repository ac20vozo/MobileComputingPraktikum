package com.example.geogeo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.view.Window;


public class frag_play extends Fragment {
    protected Button play_classicGame;
    int gameId;
    int amount = 2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_play, container, false);

        return inflater.inflate(R.layout.frag_play, null);
    }
    public void onViewCreated(View view, Bundle savedInstanceState){
        final Controller con = new Controller(getActivity());
        Button play_classicGame = (Button) view.findViewById(R.id.play_classicGame);
        play_classicGame.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                gameId = con.createGame(amount, "random", null);
                if (gameId != 0){
                    Intent intent = new Intent(getActivity(), map.class);
                    intent.putExtra("gameId", gameId);
                    startActivity(intent);
                }

            }

        });
    }


}
