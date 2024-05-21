package utils;

import jakarta.servlet.ServletContextListener;
import lombok.experimental.UtilityClass;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class MBeanRegUtil implements ServletContextListener {

    private final static Map<Class<?>, ObjectName> beans = new HashMap<>();

    public static void registerBean(Object bean, String name) {
        try {
            String domain = bean.getClass().getPackageName();
            String type = bean.getClass().getSimpleName();
            ObjectName objectName = new ObjectName(String.format("%s:type=%s,name=%s", domain, type, name));
            ManagementFactory.getPlatformMBeanServer().registerMBean(bean, objectName);
            beans.put(bean.getClass(), objectName);
            System.out.println("reg " + name);
        } catch (InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException |
                 MalformedObjectNameException ex) {
            ex.printStackTrace();
        }
    }

    public static void unregisterBean(Object bean) {
        if (!beans.containsKey(bean.getClass())) {
            throw new IllegalArgumentException("Specified bean is not registered.");
        }
        try {
            ObjectName objectName = beans.get(bean.getClass());
            ManagementFactory.getPlatformMBeanServer().unregisterMBean(objectName);
        } catch (InstanceNotFoundException | MBeanRegistrationException ex) {
            ex.printStackTrace();
        }
    }

    public static Object getBeanInstance(Class<?> clazz) {
        return beans.get(clazz);
    }
}