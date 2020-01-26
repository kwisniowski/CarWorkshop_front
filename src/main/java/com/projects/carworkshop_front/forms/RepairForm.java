package com.projects.carworkshop_front.forms;

import com.projects.carworkshop_front.domain.dto.RepairDto;
import com.projects.carworkshop_front.service.RepairService;
import com.projects.carworkshop_front.view.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RepairForm extends FormLayout {

    private TextField carId = new TextField("Car Id");
    private TextField startDate = new TextField("Start Date");
    private TextField endDate = new TextField("End Date");
    private NumberField totalCost = new NumberField("TotalCost");

    private Button save = new Button("Save");
    private RepairService service = RepairService.getInstance();
    private Binder<RepairDto> binder = new Binder<>(RepairDto.class);

    private MainView mainView;

    public RepairForm (MainView mainView) {

        this.mainView = mainView;
        save.addClickListener(event -> save());
        binder.bindInstanceFields(this);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(carId,startDate,endDate,totalCost,save);
    }

    private void save() {
        RepairDto repairDto = binder.getBean();
        service.save(repairDto);
        mainView.refresh();
        setRepair(null);
    }

    public void setRepair(RepairDto repairDto) {
        binder.setBean(repairDto);

        if (repairDto==null) {
            setVisible(false);
        } else {
            setVisible(true);
            startDate.focus();
        }
    }
}
