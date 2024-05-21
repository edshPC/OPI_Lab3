package models;

import database.DatabaseHandler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Destroyed;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import utils.AreaValidator;
import utils.MBeanRegistryUtil;
import utils.DataValidator;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Map;

@Named
@ApplicationScoped
public class PointHandler extends NotificationBroadcasterSupport implements Serializable, PointHandlerMBean {

    private Point point = new Point();
    private LinkedList<Point> points = new LinkedList<>();
    @Inject
    DataValidator dataValidator;
    private int sequenceNumber = 1;
    private int failedAttempts = 0;

    public LinkedList<Point> getPoints() {
        return points;
    }

    public void init(@Observes @Initialized(ApplicationScoped.class) Object unused) {
        this.points = DatabaseHandler.getDatabaseManager().loadCollection();
        MBeanRegistryUtil.registerBean(this, "main");
    }

    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object unused) {
        MBeanRegistryUtil.unregisterBean(this);
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String add() throws IOException {
        long timer = System.nanoTime();
        point.setCurrentTime(DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()));
        point.setSuccess(AreaValidator.checkArea(point));
        point.setExecutionTime(String.valueOf(String.format("%.2f", ((System.nanoTime() - timer) * 0.000001))));

        if (point.getX() == null || point.getY() == null || point.getR() == null) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("error.xhtml");
        }
        else if (!dataValidator.isDataCorrect(point.getX(), point.getY(), point.getR())) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("error.xhtml");
        } else {
            this.addPoint(point);
            point = new Point(point.getX(), point.getY(), point.getR());
        }
        return "main.xhtml?faces-redirect=true";
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
                return "error.xhtml?faces-redirect=true";

            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    public void addPoint(Point point) {
        DatabaseHandler.getDatabaseManager().addPoint(point);
        this.points.addFirst(point);
        if (!point.getSuccess()) {
            failedAttempts++;
            if (failedAttempts == 4) {
                Notification notification = new Notification(
                        "Four failed attempts",
                        this,
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

    public LinkedList<Point> getRequests() {
        return points;
    }

    public void setRequests(LinkedList<Point> points) {
        this.points = points;
    }

    public void clearRequests() {
        DatabaseHandler.getDatabaseManager().clearCollection();
        this.points = new LinkedList<>();
    }

    @Override
    public long getDotsCount() {
        return this.points.size();
    }

    @Override
    public long getFailedDotsCount() {
        return this.points.stream()
                .filter(point -> !point.getSuccess())
                .count();
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[] { AttributeChangeNotification.ATTRIBUTE_CHANGE };
        String name = AttributeChangeNotification.class.getName();
        String description = "The user has made four failed attempts in a row.";
        MBeanNotificationInfo info = new MBeanNotificationInfo(types, name, description);
        return new MBeanNotificationInfo[] { info };
    }
}
