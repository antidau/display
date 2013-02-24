display
=======

Creating a 64x8 Pixel Display out of discrete LEDs, driven by an ATMEGA328. Content is sent from a computer, possibly a Raspberry Pi lateron.

Hardware
--------

The Schematics are based on Matthew T. Pandina's  [Demystifying the TLC5940](https://sites.google.com/site/artcfox/demystifying-the-tlc5940). The LEDs are arranged in a Matrix, Cathodes together in 64 Columns, Anodes together in 8 Rows. Every Block of 16 Columns needs one TLC5940 board (Schematics/tlc.{brd,sch}). To drive the columns one mosfet.{brd,sch} is needed. Finally, to tie everything together, one main.{brd,sch} is needed.
The Boards are completely through hole, all traces have 0.1in pitch and can therefore be built on a standard perfboard, which is what I did.

Sadly, there seems to be a bug somewhere, as the TLC5940 currently only displays strange patterns and overheats.

Atmega software
---------------

Does not yet exist. Use the Samples from [Demystifying the TLC5940](https://sites.google.com/site/artcfox/demystifying-the-tlc5940) for testing.

PC Software
-----------
The Software currently can display simple test patterns, text and music visualisation. It can only show them on screen yet, but will be able to send them to the ATMEGA.
My Goal is to have a web interface (with a nice mobile interface :wink:) to select what should be displayed.
