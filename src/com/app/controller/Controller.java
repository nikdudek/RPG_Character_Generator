package com.app.controller;

import com.app.model.CharacterSheet;
import com.app.model.CoreRules;
import com.app.model.DiceRoller;
import com.app.view.*;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

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

    public void setRandomSkillsProficiencies(final int[] skillsList, int skillCount) {
        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        for(int i = 0 ; i < skillCount ; i++) {
            characterSheet.getSkillsProficient()[skillsList[ThreadLocalRandom.current().nextInt(coreRules.STARTING_DEFAULT, skillsList.length)]] = true;
        }
    }

    public void raiseRandomAttributes() {
        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        int[] attributes = characterSheet.getAttributes();

        attributes[ThreadLocalRandom.current().nextInt(coreRules.STARTING_DEFAULT,coreRules.ALL_ATTRIBUTES)]++;
        attributes[ThreadLocalRandom.current().nextInt(coreRules.STARTING_DEFAULT,coreRules.ALL_ATTRIBUTES)]++;

        characterSheet.setAttributes(attributes);
    }

    public void setAttributeProficiencies(int[] attributeProficient) {
        CharacterSheet characterSheet = CharacterSheet.getInstance();

        boolean[] attrProficient = characterSheet.getAttributesProficient();
        for (int x : attributeProficient) {
            attrProficient[x] = true;
        }
        characterSheet.setAttributesProficient(attrProficient);

    }

//    public void clearAttributeProficiencies() {
//        CharacterSheet characterSheet = CharacterSheet.getInstance();
//
//        boolean[] attributesProficient = characterSheet.getAttributesProficient();
//
//        IntStream.range(0,attributesProficient.length).forEach(i -> attributesProficient[i] = false);
//
//        characterSheet.setAttributesProficient(attributesProficient);
//    }

    public void calculateAC() {
        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        if(characterSheet.getMainClass() == coreRules.BARBARIAN) {
            int baseAC = coreRules.BASE_AC + characterSheet.getAttributesMod()[1], altAC = baseAC + characterSheet.getAttributesMod()[2];
            characterSheet.setArmorClass(Math.max(baseAC,altAC));
        }
        else if(characterSheet.getMainClass() == coreRules.MONK) {
            int baseAC = coreRules.BASE_AC + characterSheet.getAttributesMod()[1], altAC = baseAC + characterSheet.getAttributesMod()[4];
            characterSheet.setArmorClass(Math.max(baseAC,altAC));
        }
        else
            characterSheet.setArmorClass(coreRules.BASE_AC + characterSheet.getAttributesMod()[1]);
    }

    public void calculateHP() {
        CharacterSheet characterSheet = CharacterSheet.getInstance();

        int hitPoints = characterSheet.getHitDiceType() + characterSheet.getAttributesMod()[2];

        if(characterSheet.getLevel() > 1) {
            DiceRoller diceRoller = DiceRoller.getInstance();

            int conMod = characterSheet.getAttributesMod()[2];
            int level = characterSheet.getLevel() - 1;
            switch (characterSheet.getHitDiceType()) {
                case 6 -> {
                    int tempHP = diceRoller.d6(level) + (conMod * level);
                    if(tempHP < 1) tempHP = level;
                    hitPoints += tempHP;
                }
                case 8 -> {
                    int tempHP = diceRoller.d8(level) + (conMod * level);
                    if(tempHP < 1) tempHP = level;
                    hitPoints += tempHP;
                }
                case 10 -> {
                    int tempHP = diceRoller.d10(level) + (conMod * level);
                    if(tempHP < 1) tempHP = level;
                    hitPoints += tempHP;
                }
                case 12 -> {
                    int tempHP = diceRoller.d12(level) + (conMod * level);
                    if(tempHP < 1) tempHP = level;
                    hitPoints += tempHP;
                }
                default -> throw new IllegalStateException("Unexpected value.");
            }
        }
        characterSheet.setHitPointsMax(hitPoints);
    }

    public void rollRace() {
        InfoPanel infoPanel = InfoPanel.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        infoPanel.getRaceBox().setSelectedIndex(ThreadLocalRandom.current().nextInt(coreRules.STARTING_DEFAULT,coreRules.ALL_RACES));
        setRace();
    }

    public void rollClass() {
        InfoPanel infoPanel = InfoPanel.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        infoPanel.getClassBox().setSelectedIndex(ThreadLocalRandom.current().nextInt(coreRules.STARTING_DEFAULT,coreRules.ALL_CLASSES));
        setClass();
    }

    public void rollAlignment() {
        InfoPanel infoPanel = InfoPanel.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        infoPanel.getAlignmentBox().setSelectedIndex(ThreadLocalRandom.current().nextInt(coreRules.STARTING_DEFAULT,coreRules.ALL_ALIGNMENTS));
        setAlignment();
    }

    public void rollBackground() {
        InfoPanel infoPanel = InfoPanel.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        infoPanel.getBackgroundBox().setSelectedIndex(ThreadLocalRandom.current().nextInt(coreRules.STARTING_DEFAULT,coreRules.ALL_BACKGROUNDS));
        setBackground();
    }

    public void readFeats() {
        FeatsPanel featsPanel = FeatsPanel.getInstance();
        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();
        int bgIndex = characterSheet.getBackground();

        characterSheet.setRaceFeats(coreRules.getRaces()[characterSheet.getRace()] + ":\n" + coreRules.getRaceFeats()[characterSheet.getRace()] + "\n");
        characterSheet.setBackgroundFeats("Background - " + coreRules.getBackgrounds()[bgIndex] + ":\n" + coreRules.getBackgroundFeats()[bgIndex] + "\n");

        String allFeats = characterSheet.getRaceFeats() + characterSheet.getBackgroundFeats() + characterSheet.getClassFeats();
        featsPanel.getFeatsTextArea().setText(allFeats);
    }

    public void rollAll() {
        //Roll attributes
        rollAttributes();

        //Roll race
        rollRace();

        //Roll class
        rollClass();

        //Roll alignment
        rollAlignment();

        //Roll background
        rollBackground();

        //Update Combat Values and Skills Labels
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

        characterSheet.setInitiative(characterSheet.getAttributesMod()[1]);
        calculateAC();
        calculateHP();
    }

    public void refreshCombatValues() {

        setAttributesModifiers();
        setCombatValues();
        setSkills();

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CombatValuesPanel combatValuesPanel = CombatValuesPanel.getInstance();

        combatValuesPanel.getIniValLabel().setText(String.valueOf(characterSheet.getInitiative()));
        combatValuesPanel.getSpeValLabel().setText(String.valueOf(characterSheet.getSpeed()));
        combatValuesPanel.getProValLabel().setText(String.valueOf(characterSheet.getProficiency()));
        combatValuesPanel.getHidValLabel().setText(characterSheet.getHitDiceCount() + "d" + characterSheet.getHitDiceType());
        combatValuesPanel.getHipValLabel().setText(String.valueOf(characterSheet.getHitPointsMax()));
        combatValuesPanel.getArmValLabel().setText(String.valueOf(characterSheet.getArmorClass()));
    }

    // --------------------------------------------- ATTRIBUTES PANEL -> --------------------------------------------- //

    public void setAttributesModifiers() {
        CharacterSheet characterSheet = CharacterSheet.getInstance();
        AttributePanel attributePanel = AttributePanel.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        // Set Proficiency:
        characterSheet.setProficiency(calculateProficiency(characterSheet.getLevel()));

        int allAttributes = coreRules.ALL_ATTRIBUTES;
        int[] attrVal = characterSheet.getAttributes(), attrMod = characterSheet.getAttributesMod(), attrST = characterSheet.getAttributesST();
        boolean[] attrProficient = characterSheet.getAttributesProficient();

        //Show Attributes from Character Sheet
        IntStream.range(0,allAttributes).forEach(i -> attributePanel.getValueLabels()[i].setText(String.valueOf(characterSheet.getAttributes()[i])));

        //Set modifiers ->
        IntStream.range(0,allAttributes).forEach(i -> attrMod[i] = attributeToModifier(attrVal[i]));
        characterSheet.setAttributesMod(attrMod);

        //Set Mod labels ->
        IntStream.range(0,allAttributes).forEach(i -> attributePanel.getModifierLabels()[i].setText(String.valueOf(attrMod[i])));

        //Set saving throws ->
        IntStream.range(0,allAttributes).forEach(i -> attrST[i] = attributeToST(attrMod[i],attrProficient[i]));
        characterSheet.setAttributesST(attrST);

        //Set ST labels ->
        IntStream.range(0,allAttributes).forEach(i -> attributePanel.getStLabels()[i].setText(String.valueOf(attrST[i])));
    }

    public void rollAttributes() {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        DiceRoller diceRoller = DiceRoller.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        int allAttributes = coreRules.ALL_ATTRIBUTES;
        int[] attrVal = characterSheet.getAttributes();

        //Roll Attributes & Set them at Character Sheet ->
        IntStream.range(0,allAttributes).forEach(i -> attrVal[i] = diceRoller.d6(coreRules.ATTRIBUTE_DICE_COUNT));
        characterSheet.setAttributes(attrVal);

        setAttributesModifiers();

        refreshCombatValues();
    }

    // ------------------------------------------ SKILLS PANEL -> LABELS ------------------------------------------- //

    public void setSkills() {
        calculateSkills();

        SkillsPanel skillsPanel = SkillsPanel.getInstance();
        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        for(int i = 0 ; i < coreRules.ALL_SKILLS ; i++)
            skillsPanel.getSkillsLabels()[i].setText(String.valueOf(characterSheet.getSkills()[i]));
    }

    // --------------------------------------------------- RACE ---------------------------------------------------- //

    public void setRace() {
        CharacterSheet characterSheet = CharacterSheet.getInstance();
        InfoPanel infoPanel = InfoPanel.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        int race = infoPanel.getRaceBox().getSelectedIndex();
//        if (characterSheet.getRace() != race) {
            characterSheet.clearProficients();
            characterSheet.changeRace(race);
            rollAttributes();
            readFeats();

            int[] attributes = characterSheet.getAttributes();

            if(race == coreRules.DRAGONBORN) {
                attributes[coreRules.STRENGTH] += 2;
                attributes[coreRules.CHARISMA] += 1;
                characterSheet.setSpeed(coreRules.getRaceSpeed()[race]);
            }
            else if(race == coreRules.DWARF) {
                attributes[coreRules.CONSTITUTION] += 2;
                characterSheet.setSpeed(coreRules.getRaceSpeed()[race]);
            }
            else if(race == coreRules.ELF) {
                attributes[coreRules.DEXTERITY] += 2;
                characterSheet.setSpeed(coreRules.getRaceSpeed()[race]);
                characterSheet.getSkillsProficient()[coreRules.PERCEPTION] = true;
            }
            else if(race == coreRules.GNOME) {
                attributes[coreRules.INTELLIGENCE] += 2;
                characterSheet.setSpeed(coreRules.getRaceSpeed()[race]);
                characterSheet.getAttributesProficient()[coreRules.INTELLIGENCE] = true;
                characterSheet.getAttributesProficient()[coreRules.WISDOM] = true;
                characterSheet.getAttributesProficient()[coreRules.CHARISMA] = true;
            }
            else if(race == coreRules.HALF_ELF) {
                attributes[coreRules.CHARISMA] += 2;
                attributes[ThreadLocalRandom.current().nextInt(coreRules.STARTING_DEFAULT,coreRules.ALL_ATTRIBUTES-1)]++;
                attributes[ThreadLocalRandom.current().nextInt(coreRules.STARTING_DEFAULT,coreRules.ALL_ATTRIBUTES-1)]++;
                characterSheet.setSpeed(coreRules.getRaceSpeed()[race]);
                characterSheet.getSkillsProficient()[ThreadLocalRandom.current().nextInt(coreRules.STARTING_DEFAULT,coreRules.ALL_SKILLS)] = true;
                characterSheet.getSkillsProficient()[ThreadLocalRandom.current().nextInt(coreRules.STARTING_DEFAULT,coreRules.ALL_SKILLS)] = true;
            }
            else if(race == coreRules.HALFLING) {
                attributes[coreRules.DEXTERITY] += 2;
                characterSheet.setSpeed(coreRules.getRaceSpeed()[race]);
            }
            else if(race == coreRules.HALF_ORC) {
                attributes[coreRules.STRENGTH] += 2;
                attributes[coreRules.CONSTITUTION] += 1;
                characterSheet.setSpeed(coreRules.getRaceSpeed()[race]);
                characterSheet.getSkillsProficient()[coreRules.INTIMIDATION] = true;
            }
            else if(race == coreRules.HUMAN) {
                IntStream.range(0,coreRules.ALL_ATTRIBUTES).forEach(i -> attributes[i] += 1);
                characterSheet.setSpeed(coreRules.getRaceSpeed()[race]);
            }
            else if(race == coreRules.TIEFLING) {
                attributes[coreRules.INTELLIGENCE] += 1;
                attributes[coreRules.CHARISMA] += 2;
                characterSheet.setSpeed(coreRules.getRaceSpeed()[race]);
            }

            characterSheet.setAttributes(attributes);
            refreshCombatValues();
        }
//    }

    // ------------------------------------------------- ALIGNMENT ------------------------------------------------- //

    public void setAlignment() {
        InfoPanel infoPanel = InfoPanel.getInstance();
        CharacterSheet characterSheet = CharacterSheet.getInstance();

        if(characterSheet.getAlignment() != infoPanel.getAlignmentBox().getSelectedIndex())
            characterSheet.setAlignment(infoPanel.getAlignmentBox().getSelectedIndex());
    }

    // ------------------------------------------------ BACKGROUND ------------------------------------------------- //

    public void setBackground() {
        InfoPanel infoPanel = InfoPanel.getInstance();
        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();
        int background = infoPanel.getBackgroundBox().getSelectedIndex();
        boolean[] arr = characterSheet.getSkillsProficient();

        for (int x : coreRules.getBackgroundSkillsProficiencies()[characterSheet.getBackground()]) {
            arr[x] = false;
        }
        for (int x : coreRules.getBackgroundSkillsProficiencies()[background]) {
            arr[x] = true;
        }
        characterSheet.setBackground(background);
        readFeats();
        refreshCombatValues();
    }

    // --------------------------------------------------- CLASS --------------------------------------------------- //

    public void setClass() {
        CharacterSheet characterSheet = CharacterSheet.getInstance();
        InfoPanel infoPanel = InfoPanel.getInstance();
        int index = infoPanel.getClassBox().getSelectedIndex();

        if(index != characterSheet.getMainClass()) {
            CoreRules coreRules = CoreRules.getInstance();
            switch (index) {
                case 0 -> {
                    System.out.println("Barbarian");
                    characterSheet.setMainClass(coreRules.BARBARIAN);
                    infoPanel.setSubClassBoxModel(coreRules.BARBARIAN);
                    setRace();
                    setLevel();
                }
                case 1 -> {
                    System.out.println("Bard");
                    characterSheet.setMainClass(coreRules.BARD);
                    infoPanel.setSubClassBoxModel(coreRules.BARD);
                    setRace();
                    setLevel();
                    }
                case 2 -> {
                    System.out.println("Cleric");
                    characterSheet.setMainClass(coreRules.CLERIC);
                    infoPanel.setSubClassBoxModel(coreRules.CLERIC);
                    setRace();
                    setLevel();
                }
                case 3 -> {
                    System.out.println("Druid");
                    characterSheet.setMainClass(coreRules.DRUID);
                    infoPanel.setSubClassBoxModel(coreRules.DRUID);
                    setRace();
                    setLevel();
                }
                case 4 -> {
                    System.out.println("Fighter");
                    characterSheet.setMainClass(coreRules.FIGHTER);
                    infoPanel.setSubClassBoxModel(coreRules.FIGHTER);
                    setRace();
                    setLevel();
                }
                case 5 -> {
                    System.out.println("Monk");
                    characterSheet.setMainClass(coreRules.MONK);
                    infoPanel.setSubClassBoxModel(coreRules.MONK);
                    setRace();
                    setLevel();
                }
                case 6 -> {
                    System.out.println("Paladin");
                    characterSheet.setMainClass(coreRules.PALADIN);
                    infoPanel.setSubClassBoxModel(coreRules.PALADIN);
                    setRace();
                    setLevel();
                }
                case 7 -> {
                    System.out.println("Ranger");
                    characterSheet.setMainClass(coreRules.RANGER);
                    infoPanel.setSubClassBoxModel(coreRules.RANGER);
                    setRace();
                    setLevel();
                }
                case 8 -> {
                    System.out.println("Rogue");
                    characterSheet.setMainClass(coreRules.ROGUE);
                    infoPanel.setSubClassBoxModel(coreRules.ROGUE);
                    setRace();
                    setLevel();
                }
                case 9 -> {
                    System.out.println("Sorcerer");
                    characterSheet.setMainClass(coreRules.SORCERER);
                    infoPanel.setSubClassBoxModel(coreRules.SORCERER);
                    setRace();
                    setLevel();
                }
                case 10 -> {
                    System.out.println("Warlock");
                    characterSheet.setMainClass(coreRules.WARLOCK);
                    infoPanel.setSubClassBoxModel(coreRules.WARLOCK);
                    setRace();
                    setLevel();
                }
                case 11 -> {
                    System.out.println("Wizard");
                    characterSheet.setMainClass(coreRules.WIZARD);
                    infoPanel.setSubClassBoxModel(coreRules.WIZARD);
                    setRace();
                    setLevel();
                }
                default -> throw new IllegalStateException("Unexpected value.");
            }
        }
    }

    // ------------------------------------------------- SUBCLASS -------------------------------------------------- //

    public void setSubClass() {
        //Subclass
    }

    public String[] getSubClasses(int arrayOfSubClasses) {
        //TO DO
        CoreRules coreRules = CoreRules.getInstance();
        return coreRules.getSubClasses(arrayOfSubClasses);
    }

    // --------------------------------------------------- LEVEL --------------------------------------------------- //

    public void setLevel() {
        CharacterSheet characterSheet = CharacterSheet.getInstance();
        InfoPanel infoPanel = InfoPanel.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        int targetLevel = Integer.parseInt(infoPanel.getLevelBox().getSelectedItem().toString());

        characterSheet.setHitDiceCount(targetLevel);

        if (characterSheet.getLevel() >= targetLevel) {
            characterSheet.clearProficients();
            rollAttributes();
            setRace();
            raiseLevel(coreRules.STARTING_LEVEL, targetLevel);
        }
        else {
            raiseLevel(characterSheet.getLevel() + 1, targetLevel);
        }

        characterSheet.setLevel(targetLevel);
        readFeats();
    }

    public void raiseLevel(int startingLevel, int targetLevel) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        if (characterSheet.getMainClass() == coreRules.BARBARIAN) {
            for (int i = startingLevel; i <= targetLevel; i++) barbarianSelected(i);
        }
        else if (characterSheet.getMainClass() == coreRules.BARD) {
            for (int i = startingLevel; i <= targetLevel; i++) bardSelected(i);
        }
        else if (characterSheet.getMainClass() == coreRules.CLERIC) {
            for (int i = startingLevel; i <= targetLevel; i++) clericSelected(i);
        }
        else if (characterSheet.getMainClass() == coreRules.DRUID) {
            for (int i = startingLevel; i <= targetLevel; i++) druidSelected(i);
        }
        else if (characterSheet.getMainClass() == coreRules.FIGHTER) {
            for (int i = startingLevel; i <= targetLevel; i++) fighterSelected(i);
        }
        else if (characterSheet.getMainClass() == coreRules.MONK) {
            for (int i = startingLevel; i <= targetLevel; i++) monkSelected(i);
        }
        else if (characterSheet.getMainClass() == coreRules.PALADIN) {
            for (int i = startingLevel; i <= targetLevel; i++) paladinSelected(i);
        }
        else if (characterSheet.getMainClass() == coreRules.RANGER) {
            for (int i = startingLevel; i <= targetLevel; i++) rangerSelected(i);
        }
        else if (characterSheet.getMainClass() == coreRules.ROGUE) {
            for (int i = startingLevel; i <= targetLevel; i++) rogueSelected(i);
        }
        else if (characterSheet.getMainClass() == coreRules.SORCERER) {
            for (int i = startingLevel; i <= targetLevel; i++) sorcererSelected(i);
        }
        else if (characterSheet.getMainClass() == coreRules.WARLOCK) {
            for (int i = startingLevel; i <= targetLevel; i++) warlockSelected(i);
        }
        else if (characterSheet.getMainClass() == coreRules.WIZARD) {
            for (int i = startingLevel; i <= targetLevel; i++) wizardSelected(i);
        }

        readFeats();
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
                skills[i] = characterSheet.getAttributesMod()[0];
                if(skillsProficient[i])
                    skills[i] += proficiency;
            }

            else if(containsIn(coreRules.getDexSkills(),i)) {
                skills[i] = characterSheet.getAttributesMod()[1];
                if(skillsProficient[i])
                    skills[i] += proficiency;
            }

            else if(containsIn(coreRules.getIntSkills(),i)) {
                skills[i] = characterSheet.getAttributesMod()[3];
                if(skillsProficient[i])
                    skills[i] += proficiency;
            }

            else if(containsIn(coreRules.getWisSkills(),i)) {
                skills[i] = characterSheet.getAttributesMod()[4];
                if(skillsProficient[i])
                    skills[i] += proficiency;
            }

            else if(containsIn(coreRules.getChaSkills(),i)) {
                skills[i] = characterSheet.getAttributesMod()[5];
                if(skillsProficient[i])
                    skills[i] += proficiency;
            }
        }

        characterSheet.setSkills(skills);
    }

    // ------------------------------------------- CLASSES CHOOSING ------------------------------------------------ //

    public void barbarianSelected(int level) {
        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();
        switch (level) {

            case 1 -> {
                characterSheet.clearProficients();
                setAttributeProficiencies(coreRules.getClassAttributesProficiencies()[CoreRules.getInstance().BARBARIAN]);
                setBackground();
                setRandomSkillsProficiencies(coreRules.getBarbarianSkills(),2);
                characterSheet.setHitDiceType(coreRules.BARBARIAN_DICE);
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense" + "\n");
            }

            case 2 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense\n> Reckless Attack\n> Danger Sense" + "\n");
            }

            case 3 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense\n> Reckless Attack\n> Danger Sense\n> Primal Path" + "\n");
            }

            case 4 -> {
                raiseRandomAttributes();
            }

            case 5 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense\n> Reckless Attack\n> Danger Sense\n> Primal Path\n> Extra Attack\n> Fast Movement" + "\n");
            }

            case 6 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense\n> Reckless Attack\n> Danger Sense\n> Primal Path\n> Extra Attack\n> Fast Movement\n> Path feature" + "\n");
            }

            case 7 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense\n> Reckless Attack\n> Danger Sense\n> Primal Path\n> Extra Attack\n> Fast Movement\n> Path feature\n> Feral Instinct" + "\n");
            }

            case 8 -> {
                raiseRandomAttributes();
            }

            case 9 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense\n> Reckless Attack\n> Danger Sense\n> Primal Path\n> Extra Attack\n> Fast Movement\n> Path feature\n> Feral Instinct\n> Brutal Critical (1 die)" + "\n");
            }

            case 10 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense\n> Reckless Attack\n> Danger Sense\n> Primal Path\n> Extra Attack\n> Fast Movement\n> Path feature\n> Feral Instinct\n> Brutal Critical (1 die)\n> Path feature" + "\n");
            }

            case 11 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense\n> Reckless Attack\n> Danger Sense\n> Primal Path\n> Extra Attack\n> Fast Movement\n> Path feature\n> Feral Instinct\n> Brutal Critical (1 die)\n> Path feature\n> Relentless Rage" + "\n");
            }

            case 12 -> {
                raiseRandomAttributes();
            }

            case 13 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense\n> Reckless Attack\n> Danger Sense\n> Primal Path\n> Extra Attack\n> Fast Movement\n> Path feature\n> Feral Instinct\n> Brutal Critical (2 dice)\n> Path feature\n> Relentless Rage" + "\n");
            }

            case 14 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense\n> Reckless Attack\n> Danger Sense\n> Primal Path\n> Extra Attack\n> Fast Movement\n> Path feature\n> Feral Instinct\n> Brutal Critical (2 dice)\n> Path feature\n> Relentless Rage\n> Path feature" + "\n");
            }

            case 15 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense\n> Reckless Attack\n> Danger Sense\n> Primal Path\n> Extra Attack\n> Fast Movement\n> Path feature\n> Feral Instinct\n> Brutal Critical (2 dice)\n> Path feature\n> Relentless Rage\n> Path feature\n> Persistent Rage" + "\n");
            }

            case 16 -> {
                raiseRandomAttributes();
            }

            case 17 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense\n> Reckless Attack\n> Danger Sense\n> Primal Path\n> Extra Attack\n> Fast Movement\n> Path feature\n> Feral Instinct\n> Brutal Critical (3 dice)\n> Path feature\n> Relentless Rage\n> Path feature\n> Persistent Rage" + "\n");
            }

            case 18 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense\n> Reckless Attack\n> Danger Sense\n> Primal Path\n> Extra Attack\n> Fast Movement\n> Path feature\n> Feral Instinct\n> Brutal Critical (3 dice)\n> Path feature\n> Relentless Rage\n> Path feature\n> Persistent Rage\n> Indomitable Might" + "\n");
            }

            case 19 -> {
                raiseRandomAttributes();
            }

            case 20 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense\n> Reckless Attack\n> Danger Sense\n> Primal Path\n> Extra Attack\n> Fast Movement\n> Path feature\n> Feral Instinct\n> Brutal Critical (3 dice)\n> Path feature\n> Relentless Rage\n> Path feature\n> Persistent Rage\n> Indomitable Might\n> Primal Champion" + "\n");
            }

            default -> throw new IllegalStateException("Unexpected value.");
        }
    }

    public void bardSelected(int level) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        switch (level) {

            case 1 -> {
                characterSheet.clearProficients();
                setAttributeProficiencies(coreRules.getClassAttributesProficiencies()[CoreRules.getInstance().BARD]);
                setBackground();
                for(int i = 0 ; i < 3 ; i++) {
                    characterSheet.getSkillsProficient()[ThreadLocalRandom.current().nextInt(coreRules.STARTING_DEFAULT, coreRules.ALL_SKILLS)] = true;
                }

                characterSheet.setHitDiceType(coreRules.BARD_DICE);
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Bardic Inspiration (d6)" + "\n");
            }

            case 2 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Bardic Inspiration (d6)\n> Jack of All Trades\n> Song of Rest (d6)" + "\n");
            }

            case 3 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Bardic Inspiration (d6)\n> Jack of All Trades\n> Song of Rest (d6)\n> Bard College\n> Expertise" + "\n");
            }

            case 4 -> {
                raiseRandomAttributes();
            }

            case 5 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Bardic Inspiration (d8)\n> Jack of All Trades\n> Song of Rest (d6)\n> Bard College\n> Expertise\n> Font of Inspiration" + "\n");
            }

            case 6 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Bardic Inspiration (d8)\n> Jack of All Trades\n> Song of Rest (d6)\n> Bard College\n> Expertise\n> Font of Inspiration\n> Countercharm\n> Bard College feature" + "\n");
            }

            case 7 -> {}

            case 8 -> {
                raiseRandomAttributes();
            }

            case 9 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Bardic Inspiration (d8)\n> Jack of All Trades\n> Song of Rest (d8)\n> Bard College\n> Expertise\n> Font of Inspiration\n> Countercharm\n> Bard College feature" + "\n");
            }

            case 10 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Bardic Inspiration (d10)\n> Jack of All Trades\n> Song of Rest (d8)\n> Bard College\n> Expertise\n> Font of Inspiration\n> Countercharm\n> Bard College feature\n> Expertise (second)\n> Magical Secrets" + "\n");
            }

            case 11 -> {}

            case 12 -> {
                raiseRandomAttributes();
            }

            case 13 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Bardic Inspiration (d10)\n> Jack of All Trades\n> Song of Rest (d10)\n> Bard College\n> Expertise\n> Font of Inspiration\n> Countercharm\n> Bard College feature\n> Expertise (second)\n> Magical Secrets" + "\n");
            }

            case 14 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Bardic Inspiration (d10)\n> Jack of All Trades\n> Song of Rest (d10)\n> Bard College\n> Expertise\n> Font of Inspiration\n> Countercharm\n> Bard College feature\n> Expertise (second)\n> Magical Secrets\n> Magical Secrets (second)\n> Bard College feature" + "\n");
            }

            case 15 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Bardic Inspiration (d12)\n> Jack of All Trades\n> Song of Rest (d10)\n> Bard College\n> Expertise\n> Font of Inspiration\n> Countercharm\n> Bard College feature\n> Expertise (second)\n> Magical Secrets\n> Magical Secrets (second)\n> Bard College feature" + "\n");
            }

            case 16 -> {
                raiseRandomAttributes();
            }

            case 17 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Bardic Inspiration (d12)\n> Jack of All Trades\n> Song of Rest (d12)\n> Bard College\n> Expertise\n> Font of Inspiration\n> Countercharm\n> Bard College feature\n> Expertise (second)\n> Magical Secrets\n> Magical Secrets (second)\n> Bard College feature" + "\n");
            }

            case 18 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Bardic Inspiration (d12)\n> Jack of All Trades\n> Song of Rest (d12)\n> Bard College\n> Expertise\n> Font of Inspiration\n> Countercharm\n> Bard College feature\n> Expertise (second)\n> Magical Secrets\n> Magical Secrets (second)\n> Bard College feature\n> Magical Secrets (third)" + "\n");
            }

            case 19 -> {
                raiseRandomAttributes();
            }

            case 20 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Bardic Inspiration (d12)\n> Jack of All Trades\n> Song of Rest (d12)\n> Bard College\n> Expertise\n> Font of Inspiration\n> Countercharm\n> Bard College feature\n> Expertise (second)\n> Magical Secrets\n> Magical Secrets (second)\n> Bard College feature\n> Magical Secrets (third)\n> Superior Inspiration" + "\n");
            }

            default -> throw new IllegalStateException("Unexpected value.");
        }
    }

    public void clericSelected(int level) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        switch (level) {

            case 1 -> {
                characterSheet.clearProficients();
                setAttributeProficiencies(coreRules.getClassAttributesProficiencies()[CoreRules.getInstance().CLERIC]);
                setBackground();
                setRandomSkillsProficiencies(coreRules.getClericSkills(),2);
                characterSheet.setHitDiceType(coreRules.CLERIC_DICE);
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Divine Domain" + "\n");
            }

            case 2 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Divine Domain\n> Channel Divinity (1/rest)\n> Divine Domain feature" + "\n");
            }

            case 3 -> {}

            case 4 -> {
                raiseRandomAttributes();
            }

            case 5 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Divine Domain\n> Channel Divinity (1/rest)\n> Divine Domain feature\n> Destroy Undead (CR 1/2)" + "\n");
            }

            case 6 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Divine Domain\n> Channel Divinity (2/rest)\n> Divine Domain feature\n> Destroy Undead (CR 1/2)\n> Divine Domain feature (second)" + "\n");
            }

            case 7 -> {}

            case 8 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Divine Domain\n> Channel Divinity (2/rest)\n> Divine Domain feature\n> Destroy Undead (CR 1)\n> Divine Domain feature (second)\n> Divine Domain feature (third)" + "\n");

                raiseRandomAttributes();
            }

            case 9 -> {}

            case 10 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Divine Domain\n> Channel Divinity (2/rest)\n> Divine Domain feature\n> Destroy Undead (CR 1)\n> Divine Domain feature (second)\n> Divine Domain feature (third)\n> Divine Intervention" + "\n");
            }

            case 11 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Divine Domain\n> Channel Divinity (2/rest)\n> Divine Domain feature\n> Destroy Undead (CR 2)\n> Divine Domain feature (second)\n> Divine Domain feature (third)\n> Divine Intervention" + "\n");
            }

            case 12 -> {
                raiseRandomAttributes();
            }

            case 13 -> {}

            case 14 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Divine Domain\n> Channel Divinity (2/rest)\n> Divine Domain feature\n> Destroy Undead (CR 3)\n> Divine Domain feature (second)\n> Divine Domain feature (third)\n> Divine Intervention" + "\n");
            }

            case 15 -> {}

            case 16 -> {
                raiseRandomAttributes();
            }

            case 17 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Divine Domain\n> Channel Divinity (2/rest)\n> Divine Domain feature\n> Destroy Undead (CR 4)\n> Divine Domain feature (second)\n> Divine Domain feature (third)\n> Divine Intervention\n> Divine Domain feature (fourth)" + "\n");
            }

            case 18 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Divine Domain\n> Channel Divinity (3/rest)\n> Divine Domain feature\n> Destroy Undead (CR 4)\n> Divine Domain feature (second)\n> Divine Domain feature (third)\n> Divine Intervention\n> Divine Domain feature (fourth)" + "\n");
            }

            case 19 -> {
                raiseRandomAttributes();
            }

            case 20 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Divine Domain\n> Channel Divinity (3/rest)\n> Divine Domain feature\n> Destroy Undead (CR 4)\n> Divine Domain feature (second)\n> Divine Domain feature (third)\n> Divine Intervention\n> Divine Domain feature (fourth)\n> Divine Intervention improvement" + "\n");
            }

            default -> throw new IllegalStateException("Unexpected value.");
        }
    }

    public void druidSelected(int level) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        switch (level) {

            case 1 -> {
                characterSheet.clearProficients();
                setAttributeProficiencies(coreRules.getClassAttributesProficiencies()[CoreRules.getInstance().DRUID]);
                setBackground();
                setRandomSkillsProficiencies(coreRules.getDruidSkills(),2);
                characterSheet.setHitDiceType(coreRules.DRUID_DICE);
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Druidic\n> Spellcasting" + "\n");
            }

            case 2 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Druidic\n> Spellcasting\n> Wild Shape\n> Druid Circle" + "\n");
            }

            case 3 -> {}

            case 4 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Druidic\n> Spellcasting\n> Wild Shape\n> Druid Circle\n> Wild Shape improvement" + "\n");

                raiseRandomAttributes();
            }

            case 5 -> {}

            case 6 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Druidic\n> Spellcasting\n> Wild Shape\n> Druid Circle\n> Wild Shape improvement\n> Druid Circle feature" + "\n");
            }

            case 7 -> {}

            case 8 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Druidic\n> Spellcasting\n> Wild Shape\n> Druid Circle\n> Wild Shape improvement\n> Druid Circle feature\n> Wild Shape improvement (second)" + "\n");

                raiseRandomAttributes();
            }

            case 9 -> {}

            case 10 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Druidic\n> Spellcasting\n> Wild Shape\n> Druid Circle\n> Wild Shape improvement\n> Druid Circle feature\n> Wild Shape improvement (second)\n> Druid Circle feature (second)" + "\n");
            }

            case 11 -> {}

            case 12 -> {
                raiseRandomAttributes();
            }

            case 13 -> {}

            case 14 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Druidic\n> Spellcasting\n> Wild Shape\n> Druid Circle\n> Wild Shape improvement\n> Druid Circle feature\n> Wild Shape improvement (second)\n> Druid Circle feature (second)\n> Druid Circle feature (third)" + "\n");
            }

            case 15 -> {}

            case 16 -> {
                raiseRandomAttributes();
            }

            case 17 -> {}

            case 18 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Druidic\n> Spellcasting\n> Wild Shape\n> Druid Circle\n> Wild Shape improvement\n> Druid Circle feature\n> Wild Shape improvement (second)\n> Druid Circle feature (second)\n> Druid Circle feature (third)\n> Timeless Body\n> Beast Spells" + "\n");
            }

            case 19 -> {
                raiseRandomAttributes();
            }

            case 20 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Druidic\n> Spellcasting\n> Wild Shape\n> Druid Circle\n> Wild Shape improvement\n> Druid Circle feature\n> Wild Shape improvement (second)\n> Druid Circle feature (second)\n> Druid Circle feature (third)\n> Timeless Body\n> Beast Spells\n> Archdruid" + "\n");
            }

            default -> throw new IllegalStateException("Unexpected value.");
        }
    }

    public void fighterSelected(int level) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        switch (level) {

            case 1 -> {
                characterSheet.clearProficients();
                setAttributeProficiencies(coreRules.getClassAttributesProficiencies()[CoreRules.getInstance().FIGHTER]);
                setBackground();
                setRandomSkillsProficiencies(coreRules.getFighterSkills(),2);
                characterSheet.setHitDiceType(coreRules.FIGHTER_DICE);
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Fighting Style\n> Second Wind" + "\n");
            }

            case 2 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Fighting Style\n> Second Wind\n> Action Surge (one use)" + "\n");
            }

            case 3 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Fighting Style\n> Second Wind\n> Action Surge (one use)\n> Martial Archetype" + "\n");
            }

            case 4 -> {
                raiseRandomAttributes();
            }

            case 5 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Fighting Style\n> Second Wind\n> Action Surge (one use)\n> Martial Archetype\n> Extra Attack" + "\n");
            }

            case 6 -> {
                raiseRandomAttributes();
            }

            case 7 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Fighting Style\n> Second Wind\n> Action Surge (one use)\n> Martial Archetype\n> Extra Attack\n> Martial Archetype feature" + "\n");
            }

            case 8 -> {
                raiseRandomAttributes();
            }

            case 9 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Fighting Style\n> Second Wind\n> Action Surge (one use)\n> Martial Archetype\n> Extra Attack\n> Martial Archetype feature\n> Indomitable (one use)" + "\n");
            }

            case 10 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Fighting Style\n> Second Wind\n> Action Surge (one use)\n> Martial Archetype\n> Extra Attack\n> Martial Archetype feature\n> Indomitable (one use)\n> Martial Archetype feature (second)" + "\n");
            }

            case 11 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Fighting Style\n> Second Wind\n> Action Surge (one use)\n> Martial Archetype\n> Extra Attack (2)\n> Martial Archetype feature\n> Indomitable (one use)\n> Martial Archetype feature (second)" + "\n");
            }

            case 12 -> {
                raiseRandomAttributes();
            }

            case 13 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Fighting Style\n> Second Wind\n> Action Surge (one use)\n> Martial Archetype\n> Extra Attack (2)\n> Martial Archetype feature\n> Indomitable (two uses)\n> Martial Archetype feature (second)" + "\n");
            }

            case 14 -> {
                raiseRandomAttributes();
            }

            case 15 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Fighting Style\n> Second Wind\n> Action Surge (one use)\n> Martial Archetype\n> Extra Attack (2)\n> Martial Archetype feature\n> Indomitable (two uses)\n> Martial Archetype feature (second)\n> Martial Archetype feature (third)" + "\n");
            }

            case 16 -> {
                raiseRandomAttributes();
            }

            case 17 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Fighting Style\n> Second Wind\n> Action Surge (two uses)\n> Martial Archetype\n> Extra Attack (2)\n> Martial Archetype feature\n> Indomitable (three uses)\n> Martial Archetype feature (second)\n> Martial Archetype feature (third)" + "\n");
            }

            case 18 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Fighting Style\n> Second Wind\n> Action Surge (two uses)\n> Martial Archetype\n> Extra Attack (2)\n> Martial Archetype feature\n> Indomitable (three uses)\n> Martial Archetype feature (second)\n> Martial Archetype feature (third)\n> Martial Archetype feature (fourth)" + "\n");
            }

            case 19 -> {
                raiseRandomAttributes();
            }

            case 20 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Fighting Style\n> Second Wind\n> Action Surge (two uses)\n> Martial Archetype\n> Extra Attack (3)\n> Martial Archetype feature\n> Indomitable (three uses)\n> Martial Archetype feature (second)\n> Martial Archetype feature (third)\n> Martial Archetype feature (fourth)" + "\n");
            }

            default -> throw new IllegalStateException("Unexpected value.");
        }
    }

    public void monkSelected(int level) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        switch (level) {

            case 1 -> {
                characterSheet.clearProficients();
                setAttributeProficiencies(coreRules.getClassAttributesProficiencies()[CoreRules.getInstance().MONK]);
                setBackground();
                setRandomSkillsProficiencies(coreRules.getMonkSkills(),2);
                characterSheet.setHitDiceType(coreRules.MONK_DICE);
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Unarmored Defense\n> Martial Arts" + "\n");
            }

            case 2 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Unarmored Defense\n> Martial Arts\n> Ki\n> Unarmored Movement" + "\n");
                characterSheet.setSpeed(coreRules.getRaceSpeed()[characterSheet.getRace()]+10);
            }

            case 3 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Unarmored Defense\n> Martial Arts\n> Ki\n> Unarmored Movement\n> Monastic Tradition\n> Deflect Missiles" + "\n");
            }

            case 4 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Unarmored Defense\n> Martial Arts\n> Ki\n> Unarmored Movement\n> Monastic Tradition\n> Deflect Missiles\n> Slow Fall" + "\n");

                raiseRandomAttributes();
            }

            case 5 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Unarmored Defense\n> Martial Arts\n> Ki\n> Unarmored Movement\n> Monastic Tradition\n> Deflect Missiles\n> Slow Fall\n> Extra Attack\n> Stunning Strike" + "\n");
            }

            case 6 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Unarmored Defense\n> Martial Arts\n> Ki\n> Unarmored Movement\n> Monastic Tradition\n> Deflect Missiles\n> Slow Fall\n> Extra Attack\n> Stunning Strike\n> Ki-Empowered Strikes\n> Monastic Tradition feature" + "\n");
                characterSheet.setSpeed(coreRules.getRaceSpeed()[characterSheet.getRace()]+15);
            }

            case 7 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Unarmored Defense\n> Martial Arts\n> Ki\n> Unarmored Movement\n> Monastic Tradition\n> Deflect Missiles\n> Slow Fall\n> Extra Attack\n> Stunning Strike\n> Ki-Empowered Strikes\n> Monastic Tradition feature\n> Evasion\n> Stillness of Mind" + "\n");
            }

            case 8 -> {
                raiseRandomAttributes();
            }

            case 9 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Unarmored Defense\n> Martial Arts\n> Ki\n> Unarmored Movement\n> Monastic Tradition\n> Deflect Missiles\n> Slow Fall\n> Extra Attack\n> Stunning Strike\n> Ki-Empowered Strikes\n> Monastic Tradition feature\n> Evasion\n> Stillness of Mind\n> Unarmored Movement improvement" + "\n");
            }

            case 10 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Unarmored Defense\n> Martial Arts\n> Ki\n> Unarmored Movement\n> Monastic Tradition\n> Deflect Missiles\n> Slow Fall\n> Extra Attack\n> Stunning Strike\n> Ki-Empowered Strikes\n> Monastic Tradition feature\n> Evasion\n> Stillness of Mind\n> Unarmored Movement improvement\n> Purity of Body" + "\n");
                characterSheet.setSpeed(coreRules.getRaceSpeed()[characterSheet.getRace()]+20);
            }

            case 11 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Unarmored Defense\n> Martial Arts\n> Ki\n> Unarmored Movement\n> Monastic Tradition\n> Deflect Missiles\n> Slow Fall\n> Extra Attack\n> Stunning Strike\n> Ki-Empowered Strikes\n> Monastic Tradition feature\n> Evasion\n> Stillness of Mind\n> Unarmored Movement improvement\n> Purity of Body\n> Monastic Tradition feature (second)" + "\n");
            }

            case 12 -> {
                raiseRandomAttributes();
            }

            case 13 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Unarmored Defense\n> Martial Arts\n> Ki\n> Unarmored Movement\n> Monastic Tradition\n> Deflect Missiles\n> Slow Fall\n> Extra Attack\n> Stunning Strike\n> Ki-Empowered Strikes\n> Monastic Tradition feature\n> Evasion\n> Stillness of Mind\n> Unarmored Movement improvement\n> Purity of Body\n> Monastic Tradition feature (second)\n> Tongue of the Sun and Moon" + "\n");
            }

            case 14 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Unarmored Defense\n> Martial Arts\n> Ki\n> Unarmored Movement\n> Monastic Tradition\n> Deflect Missiles\n> Slow Fall\n> Extra Attack\n> Stunning Strike\n> Ki-Empowered Strikes\n> Monastic Tradition feature\n> Evasion\n> Stillness of Mind\n> Unarmored Movement improvement\n> Purity of Body\n> Monastic Tradition feature (second)\n> Tongue of the Sun and Moon\n> Diamond Soul" + "\n");
                characterSheet.setSpeed(coreRules.getRaceSpeed()[characterSheet.getRace()]+25);
            }

            case 15 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Unarmored Defense\n> Martial Arts\n> Ki\n> Unarmored Movement\n> Monastic Tradition\n> Deflect Missiles\n> Slow Fall\n> Extra Attack\n> Stunning Strike\n> Ki-Empowered Strikes\n> Monastic Tradition feature\n> Evasion\n> Stillness of Mind\n> Unarmored Movement improvement\n> Purity of Body\n> Monastic Tradition feature (second)\n> Tongue of the Sun and Moon\n> Diamond Soul\n> Timeless Body" + "\n");
            }

            case 16 -> {
                raiseRandomAttributes();
            }

            case 17 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Unarmored Defense\n> Martial Arts\n> Ki\n> Unarmored Movement\n> Monastic Tradition\n> Deflect Missiles\n> Slow Fall\n> Extra Attack\n> Stunning Strike\n> Ki-Empowered Strikes\n> Monastic Tradition feature\n> Evasion\n> Stillness of Mind\n> Unarmored Movement improvement\n> Purity of Body\n> Monastic Tradition feature (second)\n> Tongue of the Sun and Moon\n> Diamond Soul\n> Timeless Body\n> Monastic Tradition feature (third)" + "\n");
            }

            case 18 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Unarmored Defense\n> Martial Arts\n> Ki\n> Unarmored Movement\n> Monastic Tradition\n> Deflect Missiles\n> Slow Fall\n> Extra Attack\n> Stunning Strike\n> Ki-Empowered Strikes\n> Monastic Tradition feature\n> Evasion\n> Stillness of Mind\n> Unarmored Movement improvement\n> Purity of Body\n> Monastic Tradition feature (second)\n> Tongue of the Sun and Moon\n> Diamond Soul\n> Timeless Body\n> Monastic Tradition feature (third)\n> Empty Body" + "\n");
                characterSheet.setSpeed(coreRules.getRaceSpeed()[characterSheet.getRace()]+30);
            }

            case 19 -> {
                raiseRandomAttributes();
            }

            case 20 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Unarmored Defense\n> Martial Arts\n> Ki\n> Unarmored Movement\n> Monastic Tradition\n> Deflect Missiles\n> Slow Fall\n> Extra Attack\n> Stunning Strike\n> Ki-Empowered Strikes\n> Monastic Tradition feature\n> Evasion\n> Stillness of Mind\n> Unarmored Movement improvement\n> Purity of Body\n> Monastic Tradition feature (second)\n> Tongue of the Sun and Moon\n> Diamond Soul\n> Timeless Body\n> Monastic Tradition feature (third)\n> Empty Body\n> Perfect Self" + "\n");
            }

            default -> throw new IllegalStateException("Unexpected value.");
        }

    }

    public void paladinSelected(int level) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        switch (level) {

            case 1 -> {
                characterSheet.clearProficients();
                setAttributeProficiencies(coreRules.getClassAttributesProficiencies()[CoreRules.getInstance().PALADIN]);
                setBackground();
                setRandomSkillsProficiencies(coreRules.getPaladinSkills(),2);
                characterSheet.setHitDiceType(coreRules.PALADIN_DICE);
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Divine Sense\n> Lay on Hands" + "\n");
            }

            case 2 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Divine Sense\n> Lay on Hands\n> Fighting Style\n> Spellcasting\n> Divine Smite" + "\n");
            }

            case 3 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Divine Sense\n> Lay on Hands\n> Fighting Style\n> Spellcasting\n> Divine Smite\n> Divine Health\n> Sacred Oath" + "\n");
            }

            case 4 -> {
                raiseRandomAttributes();
            }

            case 5 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Divine Sense\n> Lay on Hands\n> Fighting Style\n> Spellcasting\n> Divine Smite\n> Divine Health\n> Sacred Oath\n> Extra Attack" + "\n");
            }

            case 6 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Divine Sense\n> Lay on Hands\n> Fighting Style\n> Spellcasting\n> Divine Smite\n> Divine Health\n> Sacred Oath\n> Extra Attack\n> Aura of Protection" + "\n");
            }

            case 7 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Divine Sense\n> Lay on Hands\n> Fighting Style\n> Spellcasting\n> Divine Smite\n> Divine Health\n> Sacred Oath\n> Extra Attack\n> Aura of Protection\n> Sacred Oath feature" + "\n");
            }

            case 8 -> {
                raiseRandomAttributes();
            }

            case 9 -> {}

            case 10 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Divine Sense\n> Lay on Hands\n> Fighting Style\n> Spellcasting\n> Divine Smite\n> Divine Health\n> Sacred Oath\n> Extra Attack\n> Aura of Protection\n> Sacred Oath feature\n> Aura of Courage" + "\n");
            }

            case 11 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Divine Sense\n> Lay on Hands\n> Fighting Style\n> Spellcasting\n> Divine Smite\n> Divine Health\n> Sacred Oath\n> Extra Attack\n> Aura of Protection\n> Sacred Oath feature\n> Aura of Courage\n> Improved Divine Smite" + "\n");
            }

            case 12 -> {
                raiseRandomAttributes();
            }
            case 13 -> {}

            case 14 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Divine Sense\n> Lay on Hands\n> Fighting Style\n> Spellcasting\n> Divine Smite\n> Divine Health\n> Sacred Oath\n> Extra Attack\n> Aura of Protection\n> Sacred Oath feature\n> Aura of Courage\n> Improved Divine Smite\n> Cleansing Touch" + "\n");
            }

            case 15 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Divine Sense\n> Lay on Hands\n> Fighting Style\n> Spellcasting\n> Divine Smite\n> Divine Health\n> Sacred Oath\n> Extra Attack\n> Aura of Protection\n> Sacred Oath feature\n> Aura of Courage\n> Improved Divine Smite\n> Cleansing Touch\n> Sacred Oath feature (second)" + "\n");
            }

            case 16 -> {
                raiseRandomAttributes();
            }

            case 17 -> {}

            case 18 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Divine Sense\n> Lay on Hands\n> Fighting Style\n> Spellcasting\n> Divine Smite\n> Divine Health\n> Sacred Oath\n> Extra Attack\n> Aura of Protection\n> Sacred Oath feature\n> Aura of Courage\n> Improved Divine Smite\n> Cleansing Touch\n> Sacred Oath feature (second)\n> Aura improvements" + "\n");
            }

            case 19 -> {
                raiseRandomAttributes();
            }

            case 20 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Divine Sense\n> Lay on Hands\n> Fighting Style\n> Spellcasting\n> Divine Smite\n> Divine Health\n> Sacred Oath\n> Extra Attack\n> Aura of Protection\n> Sacred Oath feature\n> Aura of Courage\n> Improved Divine Smite\n> Cleansing Touch\n> Sacred Oath feature (second)\n> Aura improvements\n> Sacred Oath feature (third)" + "\n");
            }

            default -> throw new IllegalStateException("Unexpected value.");
        }
    }

    public void rangerSelected(int level) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        switch (level) {

            case 1 -> {
                characterSheet.clearProficients();
                setAttributeProficiencies(coreRules.getClassAttributesProficiencies()[CoreRules.getInstance().RANGER]);
                setBackground();
                setRandomSkillsProficiencies(coreRules.getRangerSkills(),3);
                characterSheet.setHitDiceType(coreRules.RANGER_DICE);
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Favored Enemy\n> Natural Explorer" + "\n");
            }

            case 2 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Favored Enemy\n> Natural Explorer\n> Fighting Style\n> Spellcasting" + "\n");
            }

            case 3 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Favored Enemy\n> Natural Explorer\n> Fighting Style\n> Spellcasting\n> Ranger Archetype\n> Primeval Awareness" + "\n");
            }

            case 4 -> {
                raiseRandomAttributes();
            }

            case 5 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Favored Enemy\n> Natural Explorer\n> Fighting Style\n> Spellcasting\n> Ranger Archetype\n> Primeval Awareness\n> Extra Attack" + "\n");
            }

            case 6 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Favored Enemy\n> Natural Explorer\n> Fighting Style\n> Spellcasting\n> Ranger Archetype\n> Primeval Awareness\n> Extra Attack\n> Favored Enemy and Natural Explorer improvements" + "\n");
            }

            case 7 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Favored Enemy\n> Natural Explorer\n> Fighting Style\n> Spellcasting\n> Ranger Archetype\n> Primeval Awareness\n> Extra Attack\n> Favored Enemy and Natural Explorer improvements\n> Ranger Archetype feature" + "\n");
            }

            case 8 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Favored Enemy\n> Natural Explorer\n> Fighting Style\n> Spellcasting\n> Ranger Archetype\n> Primeval Awareness\n> Extra Attack\n> Favored Enemy and Natural Explorer improvements\n> Ranger Archetype feature\n> Land's Stride" + "\n");
                raiseRandomAttributes();
            }

            case 9 -> {}

            case 10 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Favored Enemy\n> Natural Explorer\n> Fighting Style\n> Spellcasting\n> Ranger Archetype\n> Primeval Awareness\n> Extra Attack\n> Favored Enemy and Natural Explorer improvements\n> Ranger Archetype feature\n> Land's Stride\n> Natural Explorer improvement (second)\n> Hide in Plain Sight" + "\n");
            }

            case 11 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Favored Enemy\n> Natural Explorer\n> Fighting Style\n> Spellcasting\n> Ranger Archetype\n> Primeval Awareness\n> Extra Attack\n> Favored Enemy and Natural Explorer improvements\n> Ranger Archetype feature\n> Land's Stride\n> Natural Explorer improvement (second)\n> Hide in Plain Sight\n> Ranger Archetype feature (second)" + "\n");
            }

            case 12 -> {
                raiseRandomAttributes();
            }

            case 13 -> {}

            case 14 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Favored Enemy\n> Natural Explorer\n> Fighting Style\n> Spellcasting\n> Ranger Archetype\n> Primeval Awareness\n> Extra Attack\n> Favored Enemy and Natural Explorer improvements\n> Ranger Archetype feature\n> Land's Stride\n> Natural Explorer improvement (second)\n> Hide in Plain Sight\n> Ranger Archetype feature (second)\n> Favored Enemy improvement (second)" + "\n");
            }

            case 15 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Favored Enemy\n> Natural Explorer\n> Fighting Style\n> Spellcasting\n> Ranger Archetype\n> Primeval Awareness\n> Extra Attack\n> Favored Enemy and Natural Explorer improvements\n> Ranger Archetype feature\n> Land's Stride\n> Natural Explorer improvement (second)\n> Hide in Plain Sight\n> Ranger Archetype feature (second)\n> Favored Enemy improvement (second)\n> Ranger Archetype feature (third)" + "\n");
            }

            case 16 -> {
                raiseRandomAttributes();
            }

            case 17 -> {}

            case 18 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Favored Enemy\n> Natural Explorer\n> Fighting Style\n> Spellcasting\n> Ranger Archetype\n> Primeval Awareness\n> Extra Attack\n> Favored Enemy and Natural Explorer improvements\n> Ranger Archetype feature\n> Land's Stride\n> Natural Explorer improvement (second)\n> Hide in Plain Sight\n> Ranger Archetype feature (second)\n> Favored Enemy improvement (second)\n> Ranger Archetype feature (third)\n> Feral Senses" + "\n");
            }

            case 19 -> {
                raiseRandomAttributes();
            }

            case 20 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Favored Enemy\n> Natural Explorer\n> Fighting Style\n> Spellcasting\n> Ranger Archetype\n> Primeval Awareness\n> Extra Attack\n> Favored Enemy and Natural Explorer improvements\n> Ranger Archetype feature\n> Land's Stride\n> Natural Explorer improvement (second)\n> Hide in Plain Sight\n> Ranger Archetype feature (second)\n> Favored Enemy improvement (second)\n> Ranger Archetype feature (third)\n> Feral Senses\n> Foe Slayer" + "\n");
            }

            default -> throw new IllegalStateException("Unexpected value.");
        }
    }

    public void rogueSelected(int level) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        switch (level) {

            case 1 -> {
                characterSheet.clearProficients();
                setAttributeProficiencies(coreRules.getClassAttributesProficiencies()[CoreRules.getInstance().ROGUE]);
                setBackground();
                setRandomSkillsProficiencies(coreRules.getRogueSkills(),4);
                characterSheet.setHitDiceType(coreRules.ROGUE_DICE);
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Expertise\n> Sneak Attack (1d6)\n> Thieves' Cant" + "\n");
            }

            case 2 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Expertise\n> Sneak Attack (1d6)\n> Thieves' Cant\n> Cunning Action" + "\n");
            }

            case 3 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Expertise\n> Sneak Attack (2d6)\n> Thieves' Cant\n> Cunning Action\n> Roguish Archetype" + "\n");
            }

            case 4 -> {
                raiseRandomAttributes();
            }

            case 5 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Expertise\n> Sneak Attack (3d6)\n> Thieves' Cant\n> Cunning Action\n> Roguish Archetype\n> Uncanny Dodge" + "\n");
            }

            case 6 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Expertise\n> Sneak Attack (3d6)\n> Thieves' Cant\n> Cunning Action\n> Roguish Archetype\n> Uncanny Dodge\n> Expertise (second)" + "\n");
            }

            case 7 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Expertise\n> Sneak Attack (4d6)\n> Thieves' Cant\n> Cunning Action\n> Roguish Archetype\n> Uncanny Dodge\n> Expertise (second)\n> Evasion" + "\n");
            }

            case 8 -> {
                raiseRandomAttributes();
            }

            case 9 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Expertise\n> Sneak Attack (5d6)\n> Thieves' Cant\n> Cunning Action\n> Roguish Archetype\n> Uncanny Dodge\n> Expertise (second)\n> Evasion\n> Roguish Archetype feature" + "\n");
            }

            case 10 -> {
                raiseRandomAttributes();
            }

            case 11 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Expertise\n> Sneak Attack (6d6)\n> Thieves' Cant\n> Cunning Action\n> Roguish Archetype\n> Uncanny Dodge\n> Expertise (second)\n> Evasion\n> Roguish Archetype feature\n> Reliable Talent" + "\n");
            }

            case 12 -> {
                raiseRandomAttributes();
            }

            case 13 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Expertise\n> Sneak Attack (7d6)\n> Thieves' Cant\n> Cunning Action\n> Roguish Archetype\n> Uncanny Dodge\n> Expertise (second)\n> Evasion\n> Roguish Archetype feature\n> Reliable Talent\n> Roguish Archetype feature (second)" + "\n");
            }

            case 14 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Expertise\n> Sneak Attack (7d6)\n> Thieves' Cant\n> Cunning Action\n> Roguish Archetype\n> Uncanny Dodge\n> Expertise (second)\n> Evasion\n> Roguish Archetype feature\n> Reliable Talent\n> Roguish Archetype feature (second)\n> Blindsense" + "\n");
            }

            case 15 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Expertise\n> Sneak Attack (8d6)\n> Thieves' Cant\n> Cunning Action\n> Roguish Archetype\n> Uncanny Dodge\n> Expertise (second)\n> Evasion\n> Roguish Archetype feature\n> Reliable Talent\n> Roguish Archetype feature (second)\n> Blindsense\n> Slippery Mind" + "\n");
            }

            case 16 -> {
                raiseRandomAttributes();
            }

            case 17 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Expertise\n> Sneak Attack (9d6)\n> Thieves' Cant\n> Cunning Action\n> Roguish Archetype\n> Uncanny Dodge\n> Expertise (second)\n> Evasion\n> Roguish Archetype feature\n> Reliable Talent\n> Roguish Archetype feature (second)\n> Blindsense\n> Slippery Mind\n> Roguish Archetype feature (third)" + "\n");
            }

            case 18 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Expertise\n> Sneak Attack (9d6)\n> Thieves' Cant\n> Cunning Action\n> Roguish Archetype\n> Uncanny Dodge\n> Expertise (second)\n> Evasion\n> Roguish Archetype feature\n> Reliable Talent\n> Roguish Archetype feature (second)\n> Blindsense\n> Slippery Mind\n> Roguish Archetype feature (third)\n> Elusive" + "\n");
            }

            case 19 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Expertise\n> Sneak Attack (10d6)\n> Thieves' Cant\n> Cunning Action\n> Roguish Archetype\n> Uncanny Dodge\n> Expertise (second)\n> Evasion\n> Roguish Archetype feature\n> Reliable Talent\n> Roguish Archetype feature (second)\n> Blindsense\n> Slippery Mind\n> Roguish Archetype feature (third)\n> Elusive" + "\n");
                raiseRandomAttributes();
            }

            case 20 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Expertise\n> Sneak Attack (10d6)\n> Thieves' Cant\n> Cunning Action\n> Roguish Archetype\n> Uncanny Dodge\n> Expertise (second)\n> Evasion\n> Roguish Archetype feature\n> Reliable Talent\n> Roguish Archetype feature (second)\n> Blindsense\n> Slippery Mind\n> Roguish Archetype feature (third)\n> Elusive\n> Stroke of Luck" + "\n");
            }

            default -> throw new IllegalStateException("Unexpected value.");
        }
    }

    public void sorcererSelected(int level) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        switch (level) {

            case 1 -> {
                characterSheet.clearProficients();
                setAttributeProficiencies(coreRules.getClassAttributesProficiencies()[CoreRules.getInstance().SORCERER]);
                setBackground();
                setRandomSkillsProficiencies(coreRules.getSorcererSkills(),2);
                characterSheet.setHitDiceType(coreRules.SORCERER_DICE);
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Sorcerous Origin" + "\n");
            }

            case 2 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Sorcerous Origin\n> Font of Magic" + "\n");
            }

            case 3 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Sorcerous Origin\n> Font of Magic\n> Metamagic" + "\n");
            }

            case 4 -> {
                raiseRandomAttributes();
            }

            case 5 -> {}

            case 6 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Sorcerous Origin\n> Font of Magic\n> Metamagic\n> Sorcerous Origin feature" + "\n");
            }

            case 7 -> {}

            case 8 -> {
                raiseRandomAttributes();
            }

            case 9 -> {}

            case 10 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Sorcerous Origin\n> Font of Magic\n> Metamagic\n> Sorcerous Origin feature\n> Metamagic (second)" + "\n");
            }

            case 11 -> {}

            case 12 -> {
                raiseRandomAttributes();
            }

            case 13 -> {}

            case 14 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Sorcerous Origin\n> Font of Magic\n> Metamagic\n> Sorcerous Origin feature\n> Metamagic (second)\n> Sorcerous Origin feature (second)" + "\n");
            }

            case 15 -> {}

            case 16 -> {
                raiseRandomAttributes();
            }

            case 17 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Sorcerous Origin\n> Font of Magic\n> Metamagic\n> Sorcerous Origin feature\n> Metamagic (second)\n> Sorcerous Origin feature (second)\n> Metamagic (third)" + "\n");
            }

            case 18 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Sorcerous Origin\n> Font of Magic\n> Metamagic\n> Sorcerous Origin feature\n> Metamagic (second)\n> Sorcerous Origin feature (second)\n> Metamagic (third)\n> Sorcerous Origin feature (third)" + "\n");
            }

            case 19 -> {
                raiseRandomAttributes();
            }

            case 20 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Sorcerous Origin\n> Font of Magic\n> Metamagic\n> Sorcerous Origin feature\n> Metamagic (second)\n> Sorcerous Origin feature (second)\n> Metamagic (third)\n> Sorcerous Origin feature (third)\n> Sorcerous Restoration" + "\n");
            }

            default -> throw new IllegalStateException("Unexpected value.");
        }
    }

    public void warlockSelected(int level) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        switch (level) {

            case 1 -> {
                characterSheet.clearProficients();
                setAttributeProficiencies(coreRules.getClassAttributesProficiencies()[CoreRules.getInstance().WARLOCK]);
                setBackground();
                setRandomSkillsProficiencies(coreRules.getWarlockSkills(),2);
                characterSheet.setHitDiceType(coreRules.WARLOCK_DICE);
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Otherworldly Patron\n> Pact Magic" + "\n");
            }

            case 2 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Otherworldly Patron\n> Pact Magic\n> Eldritch Invocations" + "\n");
            }

            case 3 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Otherworldly Patron\n> Pact Magic\n> Eldritch Invocations\n> Pact Boon" + "\n");
            }

            case 4 -> {
                raiseRandomAttributes();
            }

            case 5 -> {}

            case 6 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Otherworldly Patron\n> Pact Magic\n> Eldritch Invocations\n> Pact Boon\n> Otherworldly Patron feature" + "\n");
            }

            case 7 -> {}

            case 8 -> {
                raiseRandomAttributes();
            }

            case 9 -> {}

            case 10 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Otherworldly Patron\n> Pact Magic\n> Eldritch Invocations\n> Pact Boon\n> Otherworldly Patron feature\n> Otherworldly Patron feature (second)" + "\n");
            }

            case 11 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Otherworldly Patron\n> Pact Magic\n> Eldritch Invocations\n> Pact Boon\n> Otherworldly Patron feature\n> Otherworldly Patron feature (second)\n> Mystic Arcanum (6th level)" + "\n");
            }

            case 12 -> {
                raiseRandomAttributes();
            }

            case 13 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Otherworldly Patron\n> Pact Magic\n> Eldritch Invocations\n> Pact Boon\n> Otherworldly Patron feature\n> Otherworldly Patron feature (second)\n> Mystic Arcanum (6th level)\n> Mystic Arcanum (7th level)" + "\n");
            }

            case 14 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Otherworldly Patron\n> Pact Magic\n> Eldritch Invocations\n> Pact Boon\n> Otherworldly Patron feature\n> Otherworldly Patron feature (second)\n> Mystic Arcanum (6th level)\n> Mystic Arcanum (7th level)\n> Otherworldly Patron feature (third)" + "\n");
            }

            case 15 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Otherworldly Patron\n> Pact Magic\n> Eldritch Invocations\n> Pact Boon\n> Otherworldly Patron feature\n> Otherworldly Patron feature (second)\n> Mystic Arcanum (6th level)\n> Mystic Arcanum (7th level)\n> Otherworldly Patron feature (third)\n> Mystic Arcanum (8th level)" + "\n");
            }

            case 16 -> {
                raiseRandomAttributes();
            }

            case 17 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Otherworldly Patron\n> Pact Magic\n> Eldritch Invocations\n> Pact Boon\n> Otherworldly Patron feature\n> Otherworldly Patron feature (second)\n> Mystic Arcanum (6th level)\n> Mystic Arcanum (7th level)\n> Otherworldly Patron feature (third)\n> Mystic Arcanum (8th level)\n> Mystic Arcanum (9th level)" + "\n");
            }

            case 18 -> {}

            case 19 -> {
                raiseRandomAttributes();
            }

            case 20 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Otherworldly Patron\n> Pact Magic\n> Eldritch Invocations\n> Pact Boon\n> Otherworldly Patron feature\n> Otherworldly Patron feature (second)\n> Mystic Arcanum (6th level)\n> Mystic Arcanum (7th level)\n> Otherworldly Patron feature (third)\n> Mystic Arcanum (8th level)\n> Mystic Arcanum (9th level)\n> Eldritch Master" + "\n");
            }

            default -> throw new IllegalStateException("Unexpected value.");
        }
    }

    public void wizardSelected(int level) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        switch (level) {

            case 1 -> {
                characterSheet.clearProficients();
                setAttributeProficiencies(coreRules.getClassAttributesProficiencies()[CoreRules.getInstance().WIZARD]);
                setBackground();
                setRandomSkillsProficiencies(coreRules.getWizardSkills(),2);
                characterSheet.setHitDiceType(coreRules.WIZARD_DICE);
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Arcane Recovery" + "\n");
            }

            case 2 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Arcane Recovery\n> Arcane Tradition" + "\n");
            }

            case 3 -> {}

            case 4 -> {
                raiseRandomAttributes();
            }

            case 5 -> {}

            case 6 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Arcane Recovery\n> Arcane Tradition\n> Arcane Tradition feature" + "\n");
            }

            case 7 -> {}

            case 8 -> {
                raiseRandomAttributes();
            }

            case 9 -> {}

            case 10 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Arcane Recovery\n> Arcane Tradition\n> Arcane Tradition feature\n> Arcane Tradition feature (second)" + "\n");
            }

            case 11 -> {}

            case 12 -> {
                raiseRandomAttributes();
            }

            case 13 -> {}

            case 14 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Arcane Recovery\n> Arcane Tradition\n> Arcane Tradition feature\n> Arcane Tradition feature (second)\n> Arcane Tradition feature (third)" + "\n");
            }

            case 15 -> {}

            case 16 -> {
                raiseRandomAttributes();
            }

            case 17 -> {}

            case 18 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Arcane Recovery\n> Arcane Tradition\n> Arcane Tradition feature\n> Arcane Tradition feature (second)\n> Arcane Tradition feature (third)\n> Spell Mastery" + "\n");
            }

            case 19 -> {
                raiseRandomAttributes();
            }

            case 20 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Spellcasting\n> Arcane Recovery\n> Arcane Tradition\n> Arcane Tradition feature\n> Arcane Tradition feature (second)\n> Arcane Tradition feature (third)\n> Spell Mastery\n> Signature Spell" + "\n");
            }

            default -> throw new IllegalStateException("Unexpected value.");
        }
    }
}
