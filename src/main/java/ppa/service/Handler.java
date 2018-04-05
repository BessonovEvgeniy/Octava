package ppa.service;

public interface Handler<T> {

    Handler setNext(Handler handler);

    T handleNext(T rdm);

    T handle(T rdm);
}
