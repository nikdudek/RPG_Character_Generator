package com.app.model;

public class CoreRules {

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
    private final String[] backgrounds = {"Acolyte", "Charlatan", "Criminal/Spy", "Entertainer", "Folk Hero", "Gladiator", "Guild Artisan", "Hermit", "Knight", "Noble", "Outlander", "Pirate", "Sage", "Sailor", "Soldier", "Urchin"};
    private final String[] classes = {"Barbarian", "Bard", "Cleric", "Druid", "Fighter", "Monk", "Paladin", "Ranger", "Rogue", "Sorcerer", "Warlock", "Wizard"};

    private final String[][] subClasses = {
            {"--None--"},
            {"Path of the Berserker", "Path of the Totem Warrior"},
            {"College of Lore", "College of Valor"},
            {"Knowledge Domain", "Life Domain", "Light Domain", "Nature Domain", "Tempest Domain", "Trickery Domain", "War Domain"},
            {"Circle of the Land", "Circle of the Moon"},
            {"Champion", "Battle Master", "Eldritch Knight"},
            {"Way of the Open Hand", "Way of Shadow", "Way of the Four Elements"},
            {"Oath of Devotion", "Oath of the Ancients", "Oath of Vengeance"},
            {"Hunter", "Beast Master"},
            {"Thief", "Assassin", "Arcane Trickster"},
            {"Draconic Blood", "Wild Magic"},
            {"The Archfey", "The Fiend", "The Great Old One"},
            {"School of Abjuration", "School of Conjuration", "School of Divination", "School of Enchantment", "School of Evocation", "School of Illusion", "School of Necromancy", "School of Transmutation"}
    };
//    private final String[] subClassesBarbarian = {"Path of the Berserker", "Path of the Totem Warrior"};
//    private final String[] subClassesBard = {"College of Lore", "College of Valor"};
//    private final String[] subClassesCleric = {"Knowledge Domain", "Life Domain", "Light Domain", "Nature Domain", "Tempest Domain", "Trickery Domain", "War Domain"};
//    private final String[] subClassesDruid = {"Circle of the Land", "Circle of the Moon"};
//    private final String[] subClassesFighter = {"Champion", "Battle Master", "Eldritch Knight"};
//    private final String[] subClassesMonk = {"Way of the Open Hand", "Way of Shadow", "Way of the Four Elements"};
//    private final String[] subClassesPaladin = {"Oath of Devotion", "Oath of the Ancients", "Oath of Vengeance"};
//    private final String[] subClassesRanger = {"Hunter", "Beast Master"};
//    private final String[] subClassesRogue = {"Thief", "Assassin", "Arcane Trickster"};
//    private final String[] subClassesSorcerer = {"Draconic Blood", "Wild Magic"};
//    private final String[] subClassesWarlock = {"The Archfey", "The Fiend", "The Great Old One"};
//    private final String[] subClassesWizard = {"School of Abjuration", "School of Conjuration", "School of Divination", "School of Enchantment", "School of Evocation", "School of Illusion", "School of Necromancy", "School of Transmutation"};

    private final Integer[] subClassesDefaultLessThan3 = {0, 1, 4, 5, 6, 7, 8};
    private final Integer[] subClassesDefaultLessThan2 = {3, 11};
    private final Integer[] levels = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};

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

//    public String[] getSubClassesBarbarian() {
//        return subClassesBarbarian;
//    }
//
//    public String[] getSubClassesBard() {
//        return subClassesBard;
//    }
//
//    public String[] getSubClassesCleric() {
//        return subClassesCleric;
//    }
//
//    public String[] getSubClassesDruid() {
//        return subClassesDruid;
//    }
//
//    public String[] getSubClassesFighter() {
//        return subClassesFighter;
//    }
//
//    public String[] getSubClassesMonk() {
//        return subClassesMonk;
//    }
//
//    public String[] getSubClassesPaladin() {
//        return subClassesPaladin;
//    }
//
//    public String[] getSubClassesRanger() {
//        return subClassesRanger;
//    }
//
//    public String[] getSubClassesRogue() {
//        return subClassesRogue;
//    }
//
//    public String[] getSubClassesSorcerer() {
//        return subClassesSorcerer;
//    }
//
//    public String[] getSubClassesWarlock() {
//        return subClassesWarlock;
//    }
//
//    public String[] getSubClassesWizard() {
//        return subClassesWizard;
//    }

    public Integer[] getSubClassesDefaultLessThan3() {
        return subClassesDefaultLessThan3;
    }

    public Integer[] getSubClassesDefaultLessThan2() {
        return subClassesDefaultLessThan2;
    }

    public Integer[] getLevels() {
        return levels;
    }
}
