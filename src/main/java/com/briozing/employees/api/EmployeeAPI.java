package com.briozing.employees.api;

import com.briozing.employees.models.EmployeeRequestVO;
import com.briozing.employees.models.EmployeeResponseVO;
import com.briozing.employees.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.awt.*;

@RestController
@RequestMapping("/Employee")
public class EmployeeAPI {
    EmployeeService employeeService;
    public EmployeeAPI (@Autowired EmployeeService employeeService){
        this.employeeService=employeeService;
    }


    @GetMapping
    public String hello(){
        return "Hello Docker";

    }



    @PostMapping(value = "/add",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResponseVO> addEmployee(@RequestBody EmployeeRequestVO employeeRequestVO){
        EmployeeResponseVO employeeResponseVO=employeeService.addEmployee(employeeRequestVO);
        return new ResponseEntity<>(employeeResponseVO, HttpStatus.OK);
    }

    @PutMapping(value="/updateById/{id}",consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResponseVO> updateById(@RequestBody EmployeeRequestVO employeeRequestVO, @PathVariable int id){
        EmployeeResponseVO employeeResponseVO=employeeService.updateEmployee(employeeRequestVO,id);
        return new ResponseEntity<>(employeeResponseVO,HttpStatus.OK);
    }

    @GetMapping(value="/findById/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResponseVO> findById(@PathVariable String id){
        System.out.println("Employee Id : " + id);
        HttpStatus status= HttpStatus.OK;
        EmployeeResponseVO employeeResponseVO = null;
        try{
            employeeResponseVO=employeeService.findEmployeeById(id);
        }catch(Exception e){
            status = HttpStatus.NOT_FOUND;
            employeeResponseVO = null;
        }
        return new ResponseEntity<>(employeeResponseVO,status);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id){
        HttpStatus status= HttpStatus.CREATED;
        String message = "ID is Deleted";
        try{
            int deletedId = EmployeeService.delete(id);
            if(deletedId==0){
                status = HttpStatus.NOT_FOUND;
                message ="Record Not Found";
            }
        }catch (Exception e){
            status=HttpStatus.NOT_FOUND;
            message="Record Not Found";
        }
        return new ResponseEntity<>(message,status);
    }



}
