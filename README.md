# SkylineCompare

## Background
SkylineCompare is a Test Framework for Skyline-Algorithms. The Framework is expandable, several 
Test-Cases and Data-Generators are included.

## Important
No Skyline-Algorithms are included in this version. At least one Skyline-Algorithm must be integrated
into the Framework first. Read the "New Algorithms" paragraph for further details.

## Basic Test
The **Start.java** class in the **starters** package includes some basic tests. The unmodified 
version of this class runs two test-iterations on two algorithms (**PSkyline** and **ParallelBBS**). 
Two **InputHandler** are used here: light-anticorrelation and light-anticorrelation-gaussian. 
More **InputHandler** could be added. The values used by the tests could also be changed: 
The class-variables of the **DifferentDimensionsBenchmark** and **DifferentDatasizeBenchmark** 
and their respective super-classes could be overwritten.

## Gradle
The **gradle run** command runs the main method of the **Start.java** class.

## More Tests
Other test-examples are included in the **StartExamples.java class** in the **starters** package.

## Extension
### New TestCases
New TestCases must implement the abstract **TestCase** class or one of its subclasses. The **run()** 
and **genOutput()** methods could be overwritten. Run-methods should use the run-methods of their 
super-classes.

### New Algorithms
New algorithms must implement the abstract **SkylineAlgorithm** class. Each algorithm iteration 
needs an instance of the class **TimeObject** to save their runtimes. Specific checkpoints must 
be added to the algorithms by using the **TimeObject** methods. At least the following methods 
must be used within the new algorithm, to achieve functionality with this framework:
* **setStartInput()**
* **setFinishInput()**
* **setStartCalc()**
* **setFinishCalc()**
