package com.assignment.events;

import static com.assignment.events.utils.AppConstants.FILE_PROCSSING_FAILED_MSG;
import static junit.framework.TestCase.fail;

import com.assignment.events.Respository.impl.EventsRepositoryImpl;
import com.assignment.events.exceptions.TechnicalException;
import com.assignment.events.service.impl.FileProcessorServiceImpl;
import org.junit.Assert;
import org.junit.Test;

public class EventsProcessorAppTest {

    private EventsProcessorApp app;

    @Test
    public void testEventsProcessorAppSuccess(){
        try {
            app = new EventsProcessorApp(new FileProcessorServiceImpl(new EventsRepositoryImpl()));
            String testFilePath = this.getClass().getResource("/test_logfile.txt").getPath();
            app.main(new String[]{testFilePath});
        }catch (Exception e){
            fail();
        }
    }

    @Test
    public void testEventsProcessorAppFailure(){
        try {
            app = new EventsProcessorApp(new FileProcessorServiceImpl(new EventsRepositoryImpl()));
            app.main(new String[]{"invalid_file_path"});
            fail();
        }catch (Exception e){
            Assert.assertTrue(e instanceof TechnicalException);
            Assert.assertEquals(FILE_PROCSSING_FAILED_MSG,e.getMessage());
        }
    }
}
