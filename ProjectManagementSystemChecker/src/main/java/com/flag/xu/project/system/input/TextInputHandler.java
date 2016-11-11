package com.flag.xu.project.system.input;

/**
 * handle the txt input type
 *
 * @Authuor Administrator
 * @Create 2016-11-10-14:08
 */
public class TextInputHandler extends InputHandler {

    public Object handleInput(String path) {
        if (getPathType(path).endsWith("plain")) {
            System.out.println(this.getClass().getSimpleName() + " handle");
        } else {
            if (nextHandler == null)
                return null;
            nextHandler.handleInput(path);
        }
        return new String[0];
    }
}
