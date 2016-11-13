import com.flag.xu.project.system.exception.NoHandlerInPipeLineException;
import com.flag.xu.project.system.input.InputPipeLine;
import com.flag.xu.project.system.input.TextInputHandler;
import com.flag.xu.project.system.input.XmlInputHandler;
import com.flag.xu.project.system.pojo.XmlTestPojo;
import com.flag.xu.project.system.util.PathUtil;
import com.flag.xu.project.system.util.XMLUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @Authuor Administrator
 * @Create 2016-11-10-15:17
 */
public class InputUnitTest {
    @Test
    public void test_input_handler_chain() throws NoHandlerInPipeLineException {
        InputPipeLine pipeLine = new InputPipeLine();
        pipeLine.addInputHandler(new XmlInputHandler())
        .addInputHandler(new TextInputHandler()).startHandle("/hello.txt");
    }

    @Test
    public void test_input_handler_getpathtype(){
        new TextInputHandler().handleInput("c:/slkfj/iwej/sss.gif");
    }

    @Test
    public void test_inputhandler_nohandler() throws NoHandlerInPipeLineException {
        new InputPipeLine().startHandle("c:/slkfj/iwej/sss.gif");
    }

    @Test
    public void test_xmlToBean_simple() throws IOException, DocumentException {
        Path path = PathUtil.getPath(InputUnitTest.class, "/xmltest.xml");
        SAXReader reader = new SAXReader();
        Document doc = reader.read(Files.newInputStream(path));
        XmlTestPojo test = (XmlTestPojo) XMLUtil.simpleXmlToBean(doc.asXML());
        System.out.println(test);
    }

    @Test
    public void test_xml() throws IOException, DocumentException {
        Path path = PathUtil.getPath(InputUnitTest.class, "/xmltest.xml");
        SAXReader reader = new SAXReader();
        Document doc = reader.read(Files.newInputStream(path));
        System.out.println(doc.asXML());
        Element root = doc.getRootElement();
        System.out.println(root.elementText("name") + "\t" + root.elementText("bool") + "\t" + root.elementText("anInt"));
    }
}
