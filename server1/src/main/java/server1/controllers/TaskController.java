package server1.controllers;

import Requests.Response;
import Task.Task;
import models.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@RestController
@RequestMapping("api/")
public class TaskController {
    private ConcurrentLinkedQueue<Task> tasksQueue;
    private HashMap<UUID, DeferredResult<List<Product>>> results;
    private final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private static final long MAX_CLIENT_RESULT_TIMEOUT = 60000; // Waiting client for result from Server2, else empty list returned

    public TaskController() {
        this.tasksQueue = new ConcurrentLinkedQueue<>();
        this.results = new HashMap<>();
    }

    @PostMapping("/execute")
    public DeferredResult<List<Product>> createProduct(@Valid @RequestBody Task task) {
        logger.debug("Getting task with id: " + task.getId() + "; taskType: " + task.getTaskType());
        DeferredResult<List<Product>> result = new DeferredResult<>(MAX_CLIENT_RESULT_TIMEOUT, new ArrayList<Product>());
        tasksQueue.add(task);
        results.put(task.getId(), result);
        logger.debug("Task with id: " + task.getId() + " has been add to tasksQueue");
        return result;
    }

    @GetMapping("/getATask")
    public Task getTask() {
        return tasksQueue.poll();
    }

    @GetMapping("/getATasks")
    public ResponseEntity<List<Task>> getTasks(@RequestParam int count) {
        List<Task> tasks = new ArrayList<Task>();
        count = count > tasksQueue.size() ? tasksQueue.size() : count;
        for(int idx = 0; idx < count; ++idx) {
            tasks.add(tasksQueue.poll());
        }
        Collections.reverse(tasks);
        return ResponseEntity.ok().body(tasks);
    }

    @PostMapping("/putAResult")
    public void putResult(@Valid @RequestBody Response response) {
        List<Product> result = response.getProductList();
        logger.debug("Returns result with task id: " + response.getTaskId());
        results.get(response.getTaskId()).setResult(result);
    }
}
