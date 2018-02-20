package ppa.service;

import java.io.BufferedReader;
import java.io.IOException;

public interface PreProcessRawObsService {

    String preProcess(String rawObs);

    String preProcess(BufferedReader reader) throws IOException;
}
