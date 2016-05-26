package controllers;

import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * Created by liviu on 5/26/2016.
 */
public class IntenseLifeControllerTest {

    private static final IntenseLifeController intenseLifeController = new IntenseLifeController();

    @Test
    public void testSearchingForHistoryReturnsNullWhenThereIsNoData() throws Exception {
        assertNull("Did not return null for empty data", intenseLifeController.history(null));
        assertNull("Did not return null for empty data", intenseLifeController.history(""));
        assertNull("Did not return null for empty data", intenseLifeController.history("{}"));

    }
}