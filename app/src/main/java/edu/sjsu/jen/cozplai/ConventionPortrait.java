package edu.sjsu.jen.cozplai;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by jen0e on 12/8/17.
 */

public class ConventionPortrait extends Fragment {

    private RecyclerView recyclerView;
    private AlertDialog currentDialog;
    private static final String LOG_TAG = "ConventionPortrait";
    DataHelper dataHelper;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.conventions_portrait, container, false);

        dataHelper = new DataHelper(view.getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.convention_recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        ConventionAdapter conventionAdapter = new ConventionAdapter(ConventionsTracker.conventions);
        recyclerView.setAdapter(conventionAdapter);

        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.add_convention_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Add new convention");

                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View dialogView = inflater.inflate(R.layout.new_convention_dialog, null);
                builder.setView(dialogView);
                builder.setCancelable(false);

                final EditText conventionName = (EditText) dialogView.findViewById(R.id.convention_name_edittext);
                final EditText conventionAddress = (EditText) dialogView.findViewById(R.id.convention_address_edittext);
                final EditText conventionDate = (EditText) dialogView.findViewById(R.id.convention_date_edittext);


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
                                Log.i(LOG_TAG, "onClick: CREATE");

                                String name = (String) conventionName.getText().toString();
                                String address = (String) conventionAddress.getText().toString();
                                String date = (String) conventionDate.getText().toString();
                                Log.i(LOG_TAG, "characterName.getText() = " + name);

                                if(name.equals("")){
                                    Toast.makeText(view.getContext(), "You must enter a convention name", Toast.LENGTH_SHORT).show();
                                } else if(address.equals("")){
                                    Toast.makeText(view.getContext(), "You must enter a convention address", Toast.LENGTH_SHORT).show();
                                } else if(date.equals("")){
                                    Toast.makeText(view.getContext(), "You must enter a convention date", Toast.LENGTH_SHORT).show();
                                } else {
                                    Convention convention = new Convention(name);
                                    convention.setAddress(address);
                                    convention.setDate(date);


                                    ConventionsTracker.addConvention(convention, dataHelper);

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

        return view;

    }
}
