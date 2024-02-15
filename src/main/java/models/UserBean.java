package models;

import database.DatabaseHandler;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import utils.AreaValidator;
import utils.DataValidator;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;


@Named("userBean")
@SessionScoped
public class UserBean implements Serializable {
    private Point point = new Point();
    private ArrayList<Point> requests;
    @Inject
    DataValidator dataValidator;

    @PostConstruct
    public void loadPointsFromDb() {
        this.requests = DatabaseHandler.getDatabaseManager().loadCollection();
    }


//    public String checkPoint() {
//        if (!dataValidator.isDataCorrect(point.getX(), point.getY(), point.getR()))
//            return "errorPage?faces-redirect=true";
//        return "main?faces-redirect=true";
//    }

    public String add() throws IOException {
        long timer = System.nanoTime();
        point.setCurrentTime(DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()));
        point.setSuccess(AreaValidator.checkArea(point));
        point.setExecutionTime(String.valueOf(String.format("%.2f", ((System.nanoTime() - timer) * 0.000001))));

//        if (!dataValidator.isDataCorrect(point.getX(), point.getY(), point.getR()))
//            return "error?faces-redirect=true";
        if (!dataValidator.isDataCorrect(point.getX(), point.getY(), point.getR())) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("error.xhtml");
        } else {
            this.addPoint(point);
            point = new Point(point.getX(), point.getY(), point.getR());
        }
        return "main.xhtml?faces-redirect=true";
    }

    public void addPoint(Point point) {
        DatabaseHandler.getDatabaseManager().addPoint(point);
        this.requests.add(0, point);
    }

    public void clearRequests() {
        DatabaseHandler.getDatabaseManager().clearCollection();
        this.requests = new ArrayList<>();
    }

    public String addFromJS() {
        long timer = System.nanoTime();
        final Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        params.values().forEach(System.out::println);

        try {
            double x = Double.parseDouble(params.get("x"));
            double y = Double.parseDouble(params.get("y"));
            double r = Double.parseDouble(params.get("r"));
            if (x >= -5 && x <= 5 && y >= -5 && y <= 5 && (r == 1 || r == 2 || r == 3 || r == 4 || r == 5)){
                final Point attemptBean = new Point(x, y, r);
                attemptBean.setCurrentTime(DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()));
                attemptBean.setSuccess(AreaValidator.checkArea(attemptBean));
                attemptBean.setExecutionTime(String.valueOf(String.format("%.2f", ((System.nanoTime() - timer) * 0.000001))));
                this.addPoint(attemptBean);
            } else {
                return "error?faces-redirect=true";

            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    public ArrayList<Point> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<Point> requests) {
        this.requests = requests;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "point=" + point +
                ", requests=" + requests +
                '}';
    }
}
