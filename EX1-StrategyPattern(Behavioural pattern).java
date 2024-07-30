/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Sridhar
 */
// Strategy
interface RouteStrategy {
    void buildRoute(String start, String end);
}

class FastestRoute implements RouteStrategy {
    @Override
    public void buildRoute(String start, String end) {
        System.out.println("Building fastest route from " + start + " to " + end);
    }
}

class ScenicRoute implements RouteStrategy {
    @Override
    public void buildRoute(String start, String end) {
        System.out.println("Building scenic route from " + start + " to " + end);
    }
}

// Context
class NavigationApp {
    private RouteStrategy routeStrategy;

    public void setRouteStrategy(RouteStrategy routeStrategy) {
        this.routeStrategy = routeStrategy;
    }

    public void buildRoute(String start, String end) {
        routeStrategy.buildRoute(start, end);
    }
}

public class StrategyPattern {
    public static void main(String[] args) {
        NavigationApp app = new NavigationApp();

        app.setRouteStrategy(new FastestRoute());
        app.buildRoute("Home", "Work");

        app.setRouteStrategy(new ScenicRoute());
        app.buildRoute("Home", "Park");
    }
}

