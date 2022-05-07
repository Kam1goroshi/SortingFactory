## Table of contents
* [General info](#general-info)
* [Purpose](#purpose)
* [Usage example](#how-to-use)

## General Info
This project contains a Sorting library that uses dynamic creation of functions according to needs. This offers a balanced tradeoff between flexibility and performance when handling big arrays and need several algorithms or sorting orders.

## Purpose
* Creating a useful library
* Learning lambdas and callbacks in java
* Homework of neapolis algorithms class

## Usage example

```
import sorting_machine.SortingMachine;
import sorting_machine.SortingOrderChoices;
import sorting_machine.SortingAlgorithmChoices;

public class Example{
  SortingMachine<YOUR_TYPE> sortingMachine = new SortingMachine<>(SortingAlgorithmsChoices.BUBBLE_SORT, SortingOrderChoices.ASCENDING);
  long steps = sortingMachine.sort(YOUR_ARRAY_OF_TYPE);
}
```

