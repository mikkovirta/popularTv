package com.example.poptvapp

class MainFeed(val data: List<Program>)

class Program(
    val id: String?,
    val image: ProgramImage?,
    val title: ProgramTitle?,
    val description: ProgramDescription?,
    val partOfSeries: SeriesDescription?,
    val contentRating: ContentRatingTitle?,
    val transmissionTitle: TransmissionTitleFi?
)

class ProgramImage(val id: String?)

class ProgramTitle(val fi: String?)

class ProgramDescription(val fi: String?)

class SeriesDescription(val description: SeriesDescriptionFi?)

class SeriesDescriptionFi(val fi: String?)

class ContentRatingTitle(val title: ContentRatingTitleFi)

class ContentRatingTitleFi(val fi: String?)

class TransmissionTitleFi(val fi: String?)