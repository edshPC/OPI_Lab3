package utils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Arrays;

@Named
@ApplicationScoped
public class DataValidator implements Serializable {
//    Integer[] allowedR = new Integer[] {1, 2, 3, 4, 5};

    public boolean isDataCorrect(Double x, Double y, Double r) {
        return /*Arrays.asList(allowedR).contains(r)*/
                (r == 1 || r == 2 || r == 3 || r == 4 || r ==5)
                && y >= -5 && y <= 3
                && (x == -5 || x == -4 || x == -3 || x == -2 || x == -1 || x == 0 || x == 1);
    }
}
