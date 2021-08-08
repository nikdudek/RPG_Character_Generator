package com.app.model;

import java.util.List;
import java.util.stream.IntStream;

public class CharacterSheet {

    //SINGLETON
    private CharacterSheet() {
        System.out.println("Character sheet created.");
    }

    private static CharacterSheet instance = null;
    public static synchronized CharacterSheet getInstance() {
        if(instance == null)
            instance = new CharacterSheet();
        return instance;
    }

    CoreRules coreRules = CoreRules.getInstance();

    //ATTRIBUTES:
        //Data from InfoPanel
    private int race = coreRules.STARTING_DEFAULT;
    private int mainClass = coreRules.STARTING_DEFAULT;
    private int subClass = coreRules.STARTING_DEFAULT;
    private int level = coreRules.STARTING_DEFAULT;
    private int background = coreRules.STARTING_DEFAULT;
    private int alignment = coreRules.STARTING_DEFAULT;

        //Data from CombatValuesPanel
    private int initiative = coreRules.STARTING_DEFAULT;
    private int speed = 25;
    private int proficiency = coreRules.STARTING_PROFICIENCY;
    private int hitDiceCount = 1;
    private int hitDiceType = coreRules.BARBARIAN_DICE;
    private int hitPointsMax = coreRules.BARBARIAN_DICE;
    private int armorClass = coreRules.BASE_AC;

        //Data from AttributePanel
    private int[] attributes = {0,0,0,0,0,0};
    private int[] attributesMod = {0,0,0,0,0,0};
    private int[] attributesST = {0,0,0,0,0,0};

//        //Proficients for ST/Skills
    private boolean[] attributesProficient = {false,false,false,false,false,false};
    private boolean[] skillsProficient = {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};

    //Data from SkillsPanel
    private int[] skills = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

    //List of Feats
    private List<String> featsList;

    //METHODS:

    public int getRace() {
        return race;
    }

    public void setRace(int race) {
        this.race = race;
    }

    public int getMainClass() {
        return mainClass;
    }

    public void setMainClass(int mainClass) {
        this.mainClass = mainClass;
    }

    public int getSubClass() {
        return subClass;
    }

    public void setSubClass(int subClass) {
        this.subClass = subClass;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public int getAlignment() {
        return alignment;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getProficiency() {
        return proficiency;
    }

    public void setProficiency(int proficiency) {
        this.proficiency = proficiency;
    }

    public int getHitDiceCount() {
        return hitDiceCount;
    }

    public void setHitDiceCount(int hitDiceCount) {
        this.hitDiceCount = hitDiceCount;
    }

    public int getHitDiceType() {
        return hitDiceType;
    }

    public void setHitDiceType(int hitDiceType) {
        this.hitDiceType = hitDiceType;
    }

    public int getHitPointsMax() {
        return hitPointsMax;
    }

    public void setHitPointsMax(int hitPointsMax) {
        this.hitPointsMax = hitPointsMax;
    }

    public int getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(int armorClass) {
        this.armorClass = armorClass;
    }

    public void clearProficients() {

        CoreRules coreRules = CoreRules.getInstance();
        IntStream.range(0, coreRules.ALL_ATTRIBUTES).forEach(i -> attributesProficient[i] = false);
        IntStream.range(0, coreRules.ALL_SKILLS).forEach(i -> skillsProficient[i] = false);
    }

    public void changeRace(int race) {
        setRace(race);
    }

    public int[] getSkills() {
        return skills;
    }

    public void setSkills(int[] skills) {
        this.skills = skills;
    }

    public boolean[] getSkillsProficient() {
        return skillsProficient;
    }

    public void setSkillsProficient(boolean[] skillsProficient) {
        this.skillsProficient = skillsProficient;
    }

    public List<String> getFeatsList() {
        return featsList;
    }

    public void setFeatsList(List<String> featsList) {
        this.featsList = featsList;
    }

    public int[] getAttributes() {
        return attributes;
    }

    public void setAttributes(int[] attributes) {
        this.attributes = attributes;
    }

    public int[] getAttributesMod() {
        return attributesMod;
    }

    public void setAttributesMod(int[] attributesMod) {
        this.attributesMod = attributesMod;
    }

    public int[] getAttributesST() {
        return attributesST;
    }

    public void setAttributesST(int[] attributesST) {
        this.attributesST = attributesST;
    }

    public boolean[] getAttributesProficient() {
        return attributesProficient;
    }

    public void setAttributesProficient(boolean[] attributesProficient) {
        this.attributesProficient = attributesProficient;
    }
}
