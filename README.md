# JIconExtract

# Introduction

JIconExtract is Java an library for getting bigger icons from files.
JIconExtract is Java an library for extracting bigger icons from files.

It is an good alternative for Shellfolder which only provides 16x16 and 32x32 sized icons.

# Example:

BufferedImage image = JIconExtractor.getJIconExtractor().extractIconFromFile("*.txt",IconSize.JUMBO);

# Supported Sizes
SMALL: 16x16
LARGE: 32x32
EXTRALARGE: 48x48
JUMBO: 256x256

# Requirements:
JNA 4.5.2
JNA-Platform 4.5.2

# Downloads:
<a href="https://github.com/MrMarnic/JIconExtract/releases/download/1.0/JIconExtract.jar/">JIconExtract.jar</a>



