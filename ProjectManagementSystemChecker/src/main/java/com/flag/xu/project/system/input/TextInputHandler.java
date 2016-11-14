package com.flag.xu.project.system.input;

import com.flag.xu.project.system.util.PathUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * handle the txt input type
 *
 * @Authuor Administrator
 * @Create 2016-11-10-14:08
 */
public class TextInputHandler extends InputHandler {

    public Object handleInput(String path) {
        if (path == null)
            return null;
        Object obj = null;
        if (path.endsWith(".txt")) {
            obj = handleTextInput(path);
        } else {
            if (nextHandler == null)
                return null;
            nextHandler.handleInput(path);
        }
        return obj;
    }

    private Object handleTextInput(String path){
        assert path != null;
        InputStream inputStream;
        String text = "";
        try {
            inputStream = Files.newInputStream(PathUtil.getPath(TextInputHandler.class, path));
            byte[] bytes = new byte[1024];
            int i = inputStream.read(bytes);
            while(i > 0){
                i = inputStream.read(bytes);
                text += new String(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
