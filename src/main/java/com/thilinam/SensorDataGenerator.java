package com.thilinam;

import org.apache.commons.codec.binary.Hex;
import org.eclipse.paho.client.mqttv3.*;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import static com.thilinam.DateUtil.dateToISOstring;

public class SensorDataGenerator implements Runnable {

    public static String TOPIC = "sensor-data-1";

    @Override
    public void run() {
        try {
            IMqttClient publisher = this.getPublisher();
            MqttConnectOptions config = this.publisherConfigurations();
            String sensorData = generateSensorData();
            publisher.connect(config);  // connect publisher with settings

            this.publishMessage(publisher, sensorData);

            publisher.disconnect();
            publisher.close();
        } catch (MqttException e) {
            System.out.println("Exception occurred related to Mqtt context :" + e.getMessage());
        } catch (Exception e) {
            System.out.println("UnChecked Exception occurred while execution of the program :" + e.getMessage());
        }
    }

    private String generateSensorData() {
        String deviceId = "5000"; // device id keep as a constant since it's not use for data generation
        String date = dateToISOstring(new Date());
        String sensorData = deviceId + "::" + new Random().nextInt(25) + "@" + Math.random() + "|" + new Random().nextInt(25) + "@" + Math.random() + "::" + date;
        return sensorData;
    }

    private IMqttClient getPublisher() throws MqttException {
        String publisherId = UUID.randomUUID().toString();
        IMqttClient publisher = new MqttClient("tcp://localhost:1883", publisherId);
        return publisher;
    }

    private MqttConnectOptions publisherConfigurations() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        return options;
    }

    private void publishMessage(IMqttClient publisher, String sensorData) throws MqttException {
        if (!publisher.isConnected()) {
            System.out.println("Client is not connected to the broker for publishing messages");
        } else {
            MqttMessage msg = new MqttMessage();
            msg.setQos(1);
            msg.setRetained(Boolean.TRUE);
            msg.setPayload(this.convertToHexa(sensorData));
            publisher.publish(TOPIC,msg);
        }
    }

    private byte[] convertToHexa(String convertValue){
        byte[] byteArray = convertValue.getBytes(StandardCharsets.UTF_8);
        String convertedResult = Hex.encodeHexString(byteArray);
        return convertedResult.getBytes(StandardCharsets.UTF_8);
    }
}
