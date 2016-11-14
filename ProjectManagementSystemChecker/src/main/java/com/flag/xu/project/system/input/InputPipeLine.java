package com.flag.xu.project.system.input;

import com.flag.xu.project.system.exception.NoHandlerInPipeLineException;

/**
 * @Authuor Administrator
 * @Create 2016-11-10-14:21
 */
public class InputPipeLine {

    final InputHandler head;
    final InputHandler tail;
    private Object obj = null;
    private boolean initObj;

    public InputPipeLine(){
        head = new HeadHandler(this);
        tail = new TailHandler(this);
        head.nextHandler = tail;
        tail.prevHandler = head;
    }

    public InputPipeLine addInputHandler(InputHandler inputHandler){
        synchronized (this) {
            InputHandler temp = tail.prevHandler;
            tail.prevHandler = inputHandler;
            inputHandler.nextHandler = tail;
            inputHandler.prevHandler = temp;
            temp.nextHandler = inputHandler;
        }
        return this;
    }

    public Object getObj(){
        while(!initObj){
        }
        return obj;
    }

    public void startHandle(String path) throws NoHandlerInPipeLineException {
        initObj = false;
        if (path == null)
            return;
        if (head.nextHandler instanceof TailHandler)
            throw new NoHandlerInPipeLineException();
        obj = head.nextHandler.handleInput(path);
        initObj = true;
    }
}

class HeadHandler extends InputHandler{

    public HeadHandler(InputPipeLine inputPipeLine) {
        super(inputPipeLine);
    }

    public Object handleInput(String path) {
        return null;
    }
}

class TailHandler extends InputHandler{

    public TailHandler(InputPipeLine inputPipeLine) {
        super(inputPipeLine);
    }

    public Object handleInput(String path) {
        return null;
    }
}