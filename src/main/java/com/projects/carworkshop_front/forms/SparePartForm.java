package com.projects.carworkshop_front.forms;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.projects.carworkshop_front.domain.dto.CarDto;
import com.projects.carworkshop_front.domain.dto.SparePartDto;
import com.projects.carworkshop_front.service.CarService;
import com.projects.carworkshop_front.service.SparePartService;
import com.projects.carworkshop_front.view.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import sun.applet.Main;

import java.awt.*;
import java.text.NumberFormat;

public class SparePartForm extends FormLayout {

    public String[] carBrandValues = {"CITROEN","PEUGEOT","RENAULT"};

    private ComboBox<String> carBrand = new ComboBox<>("Brand");
    private TextField model = new TextField("Model");
    private TextField manufacturer = new TextField("Manufacturer");
    private NumberField price = new NumberField();

    private Button save = new Button("Save");
    private SparePartService service = SparePartService.getInstance();
    private Binder<SparePartDto> binder = new Binder<>(SparePartDto.class);

    private MainView mainView;

    public SparePartForm(MainView mainView) {

        carBrand.setItems(carBrandValues);
        carBrand.setAllowCustomValue(false);
        this.mainView = mainView;
        save.addClickListener(event -> save());
        binder.bindInstanceFields(this);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(carBrand, model, manufacturer,price,save);
    }

    private void save() {
        SparePartDto sparePartDto = binder.getBean();
        service.save(sparePartDto);
        mainView.refresh();
        setSparePart(null);
    }

    public void setSparePart(SparePartDto sparePartDto) {
        binder.setBean(sparePartDto);
        if (sparePartDto==null) {
            setVisible(false);
        } else {
            setVisible(true);
            carBrand.focus();
        }
    }
}
