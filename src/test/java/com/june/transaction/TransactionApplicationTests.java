package com.june.transaction;

import com.june.transaction.controller.TransactionExceptionController;
import com.june.transaction.repository.TransactionRepository;
import com.june.transaction.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@SpringBootTest
class TransactionApplicationTests {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransactionService TransactionService;

    @Test
    @Order(1)
    void contextLoads() {
        //test if bootstrap data loaded
        assert(TransactionService.findAll().size() == 3);
    }

    @Test
    @Order(2)
    void testNotExistTransaction() {
        int notExistId = Integer.MAX_VALUE;
        try {
            HttpRequest request = HttpRequest.newBuilder().GET().uri(new URI("http://localhost:8080/api/v1/transaction/" + notExistId)).build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = new JSONObject(response.body());
            assert json.getInt("code") == 1004;
        }catch (Exception e){
            log.error(e.getMessage());
        }

    }

    @Test
    @Order(3)
    void testExistTransaction() {
        int existId = 3;
        try {
            HttpRequest request = HttpRequest.newBuilder().GET().uri(new URI("http://localhost:8080/api/v1/transaction/" + existId)).build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            log.info(response.body());
            assert response.statusCode() == HttpStatus.OK.value();
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @Test
    @Order(4)
    void testCreateTransaction() {
        //test insert a new Transaction
        String serialNumber = "000010";
        String title = "Transaction 10";
        String description = "This is Transaction 10";
        double amount = 100.00;
        String requestBody = "title=" + title + "&description=" + description + "&serialNumber=" + serialNumber + "&amount=" + amount;
        try{
            HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .uri(new URI("http://localhost:8080/api/v1/transaction")).setHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            log.info(response.body());
            assert response.statusCode() == HttpStatus.CREATED.value();
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @Test
    @Order(5)
    void testCreateDuplicateTransaction() {
        //test creating an Transaction with a serialNumber that already exists
        String serialNumber = "00003";
        String title = "Transaction 3";
        String description = "This is Transaction 3";
        double amount = 100.00;
        String requestBody = "title=" + title + "&description=" + description + "&serialNumber=" + serialNumber + "&amount=" + amount;
        try{
            HttpRequest request =  HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .uri(new URI("http://localhost:8080/api/v1/transaction"))
                    .setHeader("Content-Type","application/x-www-form-urlencoded")
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            log.info(response.body());
            JSONObject json = new JSONObject(response.body());
            assert json.getInt("code") == 1003;
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @Test
    @Order(6)
    void testCreateTransactionWithParameterMissing(){
        //Transaction must be created with all parameters set
        String serialNumber = "00004";
        String requestBody = "serialNumber=" + serialNumber;
        try{
            HttpRequest request =  HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .uri(new URI("http://localhost:8080/api/v1/transaction"))
                    .setHeader("Content-Type","application/x-www-form-urlencoded")
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            log.info(response.body());
            JSONObject json = new JSONObject(response.body());
            assert json.getInt("code") == 1002;
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @Test
    @Order(7)
    void testUpdateTransaction() {
        int existId = 3;
        String title = "Transaction 3 v2.0";
        String description = "This is Transaction 3 V2.0";
        String requestBody = "title=" + title + "&description=" + description;
        try{
            HttpRequest request = HttpRequest.newBuilder().PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                    .uri(new URI("http://localhost:8080/api/v1/transaction/" + existId))
                    .setHeader("Content-Type","application/x-www-form-urlencoded")
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            log.info(response.body());
            assert response.statusCode() == HttpStatus.OK.value();
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @Test
    @Order(8)
    void testUpdateTransactionConflict(){
        //update Transaction with serialNumber conflict with existed one
        int existId = 1;
        String title = "Transaction 1 v2.0";
        String description = "This is Transaction 1 V2.0";
        String serialNumber = "00002";
        String requestBody = "title=" + title + "&description=" + description + "&serialNumber=" + serialNumber;
        try{
            HttpRequest request = HttpRequest.newBuilder().PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                    .uri(new URI("http://localhost:8080/api/v1/transaction/" + existId))
                    .setHeader("Content-Type","application/x-www-form-urlencoded")
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            log.info(response.body());
            JSONObject json = new JSONObject(response.body());
            assert json.getInt("code") == 1003;
        }catch(Exception e){
            log.error(e.getMessage());
        }

    }

    @Test
    @Order(9)
    void testUpdateTransactionNotExist(){
        int NotExistId = Integer.MAX_VALUE;
        String title = "Transaction big v2.0";
        String description = "This is Transaction big V2.0";
        String requestBody = "title=" + title + "&description=" + description;
        try{
            HttpRequest request = HttpRequest.newBuilder().PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                    .uri(new URI("http://localhost:8080/api/v1/transaction/" + NotExistId))
                    .setHeader("Content-Type","application/x-www-form-urlencoded")
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            log.info(response.body());
            JSONObject json = new JSONObject(response.body());
            assert json.getInt("code") == 1004;
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @Test
    @Order(10)
    void testDeleteTransaction(){
        int existId = 3;
        try{
            HttpRequest request = HttpRequest.newBuilder().DELETE()
                    .uri(new URI("http://localhost:8080/api/v1/transaction/" + existId))
                    .setHeader("Content-Type","application/x-www-form-urlencoded")
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            log.info(response.body());
            assert response.statusCode() == HttpStatus.OK.value();
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @Test
    @Order(11)
    void testDeleteTransactionNotExist(){
        int NotExistId = Integer.MAX_VALUE;
        try{
            HttpRequest request = HttpRequest.newBuilder().DELETE()
                    .uri(new URI("http://localhost:8080/api/v1/transaction/" + NotExistId))
                    .setHeader("Content-Type","application/x-www-form-urlencoded")
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            log.info(response.body());
            JSONObject json = new JSONObject(response.body());
            assert json.getInt("code") == 1004;
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @Test
    @Order(12)
    void testUpdateTransactionWithParameterMissing(){
        int existId = 3;
        try{
            HttpRequest request = HttpRequest.newBuilder().PUT(HttpRequest.BodyPublishers.ofString(""))
                    .uri(new URI("http://localhost:8080/api/v1/transaction/" + existId))
                    .setHeader("Content-Type","application/x-www-form-urlencoded")
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            log.info(response.body());
            JSONObject json = new JSONObject(response.body());
            assert json.getInt("code") == 1002;
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
