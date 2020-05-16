package com.example.server2.services;

import Task.Task;
import com.example.server2.Tasks.CreateProductTask;
import com.example.server2.Tasks.GetAllProductsTask;
import com.example.server2.Tasks.RemoveProductTask;
import com.example.server2.repositories.ProductRepository;
import com.example.server2.services.interfaces.ProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

@Service("AsyncProcessingService")
public class AsyncProcessingServiceImpl implements ProcessingService {
    final Logger logger = LoggerFactory.getLogger(AsyncProcessingServiceImpl.class);
    private ProductRepository productRepository;
    private ThreadFactory threadFactory;
    private ExecutorService executorService;
    private static final String URL_GET_TASK = "http://127.0.0.1:8081/api/getATask";
    private static final String URL_GET_ALL_TASKS = "http://127.0.0.1:8081/api/getATasks";
    private int THREADS_COUNT = 4;
    private static final String tokenHeaderName = "token";
    @Value("${application.http.auth-token}")
    private String token;

    public AsyncProcessingServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
        threadFactory = new ThreadFactory() {
            private final AtomicLong threadIndex = new AtomicLong(0);
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("TaskThread-" + threadIndex.getAndIncrement());
                return thread;
            }
        };
        executorService = Executors.newFixedThreadPool(THREADS_COUNT, threadFactory);
    }

    private List<Task> getTasks(int count) throws ResourceAccessException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(tokenHeaderName, token);
        HttpEntity<List<Task>> entity = new HttpEntity<List<Task>>(headers);
        String url = URL_GET_ALL_TASKS + "/?count=" + count;
        ResponseEntity<List<Task>> response = restTemplate.exchange(url,
                HttpMethod.GET, entity, new ParameterizedTypeReference<List<Task>>() {});

        List<Task> result = null;
        if(response.getStatusCode() == HttpStatus.OK) {result = response.getBody();}
        return result;
    }

    @Scheduled(fixedDelay = 1)
    public void processingTask() {
        logger.debug("Start execution of asyncTask");
        try {
            List<Task> tasks = getTasks(THREADS_COUNT);
            if(tasks != null && tasks.size() > 0) {
                logger.debug("Get {} tasks", tasks.size());
                for (Task task : tasks) {
                    switch (task.getTaskType()) {
                        case GETALLPRODUCTS:
                            executorService.submit(new GetAllProductsTask(task, productRepository, token));
                            break;
                        case UPDATEPRODUCT:
                        case CREATEPRODUCT:
                            executorService.submit(new CreateProductTask(task, productRepository, token));
                            break;
                        case REMOVEPRODUCT:
                            executorService.submit(new RemoveProductTask(task, productRepository, token));
                            break;
                        default:
                            logger.info("No one tasks");
                            return;
                    }
                }
            }
        } catch (ResourceAccessException e) {
            logger.warn("RAE exception: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Exception: " + e.getMessage());
        } finally {
            logger.debug("End execution of asyncTask");
        }
    }
}
