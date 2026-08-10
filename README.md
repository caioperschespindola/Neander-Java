# Neander-Java Documentation
Java version of the Neander Computer invented by Professor Raul F. Weber.

Created by Raul F. Weber

Java implementation by Caio Persch Espindola.

Version: v1.2

Neander Shell commands:

get: receives a memory address and return the value stored in that address.

set: receives an address and a value and updates the memory accordingly.

read: receives a start address and an end address and returns memory addresses and values in that range.

write: receives a starting address and an arbitrary ammount of values, and writes them in memory in that order. breaks if a negative number is entered.

run: runs the program and displays its run time.

clear: sets all memory addresses to zero. WARNING: this will also remove the default halt commands on addreses 127 and 255.

ac: returns the current value stored in the accumulator.

fn: returns the current value stored in the Negative Flag.

fz: returns the current value stored in the Zero Flag.

opcodes: displays all opcodes.

help: displays all shell commands.

quit: ends the Neander Program.
