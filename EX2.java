/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Sridhar
 */
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        // Create devices
        Device light1 = DeviceFactory.createDevice("light", 1);
        Device thermostat1 = DeviceFactory.createDevice("thermostat", 2);
        Device door1 = DeviceFactory.createDevice("door lock", 3);

        // Create users
        User adminUser = new User("AdminUser", "admin");
        User regularUser = new User("RegularUser", "user");
        User guestUser = new User("GuestUser", "guest");

        // Create proxies
        DeviceProxy lightProxy = new DeviceProxy(light1, adminUser);
        DeviceProxy thermostatProxy = new DeviceProxy(thermostat1, regularUser);
        DeviceProxy doorProxy = new DeviceProxy(door1, guestUser);

        // Create smart home system
        SmartHomeSystem smartHome = new SmartHomeSystem();
        smartHome.addDevice(lightProxy);
        smartHome.addDevice(thermostatProxy);
        smartHome.addDevice(doorProxy);

        // Create triggers
        Map<String, Integer> properties = new HashMap<>();
        properties.put("temperature", 80);
        
        Runnable action = () -> System.out.println("Action executed!");

        Trigger trigger = new Trigger("temperature > 75", action, properties);
        smartHome.addTrigger(trigger);

        // Execute triggers
        smartHome.executeTriggers(); // Output: Action executed!

        // Check device statuses
        List<String> statusReport = smartHome.getDeviceStatus();
        for (String status : statusReport) {
            System.out.println(status);
        }
    }

}
abstract class Device {
    protected int id;
    protected String type;
    protected String status;

    public Device(int id, String type) {
        this.id = id;
        this.type = type;
        this.status = "off"; // Default status
    }

    public abstract void turnOn();
    public abstract void turnOff();
    public abstract String getStatus();
}
class Light extends Device {
    public Light(int id) {
        super(id, "light");
    }

    @Override
    public void turnOn() {
        status = "on";
        System.out.println("Light " + id + " is now on.");
    }

    @Override
    public void turnOff() {
        status = "off";
        System.out.println("Light " + id + " is now off.");
    }

    @Override
    public String getStatus() {
        return "Light " + id + " is " + status + ".";
    }
}
class Thermostat extends Device {
    private int temperature;

    public Thermostat(int id) {
        super(id, "thermostat");
        this.temperature = 70; // Default temperature
    }

    @Override
    public void turnOn() {
        status = "on";
        System.out.println("Thermostat " + id + " is now on. Temperature is set to " + temperature + " degrees.");
    }

    @Override
    public void turnOff() {
        status = "off";
        System.out.println("Thermostat " + id + " is now off.");
    }

    @Override
    public String getStatus() {
        return "Thermostat " + id + " is " + status + ". Temperature is " + temperature + " degrees.";
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
}
class DoorLock extends Device {
    public DoorLock(int id) {
        super(id, "door lock");
        this.status = "locked"; // Default status
    }

    @Override
    public void turnOn() {
        status = "locked";
        System.out.println("Door " + id + " is now locked.");
    }

    @Override
    public void turnOff() {
        status = "unlocked";
        System.out.println("Door " + id + " is now unlocked.");
    }

    @Override
    public String getStatus() {
        return "Door " + id + " is " + status + ".";
    }
}
class DeviceFactory {
    public static Device createDevice(String type, int id) {
        switch (type.toLowerCase()) {
            case "light":
                return new Light(id);
            case "thermostat":
                return new Thermostat(id);
            case "door lock":
                return new DoorLock(id);
            default:
                throw new IllegalArgumentException("Unknown device type: " + type);
        }
    }
}
class DeviceProxy extends Device {
    private Device realDevice;
    private User user;

    public DeviceProxy(Device realDevice, User user) {
        super(realDevice.id, realDevice.type);
        this.realDevice = realDevice;
        this.user = user;
    }

    @Override
    public void turnOn() {
        if (AccessControl.canOperate(user, realDevice.type)) {
            realDevice.turnOn();
            System.out.println(user.getUsername() + " turned on " + realDevice.type + " " + realDevice.id);
        } else {
            System.out.println("Access Denied: " + user.getUsername() + " does not have permission to turn on " + realDevice.type);
        }
    }

    @Override
    public void turnOff() {
        if (AccessControl.canOperate(user, realDevice.type)) {
            realDevice.turnOff();
            System.out.println(user.getUsername() + " turned off " + realDevice.type + " " + realDevice.id);
        } else {
            System.out.println("Access Denied: " + user.getUsername() + " does not have permission to turn off " + realDevice.type);
        }
    }

    @Override
    public String getStatus() {
        return realDevice.getStatus();
    }
}
class AccessControl {
    public static boolean canOperate(User user, String deviceType) {
        switch (user.getRole().toLowerCase()) {
            case "admin":
                return true; // Admin can control all devices
            case "user":
                // Regular users can control only lights and thermostats
                return deviceType.equalsIgnoreCase("light") || deviceType.equalsIgnoreCase("thermostat");
            case "guest":
                // Guests have no control over devices
                return false;
            default:
                return false; // Unknown roles have no access
        }
    }
}
class User {
    private String username;
    private String role;

    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }
}


class Trigger {
    private String condition;
    private Runnable action;
    private Map<String, Integer> properties;

    public Trigger(String condition, Runnable action, Map<String, Integer> properties) {
        this.condition = condition;
        this.action = action;
        this.properties = properties;
    }

    public void checkAndExecute() {
        if (evaluateCondition()) {
            action.run();
        }
    }

    private boolean evaluateCondition() {
        String[] parts = condition.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid condition format");
        }

        String property = parts[0];
        String operator = parts[1];
        int value;
        try {
            value = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid value in condition");
        }

        Integer propertyValue = properties.get(property);
        if (propertyValue == null) {
            throw new IllegalArgumentException("Unknown property: " + property);
        }

        switch (operator) {
            case ">":
                return propertyValue > value;
            case "<":
                return propertyValue < value;
            case ">=":
                return propertyValue >= value;
            case "<=":
                return propertyValue <= value;
            case "==":
                return propertyValue == value;
            case "!=":
                return propertyValue != value;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
}


class SmartHomeSystem {
    private List<Device> devices = new ArrayList<>();
    private List<Trigger> triggers = new ArrayList<>();

    public void addDevice(Device device) {
        devices.add(device);
    }

    public void addTrigger(Trigger trigger) {
        triggers.add(trigger);
    }

    public List<String> getDeviceStatus() {
        List<String> statusReport = new ArrayList<>();
        for (Device device : devices) {
            statusReport.add(device.getStatus());
        }
        return statusReport;
    }

    public void executeTriggers() {
        for (Trigger trigger : triggers) {
            trigger.checkAndExecute();
        }
    }
}

