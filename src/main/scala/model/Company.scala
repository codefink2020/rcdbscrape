package model

case class Company(
                  name: String,
                  address: String,
                  city: String,
                  province: String,
                  country: String,
                  owner: String,
                  status: String,
                  opened: String,
                  closed: Option[String]
                  )
