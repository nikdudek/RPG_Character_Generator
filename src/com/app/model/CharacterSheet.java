package com.app.model;

import java.util.HashMap;
import java.util.stream.IntStream;

public class CharacterSheet {

    //FINALS:
    public final int ALL_SKILLS = 18;


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


    //ATTRIBUTES:
    //Data from InfoPanel
    private String name = "...";
    private String race = "...";
    private String mainClass = "...";
    private String subClass = "...";
    private int level = 1;
    private String background = "...";
    private String alignment = "...";

    //Data from CombatValuesPanel
    private int initiative = 0;
    private int speed = 0;
    private int proficiency = 0;
    private int hitDiceCount = 0;
    private int hitDiceType = 0;
    //private int hitPointsTotal;
    private int hitPointsMax = 0;
    private int armorClass = 10;

    //Data from AttributePanel
    private int strength = 0;
    private int dexterity = 0;
    private int condition = 0;
    private int intelligence = 0;
    private int wisdom = 0;
    private int charisma = 0;

    //Proficients for ST/Skills
    private boolean strengthProficient = false;
    private boolean dexterityProficient = false;
    private boolean conditionProficient = false;
    private boolean intelligenceProficient = false;
    private boolean wisdomProficient = false;
    private boolean charismaProficient = false;
    //---------------------------------
    private boolean[] skillsProficient = {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};
//    private boolean acrobaticsProficient = false;
//    private boolean animalHandlingProficient = false;
//    private boolean arcanaProficient = false;
//    private boolean athleticsProficient = false;
//    private boolean deceptionProficient = false;
//    private boolean historyProficient = false;
//    private boolean insightProficient = false;
//    private boolean intimidationProficient = false;
//    private boolean investigationProficient = false;
//    private boolean medicineProficient = false;
//    private boolean natureProficient = false;
//    private boolean perceptionProficient = false;
//    private boolean performanceProficient = false;
//    private boolean persuasionProficient = false;
//    private boolean religionProficient = false;
//    private boolean sleightOfHandProficient = false;
//    private boolean stealthProficient = false;
//    private boolean survivalProficient = false;

    //Data from SkillsPanel
    private int[] skills = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
//    private int acrobatics = 0;
//    private int animalHandling = 0;
//    private int arcana = 0;
//    private int athletics = 0;
//    private int deception = 0;
//    private int history = 0;
//    private int insight = 0;
//    private int intimidation = 0;
//    private int investigation = 0;
//    private int medicine = 0;
//    private int nature = 0;
//    private int perception = 0;
//    private int performance = 0;
//    private int persuasion = 0;
//    private int religion = 0;
//    private int sleightOfHand = 0;
//    private int stealth = 0;
//    private int survival = 0;


        //METHODS:
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getMainClass() {
        return mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public String getSubClass() {
        return subClass;
    }

    public void setSubClass(String subClass) {
        this.subClass = subClass;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void calculateProficiency() {
        setProficiency((getLevel() + 7) / 4);
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
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

//    public int getHitPointsTotal() {
//        return hitPointsTotal;
//    }

//    public void setHitPointsTotal(int hitPointsTotal) {
//        this.hitPointsTotal = hitPointsTotal;
//    }

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

//    public int getAcrobatics() {
//        return acrobatics;
//    }
//
//    public void setAcrobatics(int acrobatics) {
//        this.acrobatics = acrobatics;
//    }
//
//    public int getAnimalHandling() {
//        return animalHandling;
//    }
//
//    public void setAnimalHandling(int animalHandling) {
//        this.animalHandling = animalHandling;
//    }
//
//    public int getArcana() {
//        return arcana;
//    }
//
//    public void setArcana(int arcana) {
//        this.arcana = arcana;
//    }
//
//    public int getAthletics() {
//        return athletics;
//    }
//
//    public void setAthletics(int athletics) {
//        this.athletics = athletics;
//    }
//
//    public int getDeception() {
//        return deception;
//    }
//
//    public void setDeception(int deception) {
//        this.deception = deception;
//    }
//
//    public int getHistory() {
//        return history;
//    }
//
//    public void setHistory(int history) {
//        this.history = history;
//    }
//
//    public int getInsight() {
//        return insight;
//    }
//
//    public void setInsight(int insight) {
//        this.insight = insight;
//    }
//
//    public int getIntimidation() {
//        return intimidation;
//    }
//
//    public void setIntimidation(int intimidation) {
//        this.intimidation = intimidation;
//    }
//
//    public int getInvestigation() {
//        return investigation;
//    }
//
//    public void setInvestigation(int investigation) {
//        this.investigation = investigation;
//    }
//
//    public int getMedicine() {
//        return medicine;
//    }
//
//    public void setMedicine(int medicine) {
//        this.medicine = medicine;
//    }
//
//    public int getNature() {
//        return nature;
//    }
//
//    public void setNature(int nature) {
//        this.nature = nature;
//    }
//
//    public int getPerception() {
//        return perception;
//    }
//
//    public void setPerception(int perception) {
//        this.perception = perception;
//    }
//
//    public int getPerformance() {
//        return performance;
//    }
//
//    public void setPerformance(int performance) {
//        this.performance = performance;
//    }
//
//    public int getPersuasion() {
//        return persuasion;
//    }
//
//    public void setPersuasion(int persuasion) {
//        this.persuasion = persuasion;
//    }
//
//    public int getReligion() {
//        return religion;
//    }
//
//    public void setReligion(int religion) {
//        this.religion = religion;
//    }
//
//    public int getSleightOfHand() {
//        return sleightOfHand;
//    }
//
//    public void setSleightOfHand(int sleightOfHand) {
//        this.sleightOfHand = sleightOfHand;
//    }
//
//    public int getStealth() {
//        return stealth;
//    }
//
//    public void setStealth(int stealth) {
//        this.stealth = stealth;
//    }
//
//    public int getSurvival() {
//        return survival;
//    }
//
//    public void setSurvival(int survival) {
//        this.survival = survival;
//    }

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

//    public boolean isAcrobaticsProficient() {
//        return acrobaticsProficient;
//    }
//
//    public void setAcrobaticsProficient(boolean acrobaticsProficient) {
//        this.acrobaticsProficient = acrobaticsProficient;
//    }
//
//    public boolean isAnimalHandlingProficient() {
//        return animalHandlingProficient;
//    }
//
//    public void setAnimalHandlingProficient(boolean animalHandlingProficient) {
//        this.animalHandlingProficient = animalHandlingProficient;
//    }
//
//    public boolean isArcanaProficient() {
//        return arcanaProficient;
//    }
//
//    public void setArcanaProficient(boolean arcanaProficient) {
//        this.arcanaProficient = arcanaProficient;
//    }
//
//    public boolean isAthleticsProficient() {
//        return athleticsProficient;
//    }
//
//    public void setAthleticsProficient(boolean athleticsProficient) {
//        this.athleticsProficient = athleticsProficient;
//    }
//
//    public boolean isDeceptionProficient() {
//        return deceptionProficient;
//    }
//
//    public void setDeceptionProficient(boolean deceptionProficient) {
//        this.deceptionProficient = deceptionProficient;
//    }
//
//    public boolean isHistoryProficient() {
//        return historyProficient;
//    }
//
//    public void setHistoryProficient(boolean historyProficient) {
//        this.historyProficient = historyProficient;
//    }
//
//    public boolean isInsightProficient() {
//        return insightProficient;
//    }
//
//    public void setInsightProficient(boolean insightProficient) {
//        this.insightProficient = insightProficient;
//    }
//
//    public boolean isIntimidationProficient() {
//        return intimidationProficient;
//    }
//
//    public void setIntimidationProficient(boolean intimidationProficient) {
//        this.intimidationProficient = intimidationProficient;
//    }
//
//    public boolean isInvestigationProficient() {
//        return investigationProficient;
//    }
//
//    public void setInvestigationProficient(boolean investigationProficient) {
//        this.investigationProficient = investigationProficient;
//    }
//
//    public boolean isMedicineProficient() {
//        return medicineProficient;
//    }
//
//    public void setMedicineProficient(boolean medicineProficient) {
//        this.medicineProficient = medicineProficient;
//    }
//
//    public boolean isNatureProficient() {
//        return natureProficient;
//    }
//
//    public void setNatureProficient(boolean natureProficient) {
//        this.natureProficient = natureProficient;
//    }
//
//    public boolean isPerceptionProficient() {
//        return perceptionProficient;
//    }
//
//    public void setPerceptionProficient(boolean perceptionProficient) {
//        this.perceptionProficient = perceptionProficient;
//    }
//
//    public boolean isPerformanceProficient() {
//        return performanceProficient;
//    }
//
//    public void setPerformanceProficient(boolean performanceProficient) {
//        this.performanceProficient = performanceProficient;
//    }
//
//    public boolean isPersuasionProficient() {
//        return persuasionProficient;
//    }
//
//    public void setPersuasionProficient(boolean persuasionProficient) {
//        this.persuasionProficient = persuasionProficient;
//    }
//
//    public boolean isReligionProficient() {
//        return religionProficient;
//    }
//
//    public void setReligionProficient(boolean religionProficient) {
//        this.religionProficient = religionProficient;
//    }
//
//    public boolean isSleightOfHandProficient() {
//        return sleightOfHandProficient;
//    }
//
//    public void setSleightOfHandProficient(boolean sleightOfHandProficient) {
//        this.sleightOfHandProficient = sleightOfHandProficient;
//    }
//
//    public boolean isStealthProficient() {
//        return stealthProficient;
//    }
//
//    public void setStealthProficient(boolean stealthProficient) {
//        this.stealthProficient = stealthProficient;
//    }
//
//    public boolean isSurvivalProficient() {
//        return survivalProficient;
//    }
//
//    public void setSurvivalProficient(boolean survivalProficient) {
//        this.survivalProficient = survivalProficient;
//    }

    public void clearProficients() {
        setStrengthProficient(false);
        setDexterityProficient(false);
        setConditionProficient(false);
        setIntelligenceProficient(false);
        setWisdomProficient(false);
        setCharismaProficient(false);
        //-----
//        setAcrobaticsProficient(false);
//        setAnimalHandlingProficient(false);
//        setArcanaProficient(false);
//        setAthleticsProficient(false);
//        setDeceptionProficient(false);
//        setHistoryProficient(false);
//        setInsightProficient(false);
//        setIntimidationProficient(false);
//        setInvestigationProficient(false);
//        setMedicineProficient(false);
//        setNatureProficient(false);
//        setPerceptionProficient(false);
//        setPerformanceProficient(false);
//        setPersuasionProficient(false);
//        setReligionProficient(false);
//        setSleightOfHandProficient(false);
//        setStealthProficient(false);
//        setSurvivalProficient(false);
        IntStream.range(0, ALL_SKILLS).forEach(i -> skillsProficient[i] = false);
    }

    public void changeRace(String race) {
        setRace(race);
        System.out.println(race);
    }
}
