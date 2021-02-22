package com.ulaps.employeemanager.services;


import com.google.common.base.Splitter;
import com.ulaps.employeemanager.dto.ClockifyUsers;
import com.ulaps.employeemanager.dto.RangeDate;
import com.ulaps.employeemanager.dto.SummaryReportDTO;
import com.ulaps.employeemanager.url.ClockifyURL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClockifyService {

    private final RestTemplate restTemplate;

    public static String sampleURL = "https://reqres.in/api/users?page=2";

    @Autowired
    public ClockifyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ClockifyUsers extractObject (Object object){

        // split One Object String into 4 parts
        String[] arr = object.toString().split(",",4);
        String arrId[] = arr[0].split("=");
        String arrEmail[] = arr[1].split("=");
        String arrName[] = arr[2].split("=");

        return new ClockifyUsers(arrId[1],arrEmail[1],arrName[1]);

    }

    public List<ClockifyUsers> getAllUsers() throws IOException, InterruptedException {
        List<ClockifyUsers> clockifyUsersList = new ArrayList<ClockifyUsers>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", "YBfeD6hLYRNKtDmO");
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<List> response = restTemplate.exchange(ClockifyURL.URL_FOR_ALL_USERS, HttpMethod.GET, entity, List.class);

        if(response.getStatusCode() == HttpStatus.OK){ // if the response is HTTP CODE 200
            System.out.println("Response Received.");

            List<Object> objectList = response.getBody();
            for(Object object : objectList){

                clockifyUsersList.add(extractObject(object));
            }

        }
        else {                                          // If not
            System.out.println("Error Occured");
            System.out.println(response.getStatusCode());
        }


        return clockifyUsersList;
    }

    public String generateDateInJSON(RangeDate rangeDate){

        return "{ \"dateRangeStart\": \"" + rangeDate.getStartDate().toString() + "\", \"dateRangeEnd\": \"" + rangeDate.getEndDate().toString() +"\", \"summaryFilter\": { \"groups\": [ \"USER\" ] }, \"sortColumn\": \"GROUP\", \"sortOrder\": \"ASCENDING\", \"exportType\": \"JSON\", \"rounding\": false, \"amountShown\": \"EARNED\" }";
    }


    public List<SummaryReportDTO> getAllTime(RangeDate rangeDate) {
        List<SummaryReportDTO> summaryReportDTOS = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", "YBfeD6hLYRNKtDmO");
        headers.add("Content-Type","application/json");


        String json = generateDateInJSON(rangeDate);
        HttpEntity entity = new HttpEntity(json,headers);
        ResponseEntity<Object> response = restTemplate.exchange(ClockifyURL.URL_FOR_ALL_REPORTS, HttpMethod.POST, entity, Object.class);
        String stringObject = trimTheObject(response.getBody().toString());
        List<String> stringList = Splitter.onPattern("},").omitEmptyStrings().splitToList(stringObject);

        for(String s : stringList){
            if(!s.equals(stringList.get(0))){
                summaryReportDTOS.add(extractTotalTime(s));
            }

        }

        long total = 0;

        for(SummaryReportDTO summaryReportDTO : summaryReportDTOS){
            total+= summaryReportDTO.getDuration();
        }
        System.out.println("Total Time " + total);

        return summaryReportDTOS;
    }

    /**
        A Method that will trim the unnecessary charater
        E.g.  {_, }, [, etc..

     */
    public String trimTheObject(String object){
        String trimObject = object;
        trimObject = trimObject.replace("[{_","");
        trimObject = trimObject.replace("{_","");
        trimObject = trimObject.replace("{","");
        trimObject = trimObject.replace("]","");
        trimObject = trimObject.replace("groupOne=","");
        trimObject = trimObject.substring(12);

        return trimObject;

    }

    public SummaryReportDTO extractTotalTime(String userReport){

        String stringArr[] = userReport.split(","); // Base Report
        String idArr[] = stringArr[0].split("="); // id
        String nameArr[] = stringArr[1].split("="); // name
        String durArr[] = stringArr[2].split("="); // duration
        return new SummaryReportDTO(idArr[1],nameArr[1],convertSecondsToHours(Long.parseLong(durArr[1])));
    }

    public long convertSecondsToHours(long secs){

        return (secs/60)/60;
    }
}
