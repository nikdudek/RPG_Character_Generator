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

    public void clearAttributeProficiencies() {
        CharacterSheet characterSheet = CharacterSheet.getInstance();

        boolean[] attributesProficient = characterSheet.getAttributesProficient();

        IntStream.range(0,attributesProficient.length).forEach(i -> attributesProficient[i] = false);

        characterSheet.setAttributesProficient(attributesProficient);
    }

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
        if (characterSheet.getRace() != race) {
            characterSheet.clearProficients();
            characterSheet.changeRace(race);
            rollAttributes();
            readFeats();

            int[] attributes = characterSheet.getAttributes();

            if(race == coreRules.DRAGONBORN) {
                attributes[coreRules.STRENGTH] += 2;
                attributes[coreRules.CHARISMA] += 1;
                characterSheet.setSpeed(30);
            }
            else if(race == coreRules.DWARF) {
                attributes[coreRules.CONSTITUTION] += 2;
                characterSheet.setSpeed(25);
            }
            else if(race == coreRules.ELF) {
                attributes[coreRules.DEXTERITY] += 2;
                characterSheet.setSpeed(30);
                characterSheet.getSkillsProficient()[coreRules.PERCEPTION] = true;
            }
            else if(race == coreRules.GNOME) {
                attributes[coreRules.INTELLIGENCE] += 2;
                characterSheet.setSpeed(25);
                characterSheet.getAttributesProficient()[coreRules.INTELLIGENCE] = true;
                characterSheet.getAttributesProficient()[coreRules.WISDOM] = true;
                characterSheet.getAttributesProficient()[coreRules.CHARISMA] = true;
            }
            else if(race == coreRules.HALF_ELF) {
                attributes[coreRules.CHARISMA] += 2;
                attributes[ThreadLocalRandom.current().nextInt(coreRules.STARTING_DEFAULT,coreRules.ALL_ATTRIBUTES-1)]++;
                attributes[ThreadLocalRandom.current().nextInt(coreRules.STARTING_DEFAULT,coreRules.ALL_ATTRIBUTES-1)]++;
                characterSheet.setSpeed(30);
                characterSheet.getSkillsProficient()[ThreadLocalRandom.current().nextInt(coreRules.STARTING_DEFAULT,coreRules.ALL_SKILLS)] = true;
                characterSheet.getSkillsProficient()[ThreadLocalRandom.current().nextInt(coreRules.STARTING_DEFAULT,coreRules.ALL_SKILLS)] = true;
            }
            else if(race == coreRules.HALFLING) {
                attributes[coreRules.DEXTERITY] += 2;
                characterSheet.setSpeed(25);
            }
            else if(race == coreRules.HALF_ORC) {
                attributes[coreRules.STRENGTH] += 2;
                attributes[coreRules.CONSTITUTION] += 1;
                characterSheet.setSpeed(30);
                characterSheet.getSkillsProficient()[coreRules.INTIMIDATION] = true;
            }
            else if(race == coreRules.HUMAN) {
                IntStream.range(0,coreRules.ALL_ATTRIBUTES).forEach(i -> attributes[i] += 1);
                characterSheet.setSpeed(30);
            }
            else if(race == coreRules.TIEFLING) {
                attributes[coreRules.INTELLIGENCE] += 1;
                attributes[coreRules.CHARISMA] += 2;
                characterSheet.setSpeed(30);
            }

            characterSheet.setAttributes(attributes);
            refreshCombatValues();
        }
    }

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
        boolean arr[] = characterSheet.getSkillsProficient();

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

        if(infoPanel.getClassBox().getSelectedIndex() != characterSheet.getMainClass()) {
            CoreRules coreRules = CoreRules.getInstance();

            characterSheet.clearProficients();
            int targetLevel = Integer.parseInt(infoPanel.getLevelBox().getSelectedItem().toString());
            switch (infoPanel.getClassBox().getSelectedIndex()) {
                case 0 -> {
                    System.out.println("Barbarian");
                    characterSheet.setMainClass(coreRules.BARBARIAN);
                    infoPanel.setSubClassBoxModel(coreRules.BARBARIAN);
                    for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                        barbarianSelected(i);
                }
                case 1 -> {
                    System.out.println("Bard");
                    characterSheet.setMainClass(coreRules.BARD);
                    infoPanel.setSubClassBoxModel(coreRules.BARD);
                    for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                        bardSelected(i);
                    }
                case 2 -> {
                    System.out.println("Cleric");
                    characterSheet.setMainClass(coreRules.CLERIC);
                    infoPanel.setSubClassBoxModel(coreRules.CLERIC);
                    for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                        clericSelected(i);
                }
                case 3 -> {
                    System.out.println("Druid");
                    characterSheet.setMainClass(coreRules.DRUID);
                    infoPanel.setSubClassBoxModel(coreRules.DRUID);
                    for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                        druidSelected(i);
                }
                case 4 -> {
                    System.out.println("Fighter");
                    infoPanel.setSubClassBoxModel(coreRules.FIGHTER);
                    for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                        fighterSelected(i);
                }
                case 5 -> {
                    System.out.println("Monk");
                    characterSheet.setMainClass(coreRules.MONK);
                    infoPanel.setSubClassBoxModel(coreRules.MONK);
                    for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                        monkSelected(i);
                }
                case 6 -> {
                    System.out.println("Paladin");
                    characterSheet.setMainClass(coreRules.PALADIN);
                    infoPanel.setSubClassBoxModel(coreRules.PALADIN);
                    for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                        paladinSelected(i);
                }
                case 7 -> {
                    System.out.println("Ranger");
                    characterSheet.setMainClass(coreRules.RANGER);
                    infoPanel.setSubClassBoxModel(coreRules.RANGER);
                    for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                        rangerSelected(i);
                }
                case 8 -> {
                    System.out.println("Rogue");
                    characterSheet.setMainClass(coreRules.ROGUE);
                    infoPanel.setSubClassBoxModel(coreRules.ROGUE);
                    for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                        rogueSelected(i);
                }
                case 9 -> {
                    System.out.println("Sorcerer");
                    characterSheet.setMainClass(coreRules.SORCERER);
                    infoPanel.setSubClassBoxModel(coreRules.SORCERER);
                    for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                        sorcererSelected(i);
                }
                case 10 -> {
                    System.out.println("Warlock");
                    characterSheet.setMainClass(coreRules.WARLOCK);
                    infoPanel.setSubClassBoxModel(coreRules.WARLOCK);
                    for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                        warlockSelected(i);
                }
                case 11 -> {
                    System.out.println("Wizard");
                    characterSheet.setMainClass(coreRules.WIZARD);
                    infoPanel.setSubClassBoxModel(coreRules.WIZARD);
                    for(int i = coreRules.STARTING_LEVEL ; i <= targetLevel ; i++)
                        wizardSelected(i);
                }
                default -> throw new IllegalStateException("Unexpected value.");
            }
            refreshCombatValues();
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

        if (characterSheet.getLevel() != targetLevel) {
            if (characterSheet.getLevel() > targetLevel) {
                characterSheet.clearProficients(); //NEED TO MAKE THEM AGAIN
                rollAttributes();
                    //clear feats, etc.
                raiseLevel(coreRules.STARTING_LEVEL, targetLevel);
            }
            else {
                raiseLevel(characterSheet.getLevel() + 1, targetLevel);
            }

            characterSheet.setLevel(targetLevel);
            refreshCombatValues();
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

        //refreshCombatValues();
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
                characterSheet.setHitDiceType(coreRules.BARBARIAN_DICE);
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense" + "\n");
                readFeats();
                refreshCombatValues();
            }

            case 2 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense\n> Reckless Attack\n> Danger Sense" + "\n");
                readFeats();
                refreshCombatValues();
            }

            case 3 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense\n> Reckless Attack\n> Danger Sense\n> Primal Path" + "\n");
                readFeats();
                refreshCombatValues();
            }

            case 4 -> {
                raiseRandomAttributes();
                refreshCombatValues();
            }

            case 5 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense\n> Reckless Attack\n> Danger Sense\n> Primal Path\n> Extra Attack\n> Fast Movement" + "\n");
                readFeats();
                refreshCombatValues();
            }

            case 6 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense\n> Reckless Attack\n> Danger Sense\n> Primal Path\n> Extra Attack\n> Fast Movement\n> Path feature" + "\n");
                readFeats();
                refreshCombatValues();
            }

            case 7 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense\n> Reckless Attack\n> Danger Sense\n> Primal Path\n> Extra Attack\n> Fast Movement\n> Path feature\n> Feral Instinct" + "\n");
                readFeats();
                refreshCombatValues();
            }

            case 8 -> {
                raiseRandomAttributes();
                refreshCombatValues();
            }

            case 9 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense\n> Reckless Attack\n> Danger Sense\n> Primal Path\n> Extra Attack\n> Fast Movement\n> Path feature\n> Feral Instinct\n> Brutal Critical (1 die)" + "\n");
                readFeats();
                refreshCombatValues();
            }

            case 10 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense\n> Reckless Attack\n> Danger Sense\n> Primal Path\n> Extra Attack\n> Fast Movement\n> Path feature\n> Feral Instinct\n> Brutal Critical (1 die)\n> Path feature" + "\n");
                readFeats();
                refreshCombatValues();
            }

            case 11 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense\n> Reckless Attack\n> Danger Sense\n> Primal Path\n> Extra Attack\n> Fast Movement\n> Path feature\n> Feral Instinct\n> Brutal Critical (1 die)\n> Path feature\n> Relentless Rage" + "\n");
                readFeats();
                refreshCombatValues();
            }

            case 12 -> {
                raiseRandomAttributes();
                refreshCombatValues();
            }

            case 13 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense\n> Reckless Attack\n> Danger Sense\n> Primal Path\n> Extra Attack\n> Fast Movement\n> Path feature\n> Feral Instinct\n> Brutal Critical (2 dice)\n> Path feature\n> Relentless Rage" + "\n");
                readFeats();
                refreshCombatValues();
            }

            case 14 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense\n> Reckless Attack\n> Danger Sense\n> Primal Path\n> Extra Attack\n> Fast Movement\n> Path feature\n> Feral Instinct\n> Brutal Critical (2 dice)\n> Path feature\n> Relentless Rage\n> Path feature" + "\n");
                readFeats();
                refreshCombatValues();
            }

            case 15 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense\n> Reckless Attack\n> Danger Sense\n> Primal Path\n> Extra Attack\n> Fast Movement\n> Path feature\n> Feral Instinct\n> Brutal Critical (2 dice)\n> Path feature\n> Relentless Rage\n> Path feature\n> Persistent Rage" + "\n");
                readFeats();
                refreshCombatValues();
            }

            case 16 -> {
                raiseRandomAttributes();
                refreshCombatValues();
            }

            case 17 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense\n> Reckless Attack\n> Danger Sense\n> Primal Path\n> Extra Attack\n> Fast Movement\n> Path feature\n> Feral Instinct\n> Brutal Critical (3 dice)\n> Path feature\n> Relentless Rage\n> Path feature\n> Persistent Rage" + "\n");
                readFeats();
                refreshCombatValues();
            }

            case 18 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense\n> Reckless Attack\n> Danger Sense\n> Primal Path\n> Extra Attack\n> Fast Movement\n> Path feature\n> Feral Instinct\n> Brutal Critical (3 dice)\n> Path feature\n> Relentless Rage\n> Path feature\n> Persistent Rage\n> Indomitable Might" + "\n");
                readFeats();
                refreshCombatValues();
            }

            case 19 -> {
                raiseRandomAttributes();
                refreshCombatValues();
            }

            case 20 -> {
                characterSheet.setClassFeats(coreRules.getClasses()[characterSheet.getMainClass()] + ":\n" + "> Rage\n> Unarmored Defense\n> Reckless Attack\n> Danger Sense\n> Primal Path\n> Extra Attack\n> Fast Movement\n> Path feature\n> Feral Instinct\n> Brutal Critical (3 dice)\n> Path feature\n> Relentless Rage\n> Path feature\n> Persistent Rage\n> Indomitable Might\n> Primal Champion" + "\n");
                readFeats();
                refreshCombatValues();
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

                characterSheet.setHitDiceType(coreRules.BARD_DICE);

                refreshCombatValues();
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

    public void clericSelected(int level) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        switch (level) {

            case 1 -> {

                characterSheet.clearProficients();
                characterSheet.setHitDiceType(coreRules.CLERIC_DICE);

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

    public void druidSelected(int level) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        switch (level) {

            case 1 -> {

                characterSheet.clearProficients();
                characterSheet.setHitDiceType(coreRules.DRUID_DICE);

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

    public void fighterSelected(int level) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        switch (level) {

            case 1 -> {

                characterSheet.clearProficients();
                characterSheet.setHitDiceType(coreRules.FIGHTER_DICE);

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

    public void monkSelected(int level) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        switch (level) {

            case 1 -> {

                characterSheet.clearProficients();
                characterSheet.setHitDiceType(coreRules.MONK_DICE);

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

    public void paladinSelected(int level) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        switch (level) {

            case 1 -> {

                characterSheet.clearProficients();
                characterSheet.setHitDiceType(coreRules.PALADIN_DICE);

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

    public void rangerSelected(int level) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        switch (level) {

            case 1 -> {

                characterSheet.clearProficients();
                characterSheet.setHitDiceType(coreRules.RANGER_DICE);

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

    public void rogueSelected(int level) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        switch (level) {

            case 1 -> {

                characterSheet.clearProficients();
                characterSheet.setHitDiceType(coreRules.ROGUE_DICE);

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

    public void sorcererSelected(int level) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        switch (level) {

            case 1 -> {

                characterSheet.clearProficients();
                characterSheet.setHitDiceType(coreRules.SORCERER_DICE);

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

    public void warlockSelected(int level) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        switch (level) {

            case 1 -> {

                characterSheet.clearProficients();
                characterSheet.setHitDiceType(coreRules.WARLOCK_DICE);

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

    public void wizardSelected(int level) {

        CharacterSheet characterSheet = CharacterSheet.getInstance();
        CoreRules coreRules = CoreRules.getInstance();

        switch (level) {

            case 1 -> {

                characterSheet.clearProficients();
                characterSheet.setHitDiceType(coreRules.WIZARD_DICE);

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
