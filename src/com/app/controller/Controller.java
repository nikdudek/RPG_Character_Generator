package com.app.controller;

import com.app.model.CharacterSheet;
import com.app.model.CoreRules;
import com.app.model.DiceRoller;
import com.app.view.AttributePanel;
import com.app.view.CombatValuesPanel;
import com.app.view.InfoPanel;
import com.app.view.SkillsPanel;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.Arrays;

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

    public void setAllDefault() {
        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();
        InfoPanel infoPanel = InfoPanel.getInstance();

        infoPanel.setSubClassBoxModel(coreRules.BARBARIAN);

        characterSheet.setHitDiceType(12);
        characterSheet.setHitDiceCount(1);

        rollAttributes();
        characterSheet.setLevel(coreRules.STARTING_LEVEL);
        characterSheet.setRace(coreRules.DRAGONBORN);
        characterSheet.setAlignment(coreRules.LAWFUL_GOOD);
        characterSheet.setBackground(coreRules.ACOLYTE);
        characterSheet.setMainClass(coreRules.BARBARIAN);
        characterSheet.setSubClass(coreRules.BARBARIAN);

        characterSheet.setHitDiceType(coreRules.BARBARIAN_DICE);
        characterSheet.setHitPointsMax(coreRules.BARBARIAN_DICE);
        characterSheet.setStrengthProficient(true);
        characterSheet.setConditionProficient(true);

        refreshCombatValues();

        //  <<----------- TU SKONCZYLES ----------------------------------------------------------------------------->>

        refreshCombatValues();
    }

    public int attributeToModifier(int getAttribute) {
        int attributeValue = getAttribute - 10;
        if(attributeValue < 0) return ((attributeValue - 1) / 2);
        else return (attributeValue / 2);
    }

    public int attributeToST(int modifier, boolean isProficient) {
        if(isProficient) {

            CharacterSheet characterSheet = CharacterSheet.getInstance();
            return (modifier + characterSheet.getProficiency());
        }
        else
            return (modifier);
    }

    public boolean containsIn(final int[] arr, int key) { //static
        return Arrays.stream(arr).anyMatch(i -> i == key);
    }

    public int calculateProficiency(int level) {
        return ((7+level) / 4);
    }

    // --------------------------------------- COMBAT VALUES PANEL -> LABELS ----------------------------------------- //

    public void setCombatValues() {

        CharacterSheet characterSheet = CharacterSheet.getInstance();

        characterSheet.setInitiative(characterSheet.getDexterityMod());
        //characterSheet.setSpeed(); --> from race (delete later)
        //characterSheet.setProficiency(); -- from level (delete later)
        // -||- same for rest
        //RANDOM HP ->
        int hitPoints = characterSheet.getHitDiceType() + characterSheet.getConditionMod();
        if(characterSheet.getLevel() != 1) {

            DiceRoller diceRoller = DiceRoller.getInstance();
            int level = characterSheet.getLevel() - 1;
            switch (characterSheet.getHitDiceType()) {
                case 6 -> hitPoints += diceRoller.d6(level);
                case 8 -> hitPoints += diceRoller.d8(level);
                case 10 -> hitPoints += diceRoller.d10(level);
                case 12 -> hitPoints += diceRoller.d12(level);
                default -> throw new IllegalStateException("Unexpected value.");
            }
        }
        characterSheet.setHitPointsMax(hitPoints);
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

    // --------------------------------------------- ATTRIBUTES PANEL -> --------------------------------------------- //

    public void rollAttributes() {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        DiceRoller diceRoller = DiceRoller.getInstance();
        AttributePanel attributePanel = AttributePanel.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        //Roll Attributes & Set them at Character Sheet ->
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

        //Set modifiers ->
        characterSheet.setStrengthMod(attributeToModifier(characterSheet.getStrength()));
        characterSheet.setDexterityMod(attributeToModifier(characterSheet.getDexterity()));
        characterSheet.setConditionMod(attributeToModifier(characterSheet.getCondition()));
        characterSheet.setIntelligenceMod(attributeToModifier(characterSheet.getIntelligence()));
        characterSheet.setWisdomMod(attributeToModifier(characterSheet.getWisdom()));
        characterSheet.setCharismaMod(attributeToModifier(characterSheet.getCharisma()));
        //Set Mod labels ->
        attributePanel.getStrModifierLabel().setText(String.valueOf(characterSheet.getStrengthMod()));
        attributePanel.getDexModifierLabel().setText(String.valueOf(characterSheet.getDexterityMod()));
        attributePanel.getConModifierLabel().setText(String.valueOf(characterSheet.getConditionMod()));
        attributePanel.getIntModifierLabel().setText(String.valueOf(characterSheet.getIntelligenceMod()));
        attributePanel.getWisModifierLabel().setText(String.valueOf(characterSheet.getWisdomMod()));
        attributePanel.getChaModifierLabel().setText(String.valueOf(characterSheet.getCharismaMod()));

        //Set saving throws ->
        characterSheet.setStrengthST(attributeToST(characterSheet.getStrengthMod(),characterSheet.isStrengthProficient()));
        characterSheet.setDexterityST(attributeToST(characterSheet.getDexterityMod(),characterSheet.isDexterityProficient()));
        characterSheet.setConditionST(attributeToST(characterSheet.getConditionMod(),characterSheet.isConditionProficient()));
        characterSheet.setIntelligenceST(attributeToST(characterSheet.getIntelligenceMod(),characterSheet.isIntelligenceProficient()));
        characterSheet.setWisdomST(attributeToST(characterSheet.getWisdomMod(),characterSheet.isWisdomProficient()));
        characterSheet.setCharismaST(attributeToST(characterSheet.getCharismaMod(),characterSheet.isCharismaProficient()));
        //Set ST labels ->
        attributePanel.getStrSTLabel().setText(String.valueOf(characterSheet.getStrengthST()));
        attributePanel.getDexSTLabel().setText(String.valueOf(characterSheet.getDexterityST()));
        attributePanel.getConSTLabel().setText(String.valueOf(characterSheet.getConditionST()));
        attributePanel.getIntSTLabel().setText(String.valueOf(characterSheet.getIntelligenceST()));
        attributePanel.getWisSTLabel().setText(String.valueOf(characterSheet.getWisdomST()));
        attributePanel.getChaSTLabel().setText(String.valueOf(characterSheet.getCharismaST()));

        //Calculate Skills ->
        calculateSkills();
        //Put Skills values into Labels ->
        setSkills();
        //Update Combat Values Label
        setCombatValues();
        refreshCombatValues();
    }

    // ------------------------------------------- SKILLS PANEL -> LABELS -------------------------------------------- //

    public void setSkills() {
        SkillsPanel skillsPanel = SkillsPanel.getInstance();
        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        for(int i = 0 ; i < coreRules.ALL_SKILLS ; i++)
            skillsPanel.skillsLabels[i].setText(String.valueOf(characterSheet.getSkills()[i]));
    }

    // --------------------------------------------------- RACE ---------------------------------------------------- //

    public void setRace(ItemEvent e, JComboBox raceBox) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        if (characterSheet.getRace() != raceBox.getSelectedIndex()) characterSheet.changeRace(raceBox.getSelectedIndex());
        //DODANIE I AKTUALIZACJA BONUSÓW Z RASY
    }

    // --------------------------------------------------- CLASS --------------------------------------------------- //

    public void setClass(ItemEvent e, JComboBox classBox, JComboBox levelBox) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        if(classBox.getSelectedIndex() != characterSheet.getMainClass()) {
            CoreRules coreRules = CoreRules.getInstance();
            InfoPanel infoPanel = InfoPanel.getInstance();
            int targetLevel = Integer.parseInt(levelBox.getSelectedItem().toString());
            switch (classBox.getSelectedIndex()) {
                case 0 -> {
                    System.out.println("Barbarian");
                    infoPanel.setSubClassBoxModel(coreRules.BARBARIAN); // TO DO KAZDEGO WYBORU KLASY!
                    for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                        barbarianSelected(i);
                }
                case 1 -> {
                    System.out.println("Bard");
                    for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                        bardSelected(i);
                    }
                case 2 -> {
                    System.out.println("Cleric");
                    for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                        clericSelected(i);
                }
                case 3 -> {
                    System.out.println("Druid");
                    for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                        druidSelected(i);
                }
                case 4 -> {
                    System.out.println("Fighter");
                    for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                        fighterSelected(i);
                }
                case 5 -> {
                    System.out.println("Monk");
                    for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                        monkSelected(i);
                }
                case 6 -> {
                    System.out.println("Paladin");
                    for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                        paladinSelected(i);
                }
                case 7 -> {
                    System.out.println("Ranger");
                    for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                        rangerSelected(i);
                }
                case 8 -> {
                    System.out.println("Rogue");
                    for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                        rogueSelected(i);
                }
                case 9 -> {
                    System.out.println("Sorcerer");
                    for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                        sorcererSelected(i);
                }
                case 10 -> {
                    System.out.println("Warlock");
                    for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                        warlockSelected(i);
                }
                case 11 -> {
                    System.out.println("Wizard");
                    for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                        wizardSelected(i);
                }
                default -> throw new IllegalStateException("Unexpected value.");
            }
        }
    }

    // ------------------------------------------------- SUBCLASS -------------------------------------------------- //

    public void setSubClass(ItemEvent e, JComboBox subclassBox) {
        //Subclass
    }

    public String[] getSubClasses(int arrayOfSubClasses) {
        //TO DO
        CoreRules coreRules = CoreRules.getInstance();
        return coreRules.getSubClasses(arrayOfSubClasses);
    }

    // --------------------------------------------------- LEVEL --------------------------------------------------- //

    public void setLevel(ItemEvent e) {
        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        if (characterSheet.getLevel() != Integer.parseInt(e.getItem().toString())) {
            if (characterSheet.getLevel() > Integer.parseInt(e.getItem().toString())) {
                characterSheet.clearProficients(); //NEED TO MAKE THEM AGAIN
                rollAttributes();
                    //clear feats, etc.
                raiseLevel(coreRules.STARTING_LEVEL, Integer.parseInt(e.getItem().toString()));
            }
            else if (characterSheet.getLevel() < Integer.parseInt(e.getItem().toString())) {
                raiseLevel(characterSheet.getLevel() + 1, Integer.parseInt(e.getItem().toString()));

                characterSheet.setLevel(Integer.parseInt(e.getItem().toString()));
                characterSheet.setProficiency(calculateProficiency(Integer.parseInt(e.getItem().toString())));
                refreshCombatValues();
            }
        }
    }

    public void raiseLevel(int startingLevel, int targetLevel) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        if(characterSheet.getMainClass() == coreRules.BARBARIAN)
            for(int i = startingLevel ; i <= targetLevel ; i++)
                barbarianSelected(i);
        else if(characterSheet.getMainClass() == coreRules.BARD)
            for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                bardSelected(startingLevel);
        else if(characterSheet.getMainClass() == coreRules.CLERIC)
            for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                clericSelected(startingLevel);
        else if(characterSheet.getMainClass() == coreRules.DRUID)
            for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                druidSelected(startingLevel);
        else if(characterSheet.getMainClass() == coreRules.FIGHTER)
            for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                fighterSelected(startingLevel);
        else if(characterSheet.getMainClass() == coreRules.MONK)
            for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                monkSelected(startingLevel);
        else if(characterSheet.getMainClass() == coreRules.PALADIN)
            for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                paladinSelected(startingLevel);
        else if(characterSheet.getMainClass() == coreRules.RANGER)
            for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                rangerSelected(startingLevel);
        else if(characterSheet.getMainClass() == coreRules.ROGUE)
            for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                rogueSelected(startingLevel);
        else if(characterSheet.getMainClass() == coreRules.SORCERER)
            for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                sorcererSelected(startingLevel);
        else if(characterSheet.getMainClass() == coreRules.WARLOCK)
            for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                warlockSelected(startingLevel);
        else if(characterSheet.getMainClass() == coreRules.WIZARD)
            for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                wizardSelected(startingLevel);

        refreshCombatValues();
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

    public void barbarianSelected(int level) {
        //RAISE HP WITH LEVEL!
        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();
        DiceRoller diceRoller = DiceRoller.getInstance();
        switch (level) {

            case 1 -> {
                InfoPanel infoPanel = InfoPanel.getInstance();
                //infoPanel.setSubClassBoxModel(coreRules.);

                characterSheet.clearProficients();
                characterSheet.setHitDiceType(coreRules.BARBARIAN_DICE);
                characterSheet.setHitPointsMax(coreRules.BARBARIAN_DICE);
                characterSheet.setStrengthProficient(true);
                characterSheet.setConditionProficient(true);
                //ADD FEATS TO SORTED ALPHANUMERICAL LIST AS STRINGS (or better yet, make every feat a function)
//                if(startingLevel < targetLevel)
//                    barbarianSelected(startingLevel+1,targetLevel);
            }

            case 2 -> {
                characterSheet.setHitPointsMax(characterSheet.getHitPointsMax() + diceRoller.d12(1));
            }

            case 3 -> {
                InfoPanel infoPanel = InfoPanel.getInstance();
                infoPanel.setSubClassBoxModel(coreRules.BARBARIAN);

                characterSheet.setHitPointsMax(characterSheet.getHitPointsMax() + diceRoller.d12(1));
            }

            case 4 -> {
            }

            case 5 -> {
            }

            case 6 -> {
            }

            case 7 -> {
            }

            case 8 -> {
            }

            case 9 -> {
            }

            case 10 -> {
            }

            case 11 -> {
            }

            case 12 -> {
            }

            case 13 -> {
            }

            case 14 -> {
            }

            case 15 -> {
            }

            case 16 -> {
            }

            case 17 -> {
            }

            case 18 -> {
            }

            case 19 -> {
            }

            case 20 -> {
            }

            default -> throw new IllegalStateException("Unexpected value.");
        }
    }

    public void bardSelected(int startingLevel) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        switch (startingLevel) {

            case 1 -> {

                characterSheet.clearProficients();
                characterSheet.setStrengthProficient(true);
                characterSheet.setConditionProficient(true);
                //ADD FEATS TO SORTED ALPHANUMERICAL LIST AS STRINGS (or better yet, make every feat a function)
            }

            case 2 -> {

            }

            case 3 -> {

            }

            case 4 -> {

            }

            case 5 -> {

            }

            case 6 -> {

            }

            case 7 -> {

            }

            case 8 -> {

            }

            case 9 -> {

            }

            case 10 -> {

            }

            case 11 -> {

            }

            case 12 -> {

            }

            case 13 -> {

            }

            case 14 -> {

            }

            case 15 -> {

            }

            case 16 -> {

            }

            case 17 -> {

            }

            case 18 -> {

            }

            case 19 -> {

            }

            case 20 -> {

            }

            default -> throw new IllegalStateException("Unexpected value.");
        }
    }

    public void clericSelected(int startingLevel) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        switch (startingLevel) {

            case 1 -> {

                characterSheet.clearProficients();
                characterSheet.setStrengthProficient(true);
                characterSheet.setConditionProficient(true);
                //ADD FEATS TO SORTED ALPHANUMERICAL LIST AS STRINGS (or better yet, make every feat a function)
            }

            case 2 -> {

            }

            case 3 -> {

            }

            case 4 -> {

            }

            case 5 -> {

            }

            case 6 -> {

            }

            case 7 -> {

            }

            case 8 -> {

            }

            case 9 -> {

            }

            case 10 -> {

            }

            case 11 -> {

            }

            case 12 -> {

            }

            case 13 -> {

            }

            case 14 -> {

            }

            case 15 -> {

            }

            case 16 -> {

            }

            case 17 -> {

            }

            case 18 -> {

            }

            case 19 -> {

            }

            case 20 -> {

            }

            default -> throw new IllegalStateException("Unexpected value.");
        }
    }

    public void druidSelected(int startingLevel) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        switch (startingLevel) {

            case 1 -> {

                characterSheet.clearProficients();
                characterSheet.setStrengthProficient(true);
                characterSheet.setConditionProficient(true);
                //ADD FEATS TO SORTED ALPHANUMERICAL LIST AS STRINGS (or better yet, make every feat a function)
            }

            case 2 -> {

            }

            case 3 -> {

            }

            case 4 -> {

            }

            case 5 -> {

            }

            case 6 -> {

            }

            case 7 -> {

            }

            case 8 -> {

            }

            case 9 -> {

            }

            case 10 -> {

            }

            case 11 -> {

            }

            case 12 -> {

            }

            case 13 -> {

            }

            case 14 -> {

            }

            case 15 -> {

            }

            case 16 -> {

            }

            case 17 -> {

            }

            case 18 -> {

            }

            case 19 -> {

            }

            case 20 -> {

            }

            default -> throw new IllegalStateException("Unexpected value.");
        }
    }

    public void fighterSelected(int startingLevel) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        switch (startingLevel) {

            case 1 -> {

                characterSheet.clearProficients();
                characterSheet.setStrengthProficient(true);
                characterSheet.setConditionProficient(true);
                //ADD FEATS TO SORTED ALPHANUMERICAL LIST AS STRINGS (or better yet, make every feat a function)
            }

            case 2 -> {

            }

            case 3 -> {

            }

            case 4 -> {

            }

            case 5 -> {

            }

            case 6 -> {

            }

            case 7 -> {

            }

            case 8 -> {

            }

            case 9 -> {

            }

            case 10 -> {

            }

            case 11 -> {

            }

            case 12 -> {

            }

            case 13 -> {

            }

            case 14 -> {

            }

            case 15 -> {

            }

            case 16 -> {

            }

            case 17 -> {

            }

            case 18 -> {

            }

            case 19 -> {

            }

            case 20 -> {

            }

            default -> throw new IllegalStateException("Unexpected value.");
        }
    }

    public void monkSelected(int startingLevel) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        switch (startingLevel) {

            case 1 -> {

                characterSheet.clearProficients();
                characterSheet.setStrengthProficient(true);
                characterSheet.setConditionProficient(true);
                //ADD FEATS TO SORTED ALPHANUMERICAL LIST AS STRINGS (or better yet, make every feat a function)
            }

            case 2 -> {

            }

            case 3 -> {

            }

            case 4 -> {

            }

            case 5 -> {

            }

            case 6 -> {

            }

            case 7 -> {

            }

            case 8 -> {

            }

            case 9 -> {

            }

            case 10 -> {

            }

            case 11 -> {

            }

            case 12 -> {

            }

            case 13 -> {

            }

            case 14 -> {

            }

            case 15 -> {

            }

            case 16 -> {

            }

            case 17 -> {

            }

            case 18 -> {

            }

            case 19 -> {

            }

            case 20 -> {

            }

            default -> throw new IllegalStateException("Unexpected value.");
        }
    }

    public void paladinSelected(int startingLevel) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        switch (startingLevel) {

            case 1 -> {

                characterSheet.clearProficients();
                characterSheet.setStrengthProficient(true);
                characterSheet.setConditionProficient(true);
                //ADD FEATS TO SORTED ALPHANUMERICAL LIST AS STRINGS (or better yet, make every feat a function)
            }

            case 2 -> {

            }

            case 3 -> {

            }

            case 4 -> {

            }

            case 5 -> {

            }

            case 6 -> {

            }

            case 7 -> {

            }

            case 8 -> {

            }

            case 9 -> {

            }

            case 10 -> {

            }

            case 11 -> {

            }

            case 12 -> {

            }

            case 13 -> {

            }

            case 14 -> {

            }

            case 15 -> {

            }

            case 16 -> {

            }

            case 17 -> {

            }

            case 18 -> {

            }

            case 19 -> {

            }

            case 20 -> {

            }

            default -> throw new IllegalStateException("Unexpected value.");
        }
    }

    public void rangerSelected(int startingLevel) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        switch (startingLevel) {

            case 1 -> {

                characterSheet.clearProficients();
                characterSheet.setStrengthProficient(true);
                characterSheet.setConditionProficient(true);
                //ADD FEATS TO SORTED ALPHANUMERICAL LIST AS STRINGS (or better yet, make every feat a function)
            }

            case 2 -> {

            }

            case 3 -> {

            }

            case 4 -> {

            }

            case 5 -> {

            }

            case 6 -> {

            }

            case 7 -> {

            }

            case 8 -> {

            }

            case 9 -> {

            }

            case 10 -> {

            }

            case 11 -> {

            }

            case 12 -> {

            }

            case 13 -> {

            }

            case 14 -> {

            }

            case 15 -> {

            }

            case 16 -> {

            }

            case 17 -> {

            }

            case 18 -> {

            }

            case 19 -> {

            }

            case 20 -> {

            }

            default -> throw new IllegalStateException("Unexpected value.");
        }
    }

    public void rogueSelected(int startingLevel) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        switch (startingLevel) {

            case 1 -> {

                characterSheet.clearProficients();
                characterSheet.setStrengthProficient(true);
                characterSheet.setConditionProficient(true);
                //ADD FEATS TO SORTED ALPHANUMERICAL LIST AS STRINGS (or better yet, make every feat a function)
            }

            case 2 -> {

            }

            case 3 -> {

            }

            case 4 -> {

            }

            case 5 -> {

            }

            case 6 -> {

            }

            case 7 -> {

            }

            case 8 -> {

            }

            case 9 -> {

            }

            case 10 -> {

            }

            case 11 -> {

            }

            case 12 -> {

            }

            case 13 -> {

            }

            case 14 -> {

            }

            case 15 -> {

            }

            case 16 -> {

            }

            case 17 -> {

            }

            case 18 -> {

            }

            case 19 -> {

            }

            case 20 -> {

            }

            default -> throw new IllegalStateException("Unexpected value.");
        }
    }

    public void sorcererSelected(int startingLevel) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        switch (startingLevel) {

            case 1 -> {

                characterSheet.clearProficients();
                characterSheet.setStrengthProficient(true);
                characterSheet.setConditionProficient(true);
                //ADD FEATS TO SORTED ALPHANUMERICAL LIST AS STRINGS (or better yet, make every feat a function)
            }

            case 2 -> {

            }

            case 3 -> {

            }

            case 4 -> {

            }

            case 5 -> {

            }

            case 6 -> {

            }

            case 7 -> {

            }

            case 8 -> {

            }

            case 9 -> {

            }

            case 10 -> {

            }

            case 11 -> {

            }

            case 12 -> {

            }

            case 13 -> {

            }

            case 14 -> {

            }

            case 15 -> {

            }

            case 16 -> {

            }

            case 17 -> {

            }

            case 18 -> {

            }

            case 19 -> {

            }

            case 20 -> {

            }

            default -> throw new IllegalStateException("Unexpected value.");
        }
    }

    public void warlockSelected(int startingLevel) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        switch (startingLevel) {

            case 1 -> {

                characterSheet.clearProficients();
                characterSheet.setStrengthProficient(true);
                characterSheet.setConditionProficient(true);
                //ADD FEATS TO SORTED ALPHANUMERICAL LIST AS STRINGS (or better yet, make every feat a function)
            }

            case 2 -> {

            }

            case 3 -> {

            }

            case 4 -> {

            }

            case 5 -> {

            }

            case 6 -> {

            }

            case 7 -> {

            }

            case 8 -> {

            }

            case 9 -> {

            }

            case 10 -> {

            }

            case 11 -> {

            }

            case 12 -> {

            }

            case 13 -> {

            }

            case 14 -> {

            }

            case 15 -> {

            }

            case 16 -> {

            }

            case 17 -> {

            }

            case 18 -> {

            }

            case 19 -> {

            }

            case 20 -> {

            }

            default -> throw new IllegalStateException("Unexpected value.");
        }
    }

    public void wizardSelected(int startingLevel) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        switch (startingLevel) {

            case 1 -> {

                characterSheet.clearProficients();
                characterSheet.setStrengthProficient(true);
                characterSheet.setConditionProficient(true);
                //ADD FEATS TO SORTED ALPHANUMERICAL LIST AS STRINGS (or better yet, make every feat a function)
            }

            case 2 -> {

            }

            case 3 -> {

            }

            case 4 -> {

            }

            case 5 -> {

            }

            case 6 -> {

            }

            case 7 -> {

            }

            case 8 -> {

            }

            case 9 -> {

            }

            case 10 -> {

            }

            case 11 -> {

            }

            case 12 -> {

            }

            case 13 -> {

            }

            case 14 -> {

            }

            case 15 -> {

            }

            case 16 -> {

            }

            case 17 -> {

            }

            case 18 -> {

            }

            case 19 -> {

            }

            case 20 -> {

            }

            default -> throw new IllegalStateException("Unexpected value.");
        }
    }
}
