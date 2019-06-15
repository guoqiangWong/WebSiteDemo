package org.study.designer;

/**
 * @author ExpanseWong
 */
public interface Subject {
    void registerObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObservers();
}
