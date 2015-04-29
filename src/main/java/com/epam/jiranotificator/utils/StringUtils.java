package com.epam.jiranotificator.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class StringUtils {

    private static final String DELIMITER = ",";

    public static List<String> splitToList(final String text){
        if (text != null){
            String[] split = text.split(DELIMITER);
            return Arrays.asList(split);
        }
        return new ArrayList<>();
    }
}
