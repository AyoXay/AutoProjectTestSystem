package com.flag.xu.project.system.input;

/**
 * @Authuor Administrator
 * @Create 2016-11-10-15:18
 */
public class XmlInputHandler extends InputHandler {
    public Object handleInput(String path) {
        if (getPathType(path).endsWith("xml")) {
            System.out.println(this.getClass().getSimpleName() + " handle");
        } else {
            if (nextHandler == null)
                return null;
            nextHandler.handleInput(path);
        }
        return new String[0];
    }
}
