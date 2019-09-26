package com.example.geogeo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;


public class frag_play extends Fragment {
    // Initializations
    protected Button play_classicGame;
    protected Button play_CustomGame;
    protected RadioButton radioPic;
    protected RadioButton radioText;
    private Spinner spinKind;
    private static final String[] paths = {"Text question", "Pictures"};
    private Spinner spinContinent;
    private static final String[] paths2 = {"Asia", "Africa", "Europe", "North America", "South America", "Oceania"};
    protected int selectKind;
    protected int selectCont;
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
        // Initializations of view Objects, Buttons, Clickmmenu, etc.
        final Controller con = new Controller(getActivity());
        play_classicGame = (Button) view.findViewById(R.id.play_classicGame);
        play_CustomGame = view.findViewById(R.id.play_CustomGame);
        spinKind = view.findViewById(R.id.spinKind);
        spinContinent = view.findViewById(R.id.spinContinent);
        np = view.findViewById(R.id.npp);
        np.setMinValue(1);
        np.setMaxValue(con.getQuestionCount());
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(frag_play.this.getActivity(),
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinKind.setAdapter(adapter);
        spinKind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // first input management of the clickmenu
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){

                    case 0:
                        selectKind = 0;
                        break;
                    case 1:
                        selectKind = 1;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(frag_play.this.getActivity(),
                android.R.layout.simple_spinner_item,paths2);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinContinent.setAdapter(adapter2);
        spinContinent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // Input management of the Clickmenu
            @Override
            public void onItemSelected(AdapterView<?> adapterView2, View view2, int i2, long l2) {
                switch(i2){

                    case 0:
                        selectCont = 0;
                        break;
                    case 1:
                        selectCont = 1;
                        break;
                    case 2:
                        selectCont = 2;
                        break;
                    case 3:
                        selectCont = 3;
                        break;
                    case 4:
                        selectCont = 4;
                        break;
                    case 5:
                        selectCont = 5;
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        play_classicGame.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                initializeGame(con,"all", "random", "all");
            }

        });

        play_CustomGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount = np.getValue();
                String text = "There are not enough questions in the database";
                if( selectKind==0){
                    if (selectCont==0){
                        if (!con.checkAmount(amount, "text", "Asia")){
                            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                        }else{
                            initializeGame(con, "all", "text", "Asia");
                        }
                    }
                    else if(selectCont==1){
                        if (!con.checkAmount(amount, "text", "Africa")){
                            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                        }else{
                            initializeGame(con, "all", "text", "Africa");
                        }

                    }
                    else if(selectCont==2){
                        if (!con.checkAmount(amount, "text", "Europe")){
                            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                        }else{
                            initializeGame(con, "all", "text", "Europe");
                        }

                    }
                    else if(selectCont==3){
                        if (!con.checkAmount(amount, "text", "North-Amerika")){
                            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                        }else{
                            initializeGame(con, "all", "text", "North-Amerika");
                        }

                    }
                    else if(selectCont==4){
                        if (!con.checkAmount(amount, "text", "South-Amerika")){
                            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                        }else{
                            initializeGame(con, "all", "text", "South-Amerika");
                        }

                    }
                    else if(selectCont==5){
                        if (!con.checkAmount(amount, "text", "Oceania")){
                            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                        }else{
                            initializeGame(con, "all", "text", "Oceania");
                        }

                    }
                }
                else if(selectKind==1){
                    if(selectCont==0){
                        if (!con.checkAmount(amount, "pic", "Asia")){
                            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                        }else{
                            initializeGame(con, "all", "pic", "Asia");
                        }

                    }
                    else if(selectCont==1){
                        if (!con.checkAmount(amount, "pic", "Africa")){
                            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                        }else{
                            initializeGame(con, "all", "pic", "Africa");
                        }

                    }
                    else if(selectCont==2){
                        if (!con.checkAmount(amount, "pic", "Europe")){
                            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                        }else{
                            initializeGame(con, "all", "pic", "Europe");
                        }

                    }
                    else if(selectCont==3){
                        if (!con.checkAmount(amount, "pic", "North-Amerika")){
                            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                        }else{
                            initializeGame(con, "all", "pic", "North-America");
                        }

                    }
                    else if(selectCont==4){
                        if (!con.checkAmount(amount, "pic", "South-Amerika")){
                            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                        }else{
                            initializeGame(con, "all", "pic", "South-Amerika");
                        }

                    }
                    else if(selectCont==5){
                        if (!con.checkAmount(amount, "pic", "Oceania")){
                            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                        }else{
                            initializeGame(con, "all", "pic", "Oceania");
                        }

                    }
                }
            }
        });

    }
    public void initializeGame(Controller con,String type, String kind, String continent){
        amount = np.getValue();
        gameId = con.createGame(amount, kind, type, continent);
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
