package edu.sjsu.jen.cozplai;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by jen0e on 12/8/17.
 */

public class CharacterDetailsActivity extends AppCompatActivity {


    final Activity _this = this;
    private RecyclerView recyclerView;
    private AlertDialog currentDialog;
    DataHelper dataHelper = new DataHelper(this);
    private static final String LOG_TAG = "CharacterDetailsActivity";
    private int characterIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_details);

        characterIndex = (int) getIntent().getExtras().get("character");

        recyclerView = (RecyclerView) findViewById(R.id.details_recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        ElementAdapter elementAdapter = new ElementAdapter(ElementTracker.elements, characterIndex);
        recyclerView.setAdapter(elementAdapter);

        ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int index = viewHolder.getAdapterPosition();
                ElementTracker.deleteElement(characterIndex, index, dataHelper);
                recyclerView.requestLayout();
            }
        };

        ItemTouchHelper swipeHelper = new ItemTouchHelper(swipeCallback);
        swipeHelper.attachToRecyclerView(recyclerView);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.details_add_element_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Add new cosplay element");

                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View dialogView = inflater.inflate(R.layout.new_cosplay_element_dialog, null);
                builder.setView(dialogView);
                builder.setCancelable(false);

                final EditText elementName = (EditText) dialogView.findViewById(R.id.element_name_edittext);
                final EditText elementCompletion = (EditText) dialogView.findViewById(R.id.element_completion_edittext);


                builder.setPositiveButton("CREATE", null);
                builder.setNegativeButton("CANCEL", null);

                currentDialog = builder.create();

                currentDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(final DialogInterface dialogInterface) {
                        Button positiveButton = currentDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String name = (String) elementName.getText().toString();
                                String completionText = elementCompletion.getText().toString();

                                if(name.equals("")){
                                    Toast.makeText(view.getContext(), "You must enter an element name", Toast.LENGTH_SHORT).show();
                                } else {
                                    Element element = new Element(name);
                                    if(completionText.equals("")){
                                        element.setCompletion(0);
                                    } else {
                                        int completion = Integer.parseInt(completionText);
                                        element.setCompletion(completion);
                                    }

                                    //Toast.makeText(getApplicationContext(), characterName, Toast.LENGTH_SHORT).show();
                                    ElementTracker.addElement(characterIndex, element, dataHelper);
                                    currentDialog.dismiss();
                                    recyclerView.requestLayout();
                                }
                            }
                        });

                        Button negativeButton = currentDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                        negativeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                currentDialog.dismiss();
                            }
                        });
                    }
                });

                currentDialog.show();
            }
        });
    }

}
