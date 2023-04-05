package model

case class Rollercoaster(
                        name: String,
                        park: String,
                        city: String,
                        province: String,
                        country: String,
                        status: String,
                        opening: Option[String],
                        closing: Option[String],
                        classification: Option[String],
                        rctype: Option[String],
                        scale: Option[String],
                        design: Option[String],
                        trackLayout: Option[String],
                        category: Option[String],
                        restraints: Option[String],
                        )
