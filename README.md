DeExcelerator
=============

Tool for extracting relations out of tables.

-------------------------------------------------------
--------------------Information------------------------
-------------------------------------------------------

===============
DeExcelerator.java:
===============
Entry point of the Extraction Point.
Create one instance of it and input yourt documents.

===============
Configuration.java
===============
Contains the configuration parameter, they can be different
for input documents. And give with some spezific changes better
results.

===============
FunctionSelection.java
===============
Say which functions in the pipeline should be used for
extraction. Switch there functions on and off.

-------------------------------------------------------
-------------------------------------------------------

===============
Package Core:
===============
Contains the classes for all input document types and
the TableAnalysis and TableSearch class in which all rules
will be execute.

-------------------------------------------------------
-------------------------------------------------------

===============
Package Rule:
===============
Contains all implemented rules for the extraction.

-------------------------------------------------------
-------------------------------------------------------

===============
Package Output:
===============
Contains the output documents, which represent the results. 

