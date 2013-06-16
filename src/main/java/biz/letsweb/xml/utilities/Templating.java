package biz.letsweb.xml.utilities;

import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Tomasz
 */
public class Templating {

    private File templateFile = new File("src/main/resources/template.txt");
    private File dataXmlFile = new File("src/main/resources/report.xml");
    private Template template;

    public Templating() throws IOException {
        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(templateFile.getParentFile());
        cfg.setEncoding(new Locale("pl", "PL"), "UTF-8");
        cfg.setObjectWrapper(ObjectWrapper.DEFAULT_WRAPPER);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setOutputEncoding("UTF-8");
        template = cfg.getTemplate(templateFile.getName());
    }

    public void parse() throws IOException, TemplateException, SAXException, ParserConfigurationException {
        Map root = new HashMap();
        loadXml(root, dataXmlFile);
        root.put("time", new Date());
        root.put("version", "123");
        FileWriter writer = new FileWriter(new File("src/main/resources/report.txt"));
        template.process(root, writer);
        writer.flush();

    }

    public void loadXml(Map root, File xml) throws SAXException, IOException, ParserConfigurationException {
        root.put("xml", freemarker.ext.dom.NodeModel.parse(xml));
    }
}
