package com.teamideals.trackitez.ocr;

import java.util.ArrayList;
import java.util.List;

public class OcrAnalysisUtil {

    private OcrAnalysisUtil(){

    }

    public static List<String> getListOfItemText(String text){
        String string = null;
        List<String> toReturn = new ArrayList<>();
        try {
            string = text.split("AMOUNT")[1];
            for (String element : string.split("\n")) {
                if (element.matches("[1-9]+.[A-Z]+.*"))
                    toReturn.add(element);
            }
        } catch (Exception e) {
            string = text;
        }
        return toReturn;
    }

}
