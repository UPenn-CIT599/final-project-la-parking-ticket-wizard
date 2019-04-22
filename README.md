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
	* [ ]	Major software integration
	* [ ]	Geo conversion
	* [ ]	Come up with user questions and define analysis collections
	* [ ]	User Interface
	* [ ]	WEKA likelihood prediction
*	May 5 2019
	* [ ]	Finishing/Fine tuning 4.28 week work
*	May 12 2019
	* [ ]	Finalizing and packaging

Team Member: [Jin-Uk Luke Shin](https://github.com/jinukshin), [Chan Woo Yang](https://github.com/chanwooyang), [Weiwen Zhao](https://github.com/weiwenz33)

*******

## Software Design Overview

The Parking Wizard software can be divided into two parts: Data Cleaning part and Data Analysis & Visualization part.

Data Cleaning part is for cleaning up the raw data in the .csv file from the Kaggle. The raw data contains invalid data (e.g. issue date out of range, empty issue date/issue time), so such data needs to be removed before the data analysis. `FileHandler` class is used to collect all raw data from the .csv file and store them as the HashMap format. Then, this raw data is passed to `DataCleaner` class to remove any invalid data set and saves in the same HashMap format. Again, this cleaned data is passed back to the `FileHandler` class to generate the new .csv file with only valid data. All these processes are doen in the `DataCleaningRunner` class.

Data Analysis & Visualization part takes this cleaned data in the new .csv file (using `FileHandler` class)and divides it into smaller data batches based on the location aspect of each data (using `Location` class). Then, each data batch gets further processed by `ParkingTicketDataProcessor` class, creating different types HashMaps, for Data Analysis & Visualization methods (e.g. `GraphTicketsByHour`,`BarChartForViolationDescription`). These methods visually show the data analysis based on specific end users' interests. Finally, `LikelyhoodPredictor` (with the help of the WEKA API) class is used to give end users guidance/likelihood of getting a parking ticekt at the specific location at the specific time.

For more details about each class and its method functionalities, please see the Javadoc in each java file in `../src` folder.

For more details about our software design (Class functionalities, etc.), please see our [CRC Card document](https://docs.google.com/document/d/1vbhLUTb2iVLndC-xgaiJIaL0skswpUP1O-wqn1qqcRQ/edit?usp=sharing).

********

## Software Testing

JUnit tests were created to test if method functions were working as expected. Please see `LikelyhoodPredictorTest.java`, `LocationTest.java`, and `ParkingTicketsTest.java`.

*********

## Developers' Notes

As of Apr 21 2019, please keep in mind that the software development is still is progress, all java files in the repo are subject to change/removal. Major software integration (+ Code Cleanup) will be happened over next few days. You may see a few java classes with no functional methods written (e.g. `GUI` and `LikelyhoodPredictor` classes), but please note that they were created prior as templates for the future works. Furthermore, new extra classes are also highly likely to be created (as a completely new entity or splitted into more "DRY" classes) as the project progresses.