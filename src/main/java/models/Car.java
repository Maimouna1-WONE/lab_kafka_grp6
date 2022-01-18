package models;

import java.util.Date;

public class Car {
    private String id;
    private String carNumber;
    private String brand;
    private double speed;
    private double limitedSpeed;
    private String date;

    public Car(){}
    public Car(String id, String carNumber, String brand, double speed, double limitedSpeed, String date) {
        this.id = id;
        this.carNumber = carNumber;
        this.brand = brand;
        this.speed = speed;
        this.limitedSpeed = limitedSpeed;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getLimitedSpeed() {
        return limitedSpeed;
    }

    public void setLimitedSpeed(double limitedSpeed) {
        this.limitedSpeed = limitedSpeed;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "models.Car{" +
                "id='" + id + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", brand='" + brand + '\'' +
                ", speed=" + speed +
                ", limitedSpeed=" + limitedSpeed +
                ", date='" + date + '\'' +
                '}';
    }
}
