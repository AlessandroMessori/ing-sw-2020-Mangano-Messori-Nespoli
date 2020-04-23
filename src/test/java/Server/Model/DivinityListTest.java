package Server.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DivinityListTest {

    static DivinityList divinityList;

    @Before
    public void setUp() throws Exception {
        divinityList = new DivinityList();
    }

    @After
    public void tearDown() throws Exception {
        divinityList = null;
    }

    @Test
    public void getLenTest() {
        divinityList = new DivinityList();

        divinityList.addDivinity(Divinity.ATHENA);
        divinityList.addDivinity(Divinity.ATLAS);

        assertTrue(divinityList.size() == 2);
    }

    @Test
    public void getDivinityTest() {
        divinityList = new DivinityList();

        divinityList.addDivinity(Divinity.ATHENA);
        divinityList.addDivinity(Divinity.ATLAS);

        assertEquals(divinityList.getDivinity(0), Divinity.ATHENA);
        assertEquals(divinityList.getDivinity(1), Divinity.ATLAS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getDivinityExceptionTest() {

        divinityList = new DivinityList();

        divinityList.addDivinity(Divinity.ATHENA);
        divinityList.addDivinity(Divinity.ATLAS);

        divinityList.getDivinity(5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addDivinityExceptionTest() {

        divinityList = new DivinityList();

        divinityList.addDivinity(Divinity.ATHENA);
        divinityList.addDivinity(Divinity.ATHENA);
    }

    @Test
    public void deleteDivinityTest() {
        divinityList = new DivinityList();

        divinityList.addDivinity(Divinity.ATHENA);
        divinityList.addDivinity(Divinity.ATLAS);

        Divinity toDeleteDivinity = divinityList.getDivinity(0);
        divinityList.deleteDivinity(toDeleteDivinity);

        assertEquals(1, divinityList.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteDivinityExceptionTest() {
        divinityList = new DivinityList();

        divinityList.addDivinity(Divinity.ATHENA);
        divinityList.addDivinity(Divinity.ATLAS);

        Divinity toDeleteDivinity = Divinity.DEMETER;
        divinityList.deleteDivinity(toDeleteDivinity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteDivinityExceptionTest2() {
        divinityList = new DivinityList();

        divinityList.addDivinity(Divinity.ATHENA);
        divinityList.addDivinity(Divinity.ATLAS);

        divinityList.deleteDivinity(null);
    }

    @Test
    public void divinityListToStringTest() {

        divinityList = new DivinityList();

        divinityList.addDivinity(Divinity.ATHENA);
        divinityList.addDivinity(Divinity.ATLAS);

        assertEquals("[ATHENA,ATLAS,]", divinityList.toString());
    }

}