package Utils;

import it.polimi.ingsw.PSP19.Server.Model.Colour;
import it.polimi.ingsw.PSP19.Server.Model.Game;
import it.polimi.ingsw.PSP19.Server.Model.Grid;
import it.polimi.ingsw.PSP19.Utils.GuiHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
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

    @Test
    public void getMoveImagePathTest() {
        assertEquals("/Images/Game/Action/build.png", GuiHelper.getMoveImagePath(Colour.BLUE, true));
        assertEquals("/Images/Game/Action/move_blue.png", GuiHelper.getMoveImagePath(Colour.BLUE, false));
    }

    @Test
    public void getBuildingImagePathTest() {
        assertNull(GuiHelper.getBuildingImagePath(5,false));
        assertEquals("/Images/Game/Buildings/Building1levels.png", GuiHelper.getBuildingImagePath(1, false));
    }

    @Test
    public void getContourImagePathTest() {
        assertEquals("/Images/Game/Gods/Bigger/currentPlayer_blue.png", GuiHelper.getContourImagePath(Colour.BLUE, true));
        assertEquals("/Images/Game/Gods/Smaller/currentPlayerSmall_red.png", GuiHelper.getContourImagePath(Colour.RED, false));

    }

    @Test
    public void getPawnImagePathTest() {
        assertEquals("/Images/Game/Pawns/MaleBuilder_blu.png", GuiHelper.getPawnImagePath(Colour.BLUE, 1));
        assertEquals("/Images/Game/Pawns/FemaleBuilder_red.png", GuiHelper.getPawnImagePath(Colour.RED, 2));
        assertEquals("/Images/Game/Pawns/MaleBuilder_white.png", GuiHelper.getPawnImagePath(Colour.WHITE, 3));
        assertEquals("/Images/Game/Pawns/FemaleBuilder_purple.png", GuiHelper.getPawnImagePath(Colour.PINK, 4));
        assertEquals("/Images/Game/Pawns/MaleBuilder_yellow.png", GuiHelper.getPawnImagePath(Colour.YELLOW, 5));
    }

    @Test
    public void getRandIntTest() {
        ArrayList<Integer> pawnTakenArray = new ArrayList<>();
        int randInt;

        randInt = GuiHelper.getRandInt(10, 0, pawnTakenArray, 0);
        assertTrue(randInt <= 10 && randInt % 2 == 1);

        randInt = GuiHelper.getRandInt(10, 0, pawnTakenArray, 1);
        assertTrue(randInt >= 0 && randInt <= 10 && randInt % 2 == 0);


        pawnTakenArray.add(1);
        pawnTakenArray.add(2);

        randInt = GuiHelper.getRandInt(5, 0, pawnTakenArray, 0);
        assertTrue(!pawnTakenArray.contains(randInt) && randInt % 2 == 1);

        randInt = GuiHelper.getRandInt(5, 0, pawnTakenArray, 1);
        assertTrue(!pawnTakenArray.contains(randInt) && randInt % 2 == 0);


    }

    @Test
    public void getNewPawnIdTest() {
        Game game = new Game(0, "codGame", false, null, new Grid(), new Grid(), null);
        assertEquals(1, GuiHelper.getNewPawnId(game, 0) % 2);
    }


}
