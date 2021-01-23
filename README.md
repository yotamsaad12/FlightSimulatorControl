# Flight Simulator Control 



During the study towards the B.sc. in Computer science one of the programming courses was Advanced software Development.
As a product of this course we developt a flight semulator program with emphasis on programming principles such as SOLID and GRASP,MVVM architecture, 
client-server, design paterns and GUI using JavaFX.

## Server Side

We implemented a generic server which can be used in different projects, in away that we seperated the server's functionality and the rest of the code.
In order to implemented a generic we configured the functionality of the server using an interface.
In this way in different projects can be used this interface with his functionality and the changes can be made using classes that implements the interface and are tailored to the given project.
In this way we maintain the open/closed principle.

We have two methods for the intreface: open, stop.
1. open - get a port int parameter for listening and its job is to open the server and wait for customers
2. stop - close the server

In this project in order to implement the server we use the class:MySerialServer which implements the functionality of the server interface.

### ClientHandler
In order to keep the single responsibility principle we can't define the client-server call protocol in the MySerialServer class.
Furthermore, in different projects the implementation is different and we want to keep the open/close principle.
Therefore we needed to separate the mechanism of the server implemented in MySerialServer from different forms of conversation with the client.

In order to solve that issue, we created another interface ClientHandler,
whose purpose is to determine the type of conversation with the client and how to handle it.
In this way MySerialServer can inject any desired implementation for the ClientHandler.

In particular, for all implementation of a server we can inject a conversation of reversing strings or solving equations. 
In the same way if tomorrow We would like to implement additional protocols, 
we will only need to add ClientHandler implementation without changing or copying again the code of the server implementations.

### Solver
In order to keep the single responsibility principle and the open/close principle 
we would also like to separate between the the conversation protocol between the server and the client 
and the algorithm that solves the the problem.
So we use a Solver interface.


## Caching

Because it is unnecessary to calculate a solution for a problem we have already solved in the past we used a caching system.
So we decided to save solutions.
Upon receiving a new problem, we will first check the cache to see if we have already solved it.
If so, we will extract the solution instead of calculating it all over again.

Since there are different ways to save the solutions when some of them are: files, databases, 
we also used here an interface to keep the different principles of SOLID. 
We have set up a CacheManager interface that will manage the cache for us, with the following methods:
1. Check - Check if there is a solution already.
2. Extract -  Extracts the data if a solution exist.
3. Save - save the solution

## UML

![ServerClient Java UML](/project_uml.png "ServerClient Java UML")

### The server for this project
When given a graph problem the solution would be calculated using A* algorithm(implemented based on djkistra algorithm using manhattan distances) or other search algorithm.

<p align="center">
  <img src="/uml/server_bridgepattern.png" width="600">
</p>
When given the weighted graph, the output of running the search algorithm will be the cheapest route to the target.
We implemented the Bridge Design pattern by creating a separation between the problem and what solves it. 
We implemented it that way so we can solve different problems using different solutions.

Here we solved the quickest path from point from one point to other in the matrix using A*.
In the example below the fastest way from top left to bottom right is:

15 as start (0,0) -->6 -->9-->8-->2-->7-->19 as end (3,3)
| 15 | 6  | 87 | 20 |
| 28 | 9  | 40 | 27 |
| 5  | 8  | 2  | 48 |
| 13 | 61 | 7  | 19 |

So the output:

Right, Down, Down, Right, Down, Right.

---
##   Interpreter 

In the project we have a GUI of a flight simulator by which one can control the plane and get information from it.
One of the features is running a script, consist of lines of commands that aims to fly the aircraft in Simultur.
For example:

```scala
openDataServer 5400 10
connect 127.0.0.1 5402
var breaks = bind "/controls/flight/speedbrake"
var throttle = bind " /controls/engines/current-engine/throttle"
var heading = bind "/instrumentation/heading-indicator/offset-deg"
var airspeed = bind "/instrumentation/airspeed-indicator/indicated-speed-kt"
var roll = bind "/instrumentation/attitude-indicator/indicated-roll-deg"
var pitch = bind "/instrumentation/attitude-indicator/internal-pitch-deg"
var rudder = bind "/controls/flight/rudder"
var aileron = bind "/controls/flight/aileron"
var elevator = bind "/controls/flight/elevator"
var alt = bind "/instrumentation/altimeter/indicated-altitude-ft"
breaks = 0
throttle = 1
var h0 = heading
while alt < 1000 {
    rudder = (h0 – heading)/20
    aileron = - roll / 70
    elevator = pitch / 50
print alt
sleep 250
}
```
In order to perform this requirement we wrote an interpreter, with its help you can connect to a simulator, open a server, 
and run different commands to fly the plane in the simulator and sample its data.

In the example above, there is a while loop for a case that the plane maintenance an altitude of less than a 1000 meters, 
in which there is an acceleration and elevation orders to the aircraft.
In this part:
rudder = (h0 - heading)/180

We use [Dijkstra's Shunting Yard algorithm] to interpret that arithmetic expressions.
(https://en.wikipedia.org/wiki/Shunting-yard_algorithm).

---

### Command pattern

<p align="center">
  <img src="/uml/Commandpattern.png" width="600">
</p>
We used the Command pattern in this project because the plane needs to receive a lot of instructions,commands in order to fly.
We implemented this with CompParser when each command has a command object.
In order to use the polymorphism principle all commands will implement the same interface.
In addition we use the Command pattern when assembling commands. 
For example, when a command holds few commands, so we combined the two design patterns: Command pattern and Composite pattern.
We can see the use in a command like if when it contains list of commands and each one can be either a standard single command or a list of commands.

---
### Interpreter stages

<p align="center">
  <img src="/uml/interpreter.png" width="600">
</p>
It works like an interpreter of a programming language.
First is a step called Lexer which takes string and converts to a logical distribution.
Next is a parser which converts the output of the lexer which is an array into commands and execute.
Before running the commands we need to make sure that a Pre-Parser will pass the initial scan on the script and check for Syntax errors,
that way the interpreter won't stumble on them in the middle of the script.

---
## MVVM Architecture

<p align="center">
  <img src="/uml/mvvm.png" width="600">
</p>

We used an MVVM architecture.
It consist of the following:
1. Model - which is responsible for the logic like the implementation of algorithms and data access.
2. View - which is responsible for the presentation for the user, input from the user. 
          In additon it is responsible for the graphics - the code when the user presses the button.
          Here we implementes an event driven programming.
3.  View Model - uses as the link between the View and the Model, transmits the commands from the view to the model, 
and in that way we separate between the two.

Use of Data Binding - We can wrap variables like the ones in the View, this way if we change something, 
Using the Observer pattern we connected all the parts together and notifieng about changes that needs to be made or already made.

```java
   FXMLLoader loader = new FXMLLoader(getClass().getResource("Flight.fxml"));
        Parent root = loader.load();
        FlightController ctrl = loader.getController();
        ViewModel viewModel=new ViewModel();
        Model model=new Model();
        model.addObserver(viewModel);
        viewModel.setModel(model);
        viewModel.addObserver(ctrl);
        ctrl.setViewModel(viewModel);
        primaryStage.setTitle("Flight Gear Simulator");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            DisconnectCommand command=new DisconnectCommand();
            String[] disconnect={""};
            command.executeCommand(disconnect);
            AutoPilotParser.thread1.interrupt();
            model.stopAll();
            System.out.println("bye");
        });
```


