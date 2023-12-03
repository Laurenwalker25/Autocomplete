## Introduction	

Autocomplete is an algorithm used in many modern software applications. In all of these applications, the user types text, and the application suggests possible completions for that text. Although finding terms that contain a query by searching through all possible results is possible, these applications need some way to select only the most useful terms to display. Thus, autocomplete algorithms not only need a way to find terms that start with or contain the prefix, but a way of determining how likely each one is to be useful to the user and displaying "good" terms first. This all needs to be done efficiently so that a user can see completions in real time.

In this project, I leverage a `Comparator` in Java as well as the binary search algorithm on sorted data to implement an efficient autocomplete. I create a second implementation based on a `HashMap`. I then benchmark and analyze the tradeoffs of these implementations.
