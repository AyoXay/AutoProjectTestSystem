import com.flag.xu.project.system.input.InputPipeLine;
import com.flag.xu.project.system.input.TextInputHandler;
import com.flag.xu.project.system.input.XmlInputHandler;
import com.flag.xu.project.system.input.exception.NoHandlerInPipeLine;
import org.junit.Test;

/**
 * @Authuor Administrator
 * @Create 2016-11-10-15:17
 */
public class InputUnitTest {
    @Test
    public void test_input_handler_chain() throws NoHandlerInPipeLine {
        InputPipeLine pipeLine = new InputPipeLine();
        pipeLine.addInputHandler(new XmlInputHandler())
        .addInputHandler(new TextInputHandler()).startHandle("/hello.txt");
    }

    @Test
    public void test_input_handler_getpathtype(){
        new TextInputHandler().handleInput("c:/slkfj/iwej/sss.gif");
    }

    @Test
    public void test_inputhandler_nohandler() throws NoHandlerInPipeLine {
        new InputPipeLine().startHandle("c:/slkfj/iwej/sss.gif");
    }
}
