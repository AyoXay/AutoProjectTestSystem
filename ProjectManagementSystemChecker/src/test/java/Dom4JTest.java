import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.junit.Test;

/**
 * @Authuor Administrator
 * @Create 2016-11-11-10:57
 */
public class Dom4JTest {

    @Test
    public void test_dom4j() throws DocumentException {
        String xml = "<person><name>zhangsan</name><sex>male</sex></person>";
        Document document = DocumentHelper.parseText(xml);
        System.out.println(document.getRootElement().getName());
    }

    @Test
    public void test_Type(){
        System.out.println(Long.class.getName() + "\t" + long.class.getName());
    }
}
