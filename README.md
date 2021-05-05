# Spring-Servers-Test
![Problem](https://theimless.top/uploads/images/306-f47f62a5410fe4b02d176177c93ca230.jpeg)
![Problem description](https://theimless.top/uploads/images/671-7b4a4b51762f557918b0168554ea6400.jpeg)

Both servers using servers-common library which contains Task model, TaskType and Product models.

## Public mappings:
#### POST requests example:
* ##### /api/execute/:
    Execute task
    * Create new products:
        ```
        {
            "taskType": "CREATEPRODUCT",
            "productList": [
                {
                    "name": "testProduct 1589308247"
                }
            ]
        }
        ```

    * Remove products
        ```
        {
            "taskType": "REMOVEPRODUCT",
            "productList": [
                {
                    "id": 1,
                    "name": "testProduct1"
                },
                {
                    "id": 2,
                    "name": "testProduct2"
                }
            ]
        }
        ```

    * Update products
        ```
        {
            "taskType": "UPDATEPRODUCT",
            "productList": [
                {
                    "id": 1,
                    "name": "testProduct1"
                },
                {
                    "id": 2,
                    "name": "testProduct2"
                }
            ]
        }

    * Get all products
        ```
        {
            "taskType": "GETALLPRODUCTS"
        }
        ```

## Private (with API Key auth) mappings:
#### GET requests example:
* ##### /api/getATask/:
    Get first task from queue for processing

* ##### /api/getATasks/?count=N:
    Get first N tasks from queue for processing

#### POST requests example:
* ##### /api/putAResult/:
    Put completed task result and return it to client
    ```
    {
        "id": "92195b55-f8a4-4a01-8bd4-3410de352dcc",
        "productList": [
            {
                "id": 1,
                "name": "testProduct1"
            },
            {
                "id": 2,
                "name": "testProduct2"
            }
        ],
        "taskId": "f3d337a1-e3a4-4147-a6a3-084db474388f"
    }
    ```
