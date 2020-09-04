package com.cosminsanda;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RecordBuilder {

    private final String _city;
    private final boolean _sorted;
    private Date _lastTimestamp = null;
    private final Random _random = new Random();

    public RecordBuilder(String city, boolean sorted) {
        _city = city;
        _sorted = sorted;
    }

    public String getRecord() {
        if (!_sorted || _lastTimestamp == null) {
            _lastTimestamp = new Date(ThreadLocalRandom.current().nextLong(System.currentTimeMillis()));
        } else {
            _lastTimestamp = new Date(ThreadLocalRandom.current().nextLong(_lastTimestamp.getTime(), System.currentTimeMillis()));
        }

        var messXml = (_random.nextInt(8) == 0);
        var messValues = (_random.nextInt(10) == 0);
        var hasCelsius = (_random.nextInt(2) == 0);
        var hasFahrenheit = (_random.nextInt(4) == 0);

        var output1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<data>\n\t</city>";
        var output2 = "</city>\n\t<temperature>\n\t";
        var output3 = "</temperature>\n\t<measured_at_ts>";
        var output4 = "</measured_at_ts>\n</data>";
        if (messXml) {
            output1 = Util.messXml(output1, _random);
            output2 = Util.messXml(output2, _random);
            output3 = Util.messXml(output3, _random);
            output4 = Util.messXml(output4, _random);

        }
        output1 += Util.randomUpper(_city, .3);

        if (hasCelsius || hasFahrenheit) {
            var temperaturesCelsius = ThreadLocalRandom.current().nextDouble(-50.0, +50.0);
            var temperaturesFahrenheit = temperaturesCelsius * 1.8 + 32;
            if (messValues) {
                temperaturesFahrenheit += 5;
            }
            if (hasCelsius) {
                if (messValues) {
                    output2 += "\t<value unit=\"celsius\">" + String.format("%.2f", temperaturesCelsius) + "abc</value>\n\t";
                } else {
                    output2 += "\t<value unit=\"celsius\">" + String.format("%.2f", temperaturesCelsius) + "</value>\n\t";
                }
            }
            if (hasFahrenheit) {
                if (messValues) {
                    output2 += "\t<value unit=\"fahrenheit\">" + String.format("%.2f", temperaturesFahrenheit) + "abc</value>\n\t";
                } else {
                    output2 += "\t<value unit=\"fahrenheit\">" + String.format("%.2f", temperaturesFahrenheit) + "</value>\n\t";
                }
            }
        }

        var formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        output3 += formatter.format(_lastTimestamp);
        if (messValues) {
            output3 += "asdasda";
        }

        return output1 + output2 + output3 + output4;

    }

}
