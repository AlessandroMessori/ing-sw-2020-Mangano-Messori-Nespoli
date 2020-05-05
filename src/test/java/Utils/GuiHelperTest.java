package Utils;

import it.polimi.ingsw.PSP19.Utils.GuiHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GuiHelperTest {

    GuiHelper guiHelper;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getPercentageTest() {
        assertEquals(68.0, GuiHelper.getPercentage(200, 34), 0.01);
    }

    @Test
    public void getCenteredXTest() {
        assertEquals(650.0, GuiHelper.getCenteredX(1500, 200), 0.01);
    }


}
