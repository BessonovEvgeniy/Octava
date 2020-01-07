package octava.service.impl.observations.processors;

public interface Handler<T> {

    void setNext(Handler handler);

    void handleNext(T rdm);

    void handle(T rdm);
}
