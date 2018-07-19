package ro.cucumber.core.symbols;


import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractSymbolRetrieveParser {

    protected String stringWithSymbols;

    public AbstractSymbolRetrieveParser(String stringWithSymbols) {
        this.stringWithSymbols = stringWithSymbols;
    }

    public String parse(Map<String, String> symbolValues) {
        String str = stringWithSymbols;
        for (Map.Entry<String, String> e : symbolValues.entrySet()) {
            str = str.replaceAll(getSymbolStart() + e.getKey() + getSymbolEnd(),
                    e.getValue());
        }
        return str;
    }

    protected abstract String getSymbolStart();

    protected abstract String getSymbolEnd();

    protected abstract Pattern getSymbolRetrievePattern();

    public Set<String> getSymbolNames() {
        Set<String> names = new HashSet<>();
        Matcher matcher = getSymbolRetrievePattern().matcher(stringWithSymbols);
        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                names.add(matcher.group(i));
            }
        }
        return names;
    }
}
