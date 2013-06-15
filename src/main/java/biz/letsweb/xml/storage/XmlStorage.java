package biz.letsweb.xml.storage;

import biz.letsweb.xml.generated.reporting.Report;
import biz.letsweb.xml.utilities.Templating;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.IOException;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.FileUtils;
import org.xml.sax.SAXException;

/**
 *
 * @author Tomasz
 */
public class XmlStorage {

    public static void main(String... args) throws IOException, JAXBException, TemplateException, SAXException, ParserConfigurationException {
        System.out.println("Strting ...");
        File xml = new File("src/main/resources/output.xml");
        File xsd = new File("src/main/resources/report.xsd");
        ReportMarshalling reportMarshalling = new ReportMarshalling();
//        Marshalling.marshal(reportMarshalling.getReport(), xml);
        if (Marshalling.validateAgainstXSD(FileUtils.openInputStream(xml), FileUtils.openInputStream(xsd))) {
            Report rep = Marshalling.unmarshal(Report.class, FileUtils.openInputStream(xml));
            System.out.println("liczba projektów " + rep.getProject().size());
            System.out.println("retrieved from report: " + rep.getProject().get(0).getName());
        } else {
            System.out.println("problem validating");
        }
        Templating t = new Templating();
        t.parse();
    }
}
