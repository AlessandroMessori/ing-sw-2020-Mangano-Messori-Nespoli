package Utils;

import Server.Model.Divinity;
import Server.Model.DivinityList;

import java.util.ArrayList;

public class CastingHelper {

    public static Divinity convertDivinity(String strDivinity) {
        Divinity currentDiv = null;
        switch (strDivinity) {
            case "APOLLO":
                currentDiv = Divinity.APOLLO;
                break;
            case "ARTEMIS":
                currentDiv = Divinity.ARTEMIS;
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

    public static ArrayList<Divinity> convertDivinityList(ArrayList<String> strDivinities) {
        ArrayList<Divinity> divs = new ArrayList<>();

        for (String dv : strDivinities) {
            Divinity currentDiv;
            currentDiv = convertDivinity(dv);
            divs.add(currentDiv);
        }

        return divs;
    }

    public static ArrayList<String> convertDivinityListToString(DivinityList divinities) {
        ArrayList<String> divs = new ArrayList<>();

        for (int i=0;i< divinities.size();i++) {
            divs.add(divinities.getDivinity(i).toString());
        }

        return divs;
    }
}
