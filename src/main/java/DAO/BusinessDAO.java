package main.java.DAO;

import main.java.classes.Business;
import main.java.csvUtil.BusinessesCSVUtil;

import java.util.List;
import java.util.stream.Collectors;

public class BusinessDAO {
    private final List<Business> businesses;

    public BusinessDAO(String filename){
        BusinessesCSVUtil csvUtil = new BusinessesCSVUtil();
        this.businesses = csvUtil.loadBusinessData(filename);
    }

    public List<Business> getCannabisRetail(){
        return businesses.stream()
                .filter(business-> business.hasCategory("Cannabis Retail Sales"))
                .collect(Collectors.toList());
    }

    /**
     * Sells alcohol for off site consumption and is not a restaurant
     * Restaurants are not considered liquor stores but some restaurants sell liquor via delivery for off site consumption
     */
    public List<Business> getAlcoholRetail(){
        return businesses.stream()
                .filter(business-> business.hasCategory("Alcohol Sales (Consumption Off-Premises)"))
                .filter(business-> !business.hasCategory("Restaurant or Food Service"))
                .collect(Collectors.toList());
    }

    public List<Business> getRestaurants(){
        return businesses.stream()
                .filter(business-> business.hasCategory("Restaurant or Food Service"))
                .collect(Collectors.toList());
    }

    public List<Business> getBars(){
        return businesses.stream()
                .filter(business-> business.hasCategory("Alcohol Sales (Consumption On-Premises / Minors Prohibited)"))
                .collect(Collectors.toList());
    }
}
