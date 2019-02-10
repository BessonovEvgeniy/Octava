package service;

import java.io.BufferedReader;
import java.io.IOException;

public interface PreProcessRawObsService {

    String preProcess(BufferedReader reader) throws IOException;

    double[] convertRawObs(String rawObs);
}
