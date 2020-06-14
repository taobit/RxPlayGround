package com.pluralsight.rxjava2.utility.events

class NewCommentPostedEvent(private val authorEmail: String, private val commentorEmail: String, private val commentText: String) : EventBase() {

    override fun toString(): String {
        return "NewCommentPostedEvent{" +
                "authorEmail='" + authorEmail + '\'' +
                ", commenterEmail='" + commentorEmail + '\'' +
                ", commentText='" + commentText + '\'' +
                "} " + super.toString()
    }

}