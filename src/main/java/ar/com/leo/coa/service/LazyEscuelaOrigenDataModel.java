package ar.com.leo.coa.service;

import ar.com.leo.coa.helper.LazySorter;
import ar.com.leo.coa.model.EscuelaOrigen;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyEscuelaOrigenDataModel extends LazyDataModel<EscuelaOrigen> {

    private EscuelaService escuelaService;
    private List<EscuelaOrigen> escuelasOrigen;

    public LazyEscuelaOrigenDataModel(EscuelaService escuelaService) {
        this.escuelaService = escuelaService;
    }

    @Override
    public Object getRowKey(EscuelaOrigen eo) {
        return eo.getId();
    }

    @Override
    public EscuelaOrigen getRowData(String rowKey) {

        Integer id = Integer.valueOf(rowKey);

        for (EscuelaOrigen eo : escuelasOrigen) {
            if (id.equals(eo.getId())) {
                return eo;
            }
        }
        return null;
    }

    @Override
    public List<EscuelaOrigen> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        escuelasOrigen = new ArrayList<EscuelaOrigen>();

        //filter
        for (EscuelaOrigen eo : escuelaService.obtenerEscuelasOrigen(first, pageSize, sortOrder, sortField)) {
            boolean match = true;

            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        String fieldValue = String.valueOf(eo.getClass().getField(filterProperty).get(eo));

                        if (filterValue == null || fieldValue.startsWith(filterValue.toString())) {
                            match = true;
                        } else {
                            match = false;
                            break;
                        }
                    } catch (Exception e) {
                        match = false;
                    }
                }
            }

            if (match) {
                escuelasOrigen.add(eo);
            }
        }

        //sort
        if (sortField != null) {
            Collections.sort(escuelasOrigen, new LazySorter(sortField, sortOrder));
        }

        //rowCount
//        int dataSize = (int) escuelaService.obtenerCantidadEscuelasOrigen();
        int dataSize = escuelasOrigen.size();
        this.setRowCount(dataSize);

        //paginate
        if (dataSize > pageSize) {
            try {
                return escuelasOrigen.subList(first, first + pageSize);
            } catch (IndexOutOfBoundsException e) {
                return escuelasOrigen.subList(first, first + (dataSize % pageSize));
            }
        } else {
            return escuelasOrigen;
        }

        // --------------------------------------------------------------------------------------
        // with datatable pagination limits
//        escuelasOrigen = escuelaService.obtenerEscuelasOrigen(first, pageSize);
//
//        // set the total of escuelasOrigen
//        if (getRowCount() <= 0) {
//            setRowCount(escuelaService.obtenerEscuelasOrigen().size());
//        }
//
//        // set the page size
//        setPageSize(pageSize);
//
//        return escuelasOrigen;
    }

}
