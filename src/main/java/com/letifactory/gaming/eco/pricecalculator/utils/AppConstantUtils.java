package com.letifactory.gaming.eco.pricecalculator.utils;

import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoConfig;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoItemType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppConstantUtils {
    public final static String JSONPATH = "src/main/resources/static/data/%s.json";

    public final static Map<Integer, String> ALL_TYPES = new HashMap<>();
    public final static Map<String, Double> ALL_CONFIG = new HashMap<>();

    public static String getJsonFilePath(String fileName) {
        return String.format(AppConstantUtils.JSONPATH, fileName);
    }

    public static void addAllTypeToConstant(List<EcoItemType> itemTypes) {
        ALL_TYPES.clear();
        itemTypes.forEach(type -> ALL_TYPES.put(type.getOrder(), type.getType()));
    }

    public static void addAllConfigToConstant(List<EcoConfig> configs) {
        ALL_CONFIG.clear();
        configs.forEach(config -> ALL_CONFIG.put(config.getName(), config.getValue()));
    }

    public static double getLaborPrice(double labor){
        final Double ratioEffortCalorie = ALL_CONFIG.get("RatioEffortCalorie");
        final Double pricePerThousandCalories = ALL_CONFIG.get("PricePerThousandCalories");
        return (labor*pricePerThousandCalories)/(1000.0*ratioEffortCalorie);
    }

}
