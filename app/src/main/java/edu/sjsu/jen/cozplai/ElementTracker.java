package edu.sjsu.jen.cozplai;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jen0e on 12/8/17.
 */

public class ElementTracker {

    public static List<Element> elements = new ArrayList<>();

    static {

    }

    public static void addElement(int characterIndex, Element element, DataHelper dataHelper){
        elements.add(element);
        Character character = CharacterTracker.characters.get(characterIndex);
        character.getElements().add(element);
        dataHelper.addElement(character, element);

        int completion = dataHelper.getCharacterCompletion(character);
        character.setCompletion(completion);
        CharacterListActivity.updateList();

    }

    public static void deleteElement(int characterIndex, int elementIndex, DataHelper dataHelper){
        Character character = CharacterTracker.characters.get(characterIndex);
        Element element = character.getElements().get(elementIndex);
        character.getElements().remove(element);
        elements.remove(element);
        dataHelper.deleteElement(character, element);

        int completion = dataHelper.getCharacterCompletion(character);
        character.setCompletion(completion);
        CharacterListActivity.updateList();
    }

    public static void editElement(int characterIndex, int elementIndex, DataHelper dataHelper){
        Character character = CharacterTracker.characters.get(characterIndex);
        Element element = character.getElements().get(elementIndex);
        dataHelper.editElement(character, element, elementIndex);

        int completion = dataHelper.getCharacterCompletion(character);
        character.setCompletion(completion);
        CharacterListActivity.updateList();
    }

}
