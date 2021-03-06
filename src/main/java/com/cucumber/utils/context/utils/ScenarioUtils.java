package com.cucumber.utils.context.utils;

import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ParameterizedMessage;

@ScenarioScoped
public class ScenarioUtils {
    private Logger log = LogManager.getLogger();
    private Scenario scenario;

    @Before(order = Integer.MIN_VALUE)
    public void init(Scenario scenario) {
        this.scenario = scenario;
        log.info("Prepared scenario: [{}]", scenario.getName());
    }

    @After(order = Integer.MIN_VALUE)
    public void finish(Scenario scenario) {
        log.info("{} | Scenario [{}]", scenario.getStatus(), scenario.getName());
    }

    public void log(String msg, Object... args) {
        scenario.write(ParameterizedMessage.format(msg, args));
    }

    public Scenario getScenario() {
        return scenario;
    }
}
