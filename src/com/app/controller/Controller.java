package com.app.controller;

import com.app.model.CharacterSheet;
import com.app.model.CoreRules;
import com.app.model.DiceRoller;
import com.app.view.AttributePanel;
import com.app.view.CombatValuesPanel;
import com.app.view.SkillsPanel;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.HashMap;

public class Controller {

    //SINGLETON
    private Controller() {
        System.out.println("Controller created.");
    }
    private static Controller instance = null;
    public static synchronized Controller getInstance() {
        if(instance == null)
            instance = new Controller();
        return instance;
    }

    // ------------------------------------------------- FUNCTIONS ------------------------------------------------- //

    public int attributeToModifier(int getAttribute) {
        int attributeValue = getAttribute - 10;
        if(attributeValue < 0) return ((attributeValue - 1) / 2);
        else return (attributeValue / 2);
    }

    public int attributeToST(int getModifier, boolean isProficient) {
        if(isProficient) {

            CharacterSheet characterSheet = CharacterSheet.getInstance();
            return (getModifier + characterSheet.getProficiency());
        }
        else
            return (getModifier);
    }

    public boolean containsIn(final int[] arr, int key) { //static
        return Arrays.stream(arr).anyMatch(i -> i == key);
    }

    public int calculateProficiency(int level) {
        return ((7+level) / 4);
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
    // --------------------------------------- COMBAT VALUES PANEL: LABELS ----------------------------------------- //

    public void setCombatValues() {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        DiceRoller diceRoller = DiceRoller.getInstance();

        characterSheet.setInitiative(characterSheet.getDexterityMod());
        //characterSheet.setSpeed(); --> from race (delete later)
        //characterSheet.setProficiency(); -- from level (delete later)
        // -||- same for rest
        //RANDOM HP:
        if(characterSheet.getHitDiceType() == 6)
            characterSheet.setHitPointsMax((characterSheet.getLevel()*characterSheet.getConditionMod())+diceRoller.d6(characterSheet.getHitDiceCount()));
        else if(characterSheet.getHitDiceType() == 8)
            characterSheet.setHitPointsMax((characterSheet.getLevel()*characterSheet.getConditionMod())+diceRoller.d8(characterSheet.getHitDiceCount()));
        else if(characterSheet.getHitDiceType() == 10)
            characterSheet.setHitPointsMax((characterSheet.getLevel()*characterSheet.getConditionMod())+diceRoller.d10(characterSheet.getHitDiceCount()));
        else if(characterSheet.getHitDiceType() == 12)
            characterSheet.setHitPointsMax((characterSheet.getLevel()*characterSheet.getConditionMod())+diceRoller.d12(characterSheet.getHitDiceCount()));
    }

    public void refreshCombatValues() {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CombatValuesPanel combatValuesPanel = CombatValuesPanel.getInstance();

        combatValuesPanel.iniValLabel.setText(String.valueOf(characterSheet.getInitiative()));
        combatValuesPanel.speValLabel.setText(String.valueOf(characterSheet.getSpeed()));
        combatValuesPanel.proValLabel.setText(String.valueOf(characterSheet.getProficiency()));
        combatValuesPanel.hidValLabel.setText(characterSheet.getHitDiceCount() + "d" + characterSheet.getHitDiceType());
        combatValuesPanel.hipValLabel.setText(String.valueOf(characterSheet.getHitPointsMax()));
        combatValuesPanel.armValLabel.setText(String.valueOf(characterSheet.getArmorClass()));
    }

    // --------------------------------------------- ATTRIBUTES PANEL: --------------------------------------------- //

    public void rollAttributes() {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        DiceRoller diceRoller = DiceRoller.getInstance();
        AttributePanel attributePanel = AttributePanel.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        //Roll Attributes & Set them at Character Sheet:
        characterSheet.setStrength(diceRoller.d6(coreRules.ATTRIBUTE_DICE_COUNT));
        characterSheet.setDexterity(diceRoller.d6(coreRules.ATTRIBUTE_DICE_COUNT));
        characterSheet.setCondition(diceRoller.d6(coreRules.ATTRIBUTE_DICE_COUNT));
        characterSheet.setIntelligence(diceRoller.d6(coreRules.ATTRIBUTE_DICE_COUNT));
        characterSheet.setWisdom(diceRoller.d6(coreRules.ATTRIBUTE_DICE_COUNT));
        characterSheet.setCharisma(diceRoller.d6(coreRules.ATTRIBUTE_DICE_COUNT));

        //Show Attributes from Character Sheet
        attributePanel.getStrValueLabel().setText(String.valueOf(characterSheet.getStrength()));
        attributePanel.getDexValueLabel().setText(String.valueOf(characterSheet.getDexterity()));
        attributePanel.getConValueLabel().setText(String.valueOf(characterSheet.getCondition()));
        attributePanel.getIntValueLabel().setText(String.valueOf(characterSheet.getIntelligence()));
        attributePanel.getWisValueLabel().setText(String.valueOf(characterSheet.getWisdom()));
        attributePanel.getChaValueLabel().setText(String.valueOf(characterSheet.getCharisma()));

        //Set modifiers:
        characterSheet.setStrengthMod(attributeToModifier(characterSheet.getStrength()));
        characterSheet.setDexterityMod(attributeToModifier(characterSheet.getDexterity()));
        characterSheet.setConditionMod(attributeToModifier(characterSheet.getCondition()));
        characterSheet.setIntelligenceMod(attributeToModifier(characterSheet.getIntelligence()));
        characterSheet.setWisdomMod(attributeToModifier(characterSheet.getWisdom()));
        characterSheet.setCharismaMod(attributeToModifier(characterSheet.getCharisma()));
        //Set Mod labels:
        attributePanel.getStrModifierLabel().setText(String.valueOf(characterSheet.getStrengthMod()));
        attributePanel.getDexModifierLabel().setText(String.valueOf(characterSheet.getDexterityMod()));
        attributePanel.getConModifierLabel().setText(String.valueOf(characterSheet.getConditionMod()));
        attributePanel.getIntModifierLabel().setText(String.valueOf(characterSheet.getIntelligenceMod()));
        attributePanel.getWisModifierLabel().setText(String.valueOf(characterSheet.getWisdomMod()));
        attributePanel.getChaModifierLabel().setText(String.valueOf(characterSheet.getCharismaMod()));

        //Set saving throws:
        characterSheet.setStrengthST(attributeToST(characterSheet.getStrengthMod(),characterSheet.isStrengthProficient()));
        characterSheet.setDexterityST(attributeToST(characterSheet.getDexterityMod(),characterSheet.isDexterityProficient()));
        characterSheet.setConditionST(attributeToST(characterSheet.getConditionMod(),characterSheet.isConditionProficient()));
        characterSheet.setIntelligenceST(attributeToST(characterSheet.getIntelligenceMod(),characterSheet.isIntelligenceProficient()));
        characterSheet.setWisdomST(attributeToST(characterSheet.getWisdomMod(),characterSheet.isWisdomProficient()));
        characterSheet.setCharismaST(attributeToST(characterSheet.getCharismaMod(),characterSheet.isCharismaProficient()));
        //Set ST labels:
        attributePanel.getStrSTLabel().setText(String.valueOf(characterSheet.getStrengthST()));
        attributePanel.getDexSTLabel().setText(String.valueOf(characterSheet.getDexterityST()));
        attributePanel.getConSTLabel().setText(String.valueOf(characterSheet.getConditionST()));
        attributePanel.getIntSTLabel().setText(String.valueOf(characterSheet.getIntelligenceST()));
        attributePanel.getWisSTLabel().setText(String.valueOf(characterSheet.getWisdomST()));
        attributePanel.getChaSTLabel().setText(String.valueOf(characterSheet.getCharismaST()));

        //Calculate Skills:
        calculateSkills();
        //Put Skills values into Labels:
        setSkills();
        //Update Combat Values Label
        setCombatValues();
        refreshCombatValues();
    }

    // ------------------------------------------- SKILLS PANEL: LABELS -------------------------------------------- //

    public void setSkills() {
        SkillsPanel skillsPanel = SkillsPanel.getInstance();
        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        for(int i = 0 ; i < coreRules.ALL_SKILLS ; i++)
            skillsPanel.skillsLabels[i].setText(String.valueOf(characterSheet.getSkills()[i]));
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
        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        if (characterSheet.getLevel() == Integer.parseInt(e.getItem().toString()))
            return;
        else if(characterSheet.getLevel() > Integer.parseInt(e.getItem().toString())) {
            characterSheet.clearProficients(); //NEED TO MAKE THEM AGAIN
            //clear feats, etc.
            raiseLevel(coreRules.STARTING_LEVEL,Integer.parseInt(e.getItem().toString()));
        }
        else if(characterSheet.getLevel() < Integer.parseInt(e.getItem().toString()))
            raiseLevel(characterSheet.getLevel()+1,Integer.parseInt(e.getItem().toString()));

        characterSheet.setLevel(Integer.parseInt(e.getItem().toString()));
        characterSheet.setProficiency(calculateProficiency(Integer.parseInt(e.getItem().toString())));
        refreshCombatValues();
    }

    public void raiseLevel(int startingLevel, int targetLevel) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        if(characterSheet.getMainClass() == coreRules.BARBARIAN)
            barbarianSelected(startingLevel,targetLevel);
        else if(characterSheet.getMainClass() == coreRules.BARD)
            bardSelected(startingLevel,targetLevel);
        else if(characterSheet.getMainClass() == coreRules.CLERIC)
            clericSelected(startingLevel,targetLevel);
        else if(characterSheet.getMainClass() == coreRules.DRUID)
            druidSelected(startingLevel,targetLevel);
        else if(characterSheet.getMainClass() == coreRules.FIGHTER)
            fighterSelected(startingLevel,targetLevel);
        else if(characterSheet.getMainClass() == coreRules.MONK)
            monkSelected(startingLevel,targetLevel);
        else if(characterSheet.getMainClass() == coreRules.PALADIN)
            paladinSelected(startingLevel,targetLevel);
        else if(characterSheet.getMainClass() == coreRules.RANGER)
            rangerSelected(startingLevel,targetLevel);
        else if(characterSheet.getMainClass() == coreRules.ROGUE)
            rogueSelected(startingLevel,targetLevel);
        else if(characterSheet.getMainClass() == coreRules.SORCERER)
            sorcererSelected(startingLevel,targetLevel);
        else if(characterSheet.getMainClass() == coreRules.WARLOCK)
            warlockSelected(startingLevel,targetLevel);
        else if(characterSheet.getMainClass() == coreRules.WIZARD)
            wizardSelected(startingLevel,targetLevel);
    }

    // -------------------------------------------------- SKILLS --------------------------------------------------- //

    public void calculateSkills() {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        int[] skills = characterSheet.getSkills();
        boolean[] skillsProficient = characterSheet.getSkillsProficient();
        int proficiency = characterSheet.getProficiency();

        for(int i = 0 ; i < coreRules.ALL_SKILLS ; i++) {

            if(containsIn(coreRules.getStrSkills(),i)) {
                skills[i] = characterSheet.getStrengthMod();
                if(skillsProficient[i])
                    skills[i] += proficiency;
            }

            else if(containsIn(coreRules.getDexSkills(),i)) {
                skills[i] = characterSheet.getDexterityMod();
                if(skillsProficient[i])
                    skills[i] += proficiency;
            }

            else if(containsIn(coreRules.getIntSkills(),i)) {
                skills[i] = characterSheet.getIntelligenceMod();
                if(skillsProficient[i])
                    skills[i] += proficiency;
            }

            else if(containsIn(coreRules.getWisSkills(),i)) {
                skills[i] = characterSheet.getWisdomMod();
                if(skillsProficient[i])
                    skills[i] += proficiency;
            }

            else if(containsIn(coreRules.getChaSkills(),i)) {
                skills[i] = characterSheet.getCharismaMod();
                if(skillsProficient[i])
                    skills[i] += proficiency;
            }
        }

        characterSheet.setSkills(skills);
    }

    // ------------------------------------------- CLASSES CHOOSING ------------------------------------------------ //

    public void barbarianSelected(int startingLevel, int targetLevel) {
//RAISE HP WITH LEVEL!
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
