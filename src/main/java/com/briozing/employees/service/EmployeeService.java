package com.briozing.employees.service;
import com.briozing.employees.models.EmployeeRequestVO;
import com.briozing.employees.models.EmployeeResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.String;


import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    private static JdbcTemplate jdbcTemplate;
    private static SimpleJdbcInsert simpleJdbcInsert;
    private DataSource dataSource;
    public EmployeeService(@Autowired DataSource dataSource){
        this.jdbcTemplate=new JdbcTemplate(dataSource);
        this.dataSource=dataSource;
    }
    public List<EmployeeResponseVO> getAllEmployeesService(){
        String query="select * from employees;";
        System.out.println("Query :- "+query);
        return jdbcTemplate.query(query,(rs,rowNum)-> new EmployeeResponseVO(rs.getLong("id"),rs.getString("name"),rs.getString("emailId"),rs.getString("country")));
    }


    public EmployeeResponseVO addEmployee(EmployeeRequestVO employeeRequestVO){
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                         .withTableName("employee")
                        .usingGeneratedKeyColumns("id");
        Map<String,String> inputs= new HashMap<>();
        inputs.put("name",employeeRequestVO.getName());
        inputs.put("emailId",employeeRequestVO.getEmailId());
        inputs.put("country",employeeRequestVO.getCountry());
        Number id = simpleJdbcInsert.executeAndReturnKey(inputs);
        EmployeeResponseVO employeeResponseVO=new EmployeeResponseVO();
        employeeResponseVO.setId(Integer.parseInt(String.valueOf(id)));
        employeeResponseVO.setName(employeeRequestVO.getName());
        employeeResponseVO.setEmailId(employeeRequestVO.getEmailId());
        employeeResponseVO.setCountry(employeeRequestVO.getCountry());
        return employeeResponseVO;
    }

    public EmployeeResponseVO updateEmployee(EmployeeRequestVO employeeRequestVO, int id){
        int id1 = jdbcTemplate.update("UPDATE employee SET name = '"+employeeRequestVO.getName()+"',emailId='"+ employeeRequestVO.getEmailId()+"' ,country='"+employeeRequestVO.getCountry()+"' WHERE id = '"+id+"'");
        System.out.println(id1);
        EmployeeResponseVO employeeResponseVO=new EmployeeResponseVO();
        employeeResponseVO.setName(employeeRequestVO.getName());
        employeeResponseVO.setEmailId(employeeRequestVO.getEmailId());
        employeeResponseVO.setCountry(employeeRequestVO.getCountry());
        employeeResponseVO.setId(id);
        return employeeResponseVO;
    }

//    public EmployeeResponse getEmployee(String name, String emailId) {
//        String query = "select * from employees";
//        System.out.println("Query :"+query);
//
//        return employeeResponseVO;
//    }

    public EmployeeResponseVO findEmployeeById(String id) {
        String query = "select * from employee where id = '" + id + "'";
        System.out.println("Query : " + query);
        Map<String ,Object> map=jdbcTemplate.queryForMap(query);
        EmployeeResponseVO employeeResponseVO=new EmployeeResponseVO();
        employeeResponseVO.setId(Integer.parseInt(String.valueOf(map.get("id"))));
        employeeResponseVO.setName(String.valueOf(map.get("name")));
        employeeResponseVO.setEmailId(String.valueOf(map.get("emailId")));
        employeeResponseVO.setCountry(String.valueOf(map.get("country")));

        System.out.println(employeeResponseVO);
        return employeeResponseVO;
    }

    public EmployeeResponseVO findEmployeeByName(String name) {
        String query = "select * from employee where name = '" + name + "'";
        System.out.println("Query : " + query);
        Map<String ,Object> map=jdbcTemplate.queryForMap(query);
        EmployeeResponseVO employeeResponseVO=new EmployeeResponseVO();
        employeeResponseVO.setId(Integer.parseInt(String.valueOf(map.get("id"))));
        employeeResponseVO.setName(String.valueOf(map.get("name")));
        employeeResponseVO.setEmailId(String.valueOf(map.get("emailId")));
        employeeResponseVO.setCountry(String.valueOf(map.get("country")));

        System.out.println(employeeResponseVO);
        return employeeResponseVO;
    }

    public static int delete(int id){
        String query="DELETE from employee WHERE id=?";
        System.out.println("DELETE Query :- " + query);
        int deletedId = jdbcTemplate.update(query,id);

        return deletedId;
    }
//    private static void getEmployees()
//    {
//        final String uri = "http://localhost:9090/employee";
//
//        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.getForObject(uri, String.class);
//
//        System.out.println(result);
//    }



}
