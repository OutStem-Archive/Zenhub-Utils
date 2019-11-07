# Zenhub-Utils
A repository to help automate some tasks related to Zenhub.

[![Build Status](https://travis-ci.com/AES-Outreach/Zenhub-Utils.svg?branch=master)](https://travis-ci.com/AES-Outreach/Zenhub-Utils)

# Purpose

At Outreach we needed a way to be able to move all the issues from one column to another after a prod release was done. We were able to do this by leveraging the GitHub and ZenHub API's. This CLI tool allows users to automate there release process by attaching this utility as part of their release process and letting it manage their pipelines.

# How to use

Simply run the Jar file like such

```
java -jar close-tickets-1.0-SNAPSHOT.jar properties_file=./properties.properties
```

This will locate all issues within a given column and then close them in order to help automate the PRODuction release process.

The properties file is located in the resources folder, edit it and add your necessary tokens in order to be able to use the application correctly.

Here are links that tell you how to setup your authentication tokens for the app.

* Github Authentication: `https://developer.github.com/v3/auth/#basic-authentication`
* Zenhub Authentication: `https://github.com/ZenHubIO/API#authentication`

# How to build

Once you have installed Java (OpenJDK 11 or higher) and Maven (3.6.1) then you can build the project.

Navigate in the close-ticket folder and execute `mvn clean install` in order to build the application.

# Author

- [Patrique Legault](https://github.com/pat-lego)