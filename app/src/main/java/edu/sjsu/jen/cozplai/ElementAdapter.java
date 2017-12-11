package edu.sjsu.jen.cozplai;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by jen0e on 12/8/17.
 */

public class ElementAdapter extends RecyclerView.Adapter<ElementAdapter.ElementViewHolder> {

    List<Element> elements;
    int characterIndex;

    public ElementAdapter(List<Element> elements, int index) {
        this.elements = elements;
        characterIndex = index;
    }

    public int getCharacterIndex(){
        return characterIndex;
    }

    @Override
    public ElementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_layout, parent, false);
        ElementViewHolder elementViewHolder = new ElementViewHolder(v);
        return elementViewHolder;
    }

    @Override
    public void onBindViewHolder(ElementViewHolder holder, int position) {
        Element element = elements.get(position);
        holder.elementName.setText(element.getName());
        holder.elementCompletion.setText(element.getCompletion() + "%");
    }


    @Override
    public int getItemCount() {
        return elements.size();
    }

    public class ElementViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView elementName;
        TextView elementCompletion;
        private AlertDialog currentDialog;


        public ElementViewHolder(View itemView) {
            super(itemView);
            elementName = (TextView) itemView.findViewById(R.id.element_name_textview);
            elementCompletion = (TextView) itemView.findViewById(R.id.element_completion_textview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final DataHelper dataHelper = new DataHelper(view.getContext());
            final int elementIndex = this.getAdapterPosition();

            final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Edit cosplay element");

            LayoutInflater inflater = LayoutInflater.from(view.getContext());
            View dialogView = inflater.inflate(R.layout.new_cosplay_element_dialog, null);
            builder.setView(dialogView);
            builder.setCancelable(false);

            String elementNameTouched = ((TextView) view.findViewById(R.id.element_name_textview)).getText().toString();
            String elementCompletionTouched = ((TextView) view.findViewById(R.id.element_completion_textview)).getText().toString();

            final EditText elementName = (EditText) dialogView.findViewById(R.id.element_name_edittext);
            final EditText elementCompletion = (EditText) dialogView.findViewById(R.id.element_completion_edittext);

            elementName.setText(elementNameTouched);
            elementCompletion.setText(elementCompletionTouched);

            builder.setPositiveButton("SAVE", null);
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
                                Element element = CharacterTracker.characters.get(characterIndex).getElements().get(elementIndex);
                                Element largerElement = ElementTracker.elements.get(ElementTracker.elements.indexOf(element));

                                element.setName(name);
                                largerElement.setName(name);

                                if(completionText.equals("")){
                                    element.setCompletion(0);
                                    largerElement.setCompletion(0);
                                } else {
                                    int completion = Integer.parseInt(completionText);
                                    element.setCompletion(completion);
                                    largerElement.setCompletion(completion);
                                }

                                ElementTracker.editElement(characterIndex, elementIndex, dataHelper);
                                ElementAdapter.this.notifyItemChanged(elementIndex);
                                currentDialog.dismiss();
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
    }

}
