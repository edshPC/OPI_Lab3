package beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;

@Named("abstractPoint")
@SessionScoped
public class Hit implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private float x = 0;
    private float y = 0;
    private float r = 1;
    private boolean result;
    private String currentTime;
    private long executionTime;

    public Hit() {
    }

    public Hit(float x, float y, float r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public Hit(long id, float x, float y, float r, boolean result, String currentTime, long executionTime) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
        this.currentTime = currentTime;
        this.executionTime = executionTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getResultWord(){
        return (result)
                ? "Попадание"
                : "Промах";
    }

    public String getResultString(){
        return (result)
                ? "попадание"
                : "промах";
    }

    public String getResultHTMLClass(){
        return (result)
                ? "green-status"
                : "red-status";
    }

    @Override
    public String toString() {
        return "Hit{" +
                "x=" + x +
                ", y=" + y +
                ", r=" + r +
                ", current_time='" + currentTime + '\'' +
                ", execution_time='" + executionTime + '\'' +
                ", result=" + result +
                '}';
    }
}
