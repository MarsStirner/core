package ru.korus.tmis.prescription;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.pharmacy.DrugChart;
import ru.korus.tmis.prescription.thservice.*;
import ru.korus.tmis.prescription.thservice.DrugComponent;

import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 29.08.13, 15:26 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
public class PrescriptionHelper {
    private static final Logger logger = LoggerFactory.getLogger(PrescriptionHelper.class);
    private Prescription prescr;
    private Action prescriptionAction;

    public PrescriptionHelper(final Action action) {
        this.prescr = new Prescription();
        this.prescriptionAction = action;
    }

    public void fillActionData() {
        this.prescr.setActInfo(Helper.createThriftActionDataFromEntityAction(prescriptionAction));
    }

    public void fillDrugIntervals() {
        for (DrugChart drugChart : PrescriptionServer.getDrugChartBean().getPrescriptionIntervals(prescriptionAction.getId())) {
            logger.debug("Founded Prescription DrugChart[{}]", drugChart.getId());
            DrugInterval drugInterval = Helper.createThriftDrugIntervalFromEnityDrugChart(drugChart);
            //Исполнения назначения
            for (DrugChart execDrugChart : PrescriptionServer.getDrugChartBean().getExecIntervals(prescriptionAction.getId(), drugChart.getId())) {
                logger.debug("Founded Execution DrugChart[{}] ", execDrugChart.getId());
                DrugIntervalExec execInterval = Helper.createThriftDrugIntervalExecFromEntityDrugChart(execDrugChart, drugChart);
                drugInterval.addToExecIntervals(execInterval);
            }
            //Добавление текущего интервала в назначение
            prescr.addToDrugIntervals(drugInterval);
        }
        //Добавление компонентов в назначение
        List<ru.korus.tmis.core.entity.model.pharmacy.DrugComponent> drugComponentList = PrescriptionServer.getDrugComponentBean()
                .getComponentsByPrescriptionAction(prescriptionAction.getId());
        for(ru.korus.tmis.core.entity.model.pharmacy.DrugComponent currentDrugComponent : drugComponentList){
            logger.debug("For Action[{}] found DrugComponent[{}]", prescriptionAction.getId(), currentDrugComponent.getId());
            DrugComponent drugComponentToAdd = Helper.createThriftDrugComponentFromEntityDrugComponent(currentDrugComponent);
            prescr.addToDrugComponents(drugComponentToAdd);
        }

    }

    public Prescription getPrescription() {
        return this.prescr;
    }
}
