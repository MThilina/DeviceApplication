package com.thilinam;

public class SensorData {

    public static void main(String[] args) throws InterruptedException {

        int numberOfDevices = 1; // you can increase the number
        for(int a=0;a<numberOfDevices;a++){
            Thread sensorDataThread= new Thread(new SensorDataGenerator(),String.valueOf(a));
            sensorDataThread.start();
            Thread.sleep(1000); // 1-second sleep as mentioned in the assignment
            System.out.println("Executing Thread Id :"+sensorDataThread.getName());
        }
    }
}
