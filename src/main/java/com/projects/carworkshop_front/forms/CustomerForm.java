package com.projects.carworkshop_front.forms;

import com.projects.carworkshop_front.domain.dto.CarDto;
import com.projects.carworkshop_front.domain.dto.CustomerDto;
import com.projects.carworkshop_front.service.CustomerService;
import com.projects.carworkshop_front.view.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerForm extends FormLayout {

    private TextField firstname = new TextField("Firstname");
    private TextField lastname = new TextField("Lastname");
    private TextField company = new TextField("Company");
    private TextField nipNumber = new TextField("Nip number");
    private TextField accountNumber = new TextField("Account");
    private TextField regonNumber = new TextField("Regon");
    private TextField emailAddress = new TextField("Email");
    private TextField phoneNumber = new TextField("Phone number");

    private Button save = new Button("Save");
    private Button update = new Button("Update");
    private CustomerService service = CustomerService.getInstance();
    private Binder<CustomerDto> binder = new Binder<>(CustomerDto.class);

    private MainView mainView;

    public CustomerForm (MainView mainView) {

        this.mainView = mainView;
        save.addClickListener(event -> save());
        update.addClickListener(event-> update());
        binder.bindInstanceFields(this);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        update.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(firstname,lastname,company,nipNumber,accountNumber,regonNumber,emailAddress,phoneNumber, save, update);
    }

    private void save() {
        CustomerDto customerDto = binder.getBean();
        service.save(customerDto);
        mainView.refresh();
        setCustomer(null);
    }

    private void update() {
        save.setVisible(false);
        CustomerDto customerDto = binder.getBean();
        service.update(customerDto);
        mainView.refresh();
        setCustomer(null);
    }

    public void setCustomer(CustomerDto customerDto) {
        binder.setBean(customerDto);

        if (customerDto==null) {
            setVisible(false);
        } else {
            setVisible(true);
            lastname.focus();
        }
    }
}
