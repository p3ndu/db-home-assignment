This repository contains vanilla Java solutions for the following exercises:

1. **2 threads ping-pong**. One thread prints 'ping', another one prints' pong'. Stop after 5 seconds
2. There is an array A consisting of N integers. What is **the maximum sum of two integers from A that share their first and last digits**? For example, 1007 and 167 share their first (1) and last (7) digits, whereas 2002 and 55 do not.
Write a function:
class Solution { public int solution(int[] A); }
that, given an array A consisting of N integers, returns the maximum sum of two integers that share their first and last digits. If there are no two integers that share their first and last digits, the function should return −1.
Examples:
* Given A = [130, 191, 200, 10], the function should return 140. The only integers in A that share first and last digits are 130 and 10.
* Given A = [405, 45, 300, 300], the function should return 600. There are two pairs of integers that share first and last digits: (405, 45) and (300, 300). The sum of the two 300s is bigger than the sum of 405 and 45.
* Given A = [50, 222, 49, 52, 25], the function should return −1. There are no two integers that share their first and last digits.
* Given A = [30, 909, 3190, 99, 3990, 9009], the function should return 9918.

The solutions can be run directly from any code editor or from the command line. For this exercise IntelliJ IDEA Ultima was used.
