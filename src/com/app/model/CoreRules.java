package com.app.model;

import java.util.HashMap;

public class CoreRules {

//    HashMap<String,Integer> skillsMap = new HashMap<String,Integer>();
//        skillsMap.put("Acrobatics",0);
//        skillsMap.put("Animal Handling",1);
//        skillsMap.put("Arcana",2);
//        skillsMap.put("Athletics",3);
//        skillsMap.put("Deception",4);
//        skillsMap.put("History",5);
//        skillsMap.put("Insight",6);
//        skillsMap.put("Intimidation",7);
//        skillsMap.put("Investigation",8);
//        skillsMap.put("Medicine",9);
//        skillsMap.put("Nature",10);
//        skillsMap.put("Perception",11);
//        skillsMap.put("Performance",12);
//        skillsMap.put("Persuasion",13);
//        skillsMap.put("Religion",14);
//        skillsMap.put("Sleight of Hand",15);
//        skillsMap.put("Stealth",16);
//        skillsMap.put("Survival",17);

//FINALS:
    //RACES
    public final int DRAGONBORN = 0;
    public final int DWARF = 1;
    public final int ELF = 2;
    public final int GNOME = 3;
    public final int HALF_ELF = 4;
    public final int HALFLING = 5;
    public final int HALF_ORC = 6;
    public final int HUMAN = 7;
    public final int TIEFLING = 8;

    //CLASSES
    public final int BARBARIAN = 0;
    public final int BARD = 1;
    public final int CLERIC = 2;
    public final int DRUID = 3;
    public final int FIGHTER = 4;
    public final int MONK = 5;
    public final int PALADIN = 6;
    public final int RANGER = 7;
    public final int ROGUE = 8;
    public final int SORCERER = 9;
    public final int WARLOCK = 10;
    public final int WIZARD = 11;
    public final int DEFAULT = 12;

    //ALIGNMENTS
    public final int LAWFUL_GOOD = 0;
    public final int LAWFUL_NEUTRAL = 1;
    public final int LAWFUL_EVIL = 2;
    public final int NEUTRAL_GOOD = 3;
    public final int NEUTRAL_NEUTRAL = 4;
    public final int NEUTRAL_EVIL = 5;
    public final int CHAOTIC_GOOD = 6;
    public final int CHAOTIC_NEUTRAL = 7;
    public final int CHAOTIC_EVIL = 8;

    //BACKGROUNDS
    public final int ACOLYTE = 0;
    public final int CHARLATAN = 1;
    public final int CRIMINAL = 2;
    public final int ENTERTAINER = 3;
    public final int FOLK_HERO = 4;
    public final int GLADIATOR = 5;
    public final int GUILD_ARTISAN = 6;
    public final int HERMIT = 7;
    public final int KNIGHT = 8;
    public final int NOBLE = 9;
    public final int OUTLANDER = 10;
    public final int PIRATE = 11;
    public final int SAGE = 12;
    public final int SAILOR = 13;
    public final int SOLDIER = 14;
    public final int URCHIN = 15;

    //SKILLS & ATTRIBUTES
    public final int ALL_SKILLS = 18;
    public final int ATTRIBUTE_DICE_COUNT = 3;
    public final int STARTING_PROFICIENCY = 2;
    public final int BASE_AC = 10;
    public final int STARTING_LEVEL = 1;

    //DICES
    public final int BARBARIAN_DICE = 12;
    public final int BARD_DICE = 8;
    public final int CLERIC_DICE = 8;
    public final int DRUID_DICE = 8;
    public final int FIGHTER_DICE = 10;
    public final int MONK_DICE = 8;
    public final int PALADIN_DICE = 10;
    public final int RANGER_DICE = 10;
    public final int ROGUE_DICE = 8;
    public final int SORCERER_DICE = 6;
    public final int WARLOCK_DICE = 8;
    public final int WIZARD_DICE = 6;

    //SINGLETON
    private CoreRules() {
        System.out.println("Core rules created.");
    }

    private static CoreRules instance = null;
    public static synchronized CoreRules getInstance() {
        if(instance == null)
            instance = new CoreRules();
        return instance;
    }

    private final String[] races = {"Dragonborn", "Dwarf", "Elf", "Gnome", "Half-Elf", "Halfling", "Half-Orc", "Human", "Tiefling"};
    private final String[] alignments = {"Lawful Good", "Neutral Good", "Chaotic Good", "Lawful Neutral", "True Neutral", "Chaotic Neutral", "Lawful Evil", "Neutral Evil", "Chaotic Evil"};
    private final String[] backgrounds = {"Acolyte", "Charlatan", "Criminal", "Entertainer", "Folk Hero", "Guild Artisan", "Hermit", "Noble", "Outlander", "Sage", "Sailor", "Soldier", "Urchin"};
    private final String[] classes = {"Barbarian", "Bard", "Cleric", "Druid", "Fighter", "Monk", "Paladin", "Ranger", "Rogue", "Sorcerer", "Warlock", "Wizard"};

    private final String[][] subClasses = {
            {"--None--", "Path of the Berserker", "Path of the Totem Warrior"},
            {"--None--", "College of Lore", "College of Valor"},
            {"--None--", "Knowledge Domain", "Life Domain", "Light Domain", "Nature Domain", "Tempest Domain", "Trickery Domain", "War Domain"},
            {"--None--", "Circle of the Land", "Circle of the Moon"},
            {"--None--", "Champion", "Battle Master", "Eldritch Knight"},
            {"--None--", "Way of the Open Hand", "Way of Shadow", "Way of the Four Elements"},
            {"--None--", "Oath of Devotion", "Oath of the Ancients", "Oath of Vengeance"},
            {"--None--", "Hunter", "Beast Master"},
            {"--None--", "Thief", "Assassin", "Arcane Trickster"},
            {"--None--", "Draconic Blood", "Wild Magic"},
            {"--None--", "The Archfey", "The Fiend", "The Great Old One"},
            {"--None--", "School of Abjuration", "School of Conjuration", "School of Divination", "School of Enchantment", "School of Evocation", "School of Illusion", "School of Necromancy", "School of Transmutation"}
    };

    private final String[] raceFeats = {
            //dragonborn
    };

    private final String[] backgroundFeats = {
            "> Two languages of your choice\n> Shelter Of The Faithful\n",
            "> Proficiency in: Disguise kit, Forgery kit\n> False Identity\n",
            "> Proficiency in: One type of gaming set, Thieves' tools\n> Criminal Contact\n",
            "> Proficiency in: Disguise kit, One type of musical instrument\n> By Popular Demand\n",
            "> Proficiency in: One type of artisan's tools, Vehicles(land)\n> Rustic Hospitality\n",
            "> Proficiency in: One type of artisan's tools\n> One language of your choice\n> Guild Membership\n",
            "> Proficiency in: Herbalism kit\n> One language of your choice\n> Discovery\n",
            "> Proficiency in: One type of gaming set\n> One language of your choice\n> Position Of Privilege\n",
            "> Proficiency in: One type of musical instrument\n> One language of your choice\n> Wanderer\n",
            "> Two languages of your choice\n> Researcher\n",
            "> Proficiency in: Navigator's tools, Vehicles(water)\n> Ship's Passage\n",
            "> Proficiency in: One type of gaming set, Vehicles(land)\n> Military Rank\n",
            "> Proficiency in: Disguise kit, thieves' tools\n> City Secrets\n"
    };

    private final int[] subClassesDefaultLessThan3 = {0, 1, 4, 5, 6, 7, 8};
    private final int[] subClassesDefaultLessThan2 = {3, 11};
    private final Integer[] levels = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
    private final int[] strSkills = {3};
    private final int[] dexSkills = {0,15,16};
    private final int[] conSkills = {};
    private final int[] intSkills = {2,5,8,10,14};
    private final int[] wisSkills = {1,6,9,11,17};
    private final int[] chaSkills = {4,7,12,13};


    public String[] getRaces() {
        return races;
    }

    public String[] getAlignments() {
        return alignments;
    }

    public String[] getBackgrounds() {
        return backgrounds;
    }

    public String[] getClasses() {
        return classes;
    }

    public String[] getSubClasses(int arrayOfSubClasses) {
        return subClasses[arrayOfSubClasses];
    }

    public int[] getSubClassesDefaultLessThan3() {
        return subClassesDefaultLessThan3;
    }

    public int[] getSubClassesDefaultLessThan2() {
        return subClassesDefaultLessThan2;
    }

    public Integer[] getLevels() {
        return levels;
    }

    public int[] getStrSkills() {
        return strSkills;
    }

    public int[] getDexSkills() {
        return dexSkills;
    }

    public int[] getConSkills() {
        return conSkills;
    }

    public int[] getIntSkills() {
        return intSkills;
    }

    public int[] getWisSkills() {
        return wisSkills;
    }

    public int[] getChaSkills() {
        return chaSkills;
    }

    public String[] getBackgroundFeats() {
        return backgroundFeats;
    }
}
