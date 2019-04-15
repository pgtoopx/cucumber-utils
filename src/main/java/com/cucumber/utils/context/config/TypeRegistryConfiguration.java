package com.cucumber.utils.context.config;

import com.cucumber.utils.clients.http.Method;
import com.cucumber.utils.context.props.ScenarioProps;
import com.cucumber.utils.context.props.ScenarioPropsParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.cucumberexpressions.ParameterByTypeTransformer;
import io.cucumber.cucumberexpressions.ParameterType;
import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableTransformer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Locale.ENGLISH;

public class TypeRegistryConfiguration implements TypeRegistryConfigurer {

    @Override
    public Locale locale() {
        return ENGLISH;
    }

    @Override
    public void configureTypeRegistry(TypeRegistry typeRegistry) {
        typeRegistry.defineParameterType(ParameterType.fromEnum(Method.class));
        // Custom data table type
        typeRegistry.defineDataTableType(
                new DataTableType(List.class, new ScenarioPropsDataTableTransformer()));
        ScenarioPropsParameterTransformer scenarioPropsTransformer = new ScenarioPropsParameterTransformer();
        typeRegistry.setDefaultParameterTransformer(scenarioPropsTransformer);
        // Needed especially for doc strings
        typeRegistry.defineDataTableType(new DataTableType(String.class,
                (DataTable dataTable) -> {
                    ScenarioProps scenarioProps = InjectorByThreadSource.getInjector(Thread.currentThread().getId()).getInstance(ScenarioProps.class);
                    return new ScenarioPropsParser(scenarioProps, dataTable.cell(0, 0).trim()).result().toString();
                }));
    }


    private class ScenarioPropsParameterTransformer implements ParameterByTypeTransformer {
        ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public Object transform(String s, Type type) {
            ScenarioProps scenarioProps = InjectorByThreadSource.getInjector(Thread.currentThread().getId()).getInstance(ScenarioProps.class);
            return objectMapper.convertValue(new ScenarioPropsParser(scenarioProps, s.trim()).result(), objectMapper.constructType(type));
        }
    }

    private class ScenarioPropsDataTableTransformer implements TableTransformer {
        @Override
        public List transform(DataTable dataTable) {
            ScenarioProps scenarioProps = InjectorByThreadSource.getInjector(Thread.currentThread().getId()).getInstance(ScenarioProps.class);
            List<Map<String, String>> list = new ArrayList<>();
            dataTable.asMaps().forEach(map ->
                    list.add(map.entrySet().stream().collect(Collectors.toMap(
                            e -> new ScenarioPropsParser(scenarioProps, e.getKey()).result().toString(),
                            e -> new ScenarioPropsParser(scenarioProps, e.getValue()).result().toString()))));
            return !list.isEmpty() ? list : dataTable.asList().stream()
                    .map(el -> new ScenarioPropsParser(scenarioProps, el).result().toString())
                    .collect(Collectors.toList());
        }
    }
}