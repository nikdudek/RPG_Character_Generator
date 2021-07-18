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
    private String name;
    private int race;
    private int mainClass;
    private int subClass;
    private int level = coreRules.STARTING_LEVEL;
    private int background;
    private int alignment;

    //Data from CombatValuesPanel
    private int initiative = 0;
    private int speed = 0;
    private int proficiency = coreRules.STARTING_PROFICIENCY;
    private int hitDiceCount = coreRules.STARTING_LEVEL;
    private int hitDiceType = 0;
    private int hitPointsMax = 0;
    private int armorClass = coreRules.BASE_AC;

    //Data from AttributePanel
    private int strength = 0;
    private int strengthMod = 0;
    private int strengthST = 0;
    private int dexterity = 0;
    private int dexterityMod = 0;
    private int dexterityST = 0;
    private int condition = 0;
    private int conditionMod = 0;
    private int conditionST = 0;
    private int intelligence = 0;
    private int intelligenceMod = 0;
    private int intelligenceST = 0;
    private int wisdom = 0;
    private int wisdomMod = 0;
    private int wisdomST = 0;
    private int charisma = 0;
    private int charismaMod = 0;
    private int charismaST = 0;

    //Proficients for ST/Skills
    private boolean strengthProficient = false;
    private boolean dexterityProficient = false;
    private boolean conditionProficient = false;
    private boolean intelligenceProficient = false;
    private boolean wisdomProficient = false;
    private boolean charismaProficient = false;
    //---------------------------------
    private boolean[] skillsProficient = {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};

    //Data from SkillsPanel
    private int[] skills = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

    //List of Feats
    private List<String> feats;


        //METHODS:
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public int getStrengthMod() {
        return strengthMod;
    }

    public void setStrengthMod(int strengthMod) {
        this.strengthMod = strengthMod;
    }

    public int getStrengthST() {
        return strengthST;
    }

    public void setStrengthST(int strengthST) {
        this.strengthST = strengthST;
    }

    public int getDexterityMod() {
        return dexterityMod;
    }

    public void setDexterityMod(int dexterityMod) {
        this.dexterityMod = dexterityMod;
    }

    public int getDexterityST() {
        return dexterityST;
    }

    public void setDexterityST(int dexterityST) {
        this.dexterityST = dexterityST;
    }

    public int getConditionMod() {
        return conditionMod;
    }

    public void setConditionMod(int conditionMod) {
        this.conditionMod = conditionMod;
    }

    public int getConditionST() {
        return conditionST;
    }

    public void setConditionST(int conditionST) {
        this.conditionST = conditionST;
    }

    public int getIntelligenceMod() {
        return intelligenceMod;
    }

    public void setIntelligenceMod(int intelligenceMod) {
        this.intelligenceMod = intelligenceMod;
    }

    public int getIntelligenceST() {
        return intelligenceST;
    }

    public void setIntelligenceST(int intelligenceST) {
        this.intelligenceST = intelligenceST;
    }

    public int getWisdomMod() {
        return wisdomMod;
    }

    public void setWisdomMod(int wisdomMod) {
        this.wisdomMod = wisdomMod;
    }

    public int getWisdomST() {
        return wisdomST;
    }

    public void setWisdomST(int wisdomST) {
        this.wisdomST = wisdomST;
    }

    public int getCharismaMod() {
        return charismaMod;
    }

    public void setCharismaMod(int charismaMod) {
        this.charismaMod = charismaMod;
    }

    public int getCharismaST() {
        return charismaST;
    }

    public void setCharismaST(int charismaST) {
        this.charismaST = charismaST;
    }

    public boolean isStrengthProficient() {
        return strengthProficient;
    }

    public void setStrengthProficient(boolean strengthProficient) {
        this.strengthProficient = strengthProficient;
    }

    public boolean isDexterityProficient() {
        return dexterityProficient;
    }

    public void setDexterityProficient(boolean dexterityProficient) {
        this.dexterityProficient = dexterityProficient;
    }

    public boolean isConditionProficient() {
        return conditionProficient;
    }

    public void setConditionProficient(boolean conditionProficient) {
        this.conditionProficient = conditionProficient;
    }

    public boolean isIntelligenceProficient() {
        return intelligenceProficient;
    }

    public void setIntelligenceProficient(boolean intelligenceProficient) {
        this.intelligenceProficient = intelligenceProficient;
    }

    public boolean isWisdomProficient() {
        return wisdomProficient;
    }

    public void setWisdomProficient(boolean wisdomProficient) {
        this.wisdomProficient = wisdomProficient;
    }

    public boolean isCharismaProficient() {
        return charismaProficient;
    }

    public void setCharismaProficient(boolean charismaProficient) {
        this.charismaProficient = charismaProficient;
    }

    public void clearProficients() {

        CoreRules coreRules = CoreRules.getInstance();
        setStrengthProficient(false);
        setDexterityProficient(false);
        setConditionProficient(false);
        setIntelligenceProficient(false);
        setWisdomProficient(false);
        setCharismaProficient(false);
        //-----
        IntStream.range(0, coreRules.ALL_SKILLS).forEach(i -> skillsProficient[i] = false);
    }

    public void changeRace(int race) {
        setRace(race);
        System.out.println(race);
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
}
