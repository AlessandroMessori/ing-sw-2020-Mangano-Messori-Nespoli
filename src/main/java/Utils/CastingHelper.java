package Utils;

import Server.Model.Divinity;
import Server.Model.DivinityList;

import java.util.ArrayList;

public class CastingHelper {

    /**
     * converts a Divinity from String
     *
     * @param strDivinity the String Divnity to convert+
     *
     * @return the converted Divinity
     */
    public static Divinity convertDivinity(String strDivinity) {
        Divinity currentDiv;
        switch (strDivinity) {
            case "APOLLO":
                currentDiv = Divinity.APOLLO;
                break;
            case "ARTEMIS":
                currentDiv = Divinity.ARTEMIS;
                break;
            case "ATLAS":
                currentDiv = Divinity.ATLAS;
                break;
            case "DEMETER":
                currentDiv = Divinity.DEMETER;
                break;
            case "ATHENA":
                currentDiv = Divinity.ATHENA;
                break;
            case "HEPHAESTUS":
                currentDiv = Divinity.HEPHAESTUS;
                break;
            case "MINOTAUR":
                currentDiv = Divinity.MINOTAUR;
                break;
            case "PAN":
                currentDiv = Divinity.PAN;
                break;
            case "PROMETHEUS":
                currentDiv = Divinity.PROMETHEUS;
                break;
            default:
                currentDiv = null;
        }

        return currentDiv;
    }

    /**
     * converts an ArrayList of String Divinities to an ArrayList of Divinities
     *
     * @param strDivinities the ArrayList to Convert
     *
     * @return the ArrayList of converted divinities
     */
    public static ArrayList<Divinity> convertDivinityList(ArrayList<String> strDivinities) {
        ArrayList<Divinity> divs = new ArrayList<>();

        for (String dv : strDivinities) {
            Divinity currentDiv;
            currentDiv = convertDivinity(dv);
            divs.add(currentDiv);
        }

        return divs;
    }

    /**
     * converts an ArrayList of Divinities to an ArrayList of String Divinities
     *
     * @param divinities the ArrayList to Convert
     *
     * @return the ArrayList of converted string divinities
     */
    public static ArrayList<String> convertDivinityListToString(DivinityList divinities) {
        ArrayList<String> divs = new ArrayList<>();

        for (int i=0;i< divinities.size();i++) {
            divs.add(divinities.getDivinity(i).toString());
        }

        return divs;
    }
}
