package model.observations.header;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import rinex.config.AppInitializer;
import rinex.config.MvcConfiguration;
import rinex.exception.RinexLineLengthMismatchException;
import rinex.model.observations.header.impl.WaveLengthFact;
import rinex.service.impl.observations.header.impl.WavelengthFactParserServiceImpl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class, MvcConfiguration.class})
@WebAppConfiguration
public class WaveLengthFactTest {

    @Autowired
    private WavelengthFactParserServiceImpl parser;

    private Collection<String> waveLengthLinesSequence = new LinkedList<>();

    private Iterator<String> waveLengthLinesIterator;

    public WaveLengthFactTest() {
        init();
    }

    public void init(){
        waveLengthLinesSequence.add("     1     1                                                WAVELENGTH FACT L1/2");
        waveLengthLinesSequence.add("     1     2     6   G14   G15   G16   G17   G18   G19      WAVELENGTH FACT L1/2");
        waveLengthLinesSequence.add("     1     2     2   G 9   G12                              WAVELENGTH FACT L1/2");
        //WAVELENGTH FACTOR CHANGED FOR 2 SATELLITES ***        COMMENT
        //NOW 8 SATELLITES HAVE WL FACT 1 AND 2!                COMMENT
    }

    @Test
    public void testParseValidWaveLengthFactEmptySat() {
        WaveLengthFact wavelengthFact;

        String line = "     1     1                                                WAVELENGTH FACT L1/2";

        wavelengthFact = parser.parse(line);

        Assert.assertTrue(wavelengthFact.getFreq1().equals(WaveLengthFact.Ambiguities.FullCycle));
        Assert.assertTrue(wavelengthFact.getFreq2().equals(WaveLengthFact.Ambiguities.FullCycle));
        Assert.assertTrue(CollectionUtils.isEmpty(wavelengthFact.getSats()));

        line = "     1     0                                                WAVELENGTH FACT L1/2";
        wavelengthFact = parser.parse(line);
        Assert.assertTrue(wavelengthFact.getFreq1().equals(WaveLengthFact.Ambiguities.FullCycle));
        Assert.assertTrue(wavelengthFact.getFreq2().equals(WaveLengthFact.Ambiguities.NoFreq));
        Assert.assertTrue(CollectionUtils.isEmpty(wavelengthFact.getSats()));

        line = "     1                                                      WAVELENGTH FACT L1/2";
        wavelengthFact = parser.parse(line);
        Assert.assertTrue(wavelengthFact.getFreq1().equals(WaveLengthFact.Ambiguities.FullCycle));
        Assert.assertTrue(wavelengthFact.getFreq2().equals(WaveLengthFact.Ambiguities.NoFreq));
        Assert.assertTrue(CollectionUtils.isEmpty(wavelengthFact.getSats()));
    }

    @Test
    public void testParseValidWaveLengthFactNotEmptySat() {
        WaveLengthFact wavelengthFact;

        String line = "     1     2     6   G14   G15   G16   G17   G18   G19      WAVELENGTH FACT L1/2";

        Collection<String> requiredSat = Arrays.asList("G14", "G15", "G16", "G17", "G18", "G19");

        wavelengthFact = parser.parse(line);

        Assert.assertTrue(wavelengthFact.getFreq1().equals(WaveLengthFact.Ambiguities.FullCycle));
        Assert.assertTrue(wavelengthFact.getFreq2().equals(WaveLengthFact.Ambiguities.HalfCycle));
        Assert.assertTrue(wavelengthFact.getSats().containsAll(requiredSat));

        line = "     1     2     2   G 9   G12                              WAVELENGTH FACT L1/2";

        requiredSat = Arrays.asList("G09", "G12");

        wavelengthFact = parser.parse(line);

        Assert.assertTrue(wavelengthFact.getFreq1().equals(WaveLengthFact.Ambiguities.FullCycle));
        Assert.assertTrue(wavelengthFact.getFreq2().equals(WaveLengthFact.Ambiguities.HalfCycle));
        Assert.assertTrue(wavelengthFact.getSats().containsAll(requiredSat));
    }

    @Test
    public void testLineLengthMismatch() {

        RinexLineLengthMismatchException exception = assertThrows(RinexLineLengthMismatchException.class, () ->
                parser.parse("     1     1                                                WAVELENGTH FACT L1/2 ")
        );
        Assert.assertTrue(exception.getMessage() != null);


        exception = assertThrows(RinexLineLengthMismatchException.class, () ->
                parser.parse("    1     1                                                WAVELENGTH FACT L1/2")
        );
        Assert.assertTrue(exception.getMessage() != null);
    }

    //TODO add tests for invalid data
}