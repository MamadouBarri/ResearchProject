



<p align="center">

  <h3 align="center"> :zap: Smart Traffic Light Scheduling Algorithms, 2018 :zap:</h3>

  
  <p align="center">
   	Java platform, called “IIS”,
which simulates several intersections and compares different
variations of our STLS algorithms.
    <br />
    <br />
    <a href="https://github.com/MamadouBarri/ResearchProject/issues">Report Bug</a>
    ·
    <a href="https://github.com/MamadouBarri/ResearchProject/issues">Request Feature</a>
  </p>
</p>



## Table of Contents

* [About the Project](#about-the-project)
  * [Built With](#built-with)
* [Research](#research)
  * [Abstract](#abstract)
  * [Our Contributions](#our-contributions)
  * [Results](#results)
* [License](#license)
* [Contact](#contact)


## About The Project


This application is an Isolated Intersection Simulator written with Java and Swing. In our simulator, cars are generated with the following properties : car action (turn left, right or go
forward), car direction (north, east, south or west), vehicle
image (an array of images for aestheitc purposes of the
simulator) and average speed (random values that follow a certain distribution based on real life data). 
 Every car is generated with a certain rate which was
determined by us based on real life data from the Montreal City
Statistics Bureau. Many parameters of the simmulation can be changed, such as the appearance rate of the vehicles,
whether or not a certain lane has abnormal traffic, which is
explained further on in this section, the average speed of the
generated vehicles and the number of vehicles generated during
the simulation. The appearance rate ranges from 0.2 to 2.0 cars
per second. The cars can have a minimum average speed of 10km/h and a maximum of 150km/h. Finally, the maximum
number of cars generated is infinity, thus it can simulate traffic 
for as long as we need it to. Changing these parameters can have
a drastic effect on the results. 

You can watch the demo of the simulation my Youtube Channel [here](https://www.youtube.com/watch?v=Gs0ZzuL_i4w&ab_channel=MamadouGuennadyBarri) and read the [research paper](https://github.com/MamadouBarri/ResearchProject/blob/master/ResearchSmartLights2019.pdf).

![alt text](https://github.com/MamadouBarri/ResearchProject/blob/master/thumbnail.jpg?raw=True)


### Built With

These are the main technologies used to build this project:
* [JDK 8](https://www.oracle.com/pt/java/technologies/javase/javase-jdk8-downloads.html)
* [Swing](https://docs.oracle.com/javase/tutorial/uiswing/index.html)




## Research

### Abstract

Traffic lights have and always will be necessary for
the safety of the traffic on the road. However, the time cycles of
these traffic lights are based on premeditated computations and
not real time data. These scheduling methods can often be
inefficient and therefore lead to traffic congestions. In this paper,
we propose three different Smart Traffic Light Scheduling (STLS)
algorithms that were tested on our Isolated Intersection Simulator
(IIS) Java platform. IIS simulates real-life traffic flow on an atgrade junction with different traffic light scheduling algorithms.
We integrate a heuristic to our proposed algorithms that takes into
account, not only the density of the junctions like traditional
schemes, but also the waiting time of the vehicles. In addition, it
can also give the right of way to emergency vehicles that need
immediate passage through the intersection. Throughout
extensive simulations, IIS displays the efficiency of our different
STLS algorithms in terms of managing the intersection and
avoiding congestions. For instance, our simulations demonstrated
a tremendous decrease in the average delay time when compared
to regular traffic lights. In fact, cars on the intersection using our
STLS algorithms performs 3 times better. On top of this, thanks
to our algorithms, the average speed of the cars is nearly doubled
and the amount of static cars on average are halved.

### Our Contribution

Our contributions in this paper can be summarized as
follows: (1) We introduce our Java platform, called “IIS”
which simulates several intersections and compares different
variations of our STLS algorithms; (2) We propose algorithms
in order to increase the flow of traffic; (3) We demonstrate the
effectiveness of the improvements made with STLSDT (Smart
Traffic Light Scheduling based on Density and delay Time)
heuristic on our IIS platform. 

### Results

You can view the [results](https://github.com/MamadouBarri/ResearchProject/blob/master/ResearchSmartLights2019.pdf) on pages 4-7.


## Local Installation

1. Clone the repo
```sh
git clone https://github.com/MamadouBarri/ResearchProject.git
```
2. Open the application in your favorite IDE.


## License


Distributed under the MIT License.


Project Link: [https://github.com/MamadouBarri/ResearchProject](https://github.com/MamadouBarri/ResearchProject)



