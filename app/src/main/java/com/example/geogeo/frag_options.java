package com.example.geogeo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class frag_options extends Fragment {
    // inflater verbindet Layout mit View
    EditText editText;
    protected Button submit;
    TextView username;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.frag_options, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = preferences.edit();
        editText = view.findViewById(R.id.profil_editUsername);
        submit = view.findViewById(R.id.profil_btnSubmit);
        username = view.findViewById(R.id.profil_username);


        String name = preferences.getString("name", "");

        username.setText(name);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newname = editText.getText().toString();

                editor.putString("name", newname);
                editor.apply();
                username.setText(preferences.getString("name", ""));

            }

        });
    }
}
