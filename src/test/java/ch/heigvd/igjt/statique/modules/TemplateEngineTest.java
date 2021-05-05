package ch.heigvd.igjt.statique.modules;

import org.junit.Test;

import java.io.IOException;

import static ch.heigvd.igjt.statique.subcommands.TestUtils.*;

public class TemplateEngineTest {

    @Test
    public void templateEngineShouldIncludeContent() throws IOException {
        initTemplate();
        TemplateEngine engine = new TemplateEngine(getExpectedSiteConfig(), TEMPLATE_PATH);
        String result = engine.build(MarkdownProcessor.compileToHtml(getExpectedIndexFileContent()), getExpectedIndexHeader());
        System.out.println(result);
    }
}
