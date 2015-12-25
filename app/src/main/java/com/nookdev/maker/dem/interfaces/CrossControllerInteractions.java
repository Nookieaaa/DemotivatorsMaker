package com.nookdev.maker.dem.interfaces;


public interface CrossControllerInteractions {
    public void performTask(String sender,String receiver,int action, Object data);
    public void getResult(String sender,String receiver,int action, Object data);
}
