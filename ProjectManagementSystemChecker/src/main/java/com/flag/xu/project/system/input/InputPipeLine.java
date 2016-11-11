package com.flag.xu.project.system.input;

import com.flag.xu.project.system.input.exception.NoHandlerInPipeLine;

/**
 * @Authuor Administrator
 * @Create 2016-11-10-14:21
 */
public class InputPipeLine {

    final InputHandler head;
    final InputHandler tail;

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

    public void startHandle(String path) throws NoHandlerInPipeLine {
        if (path == null)
            return;
        if (head.nextHandler instanceof TailHandler)
            throw new NoHandlerInPipeLine();
        head.nextHandler.handleInput(path);
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