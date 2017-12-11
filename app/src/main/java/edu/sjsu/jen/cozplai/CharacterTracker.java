package edu.sjsu.jen.cozplai;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jen0e on 12/6/17.
 */

public class CharacterTracker {
    public static List<Character> characters = new ArrayList<>();

    static {
        //TODO: DELETE AFTER PROJECT COMPLETION
        /*
        characters.add(new Character("Naruto Uzamaki"));
        characters.add(new Character("Benjamin Holman"));
        characters.add(new Character("Clark Kent"));
        characters.add(new Character("Cory Baxter"));
        characters.add(new Character("Junko Enoshima"));
        characters.add(new Character("Ash Ketchum"));
        characters.add(new Character("Bobobo-Bo Bo-Bobo"));
        characters.add(new Character("Mr. Poopy Butthole"));
        */
    }

    public static void addCharacter(Character character, DataHelper dataHelper){
        characters.add(character);
        dataHelper.addCharacter(character);
    }

    public static void deleteCharacter(Character character, DataHelper dataHelper){
        characters.remove(character);
        dataHelper.deleteCharacter(character);
    }

}
