Leap Motion Instrument
======================

People:
* Ben Iofel
* Princeton Ferro
* David Maginley
* Zach Kaplan

Todo:
* Make good GUI
* Fix lag: We think this is in `Audio.java` when the `playNote()` function is
being called many times. Therefore we should be able to fix lag at the same
time as making a `pressNote()` function and `releaseNote()` function
