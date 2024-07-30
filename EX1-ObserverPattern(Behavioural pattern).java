/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Sridhar
 */
import java.util.ArrayList;
import java.util.List;

// Subject
interface NewsSubject {
    void addObserver(NewsObserver observer);
    void removeObserver(NewsObserver observer);
    void notifyObservers(String news);
}

class NewsAgency implements NewsSubject {
    private List<NewsObserver> observers = new ArrayList<>();
    
    @Override
    public void addObserver(NewsObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(NewsObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String news) {
        for (NewsObserver observer : observers) {
            observer.update(news);
        }
    }
}

// Observer
interface NewsObserver {
    void update(String news);
}

class NewsSubscriber implements NewsObserver {
    private String name;

    public NewsSubscriber(String name) {
        this.name = name;
    }

    @Override
    public void update(String news) {
        System.out.println(name + " received news: " + news);
    }
}

public class ObserverPattern {
    public static void main(String[] args) {
        NewsAgency newsAgency = new NewsAgency();

        NewsSubscriber subscriber1 = new NewsSubscriber("Alice");
        NewsSubscriber subscriber2 = new NewsSubscriber("Bob");

        newsAgency.addObserver(subscriber1);
        newsAgency.addObserver(subscriber2);

        newsAgency.notifyObservers("Breaking News: New Design Pattern Released!");
    }
}

