package com.briozing.employees.api;

import com.briozing.employees.models.CountryRequestVO;
import com.briozing.employees.models.EmployeeRequestVO;
import com.briozing.employees.models.EmployeeResponseVO;
import com.briozing.employees.service.CountryService;
import com.briozing.employees.service.EmployeeService;
import com.briozing.employees.service.RestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/Employee")


public class EmployeeAPI {
    EmployeeService employeeService;
    RestService restService;
    CountryService countryService;
    public EmployeeAPI (EmployeeService employeeService, RestService restService, CountryService countryService){
        this.employeeService=employeeService;
        this.restService=restService;
        this.countryService=countryService;
    }


    @GetMapping
    public String hello(){
        return "Hello Docker";

    }
    @GetMapping(value="/getAll",produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeResponseVO>> getAllEmployees(){
        System.out.println("Hello World");
        List<EmployeeResponseVO> employeeResponseVOList=null;
        HttpStatus status= HttpStatus.OK;
        try{
            employeeResponseVOList= (List<EmployeeResponseVO>) employeeService.getAllEmployeesService();
        }catch (Exception e){
            status=HttpStatus.NOT_FOUND;
            employeeResponseVOList=null;
        }
        return new ResponseEntity<>(employeeResponseVOList,status);
    }




    @PostMapping(value = "/add",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResponseVO> addEmployee(@RequestBody EmployeeRequestVO employeeRequestVO){
        EmployeeResponseVO employeeResponseVO=null;
//        EmployeeResponseVO employeeResponseVO=employeeService.addEmployee(employeeRequestVO);
        HttpStatus httpStatus= countryService.FindByName(employeeRequestVO.getCountry());
        if (httpStatus.is2xxSuccessful()){
            employeeResponseVO=employeeService.addEmployee(employeeRequestVO);
        }
        else {
            employeeResponseVO=new EmployeeResponseVO();
            employeeResponseVO.setName(employeeRequestVO.getName());
            employeeResponseVO.setEmailId(employeeRequestVO.getEmailId());
            employeeResponseVO.setCountry(employeeRequestVO.getCountry());
            List<String >errors=new ArrayList<>();
            errors.add("Country not found");
            employeeResponseVO.setErrors(errors);
        }
        return ResponseEntity.status(httpStatus).body(employeeResponseVO);
    }

    @RequestMapping(value = "/addForCountry",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResponseVO> addForCountry(@RequestBody EmployeeRequestVO employeeRequestVO){
        EmployeeResponseVO employeeResponseVO=null;

        CountryRequestVO countryRequestVO=new CountryRequestVO();
        countryRequestVO.setName(employeeRequestVO.getCountry());
        HttpStatus httpStatus=countryService.addCountry(countryRequestVO);

        if(httpStatus.is2xxSuccessful()){

        }
        else{
            employeeResponseVO=new EmployeeResponseVO();
            employeeResponseVO.setName(employeeRequestVO.getName());
            employeeResponseVO.setEmailId(employeeRequestVO.getEmailId());
            employeeResponseVO.setCountry(employeeRequestVO.getCountry());
            List<String >errors=new ArrayList<>();
            errors.add("Identical country");
            employeeResponseVO.setErrors(errors);
        }

        return ResponseEntity.status(httpStatus).body(employeeResponseVO);
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

    @GetMapping(value="/findByName/{name}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResponseVO> findByName(@PathVariable String name){
        System.out.println("Employee name : " + name);
        HttpStatus status= HttpStatus.OK;
        EmployeeResponseVO employeeResponseVO = null;
        try{
            employeeResponseVO=employeeService.findEmployeeById(name);
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




//    @RequestMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
//    public String getAllEmployeesJSON(Model model)
//    {
//        model.addAttribute("employees", getEmployeesCollection());
//        return "jsonTemplate";
//    }



}
