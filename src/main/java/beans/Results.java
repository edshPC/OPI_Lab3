package beans;

import database.DatabaseHandler;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import validation.AreaCheck;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Map;

@Named("pointHandler")
@SessionScoped
public class Results implements Serializable {
    private Hit hit = new Hit();
    private LinkedList<Hit> points = new LinkedList<>();

    public LinkedList<Hit> getPoints() {
        return points;
    }

    public void setPoints(LinkedList<Hit> points) {
        this.points = points;
    }

    @PostConstruct
    public void loadPointsFromDb(){
        this.points = DatabaseHandler.getDatabaseManager().loadCollection();
    }

    public void add(){
        long timer = System.nanoTime();
        hit.setCurrentTime(DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()));
        hit.setResult(AreaCheck.isHit(hit.getX(), hit.getY(), hit.getR()));
        hit.setExecutionTime((long) ((System.nanoTime() - timer) * 0.01));

        this.addPoint(hit);
        hit = new Hit(hit.getX(), hit.getY(), hit.getR());
    }

    public void addFromJS(){
        long timer = System.nanoTime();
        final Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        params.values().forEach(System.out::println);

        try {
            float x = Float.parseFloat(params.get("x"));
            float y = Float.parseFloat(params.get("y"));
            float r = Float.parseFloat(params.get("r"));

            final Hit attemptBean = new Hit(x, y, r);
            attemptBean.setCurrentTime(DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()));
            attemptBean.setResult(AreaCheck.isHit(x, y, r));
            attemptBean.setExecutionTime((long) ((System.nanoTime() - timer) * 0.01));
            this.addPoint(attemptBean);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
        }
    }

    public Hit getHit() {
        return hit;
    }

    public void setHit(Hit hit) {
        this.hit = hit;
    }

    public void addPoint(Hit hit){
        DatabaseHandler.getDatabaseManager().addPoint(hit);
        this.points.addFirst(hit);
    }
}
