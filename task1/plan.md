# ITC313 Assignment 1 #
## Task 1 ##

>Part 1: Several types of point of interests (POI) such as - (1) Petrol station, (2) Taxi Stand, (3) ATM, (4) Hospital and (5) Shopping centre are located in a city. Their locations (longitude and latitude) are provided in a text file (a file will be provided in the resource section of the subject site, or you can create your own dummy text file according to the format you prefer).
>You must work with at least two type/kind of POI and at least 20 samples for each type (e.g. your text file should contain the location of 20 petrol stations in a city). You have to write a JAVA program that would get the locations of all the POIs from the file and plot them on a map (graph).
>Optional: Save the map/graph in a file if the user wants to.
>Part 2: incorporate a mechanism to zoom in/out the map either by having buttons or with the help of the scroll button.

* JFrame
  * Menu bar\ Toolbar
  * Image panel\canvas
  * Zoom buttons

### GUI Mockup ###

#### Main window ####

    +-------------+
    |File|Help|   |
    +----+--------+
    |            ^|
    |            ||
    | <map image>#|
    |            ||
    |            v|
    |<#---------> |
    +------+--+---+
    |Zoom: |In|Out|
    +------+--+---+

    <#-->: Scroll bar

#### Load POI dialog ####

    +---------------+
    |Select POI type|
    |               |
    |O Fuel         |
    |# Taxi         |
    |# Hospital     |
    |O Train        |
    |               |
    +---------------+
    |OK|Cancel|     |
    +---------------+

    O,#: unchecked, checked checkbox

### Menus ###

* File
  * Load Map image?
  * Load POIs
* Help
  * Readme
  * About

### Plan ###

1. Basic frame with all GUI elements.
3. Load POI file, edit image to place POI markers, display modified image. 
  * 
  * POI Marker should be a shape specific to that type of POI.
  * POI Marker could also include custom text to place at that point.
2. Load map image. Static, add load option later.
