import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DumbClassTest {

    static DumbClass dumVar;

    @Before
    public void setUp() {
        dumVar = new DumbClass();
    }


    @After
    public void tearDown() {
        dumVar = null;
    }

    @Test
    public void returnBoolPositiveTest() {
        assertTrue(dumVar.returnBool(true));
    }

    @Test
    public void returnBoolNegativeTest() {
        assertFalse(dumVar.returnBool(false));
    }

}