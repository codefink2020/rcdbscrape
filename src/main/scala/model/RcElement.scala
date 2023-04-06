package model

case class RcElement(
                    id: String,
                    name: String,
                    description: String,
                    inversions: String,
                    rollercoasters: Option[List[String]]


                    )
