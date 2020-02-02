package com.projects.carworkshop_front.view;

import com.projects.carworkshop_front.domain.Car;
import com.projects.carworkshop_front.domain.dto.*;
import com.projects.carworkshop_front.forms.*;
import com.projects.carworkshop_front.service.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import lombok.Getter;


import java.util.List;

@Route
@Getter
public class MainView extends VerticalLayout {

    private VerticalLayout customersLayout = new VerticalLayout();
    private Label customersLabel = new Label("CUSTOMERS");
    private VerticalLayout carsLayout = new VerticalLayout();
    private Label carsLabel = new Label("CARS");
    private VerticalLayout repairsLayout = new VerticalLayout();
    private Label repairsLabel = new Label("REPAIRS");
    private VerticalLayout sparePartsLayout = new VerticalLayout();
    private Label sparePartsLabel = new Label("SPARE PARTS");

    private CarService carService = CarService.getInstance();
    private CustomerService customerService = CustomerService.getInstance();
    private RepairService repairService = RepairService.getInstance();
    private SparePartService sparePartService = SparePartService.getInstance();

    private Grid<CarDto> carGrid = new Grid<>(CarDto.class);
    private Grid<CustomerDto> customerGrid = new Grid<>(CustomerDto.class);
    private Grid<RepairDto> repairGrid = new Grid<>(RepairDto.class);
    private Grid<SparePartDto> sparePartsGrid = new Grid<>(SparePartDto.class);

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

    private Button showCurrencyCalculator = new Button ("Show currency calculator");
    private Label currencyPLNLabel = new Label("PLN");
    private Label currencyLabel = new Label(" = ");
    private NumberField currencyPLNValue = new NumberField();
    private ComboBox<RepairService.repairCurrency> currencyList = new ComboBox<>();
    private NumberField currencyResult = new NumberField("");
    private Button hideCalculator = new Button ("Hide calculator");
    private HorizontalLayout currencyCalculator = new HorizontalLayout();

    private CustomerForm customerForm = new CustomerForm(this);
    private CarForm carForm = new CarForm(this);
    private RepairForm repairForm = new RepairForm(this);
    private SparePartForm sparePartForm = new SparePartForm(this);

    private Button editCustomer = new Button("Edit customer");
    private Button addNewCustomer = new Button("Add customer");
    private Button removeCustomer = new Button("Remove customer");
    private Button showCustomerMfInfo = new Button("Show MF status by NIP");
    private HorizontalLayout customerButtons = new HorizontalLayout();

    private Button hideRepairsGrid = new Button ("Hide repair grid");
    private Button deleteRepair = new Button("Delete repair");
    private Button editRepair = new Button("Edit repair");

    private Button addCostToRepair = new Button("Add cost");
    private Label costLabel = new Label("Add as cost:");
    private TextField sparePartSelected = new TextField ("");
    private NumberField sparePartQty = new NumberField();
    private HorizontalLayout costAddingGroup = new HorizontalLayout();
    private HorizontalLayout repairButtons = new HorizontalLayout();

    private Label sparePartSearchLabel = new Label("Filter by brand");
    private ComboBox<Car.CarBrand> sparePartFilter = new ComboBox<>();
    private Button showAllSparePartsButton = new Button ("Show all");
    private HorizontalLayout sparePartFilterGroup = new HorizontalLayout();
    private Button addSparePart = new Button ("Add spare part");
    private Button deleteSparePart = new Button("Delete spare part");
    private Button editSparePart = new Button("Edit spare part");
    private Button addPartButton = new Button("Add");
    private HorizontalLayout sparePartsButtons = new HorizontalLayout();


    public MainView() {

        customersLayout.add(customersLabel);
        customersLayout.setHorizontalComponentAlignment(Alignment.CENTER,customersLabel);
        carsLayout.add(carsLabel);
        carsLayout.setHorizontalComponentAlignment(Alignment.CENTER,carsLabel);
        repairsLayout.add(repairsLabel);
        repairsLayout.setHorizontalComponentAlignment(Alignment.CENTER,repairsLabel);
        sparePartsLayout.add(sparePartsLabel);
        sparePartsLayout.setHorizontalComponentAlignment(Alignment.CENTER,sparePartsLabel);

        ////////////////////////////// Cars section /////////////////////////////

        carGrid.setColumns("id", "brand", "model", "manufactureYear", "vinNumber", "engineSize", "plateNumber", "bodyType", "customerId");

        carPlateFilterField.setPlaceholder("Find by plate number");
        carPlateFilterField.setClearButtonVisible(true);
        carPlateFilterField.setValueChangeMode(ValueChangeMode.EAGER);
        carPlateFilterField.addValueChangeListener(event -> carPlateFilerUpdate());

        carVinFilterField.setPlaceholder("Find by VIN number");
        carVinFilterField.setClearButtonVisible(true);
        carVinFilterField.setValueChangeMode(ValueChangeMode.EAGER);
        carVinFilterField.addValueChangeListener(event -> carVinFilerUpdate());

        addCar.addClickListener(event -> {
            if (!(customerGrid.asSingleSelect().getValue() == null)) {
                CarDto addedCarDto = new CarDto();
                addedCarDto.setCustomerId(String.valueOf(customerGrid.asSingleSelect().getValue().getId()));
                carForm.getCustomerId().setValue(String.valueOf(customerGrid.asSingleSelect().getValue().getId()));
                carForm.getCustomerId().setReadOnly(true);
                carForm.setCar(addedCarDto);
            } else Notification.show("Please select customer", 2000, Notification.Position.MIDDLE);
        });

        removeCar.setVisible(false);
        editCar.setVisible(false);
        showCarRepairs.setVisible(false);
        addRepairToCar.setVisible(false);
        hideRepairsGrid.setVisible(false);
        repairButtons.setVisible(false);
        showCustomerMfInfo.setVisible(false);
        currencyCalculator.setVisible(false);
        hideCalculator.setVisible(false);
        costAddingGroup.setVisible(false);
        addCostToRepair.setVisible(false);

        removeCar.addClickListener(event -> {
            if (!(carGrid.asSingleSelect().getValue() == null)) {
                carService.delete(carGrid.asSingleSelect().getValue().getId());
                refresh();
            } else Notification.show("Please select car", 2000, Notification.Position.MIDDLE);
        });

        addRepairToCar.addClickListener(evemt -> {
            if (!(carGrid.asSingleSelect().getValue() == null)) {
                RepairDto addedRepairDto = new RepairDto();
                addedRepairDto.setCarId(String.valueOf(carGrid.asSingleSelect().getValue().getId()));
                repairForm.getCarId().setValue(String.valueOf(carGrid.asSingleSelect().getValue().getId()));
                repairForm.getCarId().setReadOnly(true);
                repairForm.setRepair(addedRepairDto);
            } else Notification.show("Please select car", 2000, Notification.Position.MIDDLE);
        });

        editCar.addClickListener(event -> {
            if (!(carGrid.asSingleSelect().getValue() == null)) {
                carForm.setCar(carGrid.asSingleSelect().getValue());
                carForm.getCustomerId().setReadOnly(true);
            } else Notification.show("Please select car", 2000, Notification.Position.MIDDLE);
        });

        showAllCars.addClickListener(event ->
                carGrid.setItems(carService.getCarDtos()));

        carButtons.add(showCarRepairs, removeCar, addRepairToCar, editCar, showAllCars, hideRepairsGrid);

        carGrid.asSingleSelect().addValueChangeListener(event -> {
            removeCar.setVisible(true);
            showCarRepairs.setVisible(true);
            editCar.setVisible(true);
            removeCar.setVisible(true);
            addRepairToCar.setVisible(true);
        });


        carGrid.addItemClickListener(listener -> {
            if (listener.getClickCount() == 2) {
                List<RepairDto> repairList = listener.getItem().getRepairDtos();
                for (RepairDto repairDto : repairList) {
                    Notification.show("Repair from: " + repairDto.getStartDate() + " to  " + repairDto.getEndDate() + "  , repair cost: " + repairDto.getTotalCost(),
                            5000, Notification.Position.MIDDLE);
                }
            }
        });

        filterCarsByCustomer.setVisible(false);
        filterCarsByCustomer.addClickListener(event -> {
            if (!(customerGrid.asSingleSelect().getValue() == null)) {
                carGrid.setItems(carService.filterByCustomerId(String.valueOf(customerGrid.asSingleSelect().getValue().getId())));
            } else Notification.show("Please select customer", 2000, Notification.Position.MIDDLE);
        });

        showCarRepairs.addClickListener(event -> {
            if (!(carGrid.asSingleSelect().getValue() == null)) {
                repairGrid.setVisible(true);
                hideRepairsGrid.setVisible(true);
                repairService.fetchAll();
                repairGrid.setItems(repairService.filterByCarId(carGrid.asSingleSelect().getValue().getId()));
                hideRepairsGrid.setVisible(true);
            } else Notification.show("Please select car", 2000, Notification.Position.MIDDLE);
        });

        hideRepairsGrid.addClickListener(event -> {
            repairGrid.setVisible(false);
            repairButtons.setVisible(false);
        });


        ////////////////////////////// Customers section /////////////////////////////

        customerNameFilterField.setPlaceholder("Find by lastname");
        customerNameFilterField.setClearButtonVisible(true);
        customerNameFilterField.setValueChangeMode(ValueChangeMode.EAGER);
        customerNameFilterField.addValueChangeListener(event -> customerNameFilerUpdate());

        customerCompanyFilterField.setPlaceholder("Find by company name");
        customerCompanyFilterField.setClearButtonVisible(true);
        customerCompanyFilterField.setValueChangeMode(ValueChangeMode.EAGER);
        customerCompanyFilterField.addValueChangeListener(event -> customerCompanyFilerUpdate());

        editCustomer.setVisible(false);
        addNewCustomer.addClickListener(event -> {
            customerGrid.asSingleSelect().clear();
            customerForm.setCustomer(new CustomerDto());
        });

        editCustomer.addClickListener(event -> {
            if (!(customerGrid.asSingleSelect().getValue() == null)) {
                customerForm.setCustomer(customerGrid.asSingleSelect().getValue());
            } else Notification.show("Please select customer", 2000, Notification.Position.MIDDLE);
        });

        showCustomerMfInfo.addClickListener(event-> {
            if (!(customerGrid.asSingleSelect().getValue() == null)) {
                showCustomerMfInfo.setVisible(true);
                customerService.showCustomerMfInfo(customerGrid.asSingleSelect().getValue().getNipNumber());
                Notification.show(customerService.showCustomerMfInfo(customerGrid.asSingleSelect().getValue().getNipNumber()).toString(),
                        5000, Notification.Position.MIDDLE);
            }
        });

        customerGrid.setColumns("id", "firstname", "lastname", "company", "nipNumber", "accountNumber", "regonNumber", "emailAddress",
                "phoneNumber", "vipCustomer", "companyCustomer");
        removeCustomer.setVisible(false);
        customerButtons.add(addNewCustomer, removeCustomer, addCar, filterCarsByCustomer, editCustomer, showCustomerMfInfo);
        removeCustomer.addClickListener(event -> {
            if (!(customerGrid.asSingleSelect().getValue() == null)) {
                Long idToBeDeleted = customerGrid.asSingleSelect().getValue().getId();
                customerService.delete(idToBeDeleted);
                refresh();
            } else Notification.show("Please select customer", 2000, Notification.Position.MIDDLE);
        });
        customerGrid.asSingleSelect().addValueChangeListener(event -> {
            removeCustomer.setVisible(true);
            showCustomerMfInfo.setVisible(true);
            editCustomer.setVisible(true);
            addCar.setVisible(true);
            filterCarsByCustomer.setVisible(true);
        });

        ////////////////////////////// Invoices section /////////////////////////////

        currencyList.setItems(RepairService.repairCurrency.values());
        currencyList.setAllowCustomValue(false);
        currencyList.setValue(RepairService.repairCurrency.EUR);
        currencyCalculator.add(currencyPLNValue, currencyPLNLabel, currencyLabel, currencyResult, currencyList, hideCalculator);

        showCurrencyCalculator.addClickListener(event-> {
            hideCalculator.setVisible(true);
            currencyCalculator.setVisible(true);
            });

        hideCalculator.addClickListener(event -> currencyCalculator.setVisible(false));
        currencyList.addValueChangeListener( event -> {
            double factor = repairService.getCurrencyFactorFromNBP(currencyList.getValue().toString());
            currencyResult.setValue(currencyPLNValue.getValue()/factor);
        });

        currencyPLNValue.addValueChangeListener(event-> {
            double factor = repairService.getCurrencyFactorFromNBP(currencyList.getValue().toString());
            currencyResult.setValue(currencyPLNValue.getValue()/factor);
        });

        add(customersLayout, customerSearchFileds, customerGrid, customerButtons, carForm, customerForm, carsLayout, carSearchFileds, carGrid, carButtons,
                repairsLayout, repairGrid, repairForm, repairButtons, addCostToRepair , sparePartsLayout, sparePartFilterGroup, sparePartsGrid, addSparePart, sparePartsButtons, sparePartForm);

        //////////////////////////////////////////////////////////////////////////////

        customerForm.setCustomer(null);
        carForm.setCar(null);
        repairForm.setRepair(null);
        addCar.setVisible(false);
        refresh();

        /////////////////////////////// Repairs section ///////////////////////////////

        repairGrid.setColumns("id", "startDate", "endDate", "totalCost");
        repairGrid.addItemClickListener(event -> {
            repairButtons.setVisible(true);
            addCostToRepair.setVisible(true);
        });
        repairButtons.add(editRepair, deleteRepair, showCurrencyCalculator, currencyCalculator, costAddingGroup);

        deleteRepair.addClickListener(event -> {
            if (!(repairGrid.asSingleSelect().getValue() == null)) {
                repairService.delete(repairGrid.asSingleSelect().getValue().getId());
                refresh();
            } else Notification.show("Please select car", 2000, Notification.Position.MIDDLE);
        });

        editRepair.addClickListener(event -> {
            if (!(repairGrid.asSingleSelect().getValue() == null)) {
                repairForm.setRepair(repairGrid.asSingleSelect().getValue());
            }
        });

        costAddingGroup.add(costLabel, sparePartSelected, sparePartQty, addPartButton);

        addCostToRepair.addClickListener(event-> {
                costAddingGroup.setVisible(true);
                sparePartsGrid.setVisible(true);
        });

        addPartButton.addClickListener(event -> {
            if (!(repairGrid.asSingleSelect().getValue() == null)) {
                if (!(sparePartsGrid.asSingleSelect().getValue() == null)) {
                     RepairDto tempRepairDto = repairGrid.asSingleSelect().getValue();
                     double tempCost = tempRepairDto.getTotalCost();
                     if ((sparePartQty.getValue()!=null)) {
                         if (sparePartQty.getValue()>0) {
                             repairGrid.asSingleSelect().getValue().setTotalCost(tempCost + (sparePartsGrid.asSingleSelect().getValue().getPrice() *
                                     sparePartQty.getValue()));
                             repairService.save(tempRepairDto);
                             refresh();
                         }
                     }
                }
                else Notification.show("Please select spare part to update",2000, Notification.Position.MIDDLE);
            }
            else Notification.show("Please select repair to update",2000, Notification.Position.MIDDLE);
        costAddingGroup.setVisible(false);
        });

        /////////////////////////////// SpareParts section ///////////////////////////////

        sparePartFilterGroup.add(sparePartSearchLabel,sparePartFilter, showAllSparePartsButton);
        sparePartsButtons.add(editSparePart, deleteSparePart);
        sparePartsGrid.setColumns("id", "carBrand", "model", "manufacturer", "price");
        sparePartForm.setSparePart(null);
        sparePartsGrid.addItemClickListener(event -> {
            sparePartsButtons.setVisible(true);
            sparePartSelected.setValue(sparePartsGrid.asSingleSelect().getValue().getModel());
        });
        showAllSparePartsButton.addClickListener(event-> {
            sparePartsGrid.setItems(sparePartService.getSparePartDtos());
        });

        sparePartFilter.setItems(Car.CarBrand.values());
        sparePartFilter.setAllowCustomValue(false);
        sparePartFilter.addValueChangeListener(event-> sparePartsFilterUpdate());

        addSparePart.addClickListener(event -> {
            sparePartForm.setSparePart(new SparePartDto());
        });

        deleteSparePart.addClickListener(event -> {
            if (!(sparePartsGrid.asSingleSelect().getValue() == null)) {
                sparePartService.delete(sparePartsGrid.asSingleSelect().getValue().getId());
                refresh();
            } else Notification.show("Please select spare part", 2000, Notification.Position.MIDDLE);
        });

        editSparePart.addClickListener(event -> {
            if (!(sparePartsGrid.asSingleSelect().getValue() == null)) {
                sparePartForm.setSparePart(sparePartsGrid.asSingleSelect().getValue());
            }
        });


        ////////////////////////////////////////////////////////////////////////////////
    }

    public void refresh() {
        carService.fetchAll();
        carGrid.setItems(carService.getCarDtos());
        customerService.fetchAll();
        customerGrid.setItems(customerService.getCustomers());
        removeCustomer.setVisible(false);
        removeCar.setVisible(false);
        repairService.fetchAll();
        repairGrid.setItems(repairService.getRepairs());
        sparePartService.fetchAll();
        sparePartsGrid.setItems(sparePartService.getSparePartDtos());
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

    public void sparePartsFilterUpdate() {
        sparePartsGrid.setItems(sparePartService.filterByCarBrand(sparePartFilter.getValue().toString()));
    }


}
