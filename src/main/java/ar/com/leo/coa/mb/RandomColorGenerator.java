package ar.com.leo.coa.mb;

import java.io.Serializable;
import java.util.Random;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Leo
 */

@ManagedBean
@ApplicationScoped
public class RandomColorGenerator implements Serializable {

    public String randomColor() {

        // create random object - reuse this as often as possible
        Random random = new Random();

        // create a big random number - maximum is ffffff (hex) = 16777215 (dez)
        int nextInt = random.nextInt(256 * 256 * 256);

        // format it as hexadecimal string (with hashtag and leading zeros)
        String colorCode = String.format("#%06x", nextInt);

        return colorCode;
    }

}
