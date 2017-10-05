package ar.com.leo.coa.helper;

/**
 *
 * @author Leo
 */
import ar.com.leo.coa.model.EscuelaOrigen;
import java.util.Comparator;
import org.primefaces.model.SortOrder;

public class LazySorter implements Comparator<EscuelaOrigen> {

    private String sortField;
    private SortOrder sortOrder;

    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public int compare(EscuelaOrigen eo1, EscuelaOrigen eo2) {

        try {
            Object value1 = EscuelaOrigen.class.getField(this.sortField).get(eo1);
            Object value2 = EscuelaOrigen.class.getField(this.sortField).get(eo1);

            int value = ((Comparable) value1).compareTo(value2);

            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;

        } catch (Exception e) {
            e.printStackTrace();

            return 0;
        }
    }
}
