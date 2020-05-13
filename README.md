# Spring-Servers-Test
![Problem](https://theimless.me/uploads/images/306-f47f62a5410fe4b02d176177c93ca230.jpeg)
![Problem description](https://theimless.me/uploads/images/671-7b4a4b51762f557918b0168554ea6400.jpeg)

Both servers using servers-common library which contains Task model, TaskType and Product models.

##POST requests example:


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

##GET requests example:

* Get N products
    ```
    http://server.com/api/getAllATasks?count=N
    ```