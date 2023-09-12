package com.letifactory.gaming.eco.pricecalculator.utils;

import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoConfig;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoItemType;

import java.util.*;

public class AppConstantUtils {
    public final static String JSONPATH = "src/main/resources/static/data/%s.json";

    public final static Map<Integer, String> ALL_TYPES = new HashMap<>();
    public final static Map<String, Double> ALL_CONFIG = new HashMap<>();

    public final static List<String> FEEDBACK_RECIPE =
            new ArrayList<>(List.of("Plastic", "SyntheticRubber", "Nylon", "Epoxy"));


    public static String getJsonFilePath(final String fileName) {
        return String.format(AppConstantUtils.JSONPATH, fileName);
    }

    public static void addAllTypeToConstant(final List<EcoItemType> itemTypes) {
        ALL_TYPES.clear();
        itemTypes.forEach(type -> ALL_TYPES.put(type.getTypeOrder(), type.getType()));
    }

    public static void addAllConfigToConstant(final List<EcoConfig> configs) {
        ALL_CONFIG.clear();
        configs.forEach(config -> ALL_CONFIG.put(config.getName(), config.getValue()));
    }

    public static double getLaborPrice(final double labor) {
        final Double ratioEffortCalorie = ALL_CONFIG.get("RatioEffortCalorie");
        final Double pricePerThousandCalories = ALL_CONFIG.get("PricePerThousandCalories");
        return (labor * pricePerThousandCalories) / (1000.0 * ratioEffortCalorie);
    }

    /**
     * Map of link between tag item and their real item if tag is used as input
     */
    public final static Map<String, List<String>> TAG_ITEM_LINK = new HashMap<>(Map.<String, List<String>>ofEntries(
            /*
            Map.entry("Advanced Research",
                    List.of("AgricultureResearchPaperItem", "CulinaryResearchPaperItem", "DendrologyResearchPaperItem",
                            "EngineeringResearchPaperItem", "GeologyResearchPaperItem", "MetallurgyResearchPaperItem")),
            Map.entry("Animal", List.of("")),
            */
            Map.entry("AshlarStone",
                    List.of("AshlarBasaltItem", "AshlarGneissItem", "AshlarGraniteItem", "AshlarLimestoneItem",
                            "AshlarSandstoneItem", "AshlarShaleItem")),
            Map.entry("BakedVegetable",
                    List.of("BakedAgaveItem", "BakedBeetItem", "BakedCornItem", "BakedHeartOfPalmItem", "BakedTaroItem",
                            "BakedTomatoItem", "CamasBulbBakeItem")),
            /*
            Map.entry("Basic Research", List.of("")),
            Map.entry("Block", List.of("")),
            Map.entry("Bread", List.of("")),
             */
            Map.entry("CampfireSalad",
                    List.of("BeetCampfireSaladItem", "FernCampfireSaladItem", "JungleCampfireSaladItem",
                            "RootCampfireSaladItem")),
            //Map.entry("Clothes", List.of("")),
            Map.entry("Coal", List.of("CoalItem", "CharcoalItem")),
            Map.entry("CompositeLumber",
                    List.of("CompositeBirchLumberItem", "CompositeCedarLumberItem", "CompositeCeibaLumberItem",
                            "CompositeFirLumberItem", "CompositeJoshuaLumberItem", "CompositeLumberItem",
                            "CompositeOakLumberItem", "CompositePalmLumberItem", "CompositeRedwoodLumberItem",
                            "CompositeSaguaroLumberItem", "CompositeSpruceLumberItem")),
            /*
            Map.entry("Concrete", List.of("")),
            Map.entry("Crafting Table", List.of("")),
            Map.entry("Crop", List.of("")),
            */
            Map.entry("CropSeed", List.of("AgaveSeedItem", "AmanitaMushroomSporesItem", "BeansItem", "BeetSeedItem",
                    "BoleteMushroomSporesItem", "CamasBulbItem", "CookeinaMushroomSporesItem", "CornSeedItem",
                    "CriminiMushroomSporesItem", "FernSporeItem", "FireweedSeedItem", "HuckleberrySeedItem",
                    "PapayaSeedItem", "PineappleSeedItem", "PricklyPearSeedItem", "PumpkinSeedItem", "RiceItem",
                    "SunflowerSeedItem", "TaroSeedItem", "TomatoSeedItem", "WheatSeedItem")),
            Map.entry("CrushedRock",
                    List.of("CrushedBasaltItem", "CrushedGneissItem", "CrushedGraniteItem", "CrushedLimestoneItem",
                            "CrushedMixedRockItem", "CrushedSandstoneItem", "CrushedShaleItem", "CrushedSlagItem")),
            Map.entry("Fabric", List.of("ClothItem", "CottonFabricItem", "NylonFabricItem", "WoolFabricItem")),
            Map.entry("Fat", List.of("OilItem", "TallowItem")),
            //Map.entry("Fertilizer", List.of("")),
            Map.entry("FertilizerFiller", List.of("CompositeFillerItem", "FiberFillerItem", "PulpFillerItem")),
            /*
            Map.entry("Fish", List.of("")),
            Map.entry("Food", List.of("")),
            Map.entry("FriedVegetable", List.of("")),
            */
            Map.entry("Fruit", List.of("GiantCactusFruitItem", "HuckleberriesItem", "PapayaItem", "PineappleItem",
                    "PricklyPearFruitItem", "PumpkinItem")),
            //Map.entry("Fuel", List.of("")),
            Map.entry("Fungus", List.of("AmanitaMushroomsItem", "BoleteMushroomsItem", "CookeinaMushroomsItem",
                    "CriminiMushroomsItem")),
            Map.entry("Grain", List.of("RiceItem", "WheatItem")),
            Map.entry("Greens", List.of("AgaveLeavesItem", "BeetGreensItem", "FiddleheadsItem", "FireweedShootsItem")),
            //Map.entry("Hardwood", List.of("")),
            Map.entry("HewnLog", List.of("HardwoodHewnLogItem", "HewnLogItem", "SoftwoodHewnLogItem")),
            //Map.entry("Housing", List.of("")),
            Map.entry("LargeFish", List.of("BlueSharkItem", "TunaItem")),
            Map.entry("Lumber", List.of("HardwoodLumberItem", "LumberItem", "SoftwoodLumberItem")),
            Map.entry("MediumCarcass",
                    List.of("AlligatorCarcassItem", "BighornCarcassItem", "DeerCarcassItem", "ElkCarcassItem",
                            "JaguarCarcassItem", "MountainGoatCarcassItem")),
            Map.entry("MediumFish", List.of("BassItem", "CodItem", "SalmonItem", "TroutItem")),
            Map.entry("MediumLeatherCarcass",
                    List.of("AlligatorCarcassItem", "DeerCarcassItem", "ElkCarcassItem", "JaguarCarcassItem")),
            Map.entry("MediumWoolyCarcass", List.of("BighornCarcassItem", "MountainGoatCarcassItem")),
            /*
            Map.entry("Metal", List.of("")),
            Map.entry("Modern Research", List.of("")),
            */
            Map.entry("MortaredStone", List.of("MortaredGraniteItem", "MortaredLimestoneItem", "MortaredSandstoneItem",
                    "MortaredStoneItem")),
            Map.entry("NaturalFiber", List.of("KelpItem", "PlantFibersItem", "WoodPulpItem")),
            Map.entry("Ore", List.of("CopperOreItem", "GoldOreItem", "IronOreItem")),
            //Map.entry("Power", List.of("")),
            Map.entry("RawFood", List.of("AgaveLeavesItem", "BeanSproutItem", "BeansItem", "BeetItem", "BeetGreensItem",
                    "BoleteMushroomsItem", "CamasBulbItem", "CookeinaMushroomsItem", "CornItem", "CriminiMushroomsItem",
                    "FiddleheadsItem", "FireweedShootsItem", "GiantCactusFruitItem", "HeartOfPalmItem",
                    "HuckleberriesItem", "PapayaItem", "PineappleItem", "PricklyPearFruitItem", "PumpkinItem",
                    "RiceItem", "TaroRootItem", "TomatoItem", "WheatItem")),
            //Map.entry("Research", List.of("")),
            Map.entry("Rock",
                    List.of("BasaltItem", "GneissItem", "GraniteItem", "LimestoneItem", "SandstoneItem", "ShaleItem",
                            "StoneItem")),
            Map.entry("Salad", List.of("BasicSaladItem", "FruitSaladItem")),
            //Map.entry("Seeds", List.of("")),
            Map.entry("Silica", List.of("CrushedGraniteItem", "CrushedSandstoneItem")),
            //Map.entry("Skill Scrolls", List.of("")),
            Map.entry("SmallCarcass", List.of("CoyoteCarcassItem", "FoxCarcassItem", "WolfCarcassItem")),
            //Map.entry("SmallFish", List.of("")),
            //Map.entry("Softwood", List.of("")),
            Map.entry("TinyCarcass",
                    List.of("AgoutiCarcassItem", "HareCarcassItem", "OtterCarcassItem", "PrairieDogCarcassItem",
                            "SnappingTurtleCarcassItem", "TurkeyCarcassItem")),
            Map.entry("TinyFurCarcass", List.of("AgoutiCarcassItem", "OtterCarcassItem")),
            Map.entry("TinyLeatherCarcass", List.of("PrairieDogCarcassItem",
                    "SnappingTurtleCarcassItem", "TurkeyCarcassItem")),
            /*
            Map.entry("Tool", List.of("")),
            Map.entry("Upgrade", List.of("")),
             */
            Map.entry("Vegetable",
                    List.of("BeansItem", "BeetItem", "CamasBulbItem", "CornItem", "HeartOfPalmItem", "TaroRootItem",
                            "TomatoItem")),
            //Map.entry("Vehicles", List.of("")),
            Map.entry("Wood",
                    List.of("BirchLogItem", "CedarLogItem", "CeibaLogItem", "FirLogItem", "JoshuaLogItem", "OakLogItem",
                            "PalmLogItem", "RedwoodLogItem", "SaguaroRibItem", "SpruceLogItem")),
            Map.entry("WoodBoard", List.of("BoardItem", "HardwoodBoardItem", "SoftwoodBoardItem"))

    ));
}
