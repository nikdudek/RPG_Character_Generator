package com.app.controller;

import com.app.model.CharacterSheet;
import com.app.model.CoreRules;
import com.app.model.DiceRoller;
import com.app.view.AttributePanel;
import com.app.view.CombatValuesPanel;
import com.app.view.InfoPanel;
import com.app.view.SkillsPanel;
import org.w3c.dom.Attr;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.HashMap;

public class Controller {

    //FINALS
    public final int ATTRIBUTE_DICE_COUNT = 3;

    //SINGLETON
    private Controller() {
        System.out.println("Controller created.");
        HashMap<String,Integer> skillsMap = new HashMap<String,Integer>();
        skillsMap.put("Acrobatics",0);
        skillsMap.put("Animal Handling",1);
        skillsMap.put("Arcana",2);
        skillsMap.put("Athletics",3);
        skillsMap.put("Deception",4);
        skillsMap.put("History",5);
        skillsMap.put("Insight",6);
        skillsMap.put("Intimidation",7);
        skillsMap.put("Investigation",8);
        skillsMap.put("Medicine",9);
        skillsMap.put("Nature",10);
        skillsMap.put("Perception",11);
        skillsMap.put("Performance",12);
        skillsMap.put("Persuasion",13);
        skillsMap.put("Religion",14);
        skillsMap.put("Sleight of Hand",15);
        skillsMap.put("Stealth",16);
        skillsMap.put("Survival",17);
    }
    private static Controller instance = null;
    public static synchronized Controller getInstance() {
        if(instance == null)
            instance = new Controller();
        return instance;
    }

    // ------------------------------------------- CALCULATING FUNCTIONS ------------------------------------------- //
    public String attributeToModifier(int getAttribute) {
        int attributeValue = getAttribute - 10;
        if(attributeValue < 0) return String.valueOf((attributeValue - 1) / 2);
        else return String.valueOf(attributeValue / 2);
    }

    public String attributeToST(int getModifier, boolean isProficient) {
        if(isProficient) {

            CharacterSheet characterSheet = CharacterSheet.getInstance();
            return String.valueOf(getModifier + characterSheet.getProficiency());
        }
        else
            return String.valueOf(getModifier);
    }

    public boolean containsIn(final int[] arr, int key) { //static
        return Arrays.stream(arr).anyMatch(i -> i == key);
    }

    // ------------------------------------------ INFO PANEL: NAME LABEL ------------------------------------------- //
    public void setName(JTextField nameField, JLabel renameLabel) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        characterSheet.setName(nameField.getText());
        renameLabel.setText("Character's current name is: " + characterSheet.getName());
//                characterSheet.setAlignment(alignmentBox.getSelectedItem().toString());
//                characterSheet.setBackground(backgroundBox.getSelectedItem().toString());
//                characterSheet.setMainClass(classBox.getSelectedItem().toString());
//                characterSheet.setSubClass(subClassBox.getSelectedItem().toString());
//                characterSheet.setLevel(Integer.parseInt(levelBox.getSelectedItem().toString()));
    }

    // --------------------------------------------------- RACE ---------------------------------------------------- //
    public void setRace(ItemEvent e) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        if (!(characterSheet.getRace().equals((String) e.getItem()))) characterSheet.changeRace((String) e.getItem());
        //DODANIE I AKTUALIZACJA BONUSÓW Z RASY
    }

    // --------------------------------------------------- CLASS --------------------------------------------------- //
    public void setClass(ItemEvent e,JComboBox classBox) {

        CoreRules coreRules = CoreRules.getInstance();
        switch (classBox.getSelectedIndex()) { //ADD classSelected() for each
            case 0 -> System.out.println("Barbarian");
            case 1 -> System.out.println("Bard");
            case 2 -> System.out.println("Cleric");
            case 3 -> System.out.println("Druid");
            case 4 -> System.out.println("Fighter");
            case 5 -> System.out.println("Monk");
            case 6 -> System.out.println("Paladin");
            case 7 -> System.out.println("Ranger");
            case 8 -> System.out.println("Rogue");
            case 9 -> System.out.println("Sorcerer");
            case 10 -> System.out.println("Warlock");
            case 11 -> System.out.println("Wizard");
            default -> throw new IllegalStateException("Unexpected value.");
        }
    }

    // ------------------------------------------------- SUBCLASS -------------------------------------------------- //
    public void setSubClass(ItemEvent e) {
        //Subclass
    }

    public String[] getSubClasses(int arrayOfSubClasses) {
        //TO DO
        CoreRules coreRules = CoreRules.getInstance();
        return coreRules.getSubClasses(arrayOfSubClasses);
    }

    // --------------------------------------------------- LEVEL --------------------------------------------------- //
    public void setLevel(ItemEvent e,JComboBox classBox) {
        //TO DO - class will always be the same!
        int k;
        CharacterSheet characterSheet = CharacterSheet.getInstance();

        if (characterSheet.getLevel() == Integer.parseInt(e.getItem().toString()))
            return;
        else if(characterSheet.getLevel() > Integer.parseInt(e.getItem().toString())) {
            characterSheet.clearProficients(); //NEED TO MAKE THEM AGAIN
            k = Integer.parseInt(e.getItem().toString()); //From starting lvl to k
        }
        else
            k = characterSheet.getLevel();

        characterSheet.setLevel(Integer.parseInt(e.getItem().toString()));
        characterSheet.setProficiency(Integer.parseInt(e.getItem().toString()));
    }

    public void rollAttributes() {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        DiceRoller diceRoller = DiceRoller.getInstance();
        AttributePanel attributePanel = AttributePanel.getInstance();

        //Roll Attributes & Set them at Character Sheet:
        characterSheet.setStrength(diceRoller.d6(ATTRIBUTE_DICE_COUNT));
        characterSheet.setDexterity(diceRoller.d6(ATTRIBUTE_DICE_COUNT));
        characterSheet.setCondition(diceRoller.d6(ATTRIBUTE_DICE_COUNT));
        characterSheet.setIntelligence(diceRoller.d6(ATTRIBUTE_DICE_COUNT));
        characterSheet.setWisdom(diceRoller.d6(ATTRIBUTE_DICE_COUNT));
        characterSheet.setCharisma(diceRoller.d6(ATTRIBUTE_DICE_COUNT));

        //Show Attributes from Character Sheet
        attributePanel.getStrValueLabel().setText(String.valueOf(characterSheet.getStrength()));
        attributePanel.getDexValueLabel().setText(String.valueOf(characterSheet.getDexterity()));
        attributePanel.getConValueLabel().setText(String.valueOf(characterSheet.getCondition()));
        attributePanel.getIntValueLabel().setText(String.valueOf(characterSheet.getIntelligence()));
        attributePanel.getWisValueLabel().setText(String.valueOf(characterSheet.getWisdom()));
        attributePanel.getChaValueLabel().setText(String.valueOf(characterSheet.getCharisma()));

        //Set modifiers:
        attributePanel.getStrModifierLabel().setText(attributeToModifier(characterSheet.getStrength()));
        attributePanel.getDexModifierLabel().setText(attributeToModifier(characterSheet.getDexterity()));
        attributePanel.getConModifierLabel().setText(attributeToModifier(characterSheet.getCondition()));
        attributePanel.getIntModifierLabel().setText(attributeToModifier(characterSheet.getIntelligence()));
        attributePanel.getWisModifierLabel().setText(attributeToModifier(characterSheet.getWisdom()));
        attributePanel.getChaModifierLabel().setText(attributeToModifier(characterSheet.getCharisma()));

        //Set saving throws:
        attributePanel.getStrSTLabel().setText(attributeToST(Integer.parseInt(attributePanel.getStrModifierLabel().getText()),characterSheet.isStrengthProficient()));
        attributePanel.getDexSTLabel().setText(attributeToST(Integer.parseInt(attributePanel.getDexModifierLabel().getText()),characterSheet.isDexterityProficient()));
        attributePanel.getConSTLabel().setText(attributeToST(Integer.parseInt(attributePanel.getConModifierLabel().getText()),characterSheet.isConditionProficient()));
        attributePanel.getIntSTLabel().setText(attributeToST(Integer.parseInt(attributePanel.getIntModifierLabel().getText()),characterSheet.isIntelligenceProficient()));
        attributePanel.getWisSTLabel().setText(attributeToST(Integer.parseInt(attributePanel.getWisModifierLabel().getText()),characterSheet.isWisdomProficient()));
        attributePanel.getChaSTLabel().setText(attributeToST(Integer.parseInt(attributePanel.getChaModifierLabel().getText()),characterSheet.isCharismaProficient()));

    }

    // -------------------------------------------------- SKILLS --------------------------------------------------- //
    public void setSkills() {
//TO DO
    }

    // ------------------------------------------- CLASSES CHOOSING ------------------------------------------------ //

    public void barbarianSelected(int startingLevel, int targetLevel) {

        switch (startingLevel) {

            case 1: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
                characterSheet.clearProficients();
                characterSheet.setStrengthProficient(true);
                characterSheet.setConditionProficient(true);
                //ADD FEATS TO SORTED ALPHANUMERICAL LIST AS STRINGS (or better yet, make every feat a function)
                if(startingLevel < targetLevel)
                    barbarianSelected(startingLevel+1,targetLevel);
            }

            case 2: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 3: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 4: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 5: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 6: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 7: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 8: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 9: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 10: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 11: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 12: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 13: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 14: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 15: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 16: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 17: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 18: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 19: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 20: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            default:
                return;
        }
    }

    public void bardSelected(int startingLevel, int targetLevel) {

        switch (startingLevel) {

            case 1: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
                characterSheet.clearProficients();
                characterSheet.setStrengthProficient(true);
                characterSheet.setConditionProficient(true);
                //ADD FEATS TO SORTED ALPHANUMERICAL LIST AS STRINGS (or better yet, make every feat a function)
                if(startingLevel < targetLevel)
                    bardSelected(startingLevel+1,targetLevel);
            }

            case 2: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 3: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 4: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 5: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 6: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 7: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 8: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 9: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 10: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 11: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 12: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 13: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 14: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 15: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 16: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 17: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 18: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 19: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 20: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            default:
                return;
        }
    }

    public void clericSelected(int startingLevel, int targetLevel) {

        switch (startingLevel) {

            case 1: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
                characterSheet.clearProficients();
                characterSheet.setStrengthProficient(true);
                characterSheet.setConditionProficient(true);
                //ADD FEATS TO SORTED ALPHANUMERICAL LIST AS STRINGS (or better yet, make every feat a function)
                if(startingLevel < targetLevel)
                    clericSelected(startingLevel+1,targetLevel);
            }

            case 2: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 3: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 4: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 5: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 6: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 7: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 8: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 9: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 10: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 11: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 12: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 13: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 14: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 15: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 16: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 17: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 18: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 19: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 20: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            default:
                return;
        }
    }

    public void druidSelected(int startingLevel, int targetLevel) {

        switch (startingLevel) {

            case 1: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
                characterSheet.clearProficients();
                characterSheet.setStrengthProficient(true);
                characterSheet.setConditionProficient(true);
                //ADD FEATS TO SORTED ALPHANUMERICAL LIST AS STRINGS (or better yet, make every feat a function)
                if(startingLevel < targetLevel)
                    druidSelected(startingLevel+1,targetLevel);
            }

            case 2: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 3: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 4: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 5: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 6: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 7: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 8: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 9: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 10: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 11: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 12: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 13: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 14: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 15: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 16: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 17: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 18: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 19: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 20: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            default:
                return;
        }
    }

    public void fighterSelected(int startingLevel, int targetLevel) {

        switch (startingLevel) {

            case 1: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
                characterSheet.clearProficients();
                characterSheet.setStrengthProficient(true);
                characterSheet.setConditionProficient(true);
                //ADD FEATS TO SORTED ALPHANUMERICAL LIST AS STRINGS (or better yet, make every feat a function)
                if(startingLevel < targetLevel)
                    fighterSelected(startingLevel+1,targetLevel);
            }

            case 2: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 3: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 4: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 5: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 6: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 7: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 8: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 9: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 10: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 11: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 12: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 13: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 14: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 15: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 16: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 17: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 18: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 19: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 20: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            default:
                return;
        }
    }

    public void monkSelected(int startingLevel, int targetLevel) {

        switch (startingLevel) {

            case 1: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
                characterSheet.clearProficients();
                characterSheet.setStrengthProficient(true);
                characterSheet.setConditionProficient(true);
                //ADD FEATS TO SORTED ALPHANUMERICAL LIST AS STRINGS (or better yet, make every feat a function)
                if(startingLevel < targetLevel)
                    monkSelected(startingLevel+1,targetLevel);
            }

            case 2: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 3: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 4: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 5: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 6: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 7: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 8: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 9: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 10: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 11: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 12: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 13: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 14: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 15: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 16: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 17: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 18: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 19: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 20: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            default:
                return;
        }
    }

    public void paladinSelected(int startingLevel, int targetLevel) {

        switch (startingLevel) {

            case 1: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
                characterSheet.clearProficients();
                characterSheet.setStrengthProficient(true);
                characterSheet.setConditionProficient(true);
                //ADD FEATS TO SORTED ALPHANUMERICAL LIST AS STRINGS (or better yet, make every feat a function)
                if(startingLevel < targetLevel)
                    paladinSelected(startingLevel+1,targetLevel);
            }

            case 2: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 3: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 4: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 5: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 6: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 7: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 8: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 9: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 10: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 11: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 12: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 13: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 14: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 15: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 16: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 17: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 18: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 19: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 20: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            default:
                return;
        }
    }

    public void rangerSelected(int startingLevel, int targetLevel) {

        switch (startingLevel) {

            case 1: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
                characterSheet.clearProficients();
                characterSheet.setStrengthProficient(true);
                characterSheet.setConditionProficient(true);
                //ADD FEATS TO SORTED ALPHANUMERICAL LIST AS STRINGS (or better yet, make every feat a function)
                if(startingLevel < targetLevel)
                    rangerSelected(startingLevel+1,targetLevel);
            }

            case 2: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 3: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 4: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 5: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 6: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 7: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 8: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 9: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 10: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 11: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 12: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 13: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 14: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 15: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 16: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 17: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 18: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 19: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 20: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            default:
                return;
        }
    }

    public void rogueSelected(int startingLevel, int targetLevel) {

        switch (startingLevel) {

            case 1: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
                characterSheet.clearProficients();
                characterSheet.setStrengthProficient(true);
                characterSheet.setConditionProficient(true);
                //ADD FEATS TO SORTED ALPHANUMERICAL LIST AS STRINGS (or better yet, make every feat a function)
                if(startingLevel < targetLevel)
                    rogueSelected(startingLevel+1,targetLevel);
            }

            case 2: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 3: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 4: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 5: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 6: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 7: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 8: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 9: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 10: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 11: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 12: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 13: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 14: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 15: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 16: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 17: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 18: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 19: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 20: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            default:
                return;
        }
    }

    public void sorcererSelected(int startingLevel, int targetLevel) {

        switch (startingLevel) {

            case 1: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
                characterSheet.clearProficients();
                characterSheet.setStrengthProficient(true);
                characterSheet.setConditionProficient(true);
                //ADD FEATS TO SORTED ALPHANUMERICAL LIST AS STRINGS (or better yet, make every feat a function)
                if(startingLevel < targetLevel)
                    sorcererSelected(startingLevel+1,targetLevel);
            }

            case 2: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 3: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 4: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 5: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 6: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 7: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 8: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 9: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 10: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 11: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 12: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 13: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 14: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 15: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 16: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 17: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 18: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 19: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 20: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            default:
                return;
        }
    }

    public void warlockSelected(int startingLevel, int targetLevel) {

        switch (startingLevel) {

            case 1: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
                characterSheet.clearProficients();
                characterSheet.setStrengthProficient(true);
                characterSheet.setConditionProficient(true);
                //ADD FEATS TO SORTED ALPHANUMERICAL LIST AS STRINGS (or better yet, make every feat a function)
                if(startingLevel < targetLevel)
                    warlockSelected(startingLevel+1,targetLevel);
            }

            case 2: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 3: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 4: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 5: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 6: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 7: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 8: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 9: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 10: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 11: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 12: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 13: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 14: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 15: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 16: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 17: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 18: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 19: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 20: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            default:
                return;
        }
    }

    public void wizardSelected(int startingLevel, int targetLevel) {

        switch (startingLevel) {

            case 1: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
                characterSheet.clearProficients();
                characterSheet.setStrengthProficient(true);
                characterSheet.setConditionProficient(true);
                //ADD FEATS TO SORTED ALPHANUMERICAL LIST AS STRINGS (or better yet, make every feat a function)
                if(startingLevel < targetLevel)
                    wizardSelected(startingLevel+1,targetLevel);
            }

            case 2: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 3: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 4: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 5: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 6: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 7: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 8: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 9: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 10: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 11: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 12: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 13: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 14: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 15: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 16: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 17: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 18: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 19: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            case 20: {
                CharacterSheet characterSheet = CharacterSheet.getInstance();
            }

            default:
                return;
        }
    }
}
