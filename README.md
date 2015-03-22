# nfo-json

A command-line utility that generates json for a number of .nfo files.  The utility is able to scan multiple directories at a time and combine all output into a single json document.  It is written in Java and uses Gradle to build

Building
--------
- `gradle tasks` will generate a list of tasks that can be performed.  
- `gradle distzip` will create a zip file for distributing the utility.
- `gradle installdist` will create the distribution director under `build`.
- `gradle run` will run the utility in-place.
- 
Usage
-----
If you run the utility with `-h` you will get a list of options that may be supplied to the utility.

History
-------
* **1.0** Initial commit
