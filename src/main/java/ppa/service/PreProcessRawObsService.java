package ppa.service;

import jdk.nashorn.internal.runtime.ParserException;

import java.io.BufferedReader;
import java.io.IOException;

public interface PreProcessRawObsService {

    String preProcess(String rawObs);

    String preProcess(BufferedReader reader) throws IOException;

    double[] convertAndPreProcessRawObs(String rawObs, int numTypesOfObs) throws ParserException;
}
