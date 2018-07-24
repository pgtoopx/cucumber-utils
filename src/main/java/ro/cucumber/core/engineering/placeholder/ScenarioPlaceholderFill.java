package ro.cucumber.core.engineering.placeholder;


import java.util.regex.Pattern;

public class ScenarioPlaceholderFill extends AbstractPlaceholderFill {

    public static final String PLACEHOLDER_START = "#[";
    public static final String PLACEHOLDER_END = "]";
    public static final String PLACEHOLDER_REGEX =
            "\\Q" + PLACEHOLDER_START + "\\E" + "(.*?)" + "\\Q" + PLACEHOLDER_END + "\\E";

    public static final Pattern PLACEHOLDER_PATTERN = Pattern.compile(PLACEHOLDER_REGEX,
            Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);


    public ScenarioPlaceholderFill(String stringWithSymbols) {
        super(stringWithSymbols);
    }

    @Override
    protected String getPlaceholderStart() {
        return "\\Q" + PLACEHOLDER_START + "\\E";
    }

    @Override
    protected String getPlaceholderEnd() {
        return "\\Q" + PLACEHOLDER_END + "\\E";
    }

    @Override
    protected Pattern getPlaceholderFillPattern() {
        return PLACEHOLDER_PATTERN;
    }
}