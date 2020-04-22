package Utils;

import Server.Model.Divinity;
import Server.Model.DivinityList;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CastingHelperTest {

    CastingHelper castingHelper;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void convertDivinityTest() {
        Divinity div = CastingHelper.convertDivinity("APOLLO");
        assertEquals(div, Divinity.APOLLO);
        div = CastingHelper.convertDivinity("DEMETER");
        assertEquals(div, Divinity.DEMETER);
        div = CastingHelper.convertDivinity("ATHENA");
        assertEquals(div, Divinity.ATHENA);
        div = CastingHelper.convertDivinity("ATLAS");
        assertEquals(div, Divinity.ATLAS);
        div = CastingHelper.convertDivinity("ARTEMIS");
        assertEquals(div, Divinity.ARTEMIS);
        div = CastingHelper.convertDivinity("HEPHAESTUS");
        assertEquals(div, Divinity.HEPHAESTUS);
        div = CastingHelper.convertDivinity("MINOTAUR");
        assertEquals(div, Divinity.MINOTAUR);
        div = CastingHelper.convertDivinity("PROMETHEUS");
        assertEquals(div, Divinity.PROMETHEUS);
        div = CastingHelper.convertDivinity("PAN");
        assertEquals(div, Divinity.PAN);
        div = CastingHelper.convertDivinity("Not a Divinity");
        assertEquals(div,null);
    }

    @Test
    public void convertDivinityListTest() {
        ArrayList<String> strDivs = new ArrayList();
        ArrayList<Divinity> divs;

        strDivs.add("APOLLO");
        strDivs.add("DEMETER");

        divs = CastingHelper.convertDivinityList(strDivs);

        assertEquals(strDivs.size(), divs.size());
        assertEquals(divs.get(0), Divinity.APOLLO);
        assertEquals(divs.get(1), Divinity.DEMETER);
    }

    @Test
    public void convertDivinityListToString() {
        ArrayList<String> strDivs;
        DivinityList divs = new DivinityList();

        divs.addDivinity(Divinity.HEPHAESTUS);
        divs.addDivinity(Divinity.PROMETHEUS);

        strDivs = CastingHelper.convertDivinityListToString(divs);

        assertEquals(strDivs.size(), divs.size());
        assertEquals(strDivs.get(0), "HEPHAESTUS");
        assertEquals(strDivs.get(1), "PROMETHEUS");
    }


}
