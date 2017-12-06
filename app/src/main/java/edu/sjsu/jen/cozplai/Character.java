package edu.sjsu.jen.cozplai;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jen0e on 11/28/2017.
 */

public class Character {
    private String name;
    private String source;
    private int completion;
    private List<CostumeElement> elements;
    private int photoId; //R.drawable file

    public Character(String name) {
        this.name = name;
        this.source = "???";
        this.completion = 0;
        this.elements = new ArrayList<>();
        this.photoId = R.drawable.character_picture;
    }

    //getters
    public String getName() { return name; }
    public String getSource() { return source; }
    public int getCompletion() { return completion; }
    public List<CostumeElement> getElements() { return elements; }
    public int getPhotoId() { return photoId; }

    //setters
    public void setName(String name) { this.name = name; }
    public void setSource(String source) { this.source = source; }
    public void setCompletion(int completion) { this.completion = completion; }
    public void setElements(List<CostumeElement> elements) { this.elements = elements; }
    public void setPhotoId(int photoId) { this.photoId = photoId; }

    //TODO: DELETE AFTER TEXT COMPLETION
    public static List<Character> characters = new ArrayList<>();

    static {
        characters.add(new Character("Naruto Uzamaki"));
        characters.add(new Character("Benjamin Holman"));
        characters.add(new Character("meowmeowmeowmeow"));
    }

}
