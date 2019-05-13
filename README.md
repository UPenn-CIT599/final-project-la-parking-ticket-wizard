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

Data Cleaning part is for cleaning up the raw data in the .csv file from the Kaggle. The raw data contains invalid data (e.g. issue date out of range, empty issue date/issue time), so such data needs to be removed before the data analysis. `FileHandler` class is used to collect all raw data from the .csv file and store them in the HashMap format. Then, this raw data is passed to `DataCleaner` class to remove any invalid data set and saves in another HashMap format. Again, this cleaned data is passed back to the `FileHandler` class to generate the new .csv file with only valid data. All these processes are done in the `DataCleaningRunner` class.

Data Analysis & Visualization part takes this cleaned data from the new .csv file (using `FileHandler` class) and can be used in two different cases. The first case is running the data analysis & visualization over the entire cleaned dataset to see the analysis of the entire parking tickets in the city of Los Angeles (We call it Big Data Analysis). `ParkingDataProcessor` class runs data analytics to understand ticket issueing patterns. It analyze tickets by hourly, daily and each day's hourly ticket patterns. It also analyze 10 most commonly issued parking tickets in LA and its corresponding fines. Then, using JFreeChart API, four charts are generated for visualization of these data (two bar charts and two pie charts). These charts are accessible from the GUI interface button as well. All these classes are run in the `ParkingTicketRunner` class.The other case is running the data analysis & visualization over the specific area around the end user's location. In this case, the entire cleaned data set gets divided into smaller data batches based on the location aspect of each data (using `Location` class). Then, data batch around the user's location gets further processed by `ParkingTicketDataProcessor` class and provides likelyhood of getting the ticket.

Thus, `ParkingTicketWizard` class combine `DataCleaningRunner` class and `ParkingTicketRunner` class to coordinate the workflow of the backend side of the program. And then, the workflow of the frontend side and the user interaction part are managed by the `GUI` class.

For more details about each class and its method functionalities, please see the Javadoc in each java file in `../src` folder.


********
[//]: # (Image References)

[run_config]: ./README_images/run_config.png "run_config"
[gui]: ./README_images/gui.png "gui"
[bigdataview]: ./README_images/bigdataview.png "bigdataview"

## Set Up The Envrionment Before Running The Program

In order to properly run the program, you will need to add the following three Jar files to the project build path. jcommon-1.0.23.jar, jfreechart-1.0.19.jar and jfxrt.jar.  They can be downloaded from here: https://drive.google.com/a/seas.upenn.edu/file/d/1ZWeE9bt2mHrSw51PkqIOouyXXzmMOwJL/view?usp=sharing


Due to the nature of the big data analysis (> 9 million dataset), it requires a change in the configuration parameter to avoid the `Java Heap Space Out of Memory Error`. Go to **Run** -> **Run Configurations...**. Then, from the left hand side, select `ParkingTicketWizard` and select `Arguments` tab on the center of the window. Then, in the `VM arguments:` field, fill it in like following:

![run_config][run_config]

Repeat the same steps for `GUI` class.

********

## Running The Program

Running `ParkingTicketWizard` class will run the overall backend side of the program: the data cleaning and data analysis & visualization. This will generate the image files of graphs and charts so that they can be used by the `GUI` class. The original dataset can be downloaded from Kaggle :https://www.kaggle.com/cityofLA/los-angeles-parking-citations After downloading the zip file from Kaggle, you will need to unzip it and place the `parking-citations.csv` file to the project folder which is one level above src folder. 

Running `GUI` class will show the actual window of the program for the user interaction. To run `GUI` class directly you will need to place the cleaned dataset `parking-citations_cleaned.csv` file to he project folder which is one level above src folder. The cleaned dataset file can be downloaded from here: https://drive.google.com/a/seas.upenn.edu/file/d/1Z38yOiO5r1p_pa_bzT9iTif26cNiOSWb/view?usp=sharing

![gui][gui]

`Click for Coordinates from Map!` hyperlink leads to the website where an address can be converted into coordinate values. Then, a user can input the current location data with date and time information, and click `Predict Tickets` will calculate the likelihood of getting ticket based on user's inputs.

`Big Data View` button will lead to the new window where different types of graphs and charts, analyzed over the entire parking tickets in LA. 

![bigdataview][bigdataview]

********

## Software Testing

JUnit tests were created to test if method functions were working as expected. Please see `GUITest.java`, `HeatMapTest.java`, `LikelyhoodPredictorTest.java`, `LocationTest.java`, `ParkingTicketDataProcessorTest.java`, and `ParkingTicketsTest.java`.

*********

## Developers' Notes

When we combine all of data analysis and visualization methods into `ParkingTicketRunner` class during the last week of the project, we ran into the situation that it randomly threw different kinds of exceptions during the PieChart generation step using JFreeChart API. This was very hard for us to debug this because sometimes it worked fine and other times it threw exceptions, also different exceptions thrown at different times and all StackTrace message was pointing to classes inside the JFreeChart not in our codes. Based on our research, we found this was related to synchronization related issue as JFreeChart is not really thread safe. Also, this problem got aggregated (or mainly caused) since we deal with large dataset with various collections including many ArrayList, HashMap etc.. JFreeChart also runs into a problem with generating GUI with large dataset according to our web research.  

We have spent a quite a lot of time to figure out this issue with various different kinds of experiments. During the process, we also realized even if Java showed exceptions but the chart and results were still correctly generated, especially, if we can contain the exception to a ConcurrentModificationException. I spoke to Arvind about this and he told me this is a subject we will run in 594 for about a month long.

At the end, we used "synchronized" keyword for methods in `ParkingTicketDataProcessor` class which generate the data for pie charts and added the time delay between each chart generation step to mitigate the problem. I also divide chart generation methods into independent classes. After finding the recipe, we managed ro tun the program successfully (tried numerous times both on Mac and PC machines) and generated correct results for all cases. Since we have to give a sleep time between each chart generation, it took about 20mins to generate charts but we beleive this is Okay because the big data chart generation is one time thing. Charts are saved and available to an user at GUI interface as this is static data based on historical LA parking ticket violation data. 

