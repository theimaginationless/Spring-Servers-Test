package Task;

import Requests.Response;
import enums.TaskType;
import models.Product;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Task {
    private UUID id;
    private TaskType taskType;
    private List<Product> productList;

    public Task() {
        this.id = UUID.randomUUID();
    }

    public Task(TaskType taskType) {
        this();
        this.taskType = taskType;
        this.id = UUID.randomUUID();
    }

    public Task(Response request, TaskType taskType) {
        this(taskType);
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id.equals(task.id) &&
                taskType == task.taskType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskType);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskType=" + taskType +
                '}';
    }
}
