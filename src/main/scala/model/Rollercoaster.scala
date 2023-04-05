package model

case class Rollercoaster(
                        name: String,
                        park: String,
                        city: String,
                        province: String,
                        country: String,
                        status: String,
                        opening: Option[String],
                        closing: Option[String]
                        )
