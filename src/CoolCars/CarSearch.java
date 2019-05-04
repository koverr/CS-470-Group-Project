/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoolCars;

import java.util.HashMap;
/**
 *
 * @author koryo
 */
public class CarSearch {
    private String Year, Price, Make, Model, Color, Style, Condition, Vin;
    
    public CarSearch (String year, String make, String model, String price, String color, String style, String condition) {
        Year = year;
        Make = make;
        Model = model;
        Price = price;
        Color = color;
        Style = style;
        int conditioni = Integer.parseInt(condition);
        switch (conditioni) {
            case 1:
                Condition = "New";
                break;
            case 2:
                Condition = "Fair";
                break;
            case 3:
                Condition = "Used";
                break;
            default:
                Condition = "Unavailable";
                break;
        }
    }
    
    public String getYear(){ return Year; }
    public String getPrice(){ return Price; }
    public String getMake(){ return Make; }
    public String getModel() { return Model; }
    public String getColor() { return Color; }
    public String getStyle() { return Style; }
    public String getCondition() { return Condition; }
}
