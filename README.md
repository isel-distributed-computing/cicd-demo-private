# cicd-demo

## Phase 1

* Monolithic ToDoList REST API with the following functionalities:

  * Create a ToDo item based on the ``nameuser`` and a ``description``
  
    * ``curl -XPOST -d '{"username": "jose", "description": "abc"}' 'localhost:8080/todolist'``
  
  * Get a list of all ToDo item from a ``username``
  
    * ``curl 'localhost:8080/todolist/myname'``
  
  * Delete a ToDo item based on its ``id``
  
    * ``curl -XDELETE 'localhost:8080/todolist/1'``
  
