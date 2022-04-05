package com.dailycodechallenge.jsontocsv.jsontocsv.controller;


import com.dailycodechallenge.jsontocsv.jsontocsv.model.Response;
import com.github.opendevl.JFlat;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
public class JsonToCsvController {

   /* @GetMapping("/generatecsv/{delimiter}")
    public  void generateCSVFile(@PathVariable("delimiter") String delimiter)
    {
        System.out.println("Generated" );
    }*/

    @GetMapping("/generatecsv/{name}")
    public ResponseEntity<?> generateCSVFile(@PathVariable("name") String name)
    {
        try {
            File   resource=new ClassPathResource("/json/users.json").getFile( );
            String str    =new String(Files.readAllBytes(resource.toPath( )));
            JFlat flatMe=new JFlat(str);
            List<Object[]> json2csv=flatMe.json2Sheet( ).getJsonAsSheet( );
            String outputFileName = new SimpleDateFormat("ddMMyyyyHHmmss'.csv'", Locale.getDefault()).format(new Date());
            flatMe.json2Sheet().headerSeparator("/").write2csv(outputFileName);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(new Response("Conversion Failed !!"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Response("Sucessfully Converted !!"), HttpStatus.OK);
    }
}
