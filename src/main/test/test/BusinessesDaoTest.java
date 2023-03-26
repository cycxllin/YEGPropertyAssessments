package test;

import main.java.DAO.BusinessDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BusinessesDaoTest {
    @Test
    void get(){
        BusinessDAO businessDAO = new BusinessDAO("/Users/jex/IdeaProjects/CMPT305/businesses_short.csv");

        assertEquals(1, businessDAO.getBars().size());
        assertEquals(3, businessDAO.getRestaurants().size());
        assertEquals(2, businessDAO.getCannabisRetail().size());
        assertEquals(2, businessDAO.getAlcoholRetail().size());
    }
}