package com.flag.xu.project.system.input;

import com.flag.xu.project.system.util.PathUtil;
import com.flag.xu.project.system.util.XMLUtil;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @Authuor Administrator
 * @Create 2016-11-10-15:18
 */
public class XmlInputHandler extends InputHandler {
    public Object handleInput(String path) {
        assert path != null;
        Object obj = null;
        if (path.endsWith(".xml")) {
            obj = handleXmlInput(path);
        } else {
            if (nextHandler == null)
                return null;
            nextHandler.handleInput(path);
        }
        return obj;
    }

    private Object handleXmlInput(String path){
        Path filePath = PathUtil.getPath(XmlInputHandler.class, path);
        SAXReader reader = new SAXReader();
        String xmlStr = null;
        try {
            Document doc = reader.read(Files.newInputStream(filePath));
            xmlStr = doc.asXML();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return XMLUtil.simpleXmlToBean(xmlStr);
    }

}
