# cicd-demo

## Phase 1

* Monolithic ToDoList REST API with 3 services: ToDo List service, User service (**_empty_**) and Notification service

* Currently the following functionalities are available:

  * Create a ToDo item based on the ``username`` and a ``description``
  
    * ``curl -XPOST -d '{"username": "myname", "description": "abc"}' 'localhost:8080/todolist'``
  
  * Get a list of all ToDo item from a ``username``
  
    * ``curl 'localhost:8080/todolist/myname'``
  
  * Delete a ToDo item based on its ``id``
  
    * ``curl -XDELETE 'localhost:8080/todolist/1'``

  * For each of these operation a notification is sent using a notification service. Currently, this service only prints a log message on _standard output_
  
* Code has 3 unit tests for the ``ToDoListController`` using a mockup for the ToDoListService (remaining tests are missing)

* When code is pushed to the repository, or when the user explicity requests, a Github Action (``.github/workflows/docker-image.yml``) is executed running the following steps:

  * builds the code
 
  * runs the tests
 
  * build a docker image
 
  * publish the image do a demo Harbor repository
  
## Phase 2

  * Separate the 3 services into diferent deployment artifacts (images) and build a gateway to communicate with the ToDo List and the User service. Consider loosely coupled communication between the ToDo list and the Notifications using a RabbitMQ
