package biz.letsweb.xml.storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Tomasz
 */
public class Marshalling {

    public static <T> T unmarshal(Class<T> docClass, InputStream inputStream) throws JAXBException {
        String packageName = docClass.getPackage().getName();
        JAXBContext jc = JAXBContext.newInstance(packageName);
        Unmarshaller u = jc.createUnmarshaller();
//        JAXBElement<T> doc = (JAXBElement<T>) u.unmarshal(inputStream);
        Object schemaObject = JAXBIntrospector.getValue(u.unmarshal(inputStream));
//        return doc.getValue();
        return (T) schemaObject;
    }

    public static void marshal(Object jaxbe, File dest) throws IOException, JAXBException {
        JAXBContext jc = JAXBContext.newInstance(jaxbe.getClass());
        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(jaxbe, FileUtils.openOutputStream(dest));
    }

    public static boolean validateAgainstXSD(InputStream xml, InputStream xsd) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsd));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xml));
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
