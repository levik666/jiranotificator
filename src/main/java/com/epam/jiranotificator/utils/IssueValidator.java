package com.epam.jiranotificator.utils;

import com.epam.jiranotificator.dto.IssueDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Serhiy_Kovalenko on 4/17/2015.
 */
public class IssueValidator {

    private static Log log = LogFactory.getLog(IssueValidator.class);
    private Set<String> taskSet;
    private Set<String> toNotifyPriority = new TreeSet<String>();

    public IssueValidator(){
        toNotifyPriority.add("Blocker");
        toNotifyPriority.add("Critical");
        try {
            if(!initTaskSet()){
                this.taskSet = new TreeSet<String>();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean isInTaskSet(IssueDTO issue){
        if (!taskSet.contains(issue.getCode())) {
            taskSet.add(issue.getCode());
            storeTaskSet();
            return false;
        }
        System.out.println(taskSet);
        return true;
    }

    public void storeTaskSet(){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("./taskset.txt");
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(taskSet);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            log.error("Can't open taskset.txt file to store new issue. Error:" + " " + e.getMessage());
        }
    }

    public boolean initTaskSet() throws ClassNotFoundException {
        File storageFile = new File("./taskset.txt");
        if(storageFile.exists() && storageFile.canRead()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(storageFile);
                ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
                this.taskSet = (Set<String>) inputStream.readObject();
                return true;
            } catch (IOException e) {
                log.error("Can't load taskset.txt file to init Set. Error:" + " " + e.getMessage());
            }
        }
        return false;
    }

    public boolean toPublish(IssueDTO issue){
        if(toNotifyPriority.contains(issue.getStatus())){
            if(!isInTaskSet(issue)){
                return true;
            }
        }
        return false;
    }
}
