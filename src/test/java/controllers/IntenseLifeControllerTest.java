package controllers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by liviu on 5/26/2016.
 */
public class IntenseLifeControllerTest {

    private static final IntenseLifeController intenseLifeController = new IntenseLifeController();

    @Test
    public void testSearchingForHistoryReturnsNullWhenThereIsNoData() throws Exception {
            assertEquals("Did not return null for empty data", null, intenseLifeController.history(null));
            assertEquals("Did not return null for empty data", null, intenseLifeController.history(""));
            assertEquals("Did not return null for empty data", null, intenseLifeController.history("{}"));

    }
}