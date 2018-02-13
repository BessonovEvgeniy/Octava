package ppa.service;

import ppa.model.observation.ReceiverDataModel;

import java.io.BufferedReader;

public interface State {

    void read(BufferedReader reader, ReceiverDataModel data) throws Exception;
}