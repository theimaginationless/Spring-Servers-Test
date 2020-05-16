package Requests;

import Task.Task;
import models.Product;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Response {
    private UUID id;
    private List<Product> productList;
    private UUID taskId;

    public Response() {
        this.id = UUID.randomUUID();
    }

    public Response(UUID taskId, List<Product> productList) {
        this();
        this.taskId = taskId;
        this.productList = productList;
    }

    public Response(Task task, List<Product> productList) {
        this(task.getId(), productList);
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public void setTask(Task task) {
        this.taskId = task.getId();
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Response{" +
                "id=" + id +
                ", productList=" + productList +
                ", taskId=" + taskId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return Objects.equals(id, response.id) &&
                Objects.equals(productList, response.productList) &&
                Objects.equals(taskId, response.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productList, taskId);
    }
}
