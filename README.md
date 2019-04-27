# backend--test

After start the program, just type any currency with amount as format HKD 300, then the program will store its value as an record

For user input from console, only valid currency code with upercase letters is acceptable. For example, HKD is valid but hkd is not. Other invalid input are all considered as invalid input.

For amount, the program will automatically format it to float number with two decimal points. (i.e. 100.999 -> 100.99, 100 -> 100)

There are two different .txt files to be read into the program at its starting time, the first one contains valid input but the second has invalid content. The program will report errors for files containing any invalid data. 
