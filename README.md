# cicd-demo

## Phase 1

* Monolithic ToDoList REST API with the following functionalities:

  * Create a ToDo item based on the ``nameuser`` and a ``description``
  
    * ``curl -XPOST -d '{"username": "jose", "description": "abc"}' 'localhost:8080/todolist'``
  
  * Get a list of all ToDo item from a ``username``
  
    * ``curl 'localhost:8080/todolist/myname'``
  
  * Delete a ToDo item based on its ``id``
  
    * ``curl -XDELETE 'localhost:8080/todolist/1'``
  
* Code has 3 unit tests for the ``ToDoListController`` using a mockup for the ToDoListService

* When code is pushed to the repository, or when the user explicity requests, a Github Action (``.github/workflows/docker-image.yml``) is executed running the following code:

  * builds the code
 
  * runs the tests
 
  * build a docker image
 
  * publish the image do a demo Harbor repository
