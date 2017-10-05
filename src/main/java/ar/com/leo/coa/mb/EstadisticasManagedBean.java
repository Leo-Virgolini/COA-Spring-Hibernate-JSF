package ar.com.leo.coa.mb;

import ar.com.leo.coa.service.AlumnoService;
import ar.com.leo.coa.service.ComisionService;
import ar.com.leo.coa.service.EscuelaService;
import ar.com.leo.coa.service.MateriaService;
import java.text.DecimalFormat;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Leo
 */
@ManagedBean
public class EstadisticasManagedBean {

    @ManagedProperty("#{materiaService}")
    private MateriaService materiaService; // Setter required.

    @ManagedProperty("#{escuelaService}")
    private EscuelaService escuelaService; // Setter required.

    @ManagedProperty("#{comisionService}")
    private ComisionService comisionService; // Setter required.

    @ManagedProperty("#{alumnoService}")
    private AlumnoService alumnoService; // Setter required.

    private Long cantidadMateriasAlumnos;
    private HashMap<String, Long> materiasAlumnos;
    private Long cantidadEscuelasOrigen;
    private HashMap<String, Long> escuelasOrigen;
    private Long cantidadComisiones;
    private HashMap<String, Long> comisiones;
    private HashMap<String, Long> localidades;
    private HashMap<String, Long> distritos;
    private HashMap<String, Long> provincias;
    private HashMap<String, Long> region6Distritos;
    private Long region6;
    private Long aprobados;
    private Long alumnos;
    private Long alumnosAprobados;
    private String promedioMaterias;
    private String promedioAlumnos;
    private Long alumnosMasculinos;

    private PieChartModel pieModel1;
    private PieChartModel pieModel2;
    private PieChartModel pieModel3;
    private PieChartModel pieModel4;
    private PieChartModel pieModel5;
    private PieChartModel pieModel6;
    private PieChartModel pieModel7;
    private PieChartModel pieModel8;
    private PieChartModel pieModel9;
    private PieChartModel pieModel10;
    private PieChartModel pieModel11;
    private HorizontalBarChartModel horizontalBarModel;

    @PostConstruct
    public void init() {

        materiasAlumnos = materiaService.obtenerMateriasAlumnosYCantidad();
        cantidadMateriasAlumnos = materiaService.obtenerCantidadMateriasAlumno();
        escuelasOrigen = escuelaService.obtenerEscuelasOrigenYCantidad();
        cantidadEscuelasOrigen = escuelaService.obtenerCantidadEscuelasOrigen();
        comisiones = comisionService.obtenerComisionesYCantidad();
        cantidadComisiones = comisionService.obtenerCantidadComisiones();
        localidades = escuelaService.obtenerLocalidadesYCantidad();
        distritos = escuelaService.obtenerDistritosYCantidad();
        provincias = escuelaService.obtenerProvinciasYCantidad();
        region6 = escuelaService.obtenerCantidadRegion6();
        region6Distritos = escuelaService.obtenerCantidadDistritosRegion6();
        aprobados = materiaService.obtenerCantidadMateriasAprobadas();
        alumnosAprobados = materiaService.obtenerCantidadAlumnosAprobados();
        alumnos = alumnoService.obtenerCantidadAlumnos();
        promedioMaterias = new DecimalFormat("#.##").format((double) cantidadMateriasAlumnos / (double) alumnos);
        promedioAlumnos = new DecimalFormat("#.##").format((double) alumnos / (double) cantidadComisiones);
        alumnosMasculinos = alumnoService.obtenerAlumnosMasculinos();

        createPieModel1();
        createPieModel2();
        createPieModel3();
        createPieModel4();
        createPieModel5();
        createPieModel6();
        createPieModel7();
        createPieModel8();
        createPieModel9();
        createPieModel10();
        createPieModel11();
        createHorizontalBarModel();
    }

    // PIE 1: materias alumnos
    public PieChartModel getPieModel1() {
        return pieModel1;
    }

    private void createPieModel1() {

        pieModel1 = new PieChartModel();

        materiasAlumnos.forEach((k, v) -> pieModel1.set(k + " - " + v, v));

        pieModel1.setTitle("Total Materias Alumnos: " + cantidadMateriasAlumnos + " - Total Materias Alumnos distintas: " + materiasAlumnos.size());
        pieModel1.setLegendPosition("nw");
        pieModel1.setShowDataLabels(true);
        pieModel1.setDiameter(350);
        pieModel1.setDataFormat("percent");
//        pieModel1.setDataLabelFormatString("%d");
        pieModel1.setDatatipFormat("%s");
    }

    // PIE 2: escuelas origen
    public PieChartModel getPieModel2() {
        return pieModel2;
    }

    private void createPieModel2() {

        pieModel2 = new PieChartModel();

        escuelasOrigen.forEach((k, v) -> pieModel2.set(k + " - " + v, v));

        pieModel2.setTitle("Total Escuelas Origen: " + cantidadEscuelasOrigen + " - Total Escuelas Origen distintas: " + escuelasOrigen.size());
        pieModel2.setLegendPosition("nw");
        pieModel2.setShowDataLabels(true);
        pieModel2.setDiameter(350);
        pieModel2.setDataFormat("percent");
//        pieModel2.setDataLabelFormatString("%d");
        pieModel2.setDatatipFormat("%s");
    }

    // PIE 4: localidades de escuelas origen
    public PieChartModel getPieModel4() {
        return pieModel4;
    }

    private void createPieModel4() {

        pieModel4 = new PieChartModel();

        localidades.forEach((k, v) -> pieModel4.set(k + " - " + v, v));

        pieModel4.setTitle("Localidades distintas según Escuelas Origen: " + localidades.size());
        pieModel4.setLegendPosition("nw");
        pieModel4.setShowDataLabels(true);
        pieModel4.setDiameter(300);
        pieModel4.setDataFormat("percent");
//        pieModel4.setDataLabelFormatString("%d");
        pieModel4.setDatatipFormat("%s");
    }

    // PIE 5: distritos de escuelas origen
    public PieChartModel getPieModel5() {
        return pieModel5;
    }

    private void createPieModel5() {

        pieModel5 = new PieChartModel();

        distritos.forEach((k, v) -> pieModel5.set(k + " - " + v, v));

        pieModel5.setTitle("Distritos distintos según Escuelas Origen: " + distritos.size());
        pieModel5.setLegendPosition("nw");
        pieModel5.setShowDataLabels(true);
        pieModel5.setDiameter(300);
        pieModel5.setDataFormat("percent");
//        pieModel5.setDataLabelFormatString("%d");
        pieModel5.setDatatipFormat("%s");
    }

    // PIE 6: provincias de escuelas origen
    public PieChartModel getPieModel6() {
        return pieModel6;
    }

    private void createPieModel6() {

        pieModel6 = new PieChartModel();

        provincias.forEach((k, v) -> pieModel6.set(k + " - " + v, v));

        pieModel6.setTitle("Provincias distintas según Escuelas Origen: " + provincias.size());
        pieModel6.setLegendPosition("nw");
        pieModel6.setShowDataLabels(true);
        pieModel6.setDiameter(300);
        pieModel6.setDataFormat("percent");
//        pieModel6.setDataLabelFormatString("%d");
        pieModel6.setDatatipFormat("%s");
    }

    // PIE 7: escuelas origen de Region 6
    public PieChartModel getPieModel7() {
        return pieModel7;
    }

    private void createPieModel7() {

        pieModel7 = new PieChartModel();

        pieModel7.set("Región 6 - " + region6, region6);
        pieModel7.set("Otras - " + (cantidadEscuelasOrigen - region6), cantidadEscuelasOrigen - region6);

        pieModel7.setTitle("Escuelas Origen de Región 6(Vicente López-San Isidro-San Fernando-Tigre): " + region6);
        pieModel7.setLegendPosition("nw");
        pieModel7.setShowDataLabels(true);
        pieModel7.setDiameter(300);
        pieModel7.setDataFormat("percent");
//        pieModel7.setDataLabelFormatString("%d");
        pieModel7.setDatatipFormat("%s");
    }

    // PIE 8: escuelas origen por distrito de Region 6
    public PieChartModel getPieModel8() {
        return pieModel8;
    }

    private void createPieModel8() {

        pieModel8 = new PieChartModel();

        region6Distritos.forEach((k, v) -> pieModel8.set(k + " - " + v, v));

        pieModel8.setTitle("Escuelas Origen de Región 6 por Distrito(Vicente López-San Isidro-San Fernando-Tigre): " + region6Distritos.size());
        pieModel8.setLegendPosition("nw");
        pieModel8.setShowDataLabels(true);
        pieModel8.setDiameter(300);
        pieModel8.setDataFormat("percent");
//        pieModel8.setDataLabelFormatString("%d");
        pieModel8.setDatatipFormat("%s");
    }

    // PIE 3: comisiones
    public PieChartModel getPieModel3() {
        return pieModel3;
    }

    private void createPieModel3() {

        pieModel3 = new PieChartModel();

        comisiones.forEach((k, v) -> pieModel3.set(k + " - " + v, v));

        pieModel3.setTitle("Total Comisiones: " + cantidadComisiones + " - Total Comisiones distintas: " + comisiones.size());
        pieModel3.setLegendPosition("nw");
        pieModel3.setShowDataLabels(true);
        pieModel3.setDiameter(350);
        pieModel3.setDataFormat("percent");
//        pieModel3.setDataLabelFormatString("%d");
        pieModel3.setDatatipFormat("%s");
    }

    // BAR: materias alumnos
    public HorizontalBarChartModel getHorizontalBarModel() {
        return horizontalBarModel;
    }

    private HorizontalBarChartModel initHorizontalBarModel() {

        horizontalBarModel = new HorizontalBarChartModel();

        ChartSeries materias = new ChartSeries();
        materias.setLabel("Materias Alumno");

        materiasAlumnos.forEach((k, v) -> materias.set(k + " - " + v, v));
        horizontalBarModel.addSeries(materias);

        return horizontalBarModel;
    }

    private void createHorizontalBarModel() {

        horizontalBarModel = initHorizontalBarModel();

        horizontalBarModel.setTitle("Materias Alumno");
        horizontalBarModel.setLegendPosition("ne");

        Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
        xAxis.setLabel("Cantidad");

        Axis yAxis = horizontalBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Materias");
        yAxis.setMin(0);
        yAxis.setMax(materiasAlumnos.size());
    }

    // PIE 9: materias aprobadas
    public PieChartModel getPieModel9() {
        return pieModel9;
    }

    private void createPieModel9() {

        pieModel9 = new PieChartModel();

        pieModel9.set("Materias aprobadas - " + aprobados, aprobados);
        pieModel9.set("Materias desaprobadas - " + (cantidadMateriasAlumnos - aprobados), cantidadMateriasAlumnos - aprobados);

        pieModel9.setTitle("Materias aprobadas/desaprobadas - Materias inscriptas: " + cantidadMateriasAlumnos);
        pieModel9.setLegendPosition("nw");
        pieModel9.setShowDataLabels(true);
        pieModel9.setDiameter(300);
        pieModel9.setDataFormat("percent");
//        pieModel9.setDataLabelFormatString("%d");
        pieModel9.setDatatipFormat("%s");
    }

    // PIE 10: alumnos inscriptos y aprobaron todas sus materias
    public PieChartModel getPieModel10() {
        return pieModel10;
    }

    private void createPieModel10() {

        pieModel10 = new PieChartModel();

        pieModel10.set("Alumnos aprobados - " + alumnosAprobados, alumnosAprobados);
        pieModel10.set("Alumnos no aprobados - " + (alumnos - alumnosAprobados), alumnos - alumnosAprobados);

        pieModel10.setTitle("Alumnos aprobados/desaprobados - Total de alumnos inscriptos: " + alumnos);
        pieModel10.setLegendPosition("nw");
        pieModel10.setShowDataLabels(true);
        pieModel10.setDiameter(300);
        pieModel10.setDataFormat("percent");
//        pieModel9.setDataLabelFormatString("%d");
        pieModel10.setDatatipFormat("%s");
    }

    // PIE 11: cantidad de alumnos masculino y femeninos
    public PieChartModel getPieModel11() {
        return pieModel11;
    }

    private void createPieModel11() {

        pieModel11 = new PieChartModel();

        pieModel11.set("Masculinos - " + alumnosMasculinos, alumnosMasculinos);
        pieModel11.set("Femeninos - " + (alumnos - alumnosMasculinos), alumnos - alumnosMasculinos);

        pieModel11.setTitle("Sexo - Total de alumnos inscriptos: " + alumnos);
        pieModel11.setLegendPosition("nw");
        pieModel11.setShowDataLabels(true);
        pieModel11.setDiameter(300);
        pieModel11.setDataFormat("percent");
//        pieModel9.setDataLabelFormatString("%d");
        pieModel11.setDatatipFormat("%s");
    }

    public String getPromedioAlumnos() {
        return promedioAlumnos;
    }

    public String getPromedioMaterias() {
        return promedioMaterias;
    }

    public void setMateriaService(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    public void setEscuelaService(EscuelaService escuelaService) {
        this.escuelaService = escuelaService;
    }

    public void setComisionService(ComisionService comisionService) {
        this.comisionService = comisionService;
    }

    public void setAlumnoService(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

}
