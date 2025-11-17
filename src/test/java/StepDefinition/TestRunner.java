package StepDefinition;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions
(
	features = "src/test/java/FeatureFiles/",
	glue = {"StepDefinition"},
	tags = "@sanity or @regression",
	dryRun = false,
	monochrome = false,
	plugin = {"pretty","html:target/HtmlReports/index.html",
			"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
)

public class TestRunner {

}
