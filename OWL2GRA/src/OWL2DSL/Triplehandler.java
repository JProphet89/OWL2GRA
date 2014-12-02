package OWL2DSL;

/**
 * Created by jprophet89 on 03/09/14.
 */
public class Triplehandler {
    public String range;
    public String domain;
    public String prop;
    public Triplehandler(String range, String prop, String domain) {
        this.range=range;
        this.domain=domain;
        this.prop=prop;

    }
}
