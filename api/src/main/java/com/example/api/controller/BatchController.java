package com.example.api.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.exception.DataNotFoundException;

@RestController
@RequestMapping(value = "batch")
public class BatchController {
    @Autowired
    private JobLauncher jobLauncher; 

    @Autowired
    private Job insertIntoDbFromCsvJob;

    @GetMapping(value = "/")
    public ResponseEntity<String> run() throws DataNotFoundException{
        return runJob(insertIntoDbFromCsvJob);
    }

    private ResponseEntity<String> runJob(Job job) { 
        try { 
            JobParameters jobParameters = new JobParametersBuilder() 
                    .addString("jobName", job.getName()) 
                    .toJobParameters(); 
  
            JobExecution jobExecution = jobLauncher.run(job, jobParameters); 
  
            return ResponseEntity.ok("Batch Job " + job.getName()  
                                     + " started with JobExecutionId: " + jobExecution.getId()); 
        } catch (Exception e) { 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) 
                                    .body("Failed to start Batch Job: " + e.getMessage()); 
        } 
    }
}
