package edu.sjsu.jen.cozplai;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
        holder.characterName.setText(characters.get(position).getName());
        holder.characterSource.setText(characters.get(position).getSource());
        holder.characterPhoto.setImageResource(characters.get(position).getPhotoId());
    }


    @Override
    public int getItemCount() {
        return characters.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class CharacterViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView characterName;
        TextView characterSource;
        ImageView characterPhoto;

        CharacterViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.character_cardview);
            characterName = (TextView) itemView.findViewById(R.id.character_name_textview);
            characterSource = (TextView) itemView.findViewById(R.id.character_source_textview);
            characterPhoto = (ImageView) itemView.findViewById(R.id.character_photo_imageview);
        }
    }

}
