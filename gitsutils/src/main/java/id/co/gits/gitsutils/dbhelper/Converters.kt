package id.co.gits.gitsutils.dbhelper

import id.co.gits.gitsutils.extensions.fromJson
import id.co.gits.gitsutils.extensions.toJson
import java.util.*


/**
 * Created by mochadwi on 8/1/18.
 */
//open class Converters {
//
//    @TypeConverter
//    fun fromCourse(course: Course?): String? {
//        if (course == null) {
//            return null
//        }
//
//        return course.toJson()
//    }
//
//    @TypeConverter
//    fun toCourse(json: String?): Course? {
//        if (json == null) {
//            return null
//        }
//
//        return json.fromJson()
//    }
//
//    @TypeConverter
//    fun fromContent(content: Content?): String? {
//        if (content == null) {
//            return null
//        }
//
//        return content.toJson()
//    }
//
//    @TypeConverter
//    fun toContent(json: String?): Content? {
//        if (json == null) {
//            return null
//        }
//
//        return json.fromJson()
//    }
//
//    @TypeConverter
//    fun fromListAnswers(listAnswers: List<Quiz.Answers>): String? {
//        return listAnswers.toJson()
//    }
//
//    @TypeConverter
//    fun toListAnswers(json: String?): List<Quiz.Answers> {
//        if (json == null) {
//            return Collections.emptyList()
//        }
//
//        return json.fromJson()
//    }
//}