package beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named("pointHandler")
@SessionScoped
public class Results implements Serializable {
}
