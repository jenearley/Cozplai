package edu.sjsu.jen.cozplai;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import static edu.sjsu.jen.cozplai.Character.characters;

/**
 * Created by jen0e on 12/5/2017.
 */

public class CharacterListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        RecyclerView characterList = (RecyclerView) findViewById(R.id.character_recyclerview);
        characterList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        characterList.setLayoutManager(linearLayoutManager);

        CharacterAdapter characterAdapter = new CharacterAdapter(characters);
        characterList.setAdapter(characterAdapter);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.add_character_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: CREATE NEW CHARACTER
            }
        });
    }
}
