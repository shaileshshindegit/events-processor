package com.assignment.events.service;

import com.assignment.events.Respository.EventRepository;
import com.assignment.events.exceptions.TechnicalException;
import com.assignment.events.models.LogEvent;
import com.assignment.events.service.impl.FileProcessorServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;

import static com.assignment.events.utils.AppConstants.FILE_PROCSSING_FAILED_MSG;
import static junit.framework.TestCase.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FileProcessorServiceImplTest {

    private static final String TEST_FILE_NAME = "/test_logfile.txt";

    @InjectMocks
    private FileProcessorServiceImpl service;

    @Mock
    private EventRepository repository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFileProcessSuccess(){
        doNothing().when(repository).saveEvent(any(LogEvent.class));
        try {
            String testFilePath = this.getClass().getResource(TEST_FILE_NAME).getPath();
            service.process(new File(testFilePath));
        }catch (Exception e){
            fail();
        }
    }

    @Test
    public void testFileProcessFailure(){

        doThrow(new TechnicalException("Mock Technical Exception")).when(repository).saveEvent(any(LogEvent.class));
        try {
            String testFilePath = this.getClass().getResource(TEST_FILE_NAME).getPath();
            service.process(new File(testFilePath));
            fail();
        }catch (Exception e){
            Assert.assertTrue(e instanceof TechnicalException);
            Assert.assertEquals(FILE_PROCSSING_FAILED_MSG,e.getMessage());
        }

    }

}
