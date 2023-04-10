package com.example.scheduler;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
class SchedulerTasksTest {

    @SpyBean
    SchedulerTasks schedulerTasks;

    @Test
    void testReportCurrentTime() {

        await().atMost(10, SECONDS).untilAsserted(() -> {
            verify(schedulerTasks, atLeast(2)).reportCurrentTime();
        });

    }
}