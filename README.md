# Java Date Library

Simple java library for dealing with dates. 

## Getting Started
``` Java
Date ex1 = new Date("2012-12-12")
Date ex2 = new Date("2014","1","1")
```
### Some unusual Methods
#### shiftDate - shifts date for a certain day number 
``` Java
ex1.shiftDate(12)         // =2012-12-24
ex1.shiftDate(365 + 19)   // = ex2
```
#### dayDifference - returns day difference between two dates
``` Java
ex2.dayDifference(ex1)         // = -365 - 19
```

More to find on JavaDoc or in code :>


