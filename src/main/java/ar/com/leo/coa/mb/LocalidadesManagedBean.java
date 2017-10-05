package ar.com.leo.coa.mb;

import ar.com.leo.coa.model.Localidad;
import ar.com.leo.coa.model.Provincia;
import ar.com.leo.coa.service.LocalidadService;
import ar.com.leo.coa.service.ProvinciaService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class LocalidadesManagedBean implements Serializable {

    public LocalidadesManagedBean() {
        System.out.println("localidades CONSTRUCTOR");
    }

    @PostConstruct
    public void init() {

        System.out.println("localidades INIT post construct");

        provincias = this.obtenerProvincias();
        localidadesBsAs = this.obtenerPorIdProvincia(1);
    }

    @ManagedProperty("#{localidadService}")
    private LocalidadService localidadService; // Setter required.

    @ManagedProperty("#{provinciaService}")
    private ProvinciaService provinciaService; // Setter required.

    private List<Localidad> localidades; // +getter (no setter necessary)
    private List<Localidad> localidadesBsAs; // +getter (no setter necessary)

    private List<Localidad> localidadesSeleccionadas;
    private Localidad localidadSeleccionada;

    private List<Provincia> provincias; // +getter (no setter necessary)

    private List<Provincia> provinciasSeleccionadas;
    private Provincia provinciaSeleccionada;

    public void setLocalidadService(LocalidadService localidadService) {
        this.localidadService = localidadService;
    }

    public void setProvinciaService(ProvinciaService provinciaService) {
        this.provinciaService = provinciaService;
    }

    public List<Provincia> obtenerProvincias() {
        //obtiene las provincias de la bd coa.provincias
        return provinciaService.obtenerProvincias();
    }

    public List<Provincia> getProvincias() {
        return provincias;
    }

    public void getLocalidadesProv(Provincia provinciaSeleccionada) {
    
        if (provinciaSeleccionada != null && !provinciaSeleccionada.getDescripcion().equals("")) {
            
            if (provinciaSeleccionada.getId() == 5) {
                localidadSeleccionada = new Localidad(5222); // una Localidad de Ciudad de Buenos Aires
                
            } else {
                localidades = localidadService.obtenerLocalidades(provinciaSeleccionada); //obtiene las localidades a partir del index de provincia de la bd coa.localidades
            }

        } else {
            localidades = new ArrayList<Localidad>();
        }
    }

    public void clearLocalidades() {
        localidades = null;
        localidadSeleccionada = null;
    }

    public List<Localidad> obtenerPorIdProvincia(Integer idProvincia) {
        return localidadService.obtenerPorIdProvincia(idProvincia);
    }

    public List<Localidad> getLocalidades() {
        return localidades;
    }

    public List<Localidad> getLocalidadesBsAs() {
        return localidadesBsAs;
    }

    public Localidad getLocalidadSeleccionada() {
        return localidadSeleccionada;
    }

    public void setLocalidadSeleccionada(Localidad localidadSeleccionada) {
        this.localidadSeleccionada = localidadSeleccionada;
    }

    public Provincia getProvinciaSeleccionada() {
        return provinciaSeleccionada;
    }

    public void setProvinciaSeleccionada(Provincia provinciaSeleccionada) {
        this.provinciaSeleccionada = provinciaSeleccionada;
    }

}
