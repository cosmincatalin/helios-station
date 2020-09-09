package com.cosminsanda;

import lombok.RequiredArgsConstructor;
import lombok.val;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
public class RecordBuilder {

    private final String city;
    private final boolean notSorted;
    private Date lastTimestamp = null;
    private final Random _random = new Random();

    public String getRecord() {
        if (notSorted || lastTimestamp == null) {
            lastTimestamp = new Date(ThreadLocalRandom.current().nextLong(System.currentTimeMillis()));
        } else {
            lastTimestamp = new Date(ThreadLocalRandom.current().nextLong(lastTimestamp.getTime(), System.currentTimeMillis()));
        }

        val messXml = (_random.nextDouble() < .2);
        val messValues = (_random.nextDouble() < .2);
        val hasCelsius = (_random.nextDouble() < .75);
        val hasFahrenheit = (_random.nextDouble() < .75);

        var output1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<data>\n\t<city>";
        var output2 = "</city>\n\t<temperature>\n\t";
        var output3 = "</temperature>\n\t<measured_at_ts>";
        var output4 = "</measured_at_ts>\n</data>";
        if (messXml) {
            output1 = Util.messXml(output1, _random);
            output2 = Util.messXml(output2, _random);
            output3 = Util.messXml(output3, _random);
            output4 = Util.messXml(output4, _random);

        }
        output1 += Util.randomUpper(city, .3);

        if (hasCelsius || hasFahrenheit) {
            val temperaturesCelsius = ThreadLocalRandom.current().nextDouble(-50.0, +50.0);
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

        val formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        output3 += formatter.format(lastTimestamp);
        if (messValues) {
            output3 += "mess";
        }

        return output1 + output2 + output3 + output4;

    }

}
