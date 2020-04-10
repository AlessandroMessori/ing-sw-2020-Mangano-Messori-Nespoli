package Utils;

import Server.Model.Divinity;

import java.util.ArrayList;

public class CastingHelper {

    public static ArrayList<Divinity> convertDivinityList(ArrayList<String> strDivinities) {
        ArrayList<Divinity> divs = new ArrayList<>();

        for (String dv : strDivinities) {
            Divinity currentDiv;
            switch (dv) {
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
            divs.add(currentDiv);
        }

        return divs;
    }
}
