package com.cucumber.utils.engineering.compare.comparators;

import com.cucumber.utils.engineering.placeholders.ScenarioPropertiesGenerator;
import ro.skyah.comparator.JsonComparator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class CustomJsonComparator implements JsonComparator {

    private Map<String, String> generatedProperties = new HashMap<>();

    public boolean compareValues(Object expected, Object actual) {
        String actualString = actual.toString();
        ScenarioPropertiesGenerator generator = new ScenarioPropertiesGenerator(expected.toString(), actualString);

        boolean hasPropertiesToGenerate = !generator.getProperties().isEmpty();
        String parsedExpected = hasPropertiesToGenerate ? generator.getParsedTarget() : expected.toString();
        String parsedExpectedQuoted = hasPropertiesToGenerate ? generator.getParsedTarget(true) : expected.toString();

        try {
            Pattern pattern = Pattern.compile(parsedExpectedQuoted);
            if (pattern.matcher(actualString).matches()) {
                if (hasPropertiesToGenerate) {
                    this.generatedProperties.putAll(generator.getProperties());
                }
                return true;
            } else {
                return false;
            }
        } catch (PatternSyntaxException e) {
            if (parsedExpected.equals(actual.toString())) {
                if (hasPropertiesToGenerate) {
                    this.generatedProperties.putAll(generator.getProperties());
                }
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean compareFields(String expected, String actual) {
        try {
            Pattern pattern = Pattern.compile(expected);
            return pattern.matcher(actual).matches();
        } catch (PatternSyntaxException e) {
            return expected.equals(actual);
        }
    }

    public Map<String, String> getGeneratedProperties() {
        return generatedProperties;
    }
}