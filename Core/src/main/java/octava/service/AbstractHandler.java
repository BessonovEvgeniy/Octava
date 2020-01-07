package octava.service;

import octava.service.impl.observations.processors.Handler;

public abstract class AbstractHandler<T> implements Handler<T> {

    private Handler handler;

    @Override
    public void setNext(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void handleNext(T t) {
        if (handler != null) {
            handler.handle(t);
        }
    }

    @Override
    public abstract void handle(T t);
}
