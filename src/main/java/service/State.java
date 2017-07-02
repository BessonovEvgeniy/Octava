package service;

import model.observations.ReceiverDataModel;

import java.io.BufferedReader;

public interface State {

    void read(BufferedReader reader, ReceiverDataModel data) throws Exception;
}