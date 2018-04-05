package ppa.service;

public abstract class AbstractHandler<T> implements Handler<T> {

    private Handler handler;

    @Override
    public Handler setNext(Handler handler) {
        this.handler = handler;
        return handler;
    }

    @Override
    public T handleNext(T t) {
        if (handler == null) {
            return t;
        }
        return (T) handler.handle(t);
    }

    @Override
    public abstract T handle(T t);
}
