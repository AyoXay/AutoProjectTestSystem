import com.flag.xu.project.system.config.loader.PropertiesFileConfig;
import org.junit.Test;

/**
 * @Authuor Administrator
 * @Create 2016-11-11-14:52
 */
public class PropertiesConfigTest {
    @Test
    public void test_config(){
        System.out.println(PropertiesFileConfig.getProperties().getProperty("package"));
    }
}
