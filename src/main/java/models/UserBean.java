package models;

import database.DatabaseHandler;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Destroyed;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.event.Observes;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import utils.AreaValidator;
import utils.DataValidator;
import utils.MBeanRegUtil;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;


@Named("userBean")
@ApplicationScoped
public class UserBean extends NotificationBroadcasterSupport implements UserMXBean, Serializable {
    private Point point = new Point();
    private ArrayList<Point> requests;
    int sequenceNumber = 1;
    int failedAttempts = 0;
    @Inject
    DataValidator dataValidator;
    @Inject
    IntervalBean intervalBean;

    @PostConstruct
    public void loadPointsFromDb() {
        this.requests = DatabaseHandler.getDatabaseManager().loadCollection();
    }

    public void init(@Observes @Initialized(ApplicationScoped.class) Object unused) {
        loadPointsFromDb();
        MBeanRegUtil.registerBean(this, "main");
    }

    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object unused) {
        MBeanRegUtil.unregisterBean(this);
    }

    public String add() throws IOException {
        long timer = System.nanoTime();
        point.setCurrentTime(DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()));
        point.setSuccess(AreaValidator.checkArea(point));
        point.setExecutionTime(String.valueOf(String.format("%.2f", ((System.nanoTime() - timer) * 0.000001))));

        if (point.getX() == null || point.getY() == null || point.getR() == null) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("error.xhtml");
        } else if (!dataValidator.isDataCorrect(point.getX(), point.getY(), point.getR())) {
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
        intervalBean.click();

        boolean success = point.getSuccess();
        if (!success) {
            if (++failedAttempts == 4) {
                Notification notification = new Notification(
                        "Four failed attempts",
                        getClass().getSimpleName(),
                        sequenceNumber++,
                        "User has made four failed attempts in a row!"
                );
                sendNotification(notification);
                failedAttempts = 0;
            }
        } else {
            failedAttempts = 0;
        }
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
            if (x >= -5 && x <= 5 && y >= -5 && y <= 5 && (r == 1 || r == 2 || r == 3 || r == 4 || r == 5)) {
                final Point attemptBean = new Point(x, y, r);
                attemptBean.setCurrentTime(DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()));
                attemptBean.setSuccess(AreaValidator.checkArea(attemptBean));
                attemptBean.setExecutionTime(String.valueOf(String.format("%.2f", ((System.nanoTime() - timer) * 0.000001))));
                this.addPoint(attemptBean);
            } else {
                return "error.xhtml?faces-redirect=true";

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
    public int getRequestsNumber() {
        return requests.size();
    }

    @Override
    public int getFailedRequestsNumber() {
        return (int) requests.stream()
                .filter(point -> !point.getSuccess())
                .count();
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[] { AttributeChangeNotification.ATTRIBUTE_CHANGE };
        String name = AttributeChangeNotification.class.getName();
        String description = "User has made four failed attempts in a row!";
        MBeanNotificationInfo info = new MBeanNotificationInfo(types, name, description);
        return new MBeanNotificationInfo[] { info };
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "point=" + point +
                ", requests=" + requests +
                '}';
    }
}
