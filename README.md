# CIT 591 (Spring 2019) - Parking Wizard Project

This is CIT 591 (Spring 2019) Final Project for MCIT program at University of Pennsylvania.

This program is based on insights generated from a [dataset published on Kaggle](https://www.kaggle.com/cityofLA/los-angeles-parking-citations) about
historical City of Los Angeles parking citations. The data contains more than 9 million citations
and lively updated.

Here is our initial [Project Proposal](https://drive.google.com/a/seas.upenn.edu/file/d/1B0v8sF-ZCAvBWlpojwkA7Xn3dThQWnpf/view?usp=sharing).

This is our project timeline:
*	April 21 2019 
	* [x]	Basic level coding done -> Data cleaning
	* [x]	Location filtering (conversion…)
	* [x]	Basic data analysis, graphing, etc.
*	April 28 2019
	* [x]	Major software integration
	* [x]	Come up with user questions and define analysis collections
	* [x]	User Interface
*	May 5 2019
	* [x]	Finishing/Fine tuning 4.28 week work
	* [x]	GUI
*	May 12 2019
	* [x]	Finalizing and packaging

Team Member: [Jin-Uk Luke Shin](https://github.com/jinukshin), [Chan Woo Yang](https://github.com/chanwooyang), [Weiwen Zhao](https://github.com/weiwenz33)

*******

## Software Design Overview

The Parking Wizard software can be divided into three parts: Data Cleaning part, Data Analysis & Visualization part, and GUI part for the user interaction.

Data Cleaning part is for cleaning up the raw data in the .csv file from the Kaggle. The raw data contains invalid data (e.g. issue date out of range, empty issue date/issue time), so such data needs to be removed before the data analysis. `FileHandler` class is used to collect all raw data from the .csv file and store them in the HashMap format. Then, this raw data is passed to `DataCleaner` class to remove any invalid data set and saves in another HashMap format. Again, this cleaned data is passed back to the `FileHandler` class to generate the new .csv file with only valid data. All these processes are doen in the `DataCleaningRunner` class.

Data Analysis & Visualization part takes this cleaned data from the new .csv file (using `FileHandler` class) and can be used in two different cases. The first case is running the data analysis & visualization over the entire cleaned dataset to see the analysis of the entire parking tickets in the city of Los Angeles (We call it Big Data Analysis). `ParkingDataProcessor` class runs data analytics to understand ticket issueing patterns. It analyze tickets by hourly, daily and each day's hourly ticket patterns. It also analyze 10 most commonly issued parking tickets in LA and its corresponding fines. Then, using JFreeChart API, four charts are generated for visualization of these data (two bar charts and two pie charts). These charts are accessible from the GUI interface button as well. All these classes are run in the `ParkingTicketRunner` class.The other case is running the data analysis & visualization over the specific area around the end user's location. In this case, the entire cleaned data set gets divided into smaller data batches based on the location aspect of each data (using `Location` class). Then, data batch around the user's location gets further processed by `ParkingTicketDataProcessor` class and provides likelyhood of getting the ticket.

Thus, `ParkingTicketWizard` class combine `DataCleaningRunner` class and `ParkingTicketRunner` class to coordinate the workflow of the backend side of the program. And then, the workflow of the frontend side and the user interaction part are managed by the `GUI` class.

For more details about each class and its method functionalities, please see the Javadoc in each java file in `../src` folder.

For more details about our software design (Class functionalities, etc.), please see our [CRC Card document](https://docs.google.com/document/d/1vbhLUTb2iVLndC-xgaiJIaL0skswpUP1O-wqn1qqcRQ/edit?usp=sharing).

********
[//]: # (Image References)

[run_config]: ./README_images/run_config.png "run_config"
[gui]: ./README_images/gui.png "gui"
[bigdataview]: ./README_images/bigdataview.png "bigdataview"

## Set Up The Envrionment Before Running The Program

Due to the nature of the big data analysis (> 9 million dataset), it requires a change in the configuration parameter to avoid the `Java Heap Space Out of Memory Error`. Go to **Run** -> **Run Configurations...**. Then, from the left hand side, select `ParkingTicketWizard` and select `Arguments` tab on the center of the window. Then, in the `VM arguments:` field, fill it in like following:

![run_config][run_config]

Repeat the same steps for `GUI` class.

********

## Running The Program

Running `ParkingTicketWizard` class will run the overall backend side of the program: the data cleaning and data analysis & visualization. This will generate the image files of graphs and charts so that they can be used by the `GUI` class.

RUunning `GUI` class will show the actual window of the program for the user interaction.

![gui][gui]

`Click for Coordinates from Map!` hyperlink leads to the website where an address can be converted into coordinate values. Then, a user can input the current location data with date and time information, and click `Predict Tickets` will calculate the likelihood of getting ticket based on user's inputs.

`Big Data View` button will lead to the new window where different types of graphs and charts, analyzed over the entire parking tickets in LA. 

![bigdataview][bigdataview]

********

## Software Testing

JUnit tests were created to test if method functions were working as expected. Please see `LikelyhoodPredictorTest.java`, `LocationTest.java`, and `ParkingTicketsTest.java`.

*********

## Developers' Notes

As of Apr 21 2019, please keep in mind that the software development is still is progress, all java files in the repo are subject to change/removal. Major software integration (+ Code Cleanup) will be happened over next few days. You may see a few java classes with no functional methods written (e.g. `GUI` and `LikelyhoodPredictor` classes), but please note that they were created prior as templates for the future works. Furthermore, new extra classes are also highly likely to be created (as a completely new entity or splitted into more "DRY" classes) as the project progresses.
