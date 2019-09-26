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
import android.widget.NumberPicker;
import android.widget.RadioButton;


public class frag_play extends Fragment {
    protected Button play_classicGame;
    protected Button play_CustomGame;
    protected RadioButton radioPic;
    protected RadioButton radioText;
    int gameId;
    int amount;
    NumberPicker np;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_play, container, false);

        return inflater.inflate(R.layout.frag_play, null);
    }
    public void onViewCreated(View view, Bundle savedInstanceState){
        final Controller con = new Controller(getActivity());
        play_classicGame = (Button) view.findViewById(R.id.play_classicGame);
        play_CustomGame = view.findViewById(R.id.play_CustomGame);
        radioPic = view.findViewById(R.id.radioPic);
        radioText = view.findViewById(R.id.radioText);
        np = view.findViewById(R.id.npp);
        np.setMinValue(1);
        np.setMaxValue(con.getQuestionCount());


        play_classicGame.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                initializeGame(con,"all", "random");
            }

        });

        play_CustomGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radioPic.isChecked()){
                    initializeGame(con, "all", "pic");
                }
                else if(radioText.isChecked()){
                    initializeGame(con, "all", "text");
                }
            }
        });

    }
    public void initializeGame(Controller con,String type, String kind){
        amount = np.getValue();
        gameId = con.createGame(amount, kind, type);
        int [] NextQuestionInfo = con.getNextQuestionInfo(gameId);
        if (gameId != 0){
            if (NextQuestionInfo[1] == -1) {
                if (con.isGameOver(gameId)) {
                    con.endGame(gameId);
                }
            } else {
                Intent intent = new Intent(getActivity(), map.class);
                intent.putExtra("gameId", gameId);
                intent.putExtra("questionId", NextQuestionInfo[0]);
                intent.putExtra("isPicQuestion", NextQuestionInfo[1]);
                startActivity(intent);
            }
        }
    }

}
