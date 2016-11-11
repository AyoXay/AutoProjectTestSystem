package com.flag.xu.project.system.input;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Input handler abstract
 *
 * @Authuor Administrator
 * @Create 2016-11-10-14:09
 */
public abstract class InputHandler {

    protected volatile InputHandler nextHandler;
    protected volatile InputHandler prevHandler;
    protected InputPipeLine inputPipeLine;

    public InputHandler(){}

    public InputHandler(InputPipeLine inputPipeLine){
        this.inputPipeLine = inputPipeLine;
    }

    public abstract Object handleInput(String path);

    protected String getPathType(String path){
        assert path != null;
        String result = "";
        Path source = Paths.get(path);
        try {
            result = Files.probeContentType(source);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result == null ? "" : result;
    }

}
