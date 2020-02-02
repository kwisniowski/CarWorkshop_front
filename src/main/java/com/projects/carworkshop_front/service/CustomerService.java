package com.projects.carworkshop_front.service;

import com.projects.carworkshop_front.config.AppConfig;
import com.projects.carworkshop_front.domain.dto.CustomerDto;
import com.projects.carworkshop_front.domain.dto.SubjectDto;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

public class CustomerService {

    private String backendControllerEndpoint;
    private AppConfig appConfig = AppConfig.getInstance();
    private JsonBuilder<CustomerDto> jsonBuilder = new JsonBuilder<>();
    RestTemplate restTemplate = new RestTemplate();
    private static CustomerService customerService;
    private List<CustomerDto> customerDtos;

    private CustomerService() {
    }

    public Set<CustomerDto> getCustomers () {
        return new HashSet<>(customerDtos);
    }

    public static CustomerService getInstance() {
        if (customerService==null) {
            customerService = new CustomerService();
        }
        return customerService;
    }


    public void fetchAll() {
        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint()+"customers")
                .encode()
                .build()
                .toUri();

        Optional<CustomerDto[]> customers = Optional.ofNullable(restTemplate.getForObject(url,CustomerDto[].class));
        customerDtos = new ArrayList<>(customers
                .map(Arrays::asList)
                .orElse(new ArrayList<>()));
    }

    public List<CustomerDto> filterByName(String filterString) {
        return customerDtos.stream()
                .filter(customer-> customer.getLastname().contains(filterString))
                .collect(Collectors.toList());
    }

    public List<CustomerDto> filterByCompanyName(String filterString) {
        return customerDtos.stream()
                .filter(customer-> customer.getCompany().contains(filterString))
                .collect(Collectors.toList());
    }

    public void save(CustomerDto customerDto) {
        String url = appConfig.getBackendEndpoint()+"customers";
        restTemplate.postForObject(url,jsonBuilder.prepareJson(customerDto),Void.class);
    }

    public void update(CustomerDto customerDto) {
        String url = appConfig.getBackendEndpoint()+"customers";
        restTemplate.put(url,jsonBuilder.prepareJson(customerDto));
    }

    public void delete(Long id) {
        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint()+"customers/"+id)
                .encode()
                .build()
                .toUri();
        restTemplate.delete(url);
    }

    public StringBuilder showCustomerMfInfo(String customerNip) {
        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint()+"getCustomerInfoByNip/"+customerNip)
                .encode()
                .build()
                .toUri();
        StringBuilder sb = new StringBuilder();

        try {
            SubjectDto response = restTemplate.getForObject(url, SubjectDto.class);
            sb.append(response.getName()+"\n" + response.getWorkingAddress()+"\n"+"VAT: "+response.getStatusVat()+
                    "\n"+response.getAccountNumbers()[0]);
            return sb;
        }
        catch (HttpServerErrorException e) {
            return new StringBuilder("Pole NIP ma nieprawidłową wartość");
        }
    }
}
