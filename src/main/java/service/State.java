package service;

import model.observations.ReceiverDataModel;

import java.io.BufferedReader;
import java.io.IOException;

public interface State {
    void read(BufferedReader reader, ReceiverDataModel data) throws IOException;
}