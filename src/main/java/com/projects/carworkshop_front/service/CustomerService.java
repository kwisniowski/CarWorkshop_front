package com.projects.carworkshop_front.service;

import com.google.gson.Gson;
import com.projects.carworkshop_front.domain.dto.CustomerDto;
import com.projects.carworkshop_front.domain.dto.SubjectDto;
import com.projects.carworkshop_front.view.MainView;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class CustomerService {

    @Value("http://localhost:8080/v1/carworkshop/api/")
    private String backendControllerEndpoint;

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
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/carworkshop/api/customers")
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
        Gson gson = new Gson();
        String jsonContent = gson.toJson(customerDto);
        String url = "http://localhost:8080/v1/carworkshop/api/customers";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonContent,headers);
        restTemplate.postForObject(url,httpEntity,Void.class);
    }

    public void delete(Long id) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/carworkshop/api/customers/"+id)
                .encode()
                .build()
                .toUri();
        restTemplate.delete(url);
    }

    public StringBuilder showCustomerMfInfo(String customerNip) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/carworkshop/mfapi/getCustomerInfoByNip/"+customerNip)
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
