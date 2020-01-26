package com.projects.carworkshop_front.view;

import com.projects.carworkshop_front.domain.dto.CarDto;
import com.projects.carworkshop_front.domain.dto.CustomerDto;
import com.projects.carworkshop_front.domain.dto.InvoiceDto;
import com.projects.carworkshop_front.domain.dto.RepairDto;
import com.projects.carworkshop_front.forms.CarForm;
import com.projects.carworkshop_front.forms.CustomerForm;
import com.projects.carworkshop_front.forms.RepairForm;
import com.projects.carworkshop_front.service.CarService;
import com.projects.carworkshop_front.service.CustomerService;
import com.projects.carworkshop_front.service.InvoiceService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;


import java.util.Arrays;
import java.util.List;

@Route
public class MainView extends VerticalLayout {

    private CarService carService = CarService.getInstance();
    private CustomerService customerService = CustomerService.getInstance();
    private InvoiceService invoiceService = InvoiceService.getInstance();

    private Grid<CarDto> carGrid = new Grid<>(CarDto.class);
    private Grid<CustomerDto> customerGrid = new Grid<>(CustomerDto.class);
    private Grid<InvoiceDto> invoiceGrid = new Grid<>(InvoiceDto.class);

    private TextField carPlateFilterField = new TextField();
    private TextField carVinFilterField = new TextField();
    private Label searchLabel = new Label(" or ");
    private HorizontalLayout carSearchFileds = new HorizontalLayout(carPlateFilterField,searchLabel,carVinFilterField);
    private Button addCar = new Button ("Add car to customer");
    private Button removeCar = new Button("Delete car");
    private Button editCar = new Button("Edit car");
    private Button showCarRepairs = new Button("Show car repairs");
    private Button addRepairToCar = new Button("Add repair to car");
    private Button filterCarsByCustomer = new Button("Show customers cars");
    private Button showAllCars = new Button("Show all cars");
    private HorizontalLayout carButtons = new HorizontalLayout();

    private TextField customerNameFilterField = new TextField();
    private TextField customerCompanyFilterField = new TextField();
    private Label searchCustomerLabel = new Label(" or ");
    private HorizontalLayout customerSearchFileds = new HorizontalLayout(customerNameFilterField,searchCustomerLabel,customerCompanyFilterField);

    private TextField invoiceNumberFilterField = new TextField();
    private Label invoiceFilterLabel = new Label("Show by payment status");
    private ComboBox<InvoiceService.invoicePaid> isPaidList = new ComboBox<InvoiceService.invoicePaid>();

    private CustomerForm customerForm = new CustomerForm(this);
    private CarForm carForm = new CarForm(this);
    private RepairForm repairForm = new RepairForm(this);

    private Button editCustomer = new Button("Edit customer");
    private Button addNewCustomer = new Button("Add customer");
    private Button removeCustomer = new Button("Remove customer");
    private HorizontalLayout customerButtons = new HorizontalLayout();

    private Grid<RepairDto> repairsGrid = new Grid<RepairDto>();
    private Button addRepair = new Button("Add repair");


    public MainView() {
        ////////////////////////////// Cars section /////////////////////////////

        carGrid.setColumns("id","brand","model","manufactureYear", "vinNumber", "engineSize", "plateNumber","bodyType","customerId");

        carPlateFilterField.setPlaceholder("Find by plate number");
        carPlateFilterField.setClearButtonVisible(true);
        carPlateFilterField.setValueChangeMode(ValueChangeMode.EAGER);
        carPlateFilterField.addValueChangeListener(event-> carPlateFilerUpdate());

        carVinFilterField.setPlaceholder("Find by VIN number");
        carVinFilterField.setClearButtonVisible(true);
        carVinFilterField.setValueChangeMode(ValueChangeMode.EAGER);
        carVinFilterField.addValueChangeListener(event-> carVinFilerUpdate());

        addCar.addClickListener(event-> {
            if (!(customerGrid.asSingleSelect().getValue()==null)) {
                CarDto addedCarDto = new CarDto();
                addedCarDto.setCustomerId(String.valueOf(customerGrid.asSingleSelect().getValue().getId()));
                carForm.getCustomerId().setValue(String.valueOf(customerGrid.asSingleSelect().getValue().getId()));
                carForm.getCustomerId().setReadOnly(true);
                carForm.setCar(addedCarDto);
            }
            else Notification.show("Please select customer",2000, Notification.Position.MIDDLE);
        });

        removeCar.setVisible(false);
        editCar.setVisible(false);
        showCarRepairs.setVisible(false);
        addRepairToCar.setVisible(false);

        removeCar.addClickListener(event->{
            if (!(carGrid.asSingleSelect().getValue() ==null)) {
                  carService.delete(carGrid.asSingleSelect().getValue().getId());
                  refresh();
                }
            else Notification.show("Please select car",2000, Notification.Position.MIDDLE);
        });

        addRepairToCar.addClickListener(evemt-> {
                    if (!(carGrid.asSingleSelect().getValue() == null)) {
                        RepairDto addedRepairDto = new RepairDto();
                        addedRepairDto.setCarId(String.valueOf(carGrid.asSingleSelect().getValue().getId()));
                        repairForm.getCarId().setValue(String.valueOf(carGrid.asSingleSelect().getValue().getId()));
                        repairForm.getCarId().setReadOnly(true);
                        repairForm.setRepair(addedRepairDto);
                    }
                    else Notification.show("Please select car",2000, Notification.Position.MIDDLE);
        });

        editCar.addClickListener(event-> {
            if (!(carGrid.asSingleSelect().getValue() == null)) {
                carForm.setCar(carGrid.asSingleSelect().getValue());
                carForm.getCustomerId().setReadOnly(true);
            }
            else Notification.show("Please select car",2000, Notification.Position.MIDDLE);
        });

        showAllCars.addClickListener(event->
                carGrid.setItems(carService.getCarDtos()));

        carButtons.add(showCarRepairs, removeCar , addRepairToCar, editCar, showAllCars);

        carGrid.asSingleSelect().addValueChangeListener(event -> {
            removeCar.setVisible(true);
            showCarRepairs.setVisible(true);
            editCar.setVisible(true);
            removeCar.setVisible(true);
            addRepairToCar.setVisible(true);
        });


        carGrid.addItemClickListener(listener->{
                if (listener.getClickCount()==2) {
                List<RepairDto> repairList = listener.getItem().getRepairDtos();
                for(RepairDto repairDto:repairList) {
                    Notification.show("Repair from: "+repairDto.getStartDate()+" to  "+repairDto.getEndDate()+ "  , repair cost: "+repairDto.getTotalCost(),
                            5000, Notification.Position.MIDDLE);
                }
            }
        });

        filterCarsByCustomer.setVisible(false);
        filterCarsByCustomer.addClickListener(event-> {
            if (!(customerGrid.asSingleSelect().getValue()==null)) {
                carGrid.setItems(carService.filterByCustomerId(String.valueOf(customerGrid.asSingleSelect().getValue().getId())));
            }
            else Notification.show("Please select customer",2000, Notification.Position.MIDDLE);
        });


        ////////////////////////////// Customers section /////////////////////////////

        customerNameFilterField.setPlaceholder("Find by lastname");
        customerNameFilterField.setClearButtonVisible(true);
        customerNameFilterField.setValueChangeMode(ValueChangeMode.EAGER);
        customerNameFilterField.addValueChangeListener(event-> customerNameFilerUpdate());

        customerCompanyFilterField.setPlaceholder("Find by company name");
        customerCompanyFilterField.setClearButtonVisible(true);
        customerCompanyFilterField.setValueChangeMode(ValueChangeMode.EAGER);
        customerCompanyFilterField.addValueChangeListener(event-> customerCompanyFilerUpdate());

        editCustomer.setVisible(false);
        addNewCustomer.addClickListener(event-> {
            customerGrid.asSingleSelect().clear();
            customerForm.setCustomer(new CustomerDto());
        });

        editCustomer.addClickListener(event-> {
            if (!(customerGrid.asSingleSelect().getValue() == null)) {
                customerForm.setCustomer(customerGrid.asSingleSelect().getValue());
            }
            else Notification.show("Please select customer",2000, Notification.Position.MIDDLE);
        });

        customerGrid.setColumns("id", "firstname", "lastname", "company", "nipNumber", "accountNumber", "regonNumber", "emailAddress",
                "phoneNumber", "vipCustomer", "companyCustomer");
        removeCustomer.setVisible(false);
        customerButtons.add(addNewCustomer, removeCustomer, addCar, filterCarsByCustomer, editCustomer);
        removeCustomer.addClickListener(event-> {
            if (!(customerGrid.asSingleSelect().getValue()==null)) {
                Long idToBeDeleted = customerGrid.asSingleSelect().getValue().getId();
                customerService.delete(idToBeDeleted);
                refresh();
            }
            else Notification.show("Please select customer",2000, Notification.Position.MIDDLE);
        });
        customerGrid.asSingleSelect().addValueChangeListener(event -> {
                removeCustomer.setVisible(true);
                editCustomer.setVisible(true);
                addCar.setVisible(true);
                filterCarsByCustomer.setVisible(true);
        });

        ////////////////////////////// Invoices section /////////////////////////////

        isPaidList.addValueChangeListener(event->invoicePaymentFilterUpdate());
        isPaidList.setAllowCustomValue(false);
        isPaidList.setItems(InvoiceService.invoicePaid.values());
        invoiceGrid.setColumns("id","customerId","paymentPeriod", "paymentLimitDate", "paid", "totalCost","repairId");


        add(customerSearchFileds, customerGrid, customerButtons , carForm, customerForm, carSearchFileds, carGrid, carButtons, repairForm, isPaidList, invoiceGrid);
        customerForm.setCustomer(null);
        carForm.setCar(null);
        repairForm.setRepair(null);
        addCar.setVisible(false);
        refresh();

        /////////////////////////////// Repairs section ///////////////////////////////

    }

    public void refresh() {
        carService.fetchAll();
        carGrid.setItems(carService.getCarDtos());
        customerService.fetchAll();
        customerGrid.setItems(customerService.getCustomers());
        invoiceService.fetchAll();
        invoiceGrid.setItems(invoiceService.getInvoices());
        removeCustomer.setVisible(false);
        removeCar.setVisible(false);
    }

    public void carPlateFilerUpdate() {
        carGrid.setItems(carService.filterByPlateNumber(carPlateFilterField.getValue()));
    }

    public void carVinFilerUpdate() {
        carGrid.setItems(carService.filterByVinNumber(carVinFilterField.getValue()));
    }

    public void customerNameFilerUpdate() {
        customerGrid.setItems(customerService.filterByName(customerNameFilterField.getValue()));
    }

    public void customerCompanyFilerUpdate() {
        customerGrid.setItems(customerService.filterByCompanyName(customerCompanyFilterField.getValue()));
    }

    public void invoicePaymentFilterUpdate() {
        if (isPaidList.getValue().equals(InvoiceService.invoicePaid.PAID)) {
            invoiceGrid.setItems(invoiceService.filerByPaymentCondition(true));
        }
        if (isPaidList.getValue().equals(InvoiceService.invoicePaid.UNPAID)) {
            invoiceGrid.setItems(invoiceService.filerByPaymentCondition(false));
        }
        if (isPaidList.getValue().equals(InvoiceService.invoicePaid.ALL)) {
            invoiceGrid.setItems(invoiceService.getInvoices());
        }
    }


}
