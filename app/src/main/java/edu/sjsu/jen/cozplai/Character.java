package edu.sjsu.jen.cozplai;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jen0e on 11/28/2017.
 */

public class Character {
    private String name;
    private String source;
    private int completion;
    private List<Element> elements;
    private int photoId; //R.drawable file
    private String photoUri;

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
    public List<Element> getElements() { return elements; }
    public int getPhotoId() { return photoId; }
    public String getPhotoUri() { return photoUri; }


    //setters
    public void setName(String name) { this.name = name; }
    public void setSource(String source) { this.source = source; }
    public void setCompletion(int completion) { this.completion = completion; }
    public void setElements(List<Element> elements) { this.elements = elements; }
    public void setPhotoId(int photoId) { this.photoId = photoId; }
    public void setPhotoUri(String photoUri) { this.photoUri = photoUri; }

}
