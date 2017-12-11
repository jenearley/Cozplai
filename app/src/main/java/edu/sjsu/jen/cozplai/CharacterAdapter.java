package edu.sjsu.jen.cozplai;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by jen0e on 11/28/2017.
 */

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>{

    List<Character> characters;

    CharacterAdapter(List<Character> characters) { this.characters = characters; }

    @Override
    public CharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.character_card, parent, false);
        CharacterViewHolder characterViewHolder = new CharacterViewHolder(v);
        return characterViewHolder;
    }

    @Override
    public void onBindViewHolder(CharacterViewHolder holder, int position) {
        Character character = characters.get(position);
        holder.characterName.setText(character.getName());
        holder.characterSource.setText(character.getSource());
        if (character.getPhotoUri() != null && !character.getPhotoUri().equals("")) {
            holder.characterPhoto.setImageURI(Uri.parse(character.getPhotoUri()));
        } else {
            holder.characterPhoto.setImageResource(character.getPhotoId());
        }
        Log.d("CharacterAdapter", "onBindViewHolder: CharComp: " + character.getCompletion());
        if(character.getCompletion() == 0){
            holder.characterCompletion.setText("0%");
        } else {
            holder.characterCompletion.setText(character.getCompletion() + "%");
        }
    }


    @Override
    public int getItemCount() {
        return characters.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class CharacterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        CardView cardView;
        TextView characterName;
        TextView characterSource;
        ImageView characterPhoto;
        TextView characterCompletion;

        private AlertDialog currentDialog;

        CharacterViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.character_cardview);
            characterName = (TextView) itemView.findViewById(R.id.character_name_textview);
            characterSource = (TextView) itemView.findViewById(R.id.character_source_textview);
            characterPhoto = (ImageView) itemView.findViewById(R.id.character_photo_imageview);
            characterCompletion = (TextView) itemView.findViewById(R.id.character_completion_textview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), CharacterDetailsActivity.class);

            int index = this.getAdapterPosition();
            intent.putExtra("character", index);

            view.getContext().startActivity(intent);
            Log.i("CharacterAdapter", "onClick: click character card");
        }

        @Override
        public boolean onLongClick(View view) {
            //TODO: EDIT CHARACTER DIALOG
            return false;
        }

    }

}
