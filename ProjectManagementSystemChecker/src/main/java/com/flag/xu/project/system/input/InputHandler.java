package com.flag.xu.project.system.input;

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
}
